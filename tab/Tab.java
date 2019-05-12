
package tab;

import static  java.io.File.separator;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

import java.awt.Dimension;

import ide.IdeTab;

import layouts.SlickLayout;
import layouts.SlickConstraint;

import ide.IdeTab;
import ide.IdeDocument;

import panels.LinesPanel;
import panels.StatusBar;

// tab merges the ide tab and the lines panel together 

public class Tab extends JPanel implements DocumentListener{

	private LinesPanel linesPanel = new LinesPanel();
	private IdeTab editor;
	private StatusBar statusBar = new StatusBar();
	private boolean saved = true;

	Tab(String filePath){

		initTab(filePath);
		initUI();
	}

	public void initTab(String filePath){

		editor = new IdeTab(filePath);	
		statusBar.setLanguage(editor.getLang());

		IdeDocument doc = editor.getDoc();

		linesPanel.setLinesCount(doc.getNumberOfLines());
		doc.addDocumentListener(this);
	}

	public String getTitle(){

		return editor.getTitle();
	}

		
	private void initUI(){

		setLayout(new SlickLayout());
		JScrollPane scroller = new JScrollPane(editor);
		scroller.setRowHeaderView(linesPanel);
		add(scroller,new SlickConstraint(0,SlickConstraint.HorizontalFill, SlickConstraint.VerticalFill));	
		add(statusBar,new SlickConstraint(1,SlickConstraint.HorizontalFill, SlickConstraint.VerticalPack));
	}

	
	public void saveFile(){

		saveTo(editor.getFilePath());
	}

	public void saveTo(String filePath){

		saved = true;
		statusBar.setState(saved);
		editor.saveTo(filePath);
	}

	public IdeDocument getDoc(){

		return editor.getDoc();
	}

	public IdeTab getEditor(){

		return editor;
	}

	public String getFilePath(){

		return editor.getFilePath();
	}

	public String getFileName(){

		// returns file name without its extension
		String filePath = getFilePath();
		int dotIndex = filePath.lastIndexOf(".");
		int beg = filePath.lastIndexOf(separator) + 1;
		
		return filePath.substring(beg,dotIndex);
	}

	public String getPath(){

		// extract the file path without its name 
		String filePath = getFilePath();
		int index = filePath.lastIndexOf(separator) + 1;

		return filePath.substring(0,index);
	}

	public boolean isSaved(){

		return saved;
	}

	public ImageIcon getStateIcon(){

		return (isSaved() ? 
		new ImageIcon("Resources/Icons/saved.png") :
		new ImageIcon("Resources/Icons/unsaved.png"));
	}


	public void insertUpdate(DocumentEvent e){

		saved = false;
		statusBar.setState(saved);
		linesPanel.setLinesCount(getDoc().getNumberOfLines() + 1);
	}

	public void removeUpdate(DocumentEvent e){

		saved = false;
		statusBar.setState(saved);
		linesPanel.setLinesCount(getDoc().getNumberOfLines() + 1);
	}

	public void changedUpdate(DocumentEvent e){

	}
}