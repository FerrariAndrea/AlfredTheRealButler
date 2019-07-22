import java.io.BufferedReader;
import java.io.InputStreamReader;

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
}
