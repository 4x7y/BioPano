package cn.edu.ustc.biofilm.BioPano.editor;

import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.TransferHandler;

import cn.edu.ustc.biofilm.BioPano.editor.EditorActions.HistoryAction;
import com.mxgraph.swing.util.mxGraphActions;
import com.mxgraph.util.mxResources;

public class EditorPopupMenu extends JPopupMenu
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3132749140550242191L;

	public EditorPopupMenu(BasicGraphEditor editor)
	{
		boolean selected = !editor.getGraphComponent().getGraph()
				.isSelectionEmpty();

		add(editor.bind("undo", new HistoryAction(true),
				"/cn/edu/ustc/biofilm/BioPano/images/undo.gif"));

		addSeparator();

		add(
				editor.bind("cut", TransferHandler
						.getCutAction(),
						"/cn/edu/ustc/biofilm/BioPano/images/cut.gif"))
				.setEnabled(selected);
		add(
				editor.bind("copy", TransferHandler
						.getCopyAction(),
						"/cn/edu/ustc/biofilm/BioPano/images/copy.gif"))
				.setEnabled(selected);
		add(editor.bind("paste", TransferHandler
				.getPasteAction(),
				"/cn/edu/ustc/biofilm/BioPano/images/paste.gif"));

		addSeparator();

		add(
				editor.bind("delete", mxGraphActions
						.getDeleteAction(),
						"/cn/edu/ustc/biofilm/BioPano/images/delete.gif"))
				.setEnabled(selected);

		addSeparator();

		// Creates the format menu
		JMenu menu = (JMenu) add(new JMenu("format"));

		EditorMenuBar.populateFormatMenu(menu, editor);

		// Creates the shape menu
		menu = (JMenu) add(new JMenu("shape"));

		EditorMenuBar.populateShapeMenu(menu, editor);

		addSeparator();

		add(
				editor.bind("edit", mxGraphActions
						.getEditAction())).setEnabled(selected);

		addSeparator();

		add(editor.bind("selectVertices", mxGraphActions
				.getSelectVerticesAction()));
		add(editor.bind("selectEdges", mxGraphActions
				.getSelectEdgesAction()));

		addSeparator();

		add(editor.bind("selectAll", mxGraphActions
				.getSelectAllAction()));
	}

}
