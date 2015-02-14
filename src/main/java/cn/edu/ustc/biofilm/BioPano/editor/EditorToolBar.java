package cn.edu.ustc.biofilm.BioPano.editor;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;

import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.ColorAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.FontStyleAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.HistoryAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.KeyValueAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.NewAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.OpenAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.PrintAction;
import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.SaveAction;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxGraphActions;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxResources;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxGraphView;


import cn.edu.ustc.biofilm.BioPano.editor.SearchToolBar;

public class EditorToolBar extends JToolBar
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8015443128436394471L;

	/**
	 * 
	 * @param frame
	 * @param orientation
	 */
	private boolean ignoreZoomChange = false;

	/**
	 * 
	 */
	//public EditorToolBar(final BasicGraphEditor editor, int orientation)
	public EditorToolBar(final BasicGraphEditor editor, final JPanel panel, int orientation)
	{
		super(orientation);
		setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createEmptyBorder(3, 3, 3, 3), getBorder()));
		setFloatable(false);

		add(editor.bind("New", new NewAction(),
				"/cn/edu/ustc/biofilm/BioPano/images/new.gif"));
		add(editor.bind("Open", new OpenAction(),
				"/cn/edu/ustc/biofilm/BioPano/images/open.gif"));
		add(editor.bind("Save", new SaveAction(false),
				"/cn/edu/ustc/biofilm/BioPano/images/save.gif"));

		addSeparator();

		add(editor.bind("Print", new PrintAction(),
				"/cn/edu/ustc/biofilm/BioPano/images/print.gif"));

		addSeparator();

		add(editor.bind("Cut", TransferHandler.getCutAction(),
				"/cn/edu/ustc/biofilm/BioPano/images/cut.gif"));
		add(editor.bind("Copy", TransferHandler.getCopyAction(),
				"/cn/edu/ustc/biofilm/BioPano/images/copy.gif"));
		add(editor.bind("Paste", TransferHandler.getPasteAction(),
				"/cn/edu/ustc/biofilm/BioPano/images/paste.gif"));

		addSeparator();

		add(editor.bind("Delete", mxGraphActions.getDeleteAction(),
				"/cn/edu/ustc/biofilm/BioPano/images/delete.gif"));

		addSeparator();

		add(editor.bind("Undo", new HistoryAction(true),
				"/cn/edu/ustc/biofilm/BioPano/images/undo.gif"));
		add(editor.bind("Redo", new HistoryAction(false),
				"/cn/edu/ustc/biofilm/BioPano/images/redo.gif"));

		addSeparator();

		// Gets the list of available fonts from the local graphics environment
		// and adds some frequently used fonts at the beginning of the list
		GraphicsEnvironment env = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		List<String> fonts = new ArrayList<String>();
		fonts.addAll(Arrays.asList(new String[] { "Helvetica", "Verdana",
				"Times New Roman", "Garamond", "Courier New", "-" }));
		fonts.addAll(Arrays.asList(env.getAvailableFontFamilyNames()));

		final JComboBox fontCombo = new JComboBox(fonts.toArray());
		fontCombo.setEditable(true);
		fontCombo.setMinimumSize(new Dimension(120, 0));
		fontCombo.setPreferredSize(new Dimension(120, 0));
		fontCombo.setMaximumSize(new Dimension(120, 100));
		add(fontCombo);

		fontCombo.addActionListener(new ActionListener()
		{
			/**
			 * 
			 */
			public void actionPerformed(ActionEvent e)
			{
				String font = fontCombo.getSelectedItem().toString();

				if (font != null && !font.equals("-"))
				{
					mxGraph graph = editor.getGraphComponent().getGraph();
					graph.setCellStyles(mxConstants.STYLE_FONTFAMILY, font);
				}
			}
		});

		final JComboBox sizeCombo = new JComboBox(new Object[] { "6pt", "8pt",
				"9pt", "10pt", "12pt", "14pt", "18pt", "24pt", "30pt", "36pt",
				"48pt", "60pt" });
		sizeCombo.setEditable(true);
		sizeCombo.setMinimumSize(new Dimension(65, 0));
		sizeCombo.setPreferredSize(new Dimension(65, 0));
		sizeCombo.setMaximumSize(new Dimension(65, 100));
		add(sizeCombo);

		sizeCombo.addActionListener(new ActionListener()
		{
			/**
			 * 
			 */
			public void actionPerformed(ActionEvent e)
			{
				mxGraph graph = editor.getGraphComponent().getGraph();
				graph.setCellStyles(mxConstants.STYLE_FONTSIZE, sizeCombo
						.getSelectedItem().toString().replace("pt", ""));
			}
		});

		addSeparator();

		add(editor.bind("Bold", new FontStyleAction(true),
				"/cn/edu/ustc/biofilm/BioPano/images/bold.gif"));
		add(editor.bind("Italic", new FontStyleAction(false),
				"/cn/edu/ustc/biofilm/BioPano/images/italic.gif"));

		addSeparator();

		add(editor.bind("Left", new KeyValueAction(mxConstants.STYLE_ALIGN,
				mxConstants.ALIGN_LEFT),
				"/cn/edu/ustc/biofilm/BioPano/images/left.gif"));
		add(editor.bind("Center", new KeyValueAction(mxConstants.STYLE_ALIGN,
				mxConstants.ALIGN_CENTER),
				"/cn/edu/ustc/biofilm/BioPano/images/center.gif"));
		add(editor.bind("Right", new KeyValueAction(mxConstants.STYLE_ALIGN,
				mxConstants.ALIGN_RIGHT),
				"/cn/edu/ustc/biofilm/BioPano/images/right.gif"));

		addSeparator();

		add(editor.bind("Font", new ColorAction("Font",
				mxConstants.STYLE_FONTCOLOR),
				"/cn/edu/ustc/biofilm/BioPano/images/fontcolor.gif"));
		add(editor.bind("Stroke", new ColorAction("Stroke",
				mxConstants.STYLE_STROKECOLOR),
				"/cn/edu/ustc/biofilm/BioPano/images/linecolor.gif"));
		add(editor.bind("Fill", new ColorAction("Fill",
				mxConstants.STYLE_FILLCOLOR),
				"/cn/edu/ustc/biofilm/BioPano/images/fillcolor.gif"));

		addSeparator();

		final mxGraphView view = editor.getGraphComponent().getGraph()
				.getView();
		final JComboBox zoomCombo = new JComboBox(new Object[] { "400%",
				"200%", "150%", "100%", "75%", "50%", "page",
				"width", "actualSize" });
		zoomCombo.setEditable(true);
		zoomCombo.setMinimumSize(new Dimension(75, 0));
		zoomCombo.setPreferredSize(new Dimension(75, 0));
		zoomCombo.setMaximumSize(new Dimension(75, 100));
		zoomCombo.setMaximumRowCount(9);
		add(zoomCombo);

		// Sets the zoom in the zoom combo the current value
		mxIEventListener scaleTracker = new mxIEventListener()
		{
			/**
			 * 
			 */
			public void invoke(Object sender, mxEventObject evt)
			{
				ignoreZoomChange = true;

				try
				{
					zoomCombo.setSelectedItem((int) Math.round(100 * view
							.getScale())
							+ "%");
				}
				finally
				{
					ignoreZoomChange = false;
				}
			}
		};

		// Installs the scale tracker to update the value in the combo box
		// if the zoom is changed from outside the combo box
		view.getGraph().getView().addListener(mxEvent.SCALE, scaleTracker);
		view.getGraph().getView().addListener(mxEvent.SCALE_AND_TRANSLATE,
				scaleTracker);

		// Invokes once to sync with the actual zoom value
		scaleTracker.invoke(null, null);

		zoomCombo.addActionListener(new ActionListener()
		{
			/**
			 * 
			 */
			public void actionPerformed(ActionEvent e)
			{
				mxGraphComponent graphComponent = editor.getGraphComponent();

				// Zoomcombo is changed when the scale is changed in the diagram
				// but the change is ignored here
				if (!ignoreZoomChange)
				{
					String zoom = zoomCombo.getSelectedItem().toString();

					if (zoom.equals("page"))
					{
						graphComponent.setPageVisible(true);
						graphComponent
								.setZoomPolicy(mxGraphComponent.ZOOM_POLICY_PAGE);
					}
					else if (zoom.equals("width"))
					{
						graphComponent.setPageVisible(true);
						graphComponent
								.setZoomPolicy(mxGraphComponent.ZOOM_POLICY_WIDTH);
					}
					else if (zoom.equals("actualSize"))
					{
						graphComponent.zoomActual();
					}
					else
					{
						try
						{
							zoom = zoom.replace("%", "");
							double scale = Math.min(16, Math.max(0.01,
									Double.parseDouble(zoom) / 100));
							graphComponent.zoomTo(scale, graphComponent
									.isCenterZoom());
						}
						catch (Exception ex)
						{
							JOptionPane.showMessageDialog(editor, ex
									.getMessage());
						}
					}
				}
			}
		});

	}
}
