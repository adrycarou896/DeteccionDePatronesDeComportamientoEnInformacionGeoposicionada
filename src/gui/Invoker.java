package gui;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class Invoker {
	public static void main(String[] args) {	
		/*
		System.load("C:\\opencv\\build\\java\\x64\\opencv_java400.dll");
		
    	Recorder recorder = new Recorder();
    	Thread thRecorder = new Thread(recorder);
    	
    	Reader reader = new Reader();
    	Thread thReader = new Thread(reader);
        
        thReader.run();*/
    	//thRecorder.run();
		
		System.load("C:\\opencv\\build\\java\\x64\\opencv_java400.dll");
		
		Entrenar entrenamiento = new Entrenar();
		entrenamiento.run();
		
		Reader reader = new Reader(entrenamiento);
		reader.run();
		
		
    }
	
	public static void generarImagenesEnFormatoAdecuado(String rutaCarpetaOrigen, String extensionImagen, int numImagenes, String rutaCarpetaDestino){
		for (int i = 1; i < numImagenes; i++) {
			try {
				InputStream input = new FileInputStream(rutaCarpetaOrigen+"/img"+i+"."+extensionImagen);
				int label = i+193;
				String srcSalida=rutaCarpetaDestino+"/img"+label+".jpg";
				OutputStream output = new FileOutputStream(srcSalida);
				try {
					resize(input, output, 607, 607);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void resize(InputStream input, OutputStream output, int width, int height) throws Exception {
	    BufferedImage src = ImageIO.read(input);
	    BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g = dest.createGraphics();
	    AffineTransform at = AffineTransform.getScaleInstance
	    		((double)width / src.getWidth(), 
	    				(double)height / src.getHeight());
	    g.drawRenderedImage(src, at);
	    ImageIO.write(dest, "JPG", output);
	    output.close();
	}
	
	
}
