
package panels;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.GridLayout;


// this class holdes the lower box of the ide 
// in which compiling result appears 
// and the scribble etc ..
public class IdeBox extends JPanel{

	private CompilerBox compilerBox = new CompilerBox();

	public IdeBox(){

		JScrollPane scroller = new JScrollPane(compilerBox);
		setLayout(new GridLayout(1,1));
		add(scroller);
	}

	public CompilerBox getCompilerBox(){

		return compilerBox;
	}


}
