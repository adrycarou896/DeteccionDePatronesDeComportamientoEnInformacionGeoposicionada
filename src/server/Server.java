package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONObject;

import model.Camera;
import model.Match;
import model.Person;

public class Server {
	
	private static final String URL_INSERT_MATCH = "http://localhost:8090/sendFrame/insertMatch";
	
	public void sendMatch(long cameraId, long personId, Date fecha) throws IOException {
		URL url = null;
	    url = new URL(URL_INSERT_MATCH);
	    HttpURLConnection urlConn = null;
	    urlConn = (HttpURLConnection) url.openConnection();
	    urlConn.setDoInput (true);
	    urlConn.setDoOutput (true);
	    urlConn.setRequestMethod("POST");
	    urlConn.setRequestProperty("Content-Type", "application/json");
	    urlConn.connect();

	    DataOutputStream output = null;
	    DataInputStream input = null;
	    output = new DataOutputStream(urlConn.getOutputStream());
	    
	    Camera camera = new Camera(cameraId, new ArrayList<Camera>());
	    Person person = new Person(personId);
	    
	    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	    String day = format.format(fecha);
	    
	    format = new SimpleDateFormat("HH:mm:ss");
	    String hour = format.format(fecha);
	    
	    Match match = new Match(camera, person, day, hour);
	    
	    JSONObject jsonObject = match.getJson();
	    
	    String content = jsonObject.toString();

	    /* Send the request data.*/
	    output.writeBytes(content);
	    output.flush();
	    output.close();

	    /* Get response data.*/
	    String response = null;
	    input = new DataInputStream (urlConn.getInputStream());
	    while (null != ((response = input.readLine()))) {
	        System.out.println(response);
	    }
	    input.close ();
	}
}
