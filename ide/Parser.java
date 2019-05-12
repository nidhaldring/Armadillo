
package ide;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.text.*;
import java.awt.Color;


class Parser{

	private final static String URL = "jdbc:mysql://localhost:3306/test";
	private final static char[] SPLITING_CHARS = { 
		';' , '(' , '[' , '{' ,
		 '=','*','/' ,'<',
		  ')' , ']' , '}','>',
		  ' ',',' };

	private final static Color KEYWORD_COLOR = new Color(255,0,0); // red 
	private final static Color NORMAL_COLOR = new Color(0,0,0); // pure white
	private final static Color NUMBER_COLOR = new Color(0,135,255); // pinkishy ?
	private final static Color HILIGHTED_SEARCH_COLOR = new Color(255,250,205); // yellowishy
	private final static Color CLASS_COLOR = new Color(27, 247, 34);
	private final static Color STRING_COLOR = new Color(249, 0, 133);

	private final static SimpleAttributeSet KEYWORD_ATTR = new SimpleAttributeSet();
	private final static SimpleAttributeSet NORMAL_ATTR = new SimpleAttributeSet();;
	private final static SimpleAttributeSet NUMBER_ATTR = new SimpleAttributeSet();
	private final static SimpleAttributeSet SEARCHED_ATTR = new SimpleAttributeSet();
	private final static SimpleAttributeSet CLASS_ATTR = new SimpleAttributeSet();
	private final static SimpleAttributeSet STRING_ATTR = new SimpleAttributeSet();

	static {
		StyleConstants.setForeground(KEYWORD_ATTR,KEYWORD_COLOR);
		StyleConstants.setForeground(NORMAL_ATTR,NORMAL_COLOR);
		StyleConstants.setForeground(NUMBER_ATTR,NUMBER_COLOR);
		StyleConstants.setBackground(SEARCHED_ATTR,HILIGHTED_SEARCH_COLOR);
		StyleConstants.setForeground(CLASS_ATTR,CLASS_COLOR);
		StyleConstants.setForeground(STRING_ATTR,STRING_COLOR);
	}
	
	private Vector<String> keywords;
	private String lang;

	Parser(String lang){

		this.lang = lang;
		keywords = getKeywords(lang);

	}

	public String getLang(){

		return lang;
	}

	public SimpleAttributeSet getKeywordAttr(){

		return KEYWORD_ATTR;
	}

	public SimpleAttributeSet getNormalAttr(){

		return NORMAL_ATTR;
	}

	public SimpleAttributeSet getNumericAttr(){

		return NUMBER_ATTR;
	}

	public SimpleAttributeSet getSearchAttr(){

		return SEARCHED_ATTR;
	}

	public SimpleAttributeSet getStringAttr(){

		return STRING_ATTR;
	}

	public SimpleAttributeSet getClassAttr(){

		return CLASS_ATTR;
	}

	/* fetches the db for all the keywords */
	private Vector<String> getKeywords(String lang){

		Vector<String> keywords = new Vector<String>();

		if(lang == null){
			return keywords; // empty keywords
		}

		try{
			Connection conn = DriverManager.getConnection(URL,"root","root");
			ResultSet res ;
			Statement s = conn.createStatement();	
			res = s.executeQuery("select * from " + lang);

			while(res.next()){
				keywords.add(res.getString(1));
			}

			conn.close();

		}catch(SQLException e){
			e.printStackTrace();
		}

		return keywords;
	}

	
	public boolean isKeyword(String word){

		for(String keyword : keywords){

			if(word.equals(keyword))
				return true;
		}

		return false;
	}


	public boolean isNumeric(String word){

		try{

			Double.parseDouble(word);

		}catch(NumberFormatException e){

			return false;
		}

		return true;
	}

	public boolean isString(String word){

		int first = word.indexOf("\"");
		int last = word.lastIndexOf("\"");

		return first == 0 && last == (word.length() - 1) ;
	}

	public boolean isClass(String word){

		return !word.equals("") 
		&& word.charAt(0) <= 'Z'
		&& word.charAt(0) >= 'A';
	}

	public ArrayList<Token> digestToken(Token token){

		ArrayList<Token> tokens = new ArrayList<Token>();
		int offset = token.getOffset();
		String str = token.getToken();
		String ch = "";
		boolean hasSplitingChars = false;

		for(int i=0;i<str.length();++i){
			char currentChar = str.charAt(i);
			if(isSplitingChar(currentChar)){
				hasSplitingChars = true;
				tokens.add(new Token(ch,offset));
				offset += ch.length();
				tokens.add(new Token(currentChar + "",offset));
				++offset;
				ch = "";
			}else{
				ch += currentChar;
			}
		}

		if(hasSplitingChars == false){
			tokens.add(token);
		}
		if(!ch.equals("")){
			tokens.add(new Token(ch,offset));
		}

		return tokens;
	}

	private boolean isSplitingChar(char ch){

		for(char splitingChar : SPLITING_CHARS){
			if(splitingChar == ch){
				return true;
			}
		}
		return false;
	}

}
