
package ide;

import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.text.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;

import helper.StringHelper;


public class IdeTab extends JTextPane{

	private final static String FONTS_PATH = StringHelper.toPath("Resources","Fonts");
	private final static float DEFAULT_FONT_SIZE = 17f;

	private String title ;
	private String filePath;
	private String lang;
	private IdeDocument doc;

	public IdeTab(String filePath){

		super();
		doc = new IdeDocument(this);
		setDocument(doc);
		setBackground(new Color(127, 125, 123));
		setFont(getMyFont("Consolas"));
		initIde(filePath);	
	}

	public void initIde(String filePath){

		this.filePath = filePath;
		title = extractTitle(filePath);
		lang = extractLanguage(title);

		doc.setLanguage(lang);
		insertString(readFile(filePath));
		
	}

	private Font getMyFont(String fontName){

		File fontFile = new File(StringHelper.toPath(FONTS_PATH,fontName + ".ttf"));
		Font res = null;
		try{
			res = Font.createFont(Font.TRUETYPE_FONT,fontFile)
				.deriveFont(DEFAULT_FONT_SIZE);
		}catch(FontFormatException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}

		return res;
	}

	private void setFontSize(float size){

		Font font = getFont();
		setFont(font.deriveFont(size));

	}

	public IdeDocument getDoc(){

		return doc;
	}

	public String getFilePath(){

		return filePath;
	}

	public String extractTitle(String filePath){

		if(filePath == null){
			return "untitled";
		}

		String[] tmp = filePath.split(File.separator);

		return tmp[tmp.length - 1];

	}

	public String getTitle(){

		return title;
	}

	private String extractLanguage(String title){

		if(title == null){
			return null;
		}

		int dotIndex = title.length() - 1;
		while( dotIndex > 0 && title.charAt(dotIndex) != '.'){
			--dotIndex;
		}

		if(dotIndex  == 0){
			return null;
		}else{ 
			return title.substring(dotIndex + 1);
		}	
	}

	private String readFile(String filePath) {

		BufferedReader file = null;
		String res = "";

		if(filePath != null){
			try{
				file = new BufferedReader(new FileReader(filePath));		
				String c = file.readLine();
				while(c != null){
					res += c + "\n";
					c = file.readLine();
					}

			}catch(FileNotFoundException e){
				e.printStackTrace();
			}catch(IOException e){
				e.printStackTrace();
			}	
		}

		
		return res;

	}

	public void insertString(String text){	

		doc.insertString(doc.getLength(),text,null);		
	}

	public void saveTo(String filePath){

		try{
			BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath));
			fileWriter.write(doc.getText(0,doc.getLength()));	
			fileWriter.close();	

		}catch(BadLocationException ex){
			ex.printStackTrace();

		}catch(IOException ex){
			ex.printStackTrace();
		}
	}

	public String getLang(){

		return lang;
	}

	public ArrayList<Token> search(String target){

		return doc.search(target);
	}
}
