import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class ConnectedUser {

	
	public static int numberOfConnectedUser() {
		try{
			Process p = Runtime.getRuntime().exec("./ask.sh");
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String data = reader.readLine();
			int count =0;
			while(data!=null){
				count++;
	 			data = reader.readLine();
 	 		}
			return count;
 		}catch(Exception e){
 			System.out.println(" ERROR " + e.getMessage() );
		}
		return 0;
	}
	
	public static String getGateway() {
		try{
			Process p = Runtime.getRuntime().exec("./network.sh");
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String data = reader.readLine();
			if(data!=null && data.length()>0) {
				return data;
			}else {
				return null;
			}
 		}catch(Exception e){
 			System.out.println(" ERROR " + e.getMessage() );
		}
		return null;
	}
	
}
