
package panels;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;

import layouts.SlickLayout;
import layouts.SlickConstraint;

import helper.StringHelper;

public class StatusBar extends JPanel{

	private final static String iconsPath = StringHelper.toPath("Resources","Icons");

	private JLabel lang = new JLabel();
	private QuoteLabel quote = new QuoteLabel();
	private JLabel state = new JLabel();
	private JLabel encoding = new JLabel("   Encoding : UTF-16   ");

	public StatusBar(){

		setLanguage("undefined");
		setState(true); // saved

		setLayout(new SlickLayout());
		add(lang,new SlickConstraint(0,SlickConstraint.HorizontalPack, SlickConstraint.VerticalPack));	
		add(quote,new SlickConstraint(0,SlickConstraint.HorizontalFill, SlickConstraint.VerticalPack));
		add(state,new SlickConstraint(0,SlickConstraint.HorizontalPack, SlickConstraint.VerticalPack));
		add(encoding,new SlickConstraint(0,SlickConstraint.HorizontalPack, SlickConstraint.VerticalPack));	
	}

	public void setLanguage(String lang){

		if(lang == null){
			lang = "undefined";
		}

		String path = StringHelper.toPath(iconsPath,lang + ".png");
		this.lang.setIcon(new ImageIcon(path));
		this.lang.setToolTipText(lang);
	}

	public void setState(boolean state){

		if(state == true){
			// saved
			this.state.setIcon(new ImageIcon(StringHelper.toPath(iconsPath,"saved.png")));
			this.state.setToolTipText("saved");
		}else{
			// duh
			this.state.setIcon(new ImageIcon(StringHelper.toPath(iconsPath,"unsaved.png")));
			this.state.setToolTipText("not saved");
		}
	}

}
