package cn.edu.ustc.biofilm.BioPano.editor;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.UIManager;

import com.mxgraph.analysis.StructuralException;
import com.mxgraph.analysis.mxGraphProperties.GraphType;
import com.mxgraph.analysis.mxAnalysisGraph;
import com.mxgraph.analysis.mxGraphProperties;
import com.mxgraph.analysis.mxGraphStructure;
import com.mxgraph.analysis.mxTraversal;
import com.mxgraph.costfunction.mxCostFunction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.AlignCellsAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.AutosizeAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.BackgroundAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.BackgroundImageAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.ColorAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.ExitAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.GridColorAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.GridStyleAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.HistoryAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.ImportAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.KeyValueAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.NewAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.OpenAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.PageBackgroundAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.PageSetupAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.PrintAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.PromptPropertyAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.PromptValueAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.SaveAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.ScaleAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.SelectShortestPathAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.SelectSpanningTreeAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.SetLabelPositionAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.SetStyleAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.StyleAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.StylesheetAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.ToggleAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.ToggleConnectModeAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.ToggleCreateTargetItem;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.ToggleDirtyAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.ToggleGridItem;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.ToggleOutlineItem;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.TogglePropertyItem;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.ToggleRulersItem;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.WarningAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.ZoomPolicyAction;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxGraphActions;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxResources;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxGraphView;

public class EditorMenuBar extends JMenuBar
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4060203894740766714L;

	public enum AnalyzeType
	{
		IS_CONNECTED, IS_SIMPLE, IS_CYCLIC_DIRECTED, IS_CYCLIC_UNDIRECTED, COMPLEMENTARY, REGULARITY, COMPONENTS, MAKE_CONNECTED, MAKE_SIMPLE, IS_TREE, ONE_SPANNING_TREE, IS_DIRECTED, GET_CUT_VERTEXES, GET_CUT_EDGES, GET_SOURCES, GET_SINKS, PLANARITY, IS_BICONNECTED, GET_BICONNECTED, SPANNING_TREE, FLOYD_ROY_WARSHALL
	}

	public EditorMenuBar(final BasicGraphEditor editor)
	{
		final mxGraphComponent graphComponent = editor.getGraphComponent();
		final mxGraph graph = graphComponent.getGraph();
		mxAnalysisGraph aGraph = new mxAnalysisGraph();

		JMenu menu = null;
		JMenu submenu = null;

		// Creates the file menu
		menu = add(new JMenu("File"));

		menu.add(editor.bind("new", new NewAction(), "/cn/edu/ustc/biofilm/BioPano/images/new.gif"));
		menu.add(editor.bind("openFile", new OpenAction(), "/cn/edu/ustc/biofilm/BioPano/images/open.gif"));
		menu.add(editor.bind("importStencil", new ImportAction(), "/cn/edu/ustc/biofilm/BioPano/images/open.gif"));

		menu.addSeparator();

		menu.add(editor.bind("save", new SaveAction(false), "/cn/edu/ustc/biofilm/BioPano/images/save.gif"));
		menu.add(editor.bind("saveAs", new SaveAction(true), "/cn/edu/ustc/biofilm/BioPano/images/saveas.gif"));

		menu.addSeparator();

		menu.add(editor.bind("pageSetup", new PageSetupAction(), "/cn/edu/ustc/biofilm/BioPano/images/pagesetup.gif"));
		menu.add(editor.bind("print", new PrintAction(), "/cn/edu/ustc/biofilm/BioPano/images/print.gif"));

		menu.addSeparator();

		menu.add(editor.bind("exit", new ExitAction()));

		// Creates the edit menu
		menu = add(new JMenu("edit"));

		menu.add(editor.bind("undo", new HistoryAction(true), "/cn/edu/ustc/biofilm/BioPano/images/undo.gif"));
		menu.add(editor.bind("redo", new HistoryAction(false), "/cn/edu/ustc/biofilm/BioPano/images/redo.gif"));

		menu.addSeparator();

		menu.add(editor.bind("cut", TransferHandler.getCutAction(), "/cn/edu/ustc/biofilm/BioPano/images/cut.gif"));
		menu.add(editor.bind("copy", TransferHandler.getCopyAction(), "/cn/edu/ustc/biofilm/BioPano/images/copy.gif"));
		menu.add(editor.bind("paste", TransferHandler.getPasteAction(), "/cn/edu/ustc/biofilm/BioPano/images/paste.gif"));

		menu.addSeparator();

		menu.add(editor.bind("delete", mxGraphActions.getDeleteAction(), "/cn/edu/ustc/biofilm/BioPano/images/delete.gif"));

		menu.addSeparator();

		menu.add(editor.bind("selectAll", mxGraphActions.getSelectAllAction()));
		menu.add(editor.bind("selectNone", mxGraphActions.getSelectNoneAction()));

		menu.addSeparator();

		menu.add(editor.bind("warning", new WarningAction()));
		menu.add(editor.bind("edit", mxGraphActions.getEditAction()));

		// Creates the view menu
		menu = add(new JMenu("view"));

		JMenuItem item = menu.add(new TogglePropertyItem(graphComponent, "pageLayout", "PageVisible", true,
				new ActionListener()
				{
					/**
					 * 
					 */
					public void actionPerformed(ActionEvent e)
					{
						if (graphComponent.isPageVisible() && graphComponent.isCenterPage())
						{
							graphComponent.zoomAndCenter();
						}
						else
						{
							graphComponent.getGraphControl().updatePreferredSize();
						}
					}
				}));

		item.addActionListener(new ActionListener()
		{
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(ActionEvent e)
			{
				if (e.getSource() instanceof TogglePropertyItem)
				{
					final mxGraphComponent graphComponent = editor.getGraphComponent();
					TogglePropertyItem toggleItem = (TogglePropertyItem) e.getSource();

					if (toggleItem.isSelected())
					{
						// Scrolls the view to the center
						SwingUtilities.invokeLater(new Runnable()
						{
							/*
							 * (non-Javadoc)
							 * @see java.lang.Runnable#run()
							 */
							public void run()
							{
								graphComponent.scrollToCenter(true);
								graphComponent.scrollToCenter(false);
							}
						});
					}
					else
					{
						// Resets the translation of the view
						mxPoint tr = graphComponent.getGraph().getView().getTranslate();

						if (tr.getX() != 0 || tr.getY() != 0)
						{
							graphComponent.getGraph().getView().setTranslate(new mxPoint());
						}
					}
				}
			}
		});

		menu.add(new TogglePropertyItem(graphComponent, "antialias", "AntiAlias", true));

		menu.addSeparator();

		menu.add(new ToggleGridItem(editor, "grid"));
		menu.add(new ToggleRulersItem(editor, "rulers"));

		menu.addSeparator();

		submenu = (JMenu) menu.add(new JMenu("zoom"));

		submenu.add(editor.bind("400%", new ScaleAction(4)));
		submenu.add(editor.bind("200%", new ScaleAction(2)));
		submenu.add(editor.bind("150%", new ScaleAction(1.5)));
		submenu.add(editor.bind("100%", new ScaleAction(1)));
		submenu.add(editor.bind("75%", new ScaleAction(0.75)));
		submenu.add(editor.bind("50%", new ScaleAction(0.5)));

		submenu.addSeparator();

		submenu.add(editor.bind("custom", new ScaleAction(0)));

		menu.addSeparator();

		menu.add(editor.bind("zoomIn", mxGraphActions.getZoomInAction()));
		menu.add(editor.bind("zoomOut", mxGraphActions.getZoomOutAction()));

		menu.addSeparator();

		menu.add(editor.bind("page", new ZoomPolicyAction(mxGraphComponent.ZOOM_POLICY_PAGE)));
		menu.add(editor.bind("width", new ZoomPolicyAction(mxGraphComponent.ZOOM_POLICY_WIDTH)));

		menu.addSeparator();

		menu.add(editor.bind("actualSize", mxGraphActions.getZoomActualAction()));

		// Creates the format menu
		menu = add(new JMenu("format"));

		populateFormatMenu(menu, editor);

		// Creates the shape menu
		menu = add(new JMenu("shape"));

		populateShapeMenu(menu, editor);

		// Creates the diagram menu
		menu = add(new JMenu("diagram"));

		menu.add(new ToggleOutlineItem(editor, "outline"));

		menu.addSeparator();

		submenu = (JMenu) menu.add(new JMenu("background"));

		submenu.add(editor.bind("backgroundColor", new BackgroundAction()));
		submenu.add(editor.bind("backgroundImage", new BackgroundImageAction()));

		submenu.addSeparator();

		submenu.add(editor.bind("pageBackground", new PageBackgroundAction()));

		submenu = (JMenu) menu.add(new JMenu("grid"));

		submenu.add(editor.bind("gridSize", new PromptPropertyAction(graph, "Grid Size", "GridSize")));
		submenu.add(editor.bind("gridColor", new GridColorAction()));

		submenu.addSeparator();

		submenu.add(editor.bind("dashed", new GridStyleAction(mxGraphComponent.GRID_STYLE_DASHED)));
		submenu.add(editor.bind("dot", new GridStyleAction(mxGraphComponent.GRID_STYLE_DOT)));
		submenu.add(editor.bind("line", new GridStyleAction(mxGraphComponent.GRID_STYLE_LINE)));
		submenu.add(editor.bind("cross", new GridStyleAction(mxGraphComponent.GRID_STYLE_CROSS)));

		menu.addSeparator();

		submenu = (JMenu) menu.add(new JMenu("layout"));

		submenu.add(editor.graphLayout("verticalHierarchical", true));
		submenu.add(editor.graphLayout("horizontalHierarchical", true));

		submenu.addSeparator();

		submenu.add(editor.graphLayout("verticalPartition", false));
		submenu.add(editor.graphLayout("horizontalPartition", false));

		submenu.addSeparator();

		submenu.add(editor.graphLayout("verticalStack", false));
		submenu.add(editor.graphLayout("horizontalStack", false));

		submenu.addSeparator();

		submenu.add(editor.graphLayout("verticalTree", true));
		submenu.add(editor.graphLayout("horizontalTree", true));

		submenu.addSeparator();

		submenu.add(editor.graphLayout("placeEdgeLabels", false));
		submenu.add(editor.graphLayout("parallelEdges", false));

		submenu.addSeparator();

		submenu.add(editor.graphLayout("organicLayout", true));
		submenu.add(editor.graphLayout("circleLayout", true));

		submenu = (JMenu) menu.add(new JMenu("selection"));

		submenu.add(editor.bind("selectPath", new SelectShortestPathAction(false)));
		submenu.add(editor.bind("selectDirectedPath", new SelectShortestPathAction(true)));

		submenu.addSeparator();

		submenu.add(editor.bind("selectTree", new SelectSpanningTreeAction(false)));
		submenu.add(editor.bind("selectDirectedTree", new SelectSpanningTreeAction(true)));

		menu.addSeparator();

		submenu = (JMenu) menu.add(new JMenu("stylesheet"));

		submenu.add(editor.bind("basicStyle",
				new StylesheetAction("/cn/edu/ustc/biofilm/BioPano/resources/basic-style.xml")));
		submenu.add(editor.bind("defaultStyle", new StylesheetAction(
				"/cn/edu/ustc/biofilm/BioPano/resources/default-style.xml")));

		// Creates the options menu
		menu = add(new JMenu("options"));

		submenu = (JMenu) menu.add(new JMenu("display"));
		submenu.add(new TogglePropertyItem(graphComponent, "buffering", "TripleBuffered", true));

		submenu.add(new TogglePropertyItem(graphComponent, "preferPageSize", "PreferPageSize", true, new ActionListener()
		{
			/**
			 * 
			 */
			public void actionPerformed(ActionEvent e)
			{
				graphComponent.zoomAndCenter();
			}
		}));

		// TODO: This feature is not yet implemented
		//submenu.add(new TogglePropertyItem(graphComponent, mxResources
		//		.get("pageBreaks", "PageBreaksVisible", true));

		submenu.addSeparator();

		submenu.add(editor.bind("tolerance", new PromptPropertyAction(graphComponent, "Tolerance")));

		submenu.add(editor.bind("dirty", new ToggleDirtyAction()));

		submenu = (JMenu) menu.add(new JMenu("zoom"));

		submenu.add(new TogglePropertyItem(graphComponent, "centerZoom", "CenterZoom", true));
		submenu.add(new TogglePropertyItem(graphComponent, "zoomToSelection", "KeepSelectionVisibleOnZoom", true));

		submenu.addSeparator();

		submenu.add(new TogglePropertyItem(graphComponent, "centerPage", "CenterPage", true, new ActionListener()
		{
			/**
			 * 
			 */
			public void actionPerformed(ActionEvent e)
			{
				if (graphComponent.isPageVisible() && graphComponent.isCenterPage())
				{
					graphComponent.zoomAndCenter();
				}
			}
		}));

		menu.addSeparator();

		submenu = (JMenu) menu.add(new JMenu("dragAndDrop"));

		submenu.add(new TogglePropertyItem(graphComponent, "dragEnabled", "DragEnabled"));
		submenu.add(new TogglePropertyItem(graph, "dropEnabled", "DropEnabled"));

		submenu.addSeparator();

		submenu.add(new TogglePropertyItem(graphComponent.getGraphHandler(), "imagePreview", "ImagePreview"));

		submenu = (JMenu) menu.add(new JMenu("labels"));

		submenu.add(new TogglePropertyItem(graph, "htmlLabels", "HtmlLabels", true));
		submenu.add(new TogglePropertyItem(graph, "showLabels", "LabelsVisible", true));

		submenu.addSeparator();

		submenu.add(new TogglePropertyItem(graph, "moveEdgeLabels", "EdgeLabelsMovable"));
		submenu.add(new TogglePropertyItem(graph, "moveVertexLabels", "VertexLabelsMovable"));

		submenu.addSeparator();

		submenu.add(new TogglePropertyItem(graphComponent, "handleReturn", "EnterStopsCellEditing"));

		menu.addSeparator();

		submenu = (JMenu) menu.add(new JMenu("connections"));

		submenu.add(new TogglePropertyItem(graphComponent, "connectable", "Connectable"));
		submenu.add(new TogglePropertyItem(graph, "connectableEdges", "ConnectableEdges"));

		submenu.addSeparator();

		submenu.add(new ToggleCreateTargetItem(editor, "createTarget"));
		submenu.add(new TogglePropertyItem(graph, "disconnectOnMove", "DisconnectOnMove"));

		submenu.addSeparator();

		submenu.add(editor.bind("connectMode", new ToggleConnectModeAction()));

		submenu = (JMenu) menu.add(new JMenu("validation"));

		submenu.add(new TogglePropertyItem(graph, "allowDanglingEdges", "AllowDanglingEdges"));
		submenu.add(new TogglePropertyItem(graph, "cloneInvalidEdges", "CloneInvalidEdges"));

		submenu.addSeparator();

		submenu.add(new TogglePropertyItem(graph, "allowLoops", "AllowLoops"));
		submenu.add(new TogglePropertyItem(graph, "multigraph", "Multigraph"));

		// Creates the window menu
		menu = add(new JMenu("window"));

		UIManager.LookAndFeelInfo[] lafs = UIManager.getInstalledLookAndFeels();

		for (int i = 0; i < lafs.length; i++)
		{
			final String clazz = lafs[i].getClassName();
			
			menu.add(new AbstractAction(lafs[i].getName())
			{
				/**
				 * 
				 */
				private static final long serialVersionUID = 7588919504149148501L;

				public void actionPerformed(ActionEvent e)
				{
					editor.setLookAndFeel(clazz);
				}
			});
		}

		// Creates a developer menu
		menu = add(new JMenu("Generate"));
		menu.add(editor.bind("Null Graph", new InsertGraph(GraphType.NULL, aGraph)));
		menu.add(editor.bind("Complete Graph", new InsertGraph(GraphType.COMPLETE, aGraph)));
		menu.add(editor.bind("Grid", new InsertGraph(GraphType.GRID, aGraph)));
		menu.add(editor.bind("Bipartite", new InsertGraph(GraphType.BIPARTITE, aGraph)));
		menu.add(editor.bind("Complete Bipartite", new InsertGraph(GraphType.COMPLETE_BIPARTITE, aGraph)));
		menu.add(editor.bind("Knight's Graph", new InsertGraph(GraphType.KNIGHT, aGraph)));
		menu.add(editor.bind("King's Graph", new InsertGraph(GraphType.KING, aGraph)));
		menu.add(editor.bind("Petersen", new InsertGraph(GraphType.PETERSEN, aGraph)));
		menu.add(editor.bind("Path", new InsertGraph(GraphType.PATH, aGraph)));
		menu.add(editor.bind("Star", new InsertGraph(GraphType.STAR, aGraph)));
		menu.add(editor.bind("Wheel", new InsertGraph(GraphType.WHEEL, aGraph)));
		menu.add(editor.bind("Friendship Windmill", new InsertGraph(GraphType.FRIENDSHIP_WINDMILL, aGraph)));
		menu.add(editor.bind("Full Windmill", new InsertGraph(GraphType.FULL_WINDMILL, aGraph)));
		menu.add(editor.bind("Knight's Tour", new InsertGraph(GraphType.KNIGHT_TOUR, aGraph)));
		menu.addSeparator();
		menu.add(editor.bind("Simple Random", new InsertGraph(GraphType.SIMPLE_RANDOM, aGraph)));
		menu.add(editor.bind("Simple Random Tree", new InsertGraph(GraphType.SIMPLE_RANDOM_TREE, aGraph)));
		menu.addSeparator();
		menu.add(editor.bind("Reset Style", new InsertGraph(GraphType.RESET_STYLE, aGraph)));

		menu = add(new JMenu("Analyze"));
		menu.add(editor.bind("Is Connected", new AnalyzeGraph(AnalyzeType.IS_CONNECTED, aGraph)));
		menu.add(editor.bind("Is Simple", new AnalyzeGraph(AnalyzeType.IS_SIMPLE, aGraph)));
		menu.add(editor.bind("Is Directed Cyclic", new AnalyzeGraph(AnalyzeType.IS_CYCLIC_DIRECTED, aGraph)));
		menu.add(editor.bind("Is Undirected Cyclic", new AnalyzeGraph(AnalyzeType.IS_CYCLIC_UNDIRECTED, aGraph)));
		menu.add(editor.bind("BFS Directed", new InsertGraph(GraphType.BFS_DIR, aGraph)));
		menu.add(editor.bind("BFS Undirected", new InsertGraph(GraphType.BFS_UNDIR, aGraph)));
		menu.add(editor.bind("DFS Directed", new InsertGraph(GraphType.DFS_DIR, aGraph)));
		menu.add(editor.bind("DFS Undirected", new InsertGraph(GraphType.DFS_UNDIR, aGraph)));
		menu.add(editor.bind("Complementary", new AnalyzeGraph(AnalyzeType.COMPLEMENTARY, aGraph)));
		menu.add(editor.bind("Regularity", new AnalyzeGraph(AnalyzeType.REGULARITY, aGraph)));
		menu.add(editor.bind("Dijkstra", new InsertGraph(GraphType.DIJKSTRA, aGraph)));
		menu.add(editor.bind("Bellman-Ford", new InsertGraph(GraphType.BELLMAN_FORD, aGraph)));
		menu.add(editor.bind("Floyd-Roy-Warshall", new AnalyzeGraph(AnalyzeType.FLOYD_ROY_WARSHALL, aGraph)));
		menu.add(editor.bind("Get Components", new AnalyzeGraph(AnalyzeType.COMPONENTS, aGraph)));
		menu.add(editor.bind("Make Connected", new AnalyzeGraph(AnalyzeType.MAKE_CONNECTED, aGraph)));
		menu.add(editor.bind("Make Simple", new AnalyzeGraph(AnalyzeType.MAKE_SIMPLE, aGraph)));
		menu.add(editor.bind("Is Tree", new AnalyzeGraph(AnalyzeType.IS_TREE, aGraph)));
		menu.add(editor.bind("One Spanning Tree", new AnalyzeGraph(AnalyzeType.ONE_SPANNING_TREE, aGraph)));
		menu.add(editor.bind("Make tree directed", new InsertGraph(GraphType.MAKE_TREE_DIRECTED, aGraph)));
		menu.add(editor.bind("Is directed", new AnalyzeGraph(AnalyzeType.IS_DIRECTED, aGraph)));
		menu.add(editor.bind("Indegree", new InsertGraph(GraphType.INDEGREE, aGraph)));
		menu.add(editor.bind("Outdegree", new InsertGraph(GraphType.OUTDEGREE, aGraph)));
		menu.add(editor.bind("Is cut vertex", new InsertGraph(GraphType.IS_CUT_VERTEX, aGraph)));
		menu.add(editor.bind("Get cut vertices", new AnalyzeGraph(AnalyzeType.GET_CUT_VERTEXES, aGraph)));
		menu.add(editor.bind("Get cut edges", new AnalyzeGraph(AnalyzeType.GET_CUT_EDGES, aGraph)));
		menu.add(editor.bind("Get sources", new AnalyzeGraph(AnalyzeType.GET_SOURCES, aGraph)));
		menu.add(editor.bind("Get sinks", new AnalyzeGraph(AnalyzeType.GET_SINKS, aGraph)));
		menu.add(editor.bind("Is biconnected", new AnalyzeGraph(AnalyzeType.IS_BICONNECTED, aGraph)));

		// Creates the help menu
		menu = add(new JMenu("help"));

		item = menu.add(new JMenuItem("aboutGraphEditor"));
		item.addActionListener(new ActionListener()
		{
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(ActionEvent e)
			{
				editor.about();
			}
		});
	}

	/**
	 * Adds menu items to the given shape menu. This is factored out because
	 * the shape menu appears in the menubar and also in the popupmenu.
	 */
	public static void populateShapeMenu(JMenu menu, BasicGraphEditor editor)
	{
		menu.add(editor.bind("home", mxGraphActions.getHomeAction(), "/cn/edu/ustc/biofilm/BioPano/images/house.gif"));

		menu.addSeparator();

		menu.add(editor.bind("exitGroup", mxGraphActions.getExitGroupAction(), "/cn/edu/ustc/biofilm/BioPano/images/up.gif"));
		menu.add(editor.bind("enterGroup", mxGraphActions.getEnterGroupAction(),
				"/cn/edu/ustc/biofilm/BioPano/images/down.gif"));

		menu.addSeparator();

		menu.add(editor.bind("group", mxGraphActions.getGroupAction(), "/cn/edu/ustc/biofilm/BioPano/images/group.gif"));
		menu.add(editor.bind("ungroup", mxGraphActions.getUngroupAction(),
				"/cn/edu/ustc/biofilm/BioPano/images/ungroup.gif"));

		menu.addSeparator();

		menu.add(editor.bind("removeFromGroup", mxGraphActions.getRemoveFromParentAction()));

		menu.add(editor.bind("updateGroupBounds", mxGraphActions.getUpdateGroupBoundsAction()));

		menu.addSeparator();

		menu.add(editor.bind("collapse", mxGraphActions.getCollapseAction(),
				"/cn/edu/ustc/biofilm/BioPano/images/collapse.gif"));
		menu.add(editor.bind("expand", mxGraphActions.getExpandAction(), "/cn/edu/ustc/biofilm/BioPano/images/expand.gif"));

		menu.addSeparator();

		menu.add(editor.bind("toBack", mxGraphActions.getToBackAction(), "/cn/edu/ustc/biofilm/BioPano/images/toback.gif"));
		menu.add(editor.bind("toFront", mxGraphActions.getToFrontAction(),
				"/cn/edu/ustc/biofilm/BioPano/images/tofront.gif"));

		menu.addSeparator();

		JMenu submenu = (JMenu) menu.add(new JMenu("align"));

		submenu.add(editor.bind("left", new AlignCellsAction(mxConstants.ALIGN_LEFT),
				"/cn/edu/ustc/biofilm/BioPano/images/alignleft.gif"));
		submenu.add(editor.bind("center", new AlignCellsAction(mxConstants.ALIGN_CENTER),
				"/cn/edu/ustc/biofilm/BioPano/images/aligncenter.gif"));
		submenu.add(editor.bind("right", new AlignCellsAction(mxConstants.ALIGN_RIGHT),
				"/cn/edu/ustc/biofilm/BioPano/images/alignright.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind("top", new AlignCellsAction(mxConstants.ALIGN_TOP),
				"/cn/edu/ustc/biofilm/BioPano/images/aligntop.gif"));
		submenu.add(editor.bind("middle", new AlignCellsAction(mxConstants.ALIGN_MIDDLE),
				"/cn/edu/ustc/biofilm/BioPano/images/alignmiddle.gif"));
		submenu.add(editor.bind("bottom", new AlignCellsAction(mxConstants.ALIGN_BOTTOM),
				"/cn/edu/ustc/biofilm/BioPano/images/alignbottom.gif"));

		menu.addSeparator();

		menu.add(editor.bind("autosize", new AutosizeAction()));

	}

	/**
	 * Adds menu items to the given format menu. This is factored out because
	 * the format menu appears in the menubar and also in the popupmenu.
	 */
	public static void populateFormatMenu(JMenu menu, BasicGraphEditor editor)
	{
		JMenu submenu = (JMenu) menu.add(new JMenu("background"));

		submenu.add(editor.bind("fillcolor", new ColorAction("Fillcolor", mxConstants.STYLE_FILLCOLOR),
				"/cn/edu/ustc/biofilm/BioPano/images/fillcolor.gif"));
		submenu.add(editor.bind("gradient", new ColorAction("Gradient", mxConstants.STYLE_GRADIENTCOLOR)));

		submenu.addSeparator();

		submenu.add(editor.bind("image", new PromptValueAction(mxConstants.STYLE_IMAGE, "Image")));
		submenu.add(editor.bind("shadow", new ToggleAction(mxConstants.STYLE_SHADOW)));

		submenu.addSeparator();

		submenu.add(editor.bind("opacity", new PromptValueAction(mxConstants.STYLE_OPACITY, "Opacity (0-100)")));

		submenu = (JMenu) menu.add(new JMenu("label"));

		submenu.add(editor.bind("fontcolor", new ColorAction("Fontcolor", mxConstants.STYLE_FONTCOLOR),
				"/cn/edu/ustc/biofilm/BioPano/images/fontcolor.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind("labelFill", new ColorAction("Label Fill", mxConstants.STYLE_LABEL_BACKGROUNDCOLOR)));
		submenu.add(editor.bind("labelBorder", new ColorAction("Label Border", mxConstants.STYLE_LABEL_BORDERCOLOR)));

		submenu.addSeparator();

		submenu.add(editor.bind("rotateLabel", new ToggleAction(mxConstants.STYLE_HORIZONTAL, true)));

		submenu.add(editor.bind("textOpacity", new PromptValueAction(mxConstants.STYLE_TEXT_OPACITY, "Opacity (0-100)")));

		submenu.addSeparator();

		JMenu subsubmenu = (JMenu) submenu.add(new JMenu("position"));

		subsubmenu.add(editor.bind("top", new SetLabelPositionAction(mxConstants.ALIGN_TOP, mxConstants.ALIGN_BOTTOM)));
		subsubmenu.add(editor.bind("middle",
				new SetLabelPositionAction(mxConstants.ALIGN_MIDDLE, mxConstants.ALIGN_MIDDLE)));
		subsubmenu.add(editor.bind("bottom", new SetLabelPositionAction(mxConstants.ALIGN_BOTTOM, mxConstants.ALIGN_TOP)));

		subsubmenu.addSeparator();

		subsubmenu.add(editor.bind("left", new SetLabelPositionAction(mxConstants.ALIGN_LEFT, mxConstants.ALIGN_RIGHT)));
		subsubmenu.add(editor.bind("center",
				new SetLabelPositionAction(mxConstants.ALIGN_CENTER, mxConstants.ALIGN_CENTER)));
		subsubmenu.add(editor.bind("right", new SetLabelPositionAction(mxConstants.ALIGN_RIGHT, mxConstants.ALIGN_LEFT)));

		submenu.addSeparator();

		submenu.add(editor.bind("wordWrap", new KeyValueAction(mxConstants.STYLE_WHITE_SPACE, "wrap")));
		submenu.add(editor.bind("noWordWrap", new KeyValueAction(mxConstants.STYLE_WHITE_SPACE, null)));

		submenu.addSeparator();

		submenu.add(editor.bind("hide", new ToggleAction(mxConstants.STYLE_NOLABEL)));

		menu.addSeparator();

		submenu = (JMenu) menu.add(new JMenu("line"));

		submenu.add(editor.bind("linecolor", new ColorAction("Linecolor", mxConstants.STYLE_STROKECOLOR),
				"/cn/edu/ustc/biofilm/BioPano/images/linecolor.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind("orthogonal", new ToggleAction(mxConstants.STYLE_ORTHOGONAL)));
		submenu.add(editor.bind("dashed", new ToggleAction(mxConstants.STYLE_DASHED)));

		submenu.addSeparator();

		submenu.add(editor.bind("linewidth", new PromptValueAction(mxConstants.STYLE_STROKEWIDTH, "Linewidth")));

		submenu = (JMenu) menu.add(new JMenu("connector"));

		submenu.add(editor.bind("straight", new SetStyleAction("straight"),
				"/cn/edu/ustc/biofilm/BioPano/images/straight.gif"));

		submenu.add(editor.bind("horizontal", new SetStyleAction(""), "/cn/edu/ustc/biofilm/BioPano/images/connect.gif"));
		submenu.add(editor.bind("vertical", new SetStyleAction("vertical"),
				"/cn/edu/ustc/biofilm/BioPano/images/vertical.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind("entityRelation", new SetStyleAction("edgeStyle=mxEdgeStyle.EntityRelation"),
				"/cn/edu/ustc/biofilm/BioPano/images/entity.gif"));
		submenu.add(editor.bind("arrow", new SetStyleAction("arrow"), "/cn/edu/ustc/biofilm/BioPano/images/arrow.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind("plain", new ToggleAction(mxConstants.STYLE_NOEDGESTYLE)));

		menu.addSeparator();

		submenu = (JMenu) menu.add(new JMenu("linestart"));

		submenu.add(editor.bind("open", new KeyValueAction(mxConstants.STYLE_STARTARROW, mxConstants.ARROW_OPEN),
				"/cn/edu/ustc/biofilm/BioPano/images/open_start.gif"));
		submenu.add(editor.bind("classic", new KeyValueAction(mxConstants.STYLE_STARTARROW, mxConstants.ARROW_CLASSIC),
				"/cn/edu/ustc/biofilm/BioPano/images/classic_start.gif"));
		submenu.add(editor.bind("block", new KeyValueAction(mxConstants.STYLE_STARTARROW, mxConstants.ARROW_BLOCK),
				"/cn/edu/ustc/biofilm/BioPano/images/block_start.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind("diamond", new KeyValueAction(mxConstants.STYLE_STARTARROW, mxConstants.ARROW_DIAMOND),
				"/cn/edu/ustc/biofilm/BioPano/images/diamond_start.gif"));
		submenu.add(editor.bind("oval", new KeyValueAction(mxConstants.STYLE_STARTARROW, mxConstants.ARROW_OVAL),
				"/cn/edu/ustc/biofilm/BioPano/images/oval_start.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind("none", new KeyValueAction(mxConstants.STYLE_STARTARROW, mxConstants.NONE)));
		submenu.add(editor.bind("size", new PromptValueAction(mxConstants.STYLE_STARTSIZE, "Linestart Size")));

		submenu = (JMenu) menu.add(new JMenu("lineend"));

		submenu.add(editor.bind("open", new KeyValueAction(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_OPEN),
				"/cn/edu/ustc/biofilm/BioPano/images/open_end.gif"));
		submenu.add(editor.bind("classic", new KeyValueAction(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC),
				"/cn/edu/ustc/biofilm/BioPano/images/classic_end.gif"));
		submenu.add(editor.bind("block", new KeyValueAction(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_BLOCK),
				"/cn/edu/ustc/biofilm/BioPano/images/block_end.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind("diamond", new KeyValueAction(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_DIAMOND),
				"/cn/edu/ustc/biofilm/BioPano/images/diamond_end.gif"));
		submenu.add(editor.bind("oval", new KeyValueAction(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_OVAL),
				"/cn/edu/ustc/biofilm/BioPano/images/oval_end.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind("none", new KeyValueAction(mxConstants.STYLE_ENDARROW, mxConstants.NONE)));
		submenu.add(editor.bind("size", new PromptValueAction(mxConstants.STYLE_ENDSIZE, "Lineend Size")));

		menu.addSeparator();

		submenu = (JMenu) menu.add(new JMenu("alignment"));

		submenu.add(editor.bind("left", new KeyValueAction(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_LEFT),
				"/cn/edu/ustc/biofilm/BioPano/images/left.gif"));
		submenu.add(editor.bind("center", new KeyValueAction(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER),
				"/cn/edu/ustc/biofilm/BioPano/images/center.gif"));
		submenu.add(editor.bind("right", new KeyValueAction(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_RIGHT),
				"/cn/edu/ustc/biofilm/BioPano/images/right.gif"));

		submenu.addSeparator();

		submenu.add(editor.bind("top", new KeyValueAction(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_TOP),
				"/cn/edu/ustc/biofilm/BioPano/images/top.gif"));
		submenu.add(editor.bind("middle", new KeyValueAction(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE),
				"/cn/edu/ustc/biofilm/BioPano/images/middle.gif"));
		submenu.add(editor.bind("bottom", new KeyValueAction(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_BOTTOM),
				"/cn/edu/ustc/biofilm/BioPano/images/bottom.gif"));

		submenu = (JMenu) menu.add(new JMenu("spacing"));

		submenu.add(editor.bind("top", new PromptValueAction(mxConstants.STYLE_SPACING_TOP, "Top Spacing")));
		submenu.add(editor.bind("right", new PromptValueAction(mxConstants.STYLE_SPACING_RIGHT, "Right Spacing")));
		submenu.add(editor.bind("bottom", new PromptValueAction(mxConstants.STYLE_SPACING_BOTTOM, "Bottom Spacing")));
		submenu.add(editor.bind("left", new PromptValueAction(mxConstants.STYLE_SPACING_LEFT, "Left Spacing")));

		submenu.addSeparator();

		submenu.add(editor.bind("global", new PromptValueAction(mxConstants.STYLE_SPACING, "Spacing")));

		submenu.addSeparator();

		submenu.add(editor.bind("sourceSpacing", new PromptValueAction(mxConstants.STYLE_SOURCE_PERIMETER_SPACING,
				"sourceSpacing")));
		submenu.add(editor.bind("targetSpacing", new PromptValueAction(mxConstants.STYLE_TARGET_PERIMETER_SPACING,
				"targetSpacing")));

		submenu.addSeparator();

		submenu.add(editor.bind("perimeter", new PromptValueAction(mxConstants.STYLE_PERIMETER_SPACING,
				"Perimeter Spacing")));

		submenu = (JMenu) menu.add(new JMenu("direction"));

		submenu.add(editor.bind("north", new KeyValueAction(mxConstants.STYLE_DIRECTION, mxConstants.DIRECTION_NORTH)));
		submenu.add(editor.bind("east", new KeyValueAction(mxConstants.STYLE_DIRECTION, mxConstants.DIRECTION_EAST)));
		submenu.add(editor.bind("south", new KeyValueAction(mxConstants.STYLE_DIRECTION, mxConstants.DIRECTION_SOUTH)));
		submenu.add(editor.bind("west", new KeyValueAction(mxConstants.STYLE_DIRECTION, mxConstants.DIRECTION_WEST)));

		submenu.addSeparator();

		submenu.add(editor.bind("rotation", new PromptValueAction(mxConstants.STYLE_ROTATION, "Rotation (0-360)")));

		menu.addSeparator();

		menu.add(editor.bind("rounded", new ToggleAction(mxConstants.STYLE_ROUNDED)));

		menu.add(editor.bind("style", new StyleAction()));
	}

	/**
	 *
	 */
	public static class InsertGraph extends AbstractAction
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 4010463992665008365L;

		/**
		 * 
		 */
		protected GraphType graphType;

		protected mxAnalysisGraph aGraph;

		/**
		 * @param aGraph 
		 * 
		 */
		public InsertGraph(GraphType tree, mxAnalysisGraph aGraph)
		{
			this.graphType = tree;
			this.aGraph = aGraph;
		}

		/**
		 * 
		 */
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() instanceof mxGraphComponent)
			{
				mxGraphComponent graphComponent = (mxGraphComponent) e.getSource();
				mxGraph graph = graphComponent.getGraph();

				// dialog = new FactoryConfigDialog();
				String dialogText = "";
				if (graphType == GraphType.NULL)
					dialogText = "Configure null graph";
				else if (graphType == GraphType.COMPLETE)
					dialogText = "Configure complete graph";
				else if (graphType == GraphType.NREGULAR)
					dialogText = "Configure n-regular graph";
				else if (graphType == GraphType.GRID)
					dialogText = "Configure grid graph";
				else if (graphType == GraphType.BIPARTITE)
					dialogText = "Configure bipartite graph";
				else if (graphType == GraphType.COMPLETE_BIPARTITE)
					dialogText = "Configure complete bipartite graph";
				else if (graphType == GraphType.BFS_DIR)
					dialogText = "Configure BFS algorithm";
				else if (graphType == GraphType.BFS_UNDIR)
					dialogText = "Configure BFS algorithm";
				else if (graphType == GraphType.DFS_DIR)
					dialogText = "Configure DFS algorithm";
				else if (graphType == GraphType.DFS_UNDIR)
					dialogText = "Configure DFS algorithm";
				else if (graphType == GraphType.DIJKSTRA)
					dialogText = "Configure Dijkstra's algorithm";
				else if (graphType == GraphType.BELLMAN_FORD)
					dialogText = "Configure Bellman-Ford algorithm";
				else if (graphType == GraphType.MAKE_TREE_DIRECTED)
					dialogText = "Configure make tree directed algorithm";
				else if (graphType == GraphType.KNIGHT_TOUR)
					dialogText = "Configure knight's tour";
				else if (graphType == GraphType.GET_ADJ_MATRIX)
					dialogText = "Configure adjacency matrix";
				else if (graphType == GraphType.FROM_ADJ_MATRIX)
					dialogText = "Input adjacency matrix";
				else if (graphType == GraphType.PETERSEN)
					dialogText = "Configure Petersen graph";
				else if (graphType == GraphType.WHEEL)
					dialogText = "Configure Wheel graph";
				else if (graphType == GraphType.STAR)
					dialogText = "Configure Star graph";
				else if (graphType == GraphType.PATH)
					dialogText = "Configure Path graph";
				else if (graphType == GraphType.FRIENDSHIP_WINDMILL)
					dialogText = "Configure Friendship Windmill graph";
				else if (graphType == GraphType.INDEGREE)
					dialogText = "Configure indegree analysis";
				else if (graphType == GraphType.OUTDEGREE)
					dialogText = "Configure outdegree analysis";
				GraphConfigDialog dialog = new GraphConfigDialog(graphType, dialogText);
				dialog.configureLayout(graph, graphType, aGraph);
				dialog.setModal(true);
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				Dimension frameSize = dialog.getSize();
				dialog.setLocation(screenSize.width / 2 - (frameSize.width / 2), screenSize.height / 2 - (frameSize.height / 2));
				dialog.setVisible(true);
			}
		}
	}

	/**
	 *
	 */
	public static class AnalyzeGraph extends AbstractAction
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 6926170745240507985L;

		mxAnalysisGraph aGraph;

		/**
		 * 
		 */
		protected AnalyzeType analyzeType;

		/**
		 * Examples for calling analysis methods from mxGraphStructure 
		 */
		public AnalyzeGraph(AnalyzeType analyzeType, mxAnalysisGraph aGraph)
		{
			this.analyzeType = analyzeType;
			this.aGraph = aGraph;
		}

		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() instanceof mxGraphComponent)
			{
				mxGraphComponent graphComponent = (mxGraphComponent) e.getSource();
				mxGraph graph = graphComponent.getGraph();
				aGraph.setGraph(graph);

				if (analyzeType == AnalyzeType.IS_CONNECTED)
				{
					boolean isConnected = mxGraphStructure.isConnected(aGraph);

					if (isConnected)
					{
						System.out.println("The graph is connected");
					}
					else
					{
						System.out.println("The graph is not connected");
					}
				}
				else if (analyzeType == AnalyzeType.IS_SIMPLE)
				{
					boolean isSimple = mxGraphStructure.isSimple(aGraph);

					if (isSimple)
					{
						System.out.println("The graph is simple");
					}
					else
					{
						System.out.println("The graph is not simple");
					}
				}
				else if (analyzeType == AnalyzeType.IS_CYCLIC_DIRECTED)
				{
					boolean isCyclicDirected = mxGraphStructure.isCyclicDirected(aGraph);

					if (isCyclicDirected)
					{
						System.out.println("The graph is cyclic directed");
					}
					else
					{
						System.out.println("The graph is acyclic directed");
					}
				}
				else if (analyzeType == AnalyzeType.IS_CYCLIC_UNDIRECTED)
				{
					boolean isCyclicUndirected = mxGraphStructure.isCyclicUndirected(aGraph);

					if (isCyclicUndirected)
					{
						System.out.println("The graph is cyclic undirected");
					}
					else
					{
						System.out.println("The graph is acyclic undirected");
					}
				}
				else if (analyzeType == AnalyzeType.COMPLEMENTARY)
				{
					graph.getModel().beginUpdate();

					mxGraphStructure.complementaryGraph(aGraph);

					mxGraphStructure.setDefaultGraphStyle(aGraph, true);
					graph.getModel().endUpdate();
				}
				else if (analyzeType == AnalyzeType.REGULARITY)
				{
					try
					{
						int regularity = mxGraphStructure.regularity(aGraph);
						System.out.println("Graph regularity is: " + regularity);
					}
					catch (StructuralException e1)
					{
						System.out.println("The graph is irregular");
					}
				}
				else if (analyzeType == AnalyzeType.COMPONENTS)
				{
					Object[][] components = mxGraphStructure.getGraphComponents(aGraph);
					mxIGraphModel model = aGraph.getGraph().getModel();

					for (int i = 0; i < components.length; i++)
					{
						System.out.print("Component " + i + " :");

						for (int j = 0; j < components[i].length; j++)
						{
							System.out.print(" " + model.getValue(components[i][j]));
						}

						System.out.println(".");
					}

					System.out.println("Number of components: " + components.length);

				}
				else if (analyzeType == AnalyzeType.MAKE_CONNECTED)
				{
					graph.getModel().beginUpdate();

					if (!mxGraphStructure.isConnected(aGraph))
					{
						mxGraphStructure.makeConnected(aGraph);
						mxGraphStructure.setDefaultGraphStyle(aGraph, false);
					}

					graph.getModel().endUpdate();
				}
				else if (analyzeType == AnalyzeType.MAKE_SIMPLE)
				{
					mxGraphStructure.makeSimple(aGraph);
				}
				else if (analyzeType == AnalyzeType.IS_TREE)
				{
					boolean isTree = mxGraphStructure.isTree(aGraph);

					if (isTree)
					{
						System.out.println("The graph is a tree");
					}
					else
					{
						System.out.println("The graph is not a tree");
					}
				}
				else if (analyzeType == AnalyzeType.ONE_SPANNING_TREE)
				{
					try
					{
						graph.getModel().beginUpdate();
						aGraph.getGenerator().oneSpanningTree(aGraph, true, true);
						mxGraphStructure.setDefaultGraphStyle(aGraph, false);
						graph.getModel().endUpdate();
					}
					catch (StructuralException e1)
					{
						System.out.println("The graph must be simple and connected");
					}
				}
				else if (analyzeType == AnalyzeType.IS_DIRECTED)
				{
					boolean isDirected = mxGraphProperties.isDirected(aGraph.getProperties(), mxGraphProperties.DEFAULT_DIRECTED);

					if (isDirected)
					{
						System.out.println("The graph is directed.");
					}
					else
					{
						System.out.println("The graph is undirected.");
					}
				}
				else if (analyzeType == AnalyzeType.GET_CUT_VERTEXES)
				{
					Object[] cutVertices = mxGraphStructure.getCutVertices(aGraph);

					System.out.print("Cut vertices of the graph are: [");
					mxIGraphModel model = aGraph.getGraph().getModel();

					for (int i = 0; i < cutVertices.length; i++)
					{
						System.out.print(" " + model.getValue(cutVertices[i]));
					}

					System.out.println(" ]");
				}
				else if (analyzeType == AnalyzeType.GET_CUT_EDGES)
				{
					Object[] cutEdges = mxGraphStructure.getCutEdges(aGraph);

					System.out.print("Cut edges of the graph are: [");
					mxIGraphModel model = aGraph.getGraph().getModel();

					for (int i = 0; i < cutEdges.length; i++)
					{
						System.out.print(" " + Integer.parseInt((String) model.getValue(aGraph.getTerminal(cutEdges[i], true))) + "-"
								+ Integer.parseInt((String) model.getValue(aGraph.getTerminal(cutEdges[i], false))));
					}

					System.out.println(" ]");
				}
				else if (analyzeType == AnalyzeType.GET_SOURCES)
				{
					try
					{
						Object[] sourceVertices = mxGraphStructure.getSourceVertices(aGraph);
						System.out.print("Source vertices of the graph are: [");
						mxIGraphModel model = aGraph.getGraph().getModel();

						for (int i = 0; i < sourceVertices.length; i++)
						{
							System.out.print(" " + model.getValue(sourceVertices[i]));
						}

						System.out.println(" ]");
					}
					catch (StructuralException e1)
					{
						System.out.println(e1);
					}
				}
				else if (analyzeType == AnalyzeType.GET_SINKS)
				{
					try
					{
						Object[] sinkVertices = mxGraphStructure.getSinkVertices(aGraph);
						System.out.print("Sink vertices of the graph are: [");
						mxIGraphModel model = aGraph.getGraph().getModel();

						for (int i = 0; i < sinkVertices.length; i++)
						{
							System.out.print(" " + model.getValue(sinkVertices[i]));
						}

						System.out.println(" ]");
					}
					catch (StructuralException e1)
					{
						System.out.println(e1);
					}
				}
				else if (analyzeType == AnalyzeType.PLANARITY)
				{
					//TODO implement
				}
				else if (analyzeType == AnalyzeType.IS_BICONNECTED)
				{
					boolean isBiconnected = mxGraphStructure.isBiconnected(aGraph);

					if (isBiconnected)
					{
						System.out.println("The graph is biconnected.");
					}
					else
					{
						System.out.println("The graph is not biconnected.");
					}
				}
				else if (analyzeType == AnalyzeType.GET_BICONNECTED)
				{
					//TODO implement
				}
				else if (analyzeType == AnalyzeType.SPANNING_TREE)
				{
					//TODO implement
				}
				else if (analyzeType == AnalyzeType.FLOYD_ROY_WARSHALL)
				{
					
					ArrayList<Object[][]> FWIresult = new ArrayList<Object[][]>();
					try
					{
						//only this line is needed to get the result from Floyd-Roy-Warshall, the rest is code for displaying the result
						FWIresult = mxTraversal.floydRoyWarshall(aGraph);

						Object[][] dist = FWIresult.get(0);
						Object[][] paths = FWIresult.get(1);
						Object[] vertices = aGraph.getChildVertices(aGraph.getGraph().getDefaultParent());
						int vertexNum = vertices.length;
						System.out.println("Distances are:");

						for (int i = 0; i < vertexNum; i++)
						{
							System.out.print("[");

							for (int j = 0; j < vertexNum; j++)
							{
								System.out.print(" " + Math.round((Double) dist[i][j] * 100.0) / 100.0);
							}

							System.out.println("] ");
						}

						System.out.println("Path info:");

						mxCostFunction costFunction = aGraph.getGenerator().getCostFunction();
						mxGraphView view = aGraph.getGraph().getView();

						for (int i = 0; i < vertexNum; i++)
						{
							System.out.print("[");

							for (int j = 0; j < vertexNum; j++)
							{
								if (paths[i][j] != null)
								{
									System.out.print(" " + costFunction.getCost(view.getState(paths[i][j])));
								}
								else
								{
									System.out.print(" -");
								}
							}

							System.out.println(" ]");
						}

						try
						{
							Object[] path = mxTraversal.getWFIPath(aGraph, FWIresult, vertices[0], vertices[vertexNum - 1]);
							System.out.print("The path from " + costFunction.getCost(view.getState(vertices[0])) + " to "
									+ costFunction.getCost((view.getState(vertices[vertexNum - 1]))) + " is:");

							for (int i = 0; i < path.length; i++)
							{
								System.out.print(" " + costFunction.getCost(view.getState(path[i])));
							}

							System.out.println();
						}
						catch (StructuralException e1)
						{
							System.out.println(e1);
						}
					}
					catch (StructuralException e2)
					{
						System.out.println(e2);
					}
				}
			}
		}
	};
};