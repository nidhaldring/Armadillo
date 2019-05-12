
package listener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import tab.TabManager;
import tab.Tab;

import dialogs.Search;

public class SearchActionListener implements ActionListener {

	private TabManager tabManager;

	public SearchActionListener(TabManager tabManager){

		this.tabManager = tabManager;
	}

	public void actionPerformed(ActionEvent e){

		Tab currentTab = tabManager.getCurrentTab();
		if(currentTab != null){
			Search searchWidget = new Search(currentTab.getEditor());
			searchWidget.setVisible(true);
		}
	}
	

}
