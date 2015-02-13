package cn.edu.ustc.biofilm.BioPano.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.UIManager;

import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.BackgroundAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.BackgroundImageAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.ExitAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.GridColorAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.GridStyleAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.HistoryAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.NewAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.OpenAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.PageBackgroundAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.PageSetupAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.PrintAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.PromptPropertyAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.SaveAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.ScaleAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.SelectShortestPathAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.SelectSpanningTreeAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.StylesheetAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.ToggleDirtyAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.ToggleGridItem;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.ToggleOutlineItem;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.TogglePropertyItem;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.ToggleRulersItem;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.WarningAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.ZoomPolicyAction;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxGraphActions;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxResources;
import com.mxgraph.view.mxGraph;

public class SchemaEditorMenuBar extends JMenuBar
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6776304509649205465L;

	@SuppressWarnings("serial")
	public SchemaEditorMenuBar(final BasicGraphEditor editor)
	{
		final mxGraphComponent graphComponent = editor.getGraphComponent();
		final mxGraph graph = graphComponent.getGraph();
		JMenu menu = null;
		JMenu submenu = null;

		// Creates the file menu
		menu = add(new JMenu("file"));

		menu.add(editor.bind("new", new NewAction(),
				"/cn/edu/ustc/biofilm/BioPano/images/new.gif"));
		menu.add(editor.bind("openFile", new OpenAction(),
				"/cn/edu/ustc/biofilm/BioPano/images/open.gif"));

		menu.addSeparator();

		menu.add(editor.bind("save", new SaveAction(false),
				"/cn/edu/ustc/biofilm/BioPano/images/save.gif"));
		menu.add(editor.bind("saveAs", new SaveAction(true),
				"/cn/edu/ustc/biofilm/BioPano/images/saveas.gif"));

		menu.addSeparator();

		menu.add(editor.bind("pageSetup",
				new PageSetupAction(),
				"/cn/edu/ustc/biofilm/BioPano/images/pagesetup.gif"));
		menu.add(editor.bind("print", new PrintAction(),
				"/cn/edu/ustc/biofilm/BioPano/images/print.gif"));

		menu.addSeparator();

		menu.add(editor.bind("exit", new ExitAction()));

		// Creates the edit menu
		menu = add(new JMenu("edit"));

		menu.add(editor.bind("undo", new HistoryAction(true),
				"/cn/edu/ustc/biofilm/BioPano/images/undo.gif"));
		menu.add(editor.bind("redo", new HistoryAction(false),
				"/cn/edu/ustc/biofilm/BioPano/images/redo.gif"));

		menu.addSeparator();

		menu.add(editor.bind("cut", TransferHandler
				.getCutAction(), "/cn/edu/ustc/biofilm/BioPano/images/cut.gif"));
		menu.add(editor
				.bind("copy", TransferHandler.getCopyAction(),
						"/cn/edu/ustc/biofilm/BioPano/images/copy.gif"));
		menu.add(editor.bind("paste", TransferHandler
				.getPasteAction(),
				"/cn/edu/ustc/biofilm/BioPano/images/paste.gif"));

		menu.addSeparator();

		menu.add(editor.bind("delete", mxGraphActions
				.getDeleteAction(),
				"/cn/edu/ustc/biofilm/BioPano/images/delete.gif"));

		menu.addSeparator();

		menu.add(editor.bind("selectAll", mxGraphActions
				.getSelectAllAction()));
		menu.add(editor.bind("selectNone", mxGraphActions
				.getSelectNoneAction()));

		menu.addSeparator();

		menu.add(editor.bind("warning", new WarningAction()));
		menu.add(editor.bind("edit", mxGraphActions
				.getEditAction()));

		// Creates the view menu
		menu = add(new JMenu("view"));

		JMenuItem item = menu.add(new TogglePropertyItem(graphComponent,
				"pageLayout", "PageVisible", true,
				new ActionListener()
				{
					/**
					 * 
					 */
					public void actionPerformed(ActionEvent e)
					{
						if (graphComponent.isPageVisible()
								&& graphComponent.isCenterPage())
						{
							graphComponent.zoomAndCenter();
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
					final mxGraphComponent graphComponent = editor
							.getGraphComponent();
					TogglePropertyItem toggleItem = (TogglePropertyItem) e
							.getSource();

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
						mxPoint tr = graphComponent.getGraph().getView()
								.getTranslate();

						if (tr.getX() != 0 || tr.getY() != 0)
						{
							graphComponent.getGraph().getView().setTranslate(
									new mxPoint());
						}
					}
				}
			}
		});

		menu.add(new TogglePropertyItem(graphComponent, mxResources
				.get("antialias"), "AntiAlias", true));

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

		menu.add(editor.bind("zoomIn", mxGraphActions
				.getZoomInAction()));
		menu.add(editor.bind("zoomOut", mxGraphActions
				.getZoomOutAction()));

		menu.addSeparator();

		menu.add(editor.bind("page", new ZoomPolicyAction(
				mxGraphComponent.ZOOM_POLICY_PAGE)));
		menu.add(editor.bind("width", new ZoomPolicyAction(
				mxGraphComponent.ZOOM_POLICY_WIDTH)));

		menu.addSeparator();

		menu.add(editor.bind("actualSize", mxGraphActions
				.getZoomActualAction()));

		// Creates the diagram menu
		menu = add(new JMenu("diagram"));

		menu.add(new ToggleOutlineItem(editor, "outline"));

		menu.addSeparator();

		submenu = (JMenu) menu.add(new JMenu("background"));

		submenu.add(editor.bind("backgroundColor",
				new BackgroundAction()));
		submenu.add(editor.bind("backgroundImage",
				new BackgroundImageAction()));

		submenu.addSeparator();

		submenu.add(editor.bind("pageBackground",
				new PageBackgroundAction()));

		submenu = (JMenu) menu.add(new JMenu("grid"));

		submenu.add(editor.bind("gridSize",
				new PromptPropertyAction(graph, "Grid Size", "GridSize")));
		submenu.add(editor.bind("gridColor",
				new GridColorAction()));

		submenu.addSeparator();

		submenu.add(editor.bind("dashed", new GridStyleAction(
				mxGraphComponent.GRID_STYLE_DASHED)));
		submenu.add(editor.bind("dot", new GridStyleAction(
				mxGraphComponent.GRID_STYLE_DOT)));
		submenu.add(editor.bind("line", new GridStyleAction(
				mxGraphComponent.GRID_STYLE_LINE)));
		submenu.add(editor.bind("cross", new GridStyleAction(
				mxGraphComponent.GRID_STYLE_CROSS)));

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

		submenu.add(editor.graphLayout("parallelEdges", false));

		submenu.addSeparator();

		submenu.add(editor.graphLayout("organicLayout", true));

		submenu = (JMenu) menu.add(new JMenu("selection"));

		submenu.add(editor.bind("selectPath",
				new SelectShortestPathAction(false)));
		submenu.add(editor.bind("selectDirectedPath",
				new SelectShortestPathAction(true)));

		submenu.addSeparator();

		submenu.add(editor.bind("selectTree",
				new SelectSpanningTreeAction(false)));
		submenu.add(editor.bind("selectDirectedTree",
				new SelectSpanningTreeAction(true)));

		menu.addSeparator();

		submenu = (JMenu) menu.add(new JMenu("stylesheet"));

		submenu
				.add(editor
						.bind(
								"basicStyle",
								new StylesheetAction(
										"/cn/edu/ustc/biofilm/BioPano/resources/basic-style.xml")));
		submenu
				.add(editor
						.bind(
								"defaultStyle",
								new StylesheetAction(
										"/cn/edu/ustc/biofilm/BioPano/resources/default-style.xml")));

		// Creates the options menu
		menu = add(new JMenu("options"));

		submenu = (JMenu) menu.add(new JMenu("display"));
		submenu.add(new TogglePropertyItem(graphComponent, mxResources
				.get("buffering"), "TripleBuffered", true));
		submenu.add(editor.bind("dirty",
				new ToggleDirtyAction()));

		submenu.addSeparator();

		item = submenu.add(new TogglePropertyItem(graphComponent, mxResources
				.get("centerPage"), "CenterPage", true, new ActionListener()
		{
			/**
			 * 
			 */
			public void actionPerformed(ActionEvent e)
			{
				if (graphComponent.isPageVisible()
						&& graphComponent.isCenterPage())
				{
					graphComponent.zoomAndCenter();
				}
			}
		}));

		submenu.add(new TogglePropertyItem(graphComponent, mxResources
				.get("centerZoom"), "CenterZoom", true));
		submenu.add(new TogglePropertyItem(graphComponent, mxResources
				.get("zoomToSelection"), "KeepSelectionVisibleOnZoom", true));

		submenu.addSeparator();

		submenu.add(new TogglePropertyItem(graphComponent, mxResources
				.get("preferPagesize"), "PreferPageSize", true));

		// This feature is not yet implemented
		//submenu.add(new TogglePropertyItem(graphComponent, mxResources
		//		.get("pageBreaks"), "PageBreaksVisible", true));

		submenu.addSeparator();

		submenu.add(editor.bind("tolerance",
				new PromptPropertyAction(graph, "Tolerance")));

		// Creates the window menu
		menu = add(new JMenu("window"));

		UIManager.LookAndFeelInfo[] lafs = UIManager.getInstalledLookAndFeels();

		for (int i = 0; i < lafs.length; i++)
		{
			final String clazz = lafs[i].getClassName();
			menu.add(new AbstractAction(lafs[i].getName())
			{
				public void actionPerformed(ActionEvent e)
				{
					editor.setLookAndFeel(clazz);
				}
			});
		}

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

}
