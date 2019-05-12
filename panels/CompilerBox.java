
package panels;

import javax.swing.JEditorPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.JOptionPane;

import java.awt.Desktop;

import java.net.URL;

import java.io.IOException;
import java.io.File;

// might need more refactoring !

public class CompilerBox extends JEditorPane{   

    public CompilerBox(){

        setContentType("text/html");
        setEditable(false);
        setBorder(new TitledBorder("◄ Compiler Box ►"));
        addHyperlinkListener();
    }

    public void setToken(String token){

         try{
            setText(parseToken(token));       
        }catch(Exception e){   
            e.printStackTrace();  
        }
    }
   

    private String parseToken( String token) throws Exception {
        // Exception should be replaced with the appropiate errors

        String[] words = token.split("\n");
        StringBuilder newToken = new StringBuilder("<html><head><style>.foot{color:red} .head{color:black}</style></head>"+ "<body><font size='+2'>");
        String exceptionIndicator = "Exception in thread \"main\" ";

        for(String word:words){
            int beg = word.indexOf(exceptionIndicator);
            int end = word.indexOf(":");

            if(beg != -1 && end != -1){
                beg += exceptionIndicator.length();
                newToken.append(word.substring(0,beg));
                String exceptionName = word.substring(beg,end);
                newToken.append(hilightString(exceptionName));
                newToken.append(word.substring(end) + "\n");
            }else{
                newToken.append(word + "\n");
            }

        }

        newToken.append("</font></body></html>");
        return newToken.toString();
    }

    private  String hilightString(String word){

        return "<span><font color='#CC13DA'><a href='" + word + "'>" + word + "</a>"+" "+"</font></span>";
    }

    private void addHyperlinkListener(){     
   
        super.addHyperlinkListener(new HyperlinkListener() {

            public void hyperlinkUpdate(HyperlinkEvent e) {

                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    String url = e.getDescription();
                    File documentationFile = new File(url);
                    try{
                        Desktop.getDesktop().browse(documentationFile.toURI());
                    }catch(IOException ex){

                        String errorMsg = "It seemls like the Documentation file has ";
                        errorMsg += "been mistakenly deleted please redownload it again !";

                        JOptionPane.showMessageDialog(null,errorMsg,"Error !",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        
        });

    }

 

}
