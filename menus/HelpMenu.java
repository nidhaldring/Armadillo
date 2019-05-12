
package menus;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;


class HelpMenu extends JMenu{


	HelpMenu(){

		super("Help");

		setIcon(new ImageIcon("Resources/Icons/help.png"));
		initHelpMenu();

	}

	public void initHelpMenu(){

		JMenuItem license = new JMenuItem("License");
		JMenuItem about = new JMenuItem("About");

		license.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){

				JDialog licenseWindow = new JDialog();
				JPanel licensePanel = new JPanel();
				licenseWindow.setTitle("License");
				licensePanel.setLayout(new BoxLayout(licensePanel,BoxLayout.PAGE_AXIS));

				// read LICENSE FILE

				try{
					BufferedReader licenseFile = new BufferedReader(new FileReader("LICENSE"));
					String line = licenseFile.readLine();
					while(line != null){
						licensePanel.add(new JLabel(line));
						line = licenseFile.readLine();
					}
				}catch(IOException ex){
					ex.printStackTrace();
				}

				licenseWindow.add(licensePanel);
				licenseWindow.pack();
				licenseWindow.setVisible(true);
			}
		});

		about.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){

				JDialog aboutWindow = new JDialog();
				aboutWindow.setTitle("About Our Ide");
				JLabel aboutText = new JLabel("<html>"
								+ "<center> <font color =\"red\">ARMADILLO 🐭</font><br/> "
								+ "This IDE is PURELY desgined by : <br/>"
								+ "<font color =\"red\"> Nidhal Rnine  </font><br/> " 
								+ "<font color = \"red\">Mouhamed Wael Samti</font><br/>"
								+ " <br/>This IDE aims to better help <br/>"
								+ "both teacher and students to better<br/>"
								+ " communicate and program <br/> "
								+ " enjoy ❤	  </center> <br/> <br/> </html>"
				);
				aboutWindow.add(aboutText);
				aboutWindow.pack();
				aboutWindow.setVisible(true);
			}
		});

		license.setIcon(new ImageIcon("Resources/Icons/license.png"));
		about.setIcon(new ImageIcon("Resources/Icons/about.png"));

		add(license);
		add(about);
	}
}
