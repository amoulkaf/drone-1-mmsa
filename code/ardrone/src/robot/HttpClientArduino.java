package robot;


import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpResponse;


public class HttpClientArduino {

private static String NETWORK = "http://192.168.1.50:8080/"; //Arduino Network
		
    @SuppressWarnings({ "deprecation", "resource" })
	public static void sendGet(String order) throws Exception {
		HttpClient client = new DefaultHttpClient();
		System.out.println("ORDER : "+order);
		HttpGet request = new HttpGet(NETWORK + "?%24=" + order);
	 
		HttpResponse response = client.execute(request);
	 
		System.out.println("Response Code : " +  response.getStatusLine().getStatusCode());
    }
	
}
	

