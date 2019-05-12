

package listener;

import java.io.InputStream;
import java.util.Scanner;

import tab.TabManager;
import tab.Tab;

import panels.CompilerBox;

class Listener {

	protected CompilerBox compilerBox;
	protected TabManager tabManager;

	Listener(CompilerBox compilerBox,TabManager tabManager){

		this.compilerBox = compilerBox;
		this.tabManager = tabManager;
	}

	protected String traverseStream(InputStream stream){

		// travserve the input stream turning it into a one string 

		Scanner input = new Scanner(stream);
		String res = "";

		while(input.hasNext()){
			res += input.next() + " ";
		}

		return res;
	}

	protected String getProgrammingLanguage(Tab currentTab){

		if(currentTab == null){
			return null;
		}
		return currentTab.getEditor().getLang();
	}
}
