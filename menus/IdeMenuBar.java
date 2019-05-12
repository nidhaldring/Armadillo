
package menus;

import javax.swing.JMenuBar;

import tab.TabManager;
import panels.CompilerBox;

public class IdeMenuBar extends JMenuBar{

	private TabManager tabManager;
	private CompilerBox compilerBox;

	public IdeMenuBar(TabManager tabManager,CompilerBox compilerBox){

		super();
		this.tabManager = tabManager;
		this.compilerBox = compilerBox;

		initMenus();
	}

	private void initMenus(){

		FileMenu fileMenu = new FileMenu(this);
		EditMenu editMenu = new EditMenu(tabManager);
		BuildMenu buildMenu = new BuildMenu(tabManager,compilerBox);
		ConnectMenu connectMenu = new ConnectMenu();
		HelpMenu helpMenu = new HelpMenu();
	
		add(fileMenu);
		add(editMenu);
		add(buildMenu);
		add(connectMenu);
		add(helpMenu);
	}

	
	public TabManager getTabManager(){

		return tabManager;
	}
}
