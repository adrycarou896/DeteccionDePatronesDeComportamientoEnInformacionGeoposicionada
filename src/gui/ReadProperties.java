package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {
	
	private static final String FILEPATH="src/cameraData.properties";
	
	public long getCameraIdentificator() throws FileNotFoundException, IOException {
		
		long cameraId = -1;
		try (InputStream input = new FileInputStream(FILEPATH)) {
			Properties prop = new Properties();
            prop.load(input);
            cameraId = Long.parseLong(prop.getProperty("identificator"));
		}
		return cameraId;
	}
}
