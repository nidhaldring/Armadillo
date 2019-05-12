
import javax.swing.*;
import java.awt.*;

import tab.TabManager;

import panels.IdeBox;

import menus.IdeToolBar;
import menus.IdeMenuBar;

import layouts.SlickLayout;
import layouts.SlickConstraint;


class Main{

	public static void main(String[]args){

		try{	
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){
			
		}

		SplashScreen splash = SplashScreen.getSplashScreen();
		TabManager t = new TabManager();
		
		JFrame f = new JFrame();
		IdeBox box = new IdeBox();
		f.setSize(800,600);
		f.setLocationRelativeTo(null);
		f.setLayout(new SlickLayout());
		
		IdeToolBar toolBar = new IdeToolBar(box.getCompilerBox(),t);
		f.add(toolBar,new SlickConstraint(0,SlickConstraint.HorizontalFill, SlickConstraint.VerticalPack));

		f.add(t,new SlickConstraint(1,SlickConstraint.HorizontalFill, SlickConstraint.VerticalFill));
		
		box.setPreferredSize(new Dimension(800,150));
		IdeMenuBar menu = new IdeMenuBar(t,box.getCompilerBox());
		f.setJMenuBar(menu);
		f.add(box,new SlickConstraint(2,SlickConstraint.HorizontalFill, SlickConstraint.VerticalPack));
		f.setIconImage(Toolkit.getDefaultToolkit().getImage("Resources/Icons/main.png"));
		f.setTitle(" 🐭 armadillo IDE -");
		f.setVisible(true);
		//f.pack();
		
	}



	

}
