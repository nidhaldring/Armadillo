
package menus;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.undo.UndoManager;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.CannotRedoException;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;

import java.io.IOException;

import tab.TabManager;
import tab.Tab;

import ide.IdeTab;
import ide.IdeDocument;

import listener.SearchActionListener;


class EditMenu extends JMenu{

	private TabManager tabManager;

	private static Clipboard clipboard;

	static{
		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	}

	EditMenu(TabManager tabManager){

		super("Edit");
		this.tabManager = tabManager;
		
		setIcon(new ImageIcon("Resources/Icons/edit.png"));
		initEditMenu();
	}

	private void initEditMenu(){

		JMenuItem undo = new JMenuItem("Undo");
		JMenuItem redo = new JMenuItem("Redo");
		JMenuItem cut = new JMenuItem("Cut");
		JMenuItem copy = new JMenuItem("Copy");
		JMenuItem paste = new JMenuItem("Paste");
		JMenuItem search = new JMenuItem("search");

		undo.addActionListener(getUndoAction());
		redo.addActionListener(getRedoAction());
		copy.addActionListener(getCopyActionListener());
		cut.addActionListener(getRemoveActionListener());
		cut.addActionListener(getCopyActionListener());
		paste.addActionListener(getPasteActionListener());
		search.addActionListener(new SearchActionListener(tabManager));

		undo.setIcon(new ImageIcon("Resources/Icons/undo.png"));
		redo.setIcon(new ImageIcon("Resources/Icons/redo.png"));
		cut.setIcon(new ImageIcon("Resources/Icons/cut.png"));
		copy.setIcon(new ImageIcon("Resources/Icons/copy.png"));
		paste.setIcon(new  ImageIcon("Resources/Icons/paste.png"));
		search.setIcon(new ImageIcon("Resources/Icons/search.png"));

		add(undo);
		add(redo);
		add(copy);
		add(cut);
		add(paste);
		add(search);
	 
	}


	private ActionListener getCopyActionListener(){

		return new ActionListener(){

			public void actionPerformed(ActionEvent e){

				Tab currentTab = tabManager.getCurrentTab();
				if(currentTab != null){
					IdeTab editor = currentTab.getEditor();
					String selectedText = editor.getSelectedText();
					if(selectedText != null){
						clipboard.setContents(new StringSelection(selectedText),null);
					}
					
				}
			}
		};
	}

	private ActionListener getRemoveActionListener(){

		return new ActionListener(){

			public void actionPerformed(ActionEvent e){

				Tab currentTab = tabManager.getCurrentTab();
				if(currentTab != null){
					IdeTab editor = currentTab.getEditor();
					String selectedText = editor.getSelectedText();

					if(selectedText != null){
						IdeDocument doc = currentTab.getDoc();
						doc.remove(editor.getSelectionStart(),selectedText.length());
					}
				}
			}
		};
	}

	private ActionListener getPasteActionListener(){

		return new ActionListener(){

			public void actionPerformed(ActionEvent e){

				Tab currentTab = tabManager.getCurrentTab();
				if(currentTab != null){
					IdeDocument doc = currentTab.getDoc();
					IdeTab editor = currentTab.getEditor();
					String data = null;

					try{
						data = (String)clipboard.getContents(this).getTransferData(DataFlavor.stringFlavor);
					}catch(UnsupportedFlavorException ex){
						ex.printStackTrace();
					}catch(IOException ex){
						ex.printStackTrace();
					}

					int offset = editor.getCaretPosition();
					doc.insertString(offset,data,null);
				}
			}
		};
	}

	private ActionListener getUndoAction(){

		return new ActionListener(){

			public void actionPerformed(ActionEvent e){

				Tab currentTab = tabManager.getCurrentTab();
				if(currentTab != null){
					try{
						currentTab.getDoc().getUndoManager().undo();

					}catch(CannotUndoException ex){
						ex.printStackTrace();
					}
				}
			}	
		};
	}

	private ActionListener getRedoAction(){

		return new ActionListener(){

			public void actionPerformed(ActionEvent e){

				Tab currentTab = tabManager.getCurrentTab();
				if(currentTab != null){
					try{
						currentTab.getDoc().getUndoManager().redo();
					}catch(CannotRedoException ex){
						ex.printStackTrace();
					}
					
				}
			}	
		};
	}
}	
