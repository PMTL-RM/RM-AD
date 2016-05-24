import java.util.Scanner;
public class GG{
	public static void main(String[] argv){
		Scanner input = new Scanner(System.in);
		while(input.hasNext()){
			String a = input.next();
			String ans = "";
			if(a.equals("end")){
				break;
			}
			else{
				try{
					if(Integer.parseInt(String.valueOf(a.charAt(1)))>0){
						for(int i=0;i<a.length();i+=2){
							for(int j=0;j<Character.getNumericValue(a.charAt(i+1));j++){
								ans += a.charAt(i);
							}
						}
						System.out.println(ans);
					}
				}
				catch(Exception err){
					char s = a.charAt(0);
					int count = 1;
					for(int i=1;i<a.length();i++){
						if(a.charAt(i) == s){
							count++;
						}
						else{
							ans += count+String.valueOf(s);
							s = a.charAt(i);
							count = 1;
						}
					}
					ans += count+String.valueOf(a.charAt(a.length()-1));
					System.out.println(ans);
				}
			}
		}
	}
}