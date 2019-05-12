
package tab;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.util.Vector;


public class TabManager extends JTabbedPane implements ChangeListener{

	private Tab currentTab;

	public TabManager(){

		super();	
		openNewTab(null);
		addChangeListener(this);
	}

	public void openNewTab(String filePath){

		currentTab = new Tab(filePath);
		add(currentTab.getTitle(),currentTab);	
	}

	public void closeTab(Tab tab){

		if(!tab.isSaved()){	
			int answer = JOptionPane.showConfirmDialog(this,
									"Do you want to save before ?",
									"Beware",
									JOptionPane.YES_NO_OPTION);
			if(answer == JOptionPane.YES_OPTION){
				tab.saveFile();	
			}	
		}
		remove(tab);
	}
	

	public void stateChanged(ChangeEvent e){

		currentTab = (Tab)getSelectedComponent();
	}

	public Tab getCurrentTab(){

		return currentTab;
	}
}