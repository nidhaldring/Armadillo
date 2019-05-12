
package dialogs;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

import ide.IdeTab;
import ide.Token;

import helper.StringHelper;


public class Search extends JDialog{

	private final static String iconsPath = StringHelper.toPath("Resources","Icons") ;

	private int index = 0;
	private IdeTab editor;
	private JButton searchBtn;
	private JButton nextBtn;
	private JButton previousBtn;
	private JTextField searchField = new JTextField();
	private ArrayList<Token> searchResult;
	
	public Search(IdeTab editor){

		this.editor = editor;

		searchBtn = createSearchButton();
		nextBtn = createNextButton();
		previousBtn = createPreviousButton();

		searchField.setColumns(10);

		searchBtn.setIcon(new ImageIcon(StringHelper.toPath(iconsPath,"searchfor.png")));
		nextBtn.setIcon(new ImageIcon(StringHelper.toPath(iconsPath,"next.png")));
		previousBtn.setIcon(new ImageIcon(StringHelper.toPath(iconsPath,"previous.png")));

		setTitle("Search");
		setLayout(new FlowLayout());
		add(searchField);
		add(searchBtn);
		add(nextBtn);
		add(previousBtn);
		pack();
		setResizable(false);
	}

	private JButton createPreviousButton(){

		JButton previousBtn = new JButton("Previous");
		previousBtn.setEnabled(false);

		previousBtn.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){

				if(index > 0 ){
					--index;
					scrollToRes();
					selectRes();
					nextBtn.setEnabled(true);
				}else{
					String errorMsg = "Top of the file has been reached !";
					JOptionPane.showMessageDialog(null,errorMsg,"alert",JOptionPane.ERROR_MESSAGE);
					previousBtn.setEnabled(false);
				}
			}
		});

		return previousBtn;
	}

	private JButton createNextButton(){

		JButton nextBtn = new JButton("Next");
		nextBtn.setEnabled(false);

		nextBtn.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){

				if(index  < searchResult.size() - 1){
					++index;
					scrollToRes();
					selectRes();
					previousBtn.setEnabled(true);
				}else{
					String errorMsg = "The end of the file has been reached !";
					JOptionPane.showMessageDialog(null,errorMsg,"alert",JOptionPane.ERROR_MESSAGE);
					JButton btn = (JButton)e.getSource();
					btn.setEnabled(false);
				}
			}
		});

		return nextBtn;
	}

	private JButton createSearchButton(){

		JButton searchBtn = new JButton("Search");
		searchBtn.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){

				String target = searchField.getText();
				if(!target.equals("")){
					search(target);
					if(!searchResult.isEmpty()){
						scrollToRes();
						selectRes();
						nextBtn.setEnabled(true);
						previousBtn.setEnabled(false);
					}else{
						String errorMsg = "No Result is Found !";
						JOptionPane.showMessageDialog(null,errorMsg, "alert", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		return searchBtn;
	}

	private void search(String target){

		index = 0;
		searchResult = editor.search(target);
	}

	private void scrollToRes(){

		int offset = searchResult.get(index).getOffset();
		editor.setCaretPosition(0);
		editor.setCaretPosition(offset);
	}

	private void selectRes(){

		Token token = searchResult.get(index);
		editor.setSelectionStart(token.getOffset());
		editor.setSelectionEnd(token.getOffset() + token.getToken().length());
	}

}
