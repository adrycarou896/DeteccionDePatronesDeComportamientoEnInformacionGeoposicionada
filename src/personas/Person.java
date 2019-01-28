package personas;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import reconocimiento.FeatureExtractionImage;

public class Person {
	
	private List<FeatureExtractionImage> featureExtractionImageLista;
	
	private String nombre;
	
	private String ruta;
	
	public Person(String nombre){
		this.nombre = nombre;
		
		this.ruta = "img/"+this.nombre;
		File directorio=new File(this.ruta); 
		directorio.mkdir(); 
		
		this.featureExtractionImageLista = new ArrayList<FeatureExtractionImage>();
	}
	
	public void addFeatureExtractionImage(String rutaImagenAnterior, Mat frame){
		String nuevaRuta = ruta+"/"+nombre+".jpg";
		Imgcodecs.imwrite(nuevaRuta, frame);
		this.featureExtractionImageLista.add(new FeatureExtractionImage(nuevaRuta));
	}

	public List<FeatureExtractionImage> getFeatureExtractionImageLista() {
		return featureExtractionImageLista;
	}

	public void setFeatureExtractionImageLista(List<FeatureExtractionImage> featureExtractionImageLista) {
		this.featureExtractionImageLista = featureExtractionImageLista;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
	
}
