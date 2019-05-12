
package ide;

import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.text.*;
import javax.swing.undo.UndoManager;

import helper.StringHelper;

public class IdeDocument extends DefaultStyledDocument{


	// states of editing
	// any string that's inserted to the ide must has one of this states
	private enum State{
		NORMAL, // hilight + add matching characters, E.g : () {} etc ..
		PASTING // hilight BUT DOSN'T match characters 
	}

	private Parser parser;
	private Token currentToken = new Token();
	private JTextPane textArea;
	private UndoManager undoManager = new UndoManager();
	private int nbLines = 1; 
	// note that nbLines is altered only on two method : remove and insertToken


	IdeDocument(String lang,JTextPane textArea){

		super();
		this.textArea = textArea;
		setLanguage(lang);
		addUndoableEditListener(undoManager);
	}

	IdeDocument(JTextPane textArea){

		this(null,textArea);
	}

	public void setLanguage(String lang){

		parser = new Parser(lang);
	}

	public UndoManager getUndoManager(){
		
		return undoManager;
	}

	public void insertString(int offset,String s,AttributeSet a){

		try{
			if(s.length() > 1){
				insertTokens(s,offset,State.PASTING);
			}else{ 
				insertToken(s,offset,State.NORMAL);
			}
		}catch(BadLocationException e){
			e.printStackTrace();
		}
	}

	public void remove(int offset,int l){

		try{
			nbLines -= StringHelper.getNumberOfLines(getText(offset,l));
			super.remove(offset,l);		
			currentToken = findToken(offset);
			highlightTokens(parser.digestToken(currentToken),State.NORMAL);
		}catch(BadLocationException e){
			e.printStackTrace();
		}

	}

	private String findLeftToken(int beg) throws BadLocationException{

		int i = beg - 1;
		String ch = null;
		String token = "";

		while(i >= 0 && i < getLength()){	
			ch = getText(i,1);
			if(Character.isWhitespace(ch.charAt(0))){
				break;
			}
			token += ch;
			--i;
		}

		return StringHelper.reverse(token);
	}

	private String findRightToken(int beg) throws BadLocationException{

		String ch = null;
		String token = "";

		int i = beg ;
		while(i < getLength()){	
			ch = getText(i,1);
			if(Character.isWhitespace(ch.charAt(0))){
				break;
			}
			token += ch;
			++i;
		}

		return token;
	}

	protected Token findToken(int beg) throws BadLocationException{

		// get a big munch  from both sides and mix'em 

		Token token = new Token();
		String leftToken = findLeftToken(beg);
		String rightToken = findRightToken(beg);

		token.setOffset(beg - leftToken.length());			
		token.setToken(leftToken + rightToken);

		return token;
	}


	private int getNextNumTabs(int offset) throws BadLocationException{

		// get the number of tabs that should be used NEXT for ientdation 
		// offset  is the index of  '\n' 

		String ch;
		int nbTabs = 0;

		if(offset > 0 && getText(offset - 1,1).equals("{") ){
			nbTabs = 1;	
		}

		--offset;
		while(offset >= 0
			&& !((ch  = getText(offset,1)).equals("\n"))
			){
			if(ch.equals("\t")){
				++nbTabs;
			}
			--offset;
		}

		return nbTabs;
	}

	private String getIndentation(int numTabs){

		String indentation = "";	 
		for(int i=0;i<numTabs;++i){
			indentation += "\t";
		}

		return indentation;
	}

	private void  insertToken(String s,int offset,State state) throws BadLocationException{

		super.insertString(offset,s,null);

		if(state == State.NORMAL){
			addMatchingCharacter(s,offset);
			if(s.equals("\n")){
				insertIndentation(offset);
			}
		}

		if(s.equals("\n")){
			++nbLines;
		}	

		currentToken = findToken(offset);
		highlightTokens(parser.digestToken(currentToken),State.NORMAL);

	}

	private void addMatchingCharacter(String s,int offset) throws BadLocationException{

		// when increamented offset is the index of the next  char to be inserted
		++offset; 
		switch(s){
			case "{":

				super.insertString(offset,"}",null);
				textArea.setCaretPosition(offset);
				break;	

			case "(":

				super.insertString(offset,")",null);
				textArea.setCaretPosition(offset);
				break;	

			case "[": 

				super.insertString(offset,"]",null);
				textArea.setCaretPosition(offset);
				break;	

			case "\"":
			case "'" :
				super.insertString(offset,s,null);
				textArea.setCaretPosition(offset);	
				break;
		}
	}

	private void insertIndentation(int offset) throws BadLocationException{

		// offset is the location of \n
		
		boolean changeCaretPosition = false;	
		int nbTabs = getNextNumTabs(offset);	
		if(offset > 0){
			String previousChar = getText(offset - 1,1);
			if(previousChar.equals("{")){
				insertTokens("\n" + getIndentation(nbTabs - 1),offset + 1,State.NORMAL);
				changeCaretPosition = true;
			}
		}

		super.insertString(offset + 1,getIndentation(nbTabs),null);
		if(changeCaretPosition){
			textArea.setCaretPosition(offset + nbTabs + 1);
		}
	}

	private void insertTokens(String tokens,int offset,State state) throws BadLocationException{

		for(int i=0;i<tokens.length();++i){
			insertToken(tokens.charAt(i) + "",offset + i,state);
		}
	}

	private void highlightToken(Token token,State state) throws BadLocationException{

		if(token == null && parser.getLang() == null){
			return;
		}

		int len = token.getToken().length();
		int offset = token.getOffset();
		SimpleAttributeSet attr = null;

		if(parser.isKeyword(token.getToken())){
			attr = parser.getKeywordAttr();
		}else if(parser.isNumeric(token.getToken())){
			attr = parser.getNumericAttr();
		}else if(parser.isString(token.getToken())){
			attr = parser.getStringAttr();
		}else if(parser.isClass(token.getToken())){
			attr = parser.getClassAttr();
		}else{
			attr = parser.getNormalAttr();
		}

		setCharacterAttributes(offset,len,attr,true);
	}

	public void highlightTokens(ArrayList<Token> tokens,State state) throws BadLocationException{

		for(Token token:tokens){
			highlightToken(token,state);
		}
	}

	public int getNumberOfLines(){

		return nbLines;
	}

	public ArrayList<Token> search(String target){

		// search throught the document for the target string
		// in case of failure it returns an empty ArrayList not a null object

		ArrayList<Token> resTokens = new ArrayList<Token>();
		int beg = 0;

		try{
			while(beg < getLength()){

				Token token = findToken(beg);
			
				// test if beg is the starting index of some whitespace char
				if(token.getToken().equals("")){
					++beg;
					// increment until we can find a token or we reach the end of doc
					continue;
				}

				// if however its a valid token 
				// test if the token contains our target string
				if(token.getToken().contains(target)){
					Token foundToken = new Token();
					int offset = token.getOffset();
					offset += token.getToken().indexOf(target);

					foundToken.setOffset(offset);
					foundToken.setToken(target);
					resTokens.add(foundToken);
				}

				beg += token.getToken().length() + 1;
				// the reason for the +1 is that findLeftToken starts
				// one step before 
			}
		}catch(BadLocationException e){
			e.printStackTrace();
		}

		return resTokens;
	}

}	

