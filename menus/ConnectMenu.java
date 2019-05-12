
package menus;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;

import helper.StringHelper;

class ConnectMenu extends JMenu{

	private final static String iconsPath = StringHelper.toPath("Resources","Icons");

	ConnectMenu(){

		super("Connect");

		setIcon(new ImageIcon(StringHelper.toPath(iconsPath,"connect.png")));
	}
}
