
package menus;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;

import panels.CompilerBox;

import tab.TabManager;

import listener.CompilationActionListener;
import listener.ExecutionActionListener;

class BuildMenu extends JMenu{

	private CompilerBox compilerBox;
	private TabManager tabManager;


	BuildMenu(TabManager tabManager,CompilerBox compilerBox){

		super("Build");
		this.tabManager = tabManager;
		this.compilerBox = compilerBox;

		setIcon(new ImageIcon("Resources/Icons/build.png"));
		initBuildMenu();
	}

	private void initBuildMenu(){

		JMenuItem compile = new JMenuItem("Compile");
		JMenuItem run = new JMenuItem("Run");

		compile.addActionListener( new CompilationActionListener(compilerBox,tabManager));
		run.addActionListener(new ExecutionActionListener(compilerBox,tabManager));

		run.setIcon(new ImageIcon("Resources/Icons/run.png"));
		compile.setIcon(new ImageIcon("Resources/Icons/compile.png"));

		add(run);
		add(compile);
	}
	
	
}
