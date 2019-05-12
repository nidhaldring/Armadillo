
package panels;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;

import java.awt.Color;

import java.util.Stack;


public class LinesPanel extends JPanel{

	private final static Color HIGHLITED = new Color(200,0,0); // redish
	private final static Color UNHIGHLITED = new Color(0,0,0); // black

	private Stack<JLabel> lines;
	private BoxLayout vertical_layout;

	public LinesPanel(){

		lines = new Stack<JLabel>();
		vertical_layout = new BoxLayout(this,BoxLayout.Y_AXIS);

		setBackground(new Color(25, 25, 17));
		setLayout(vertical_layout);
		setLinesCount(1);
	}


	public void setLinesCount(int count){

		int current_count = lines.size();
		if(current_count < count){
			addLines(count - current_count);
		}
		else{
			removeLines(current_count - count);
		}
	}

	private void addLines(int count ){

		int current_lines_number = lines.size();
		for(int i=0;i<count;++i){
			current_lines_number++;
			JLabel label = new JLabel("  " + current_lines_number + " ");
			label.setForeground(Color.WHITE);
			add(label);
			lines.push(label);
		}
	}

	private void removeLines(int count){

		for(int i=0;i<count;++i){
			JLabel label = lines.pop();
			remove(label);
		}

		revalidate();
		repaint();
	}


	public void highlightLine(int line){

		JLabel label = lines.elementAt(line - 1);
		label.setForeground(HIGHLITED);
	}

	public void unhighlightLine(int line){

		JLabel label = lines.elementAt(line);
		label.setForeground(UNHIGHLITED);
	}
}
