import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpResponse;

@SuppressWarnings("deprecation")
public class HttpClientArduino {
	
	
    public static void sendGet(String order) throws Exception {
        
	HttpClient client = new DefaultHttpClient();
	HttpGet request = new HttpGet("http://192.168.0.1/?%24=" + order);
 
	HttpResponse response = client.execute(request);
 
	System.out.println("Response Code : " +  response.getStatusLine().getStatusCode());

    }
	
}
