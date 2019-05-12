
package menus;

import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import tab.TabManager;

import panels.CompilerBox;

import listener.SearchActionListener;
import listener.ExecutionActionListener;

import helper.StringHelper;

public class IdeToolBar extends JToolBar{

	private final static String iconsPath = StringHelper.toPath("Resources","Icons");

	private TabManager tabManager;
	private CompilerBox compilerBox;
	private JButton file = new JButton(new ImageIcon(StringHelper.toPath(iconsPath,"file_t.png")));
	private JButton run = new JButton(new ImageIcon(StringHelper.toPath(iconsPath,"gears.png")));
	private JButton search = new JButton(new ImageIcon(StringHelper.toPath(iconsPath,"binoculars.png")));
	private JButton save = new JButton(new ImageIcon(StringHelper.toPath(iconsPath,"save_t.png")));

	public IdeToolBar(CompilerBox compilerBox,TabManager tabManager){

		this.tabManager = tabManager;
		this.compilerBox = compilerBox;

		setRollover(true);
		setFloatable(false);

		file.setToolTipText("New file");
		search.setToolTipText("Search");
		run.setToolTipText("Execute");
		save.setToolTipText("Save");

		file.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e){
					tabManager.openNewTab(null);
				}
		});

		search.addActionListener(new SearchActionListener(tabManager));
		run.addActionListener(new ExecutionActionListener(compilerBox,tabManager));

		add(file);
		add(search);
		add(run);
		add(save);
	}
}
