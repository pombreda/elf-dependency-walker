/**
 * $Id: mxSwimlaneManager.java,v 1.7 2010-12-10 18:03:23 gaudenz Exp $
 * Copyright (c) 2007, Gaudenz Alder
 */
package com.mxgraph.view;

import java.util.Map;

import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.util.mxUtils;

/**
 * Manager for swimlanes and nested swimlanes that sets the size of newly added
 * swimlanes to that of their siblings, and propagates changes to the size of a
 * swimlane to its siblings, if siblings is true, and its ancestors, if
 * bubbling is true.
 */
public class mxSwimlaneManager extends mxEventSource
{

	/**
	 * Defines the type of the source or target terminal. The type is a string
	 * passed to mxCell.is to check if the rule applies to a cell.
	 */
	protected mxGraph graph;

	/**
	 * Optional string that specifies the value of the attribute to be passed
	 * to mxCell.is to check if the rule applies to a cell.
	 */
	protected boolean enabled;

	/**
	 * Optional string that specifies the attributename to be passed to
	 * mxCell.is to check if the rule applies to a cell.
	 */
	protected boolean horizontal;

	/**
	 * Optional string that specifies the attributename to be passed to
	 * mxCell.is to check if the rule applies to a cell.
	 */
	protected boolean siblings;

	/**
	 * Optional string that specifies the attributename to be passed to
	 * mxCell.is to check if the rule applies to a cell.
	 */
	protected boolean bubbling;

	/**
	 * 
	 */
	protected mxIEventListener addHandler = new mxIEventListener()
	{
		public void invoke(Object source, mxEventObject evt)
		{
			if (isEnabled())
			{
				cellsAdded((Object[]) evt.getProperty("cells"));
			}
		}
	};

	/**
	 * 
	 */
	protected mxIEventListener resizeHandler = new mxIEventListener()
	{
		public void invoke(Object source, mxEventObject evt)
		{
			if (isEnabled())
			{
				cellsResized((Object[]) evt.getProperty("cells"));
			}
		}
	};

	/**
	 * 
	 */
	public mxSwimlaneManager(mxGraph graph)
	{
		setGraph(graph);
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled()
	{
		return enabled;
	}

	/**
	 * @param value the enabled to set
	 */
	public void setEnabled(boolean value)
	{
		enabled = value;
	}

	/**
	 * @return the bubbling
	 */
	public boolean isHorizontal()
	{
		return horizontal;
	}

	/**
	 * @param value the bubbling to set
	 */
	public void setHorizontal(boolean value)
	{
		horizontal = value;
	}

	/**
	 * @return the bubbling
	 */
	public boolean isSiblings()
	{
		return siblings;
	}

	/**
	 * @param value the bubbling to set
	 */
	public void setSiblings(boolean value)
	{
		siblings = value;
	}

	/**
	 * @return the bubbling
	 */
	public boolean isBubbling()
	{
		return bubbling;
	}

	/**
	 * @param value the bubbling to set
	 */
	public void setBubbling(boolean value)
	{
		bubbling = value;
	}

	/**
	 * @return the graph
	 */
	public mxGraph getGraph()
	{
		return graph;
	}

	/**
	 * @param graph the graph to set
	 */
	public void setGraph(mxGraph graph)
	{
		if (this.graph != null)
		{
			this.graph.removeListener(addHandler);
			this.graph.removeListener(resizeHandler);
		}

		this.graph = graph;

		if (this.graph != null)
		{
			this.graph.addListener(mxEvent.ADD_CELLS, addHandler);
			this.graph.addListener(mxEvent.CELLS_RESIZED, resizeHandler);
		}
	}

	/**
	 *  Returns true if the given swimlane should be ignored.
	 */
	protected boolean isSwimlaneIgnored(Object swimlane)
	{
		return !getGraph().isSwimlane(swimlane);
	}

	/**
	 * Returns true if the given cell has siblings which need to be resized after
	 * a resize. This implementation returns true.
	 */
	protected boolean hasSiblings(Object cell)
	{
		return true;
	}
	
	/**
	 * Returns true if the given cell is horizontal. If the given cell is not a
	 * swimlane, then the <horizontal> value is returned.
	 */
	protected boolean isCellHorizontal(Object cell)
	{
		if (graph.isSwimlane(cell))
		{
			mxCellState state = graph.getView().getState(cell);
			Map<String, Object> style = (state != null) ? state.getStyle() : graph.getCellStyle(cell);
			
			return mxUtils.isTrue(style, mxConstants.STYLE_HORIZONTAL, true);
		}
		
		return !isHorizontal();
	}

	/**
	 * Called if any cells have been added. Calls swimlaneAdded for all swimlanes
	 * where isSwimlaneIgnored returns false.
	 */
	protected void cellsAdded(Object[] cells)
	{
		if (cells != null)
		{
			mxIGraphModel model = getGraph().getModel();

			model.beginUpdate();
			try
			{
				for (int i = 0; i < cells.length; i++)
				{
					if (!isSwimlaneIgnored(cells[i]))
					{
						swimlaneAdded(cells[i]);
					}
				}
			}
			finally
			{
				model.endUpdate();
			}
		}
	}

	/**
	 * Called for each swimlane which has been added. This finds a reference
	 * sibling swimlane and applies its size to the newly added swimlane. If no
	 * sibling can be found then the parent swimlane is resized so that the
	 * new swimlane fits into the parent swimlane.
	 */
	protected void swimlaneAdded(Object swimlane)
	{
		if (hasSiblings(swimlane))
		{
			mxIGraphModel model = getGraph().getModel();
			Object parent = model.getParent(swimlane);
			boolean horizontal = isCellHorizontal(parent);
			int childCount = model.getChildCount(parent);
			mxGeometry geo = null;
			
			for (int i = 0; i < childCount; i++)
			{
				Object child = model.getChildAt(parent, i);

				if (child != swimlane && !isSwimlaneIgnored(child))
				{
					geo = model.getGeometry(child);
					break;
				}
			}

			// Applies dimension to new child or resizes parent for first child
			if (geo != null)
			{
				double value = (horizontal) ? geo.getWidth() : geo.getHeight();
				resizeSwimlane(swimlane, value, horizontal);
			}
			else if (geo == null && isBubbling() && !isSwimlaneIgnored(parent))
			{
				geo = model.getGeometry(swimlane);
				double value = (!horizontal) ? geo.getWidth() : geo.getHeight();
				resizeParent(parent, value, !horizontal);
				swimlaneResized(parent);
			}
		}
	}

	/**
	 * Called if any cells have been resizes. Calls swimlaneResized for all
	 * swimlanes where isSwimlaneIgnored returns false.
	 */
	protected void cellsResized(Object[] cells)
	{
		if (cells != null)
		{
			mxIGraphModel model = getGraph().getModel();

			model.beginUpdate();
			try
			{
				for (int i = 0; i < cells.length; i++)
				{
					if (!isSwimlaneIgnored(cells[i]))
					{
						swimlaneResized(cells[i]);
					}
				}
			}
			finally
			{
				model.endUpdate();
			}
		}
	}

	/**
	 * Called from cellsResized for all swimlanes that are not ignored to update
	 * the size of the siblings and the size of the parent swimlanes, recursively,
	 * if bubbling is true.
	 */
	protected void swimlaneResized(Object swimlane)
	{
		mxIGraphModel model = getGraph().getModel();
		mxGeometry geo = model.getGeometry(swimlane);

		if (geo != null)
		{
			Object parent = model.getParent(swimlane);
			boolean horizontal = !isCellHorizontal(parent);
			double value = (horizontal) ? geo.getWidth() : geo.getHeight();

			model.beginUpdate();
			try
			{
				if (isSiblings() && hasSiblings(swimlane))
				{
					int childCount = model.getChildCount(parent);

					for (int i = 0; i < childCount; i++)
					{
						Object child = model.getChildAt(parent, i);

						if (child != swimlane && !isSwimlaneIgnored(child))
						{
							resizeSwimlane(child, value, horizontal);
						}
					}
				}

				if (isBubbling() && !isSwimlaneIgnored(parent))
				{
					resizeParent(parent, value, horizontal);
					swimlaneResized(parent);
				}
			}
			finally
			{
				model.endUpdate();
			}
		}
	}

	/**
	 * Sets the width or height of the given swimlane to the given value depending
	 * on <horizontal>. If <horizontal> is true, then the width is set, otherwise,
	 * the height is set.
	 */
	protected void resizeSwimlane(Object swimlane, double value, boolean horizontal)
	{
		mxIGraphModel model = getGraph().getModel();
		mxGeometry geo = model.getGeometry(swimlane);

		if (geo != null)
		{
			geo = (mxGeometry) geo.clone();

			if (horizontal)
			{
				geo.setWidth(value);
			}
			else
			{
				geo.setHeight(value);
			}

			model.setGeometry(swimlane, geo);
		}
	}

	/**
	 * Resizes the given parent taking into account the startSize for swimlanes. If
	 * horizontal, then the value will be used to compute the width, otherwise the
	 * value will used to compute the height of the given swimlane.
	 */
	protected void resizeParent(Object parent, double value, boolean horizontal)
	{
		mxIGraphModel model = getGraph().getModel();
		mxGeometry geo = model.getGeometry(parent);

		if (geo != null)
		{
			geo = (mxGeometry) geo.clone();
			mxRectangle size = graph.getStartSize(parent);

			if (horizontal)
			{
				geo.setWidth(value + size.getWidth());
			}
			else
			{
				geo.setHeight(value + size.getHeight());
			}

			model.setGeometry(parent, geo);
		}
	}

	/**
	 * 
	 */
	public void destroy()
	{
		setGraph(null);
	}

}
