
package ide;


public class Token{

	private int offset;
	private String token;

	public Token(String token,int offset){
		this.token = token;
		this.offset = offset;
	}

	public Token(){
		this(null,0);
	}

	public int getOffset(){
		return offset;
	}

	public void setOffset(int offset){
		
		this.offset = offset;
	}


	public  String getToken(){

		return token;
	}

	public void setToken(String token){

		this.token = token;
	}
}
