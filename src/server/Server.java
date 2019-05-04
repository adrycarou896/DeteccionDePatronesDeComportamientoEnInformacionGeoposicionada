package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;

import org.json.JSONObject;

import model.Camera;
import model.Match;
import model.Person;

public class Server {
	
	private static final String URL_INSERT_MATCH = "http://localhost:8090/sendFrame/insertMatch";
	
	public void sendMatch(long cameraId, long personId, Date fecha) throws IOException, ParseException {
	    
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
	    
	    String cameraName = "camera" + cameraId;
	    String personName = "person"+ personId;
	    Camera camera = new Camera(cameraName);
	    Person person = new Person(personName);
	    
	    /*SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	    String day = format.format(fecha);
	    
	    format = new SimpleDateFormat("HH:mm:ss");
	    String hour = format.format(fecha);*/
	    
	    Match match = new Match(camera, person, new Date());
	    
	    JSONObject jsonObject = match.getJson();
	    
	    String content = jsonObject.toString();
	    output.writeBytes(content);
	    output.flush();
	    output.close();

	    String response = null;
	    input = new DataInputStream (urlConn.getInputStream());
	    while (null != ((response = input.readLine()))) {
	        System.out.println(response);
	    }
	    input.close ();
	}
}
