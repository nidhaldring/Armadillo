
package listener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.IOException;

import tab.TabManager;
import tab.Tab;

import panels.CompilerBox;

public class CompilationActionListener extends Listener implements ActionListener{

	private final static String SUCCESS_MSG = "YEAH !! Built Sucessfully with 0 Errors !!!";

	public CompilationActionListener(CompilerBox compilerBox,TabManager tabManager){

		super(compilerBox,tabManager);
	}


	public void actionPerformed(ActionEvent e){

		String compilationCommand = getCompilationCommand();
		if(compilationCommand != null){
			try{
				Process compilationProcess = Runtime.getRuntime().exec(compilationCommand);
				String compilationErrors = getCompilationErrors(compilationProcess);
				if(compilationErrors != ""){
					compilerBox.setToken(compilationErrors);
				}else{
					compilerBox.setToken(SUCCESS_MSG);
				}
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}	
	}			

	private String getCompilationCommand(){

		Tab currentTab = tabManager.getCurrentTab();
		String programmingLanguage = getProgrammingLanguage(currentTab);

		if(programmingLanguage == null){
			return null;
		}

		String command = null;
		switch(programmingLanguage){
			case "java":
				command = "javac " + currentTab.getFilePath();
				break;
			case "c":
				command = "gcc " + currentTab.getFilePath() + " -o " 
					+ currentTab.getPath() + currentTab.getFileName();
				break;
		}

		return command;
	}

	private String getCompilationErrors(Process compilationProcess){

		return traverseStream(compilationProcess.getErrorStream());
	}

}
