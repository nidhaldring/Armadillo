
package menus;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.File;

import tab.TabManager;
import tab.Tab;

import helper.StringHelper;


class FileMenu extends JMenu{

	private final static String iconsPath = StringHelper.toPath("Resources","Icons"); 

	private IdeMenuBar menuBar;
	private TabManager tabManager;

	FileMenu(IdeMenuBar menuBar){
		
		super("File");
		this.menuBar = menuBar;
		this.tabManager = this.menuBar.getTabManager();
		
		setIcon(new ImageIcon(StringHelper.toPath(iconsPath,"file.png")));
		initFileMenu();
	}

	private void initFileMenu(){

		JMenuItem newFile = new JMenuItem("New file");
		JMenuItem openFile = new JMenuItem("Open File");
		JMenuItem saveFile = new JMenuItem("Save File");
		JMenuItem closeFile = new JMenuItem("Close File");

		newFile.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e){
					tabManager.openNewTab(null);
				}
		});

		openFile.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(menuBar);

				if(returnValue == JFileChooser.APPROVE_OPTION){
					String filePath = fileChooser.getSelectedFile().getPath();

					Tab currentTab = tabManager.getCurrentTab();
					if(currentTab != null && currentTab.getTitle().equals("untitled")){
						tabManager.closeTab(currentTab);
					}
					
					tabManager.openNewTab(filePath);

				}		
			}
		});

		saveFile.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){

				Tab currentTab = tabManager.getCurrentTab();
				if(currentTab == null){
					return;
				}

				if(currentTab.getTitle().equals("untitled")){
					JFileChooser fileChooser = new JFileChooser();
					int returnValue = fileChooser.showSaveDialog(menuBar);
					if(returnValue == JFileChooser.APPROVE_OPTION){
						String filePath = fileChooser.getSelectedFile().getPath();
						currentTab.saveTo(filePath);
						tabManager.closeTab(currentTab);
						tabManager.openNewTab(filePath);
					}
				}
				else{
					currentTab.saveFile();
				} 		
			}
		});

		closeFile.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){

				Tab currentTab = tabManager.getCurrentTab();
				if(currentTab == null){
					return;
				}
				tabManager.closeTab(currentTab);
			}
		});

		newFile.setIcon(new ImageIcon(StringHelper.toPath(iconsPath,"new_file.png")));
		saveFile.setIcon(new ImageIcon(StringHelper.toPath(iconsPath,"save_file.png")));
		openFile.setIcon(new ImageIcon(StringHelper.toPath(iconsPath,"open_file.png")));
		closeFile.setIcon(new ImageIcon(StringHelper.toPath(iconsPath,"close_file.png")));

		add(newFile);
		add(openFile);
		add(saveFile);
		add(closeFile);
	}
	
}
