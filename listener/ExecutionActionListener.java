

package listener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.IOException;

import tab.TabManager;
import tab.Tab;

import panels.CompilerBox;

import dialogs.Search;


public class ExecutionActionListener extends Listener implements ActionListener{


	public ExecutionActionListener(CompilerBox compilerBox,TabManager tabManager){

		super(compilerBox,tabManager);
	}

	public void actionPerformed(ActionEvent e){

		String executionCommand = getExecutionCommand();
		if(executionCommand != null){
			try{
				Process executionProcess = Runtime.getRuntime().exec(executionCommand);
				String executionErrors = getExecutionErrors(executionProcess);
				if(executionErrors != ""){
					compilerBox.setToken(executionErrors);
				}else{
					String executionOutput = getExecutionOutput(executionProcess);
					compilerBox.setToken(executionOutput);
				}

			}catch(IOException ex){
				ex.printStackTrace();
			}
		}
	}

	private String getExecutionCommand(){

		Tab currentTab = tabManager.getCurrentTab();
		String programmingLanguage = getProgrammingLanguage(currentTab);

		if(programmingLanguage == null){
			return null;
		}

		String command = null;
		switch(programmingLanguage){
			case "java":
				command = "java -cp " + currentTab.getPath() + " " + currentTab.getFileName();
				break;
			case "c":
				command = currentTab.getPath() + currentTab.getFileName();	
				break;			
		}

		return command;
	}


	private String getExecutionErrors(Process executionProcess){

		return traverseStream(executionProcess.getErrorStream());
	}

	private String getExecutionOutput(Process executionProcess){

		return traverseStream(executionProcess.getInputStream());
	}
	
}
