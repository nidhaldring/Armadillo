
package helper;


import static java.io.File.separator;

public class StringHelper{


	public static String reverse(String s){

		String res = "";
		int len = s.length();
		for(int i=1;i<=len;++i){

			res += s.charAt(len-i);
		}
		return res;
	}

	public static int getNumberOfLines(String s){

		int nb = 0;
		for(int i =0;i<s.length();++i){

			if(s.charAt(i) == '\n'){
				++nb;
			}
		}

		return nb;
	}

	public static String toPath(String a,String b){

		return a + separator + b;
	}


}
