
package panels;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JDialog;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.Color;
import java.awt.Dimension;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import helper.StringHelper;


class QuoteLabel extends JLabel{

	private final static String quotesPath = StringHelper.toPath(
										StringHelper.toPath("Resources","Quotes"),"quotes.txt");
	private final static int quotesNumber = 360;

	private String quote;


	QuoteLabel(){

		quote = getQuote();
		addMouseListener(getMouseListener());
		setText(quote);
		setForeground(Color.BLUE);
	}

	private String getQuote(){
		
		String res = null;

		try{
			BufferedReader file = new BufferedReader(new FileReader(quotesPath));
			int quoteNumber = 1 + (int) (Math.random() * (quotesNumber - 1));
			String quote = "";

			for(int i=0;i<quotesNumber;){
				String line = file.readLine();
				quote += line + "\n ";
				if(line.startsWith("--")){
					++i;
					if(quoteNumber == i){
						res = quote;
						break;
					}
					quote = "";
				}
			}

		}catch(IOException e){
			e.printStackTrace();
		}

		return "     \"" + res + "\"   " ;
	}

	private MouseAdapter getMouseListener(){

		return new MouseAdapter(){

			public void mouseClicked(MouseEvent e){

				JDialog dialog = new JDialog();
				JTextArea txt = new JTextArea(quote);

				txt.setEditable(false);
				txt.setBackground(Color.BLACK);
				txt.setForeground(Color.WHITE);
				dialog.setSize(800,200);
				dialog.add(txt);
				dialog.setTitle("Quote of the Day <3 ");
				dialog.setVisible(true);
			}
		};
	}

}
