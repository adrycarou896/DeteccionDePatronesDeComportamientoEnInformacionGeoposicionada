package personas;

import java.util.List;

import org.opencv.core.Rect;

import reconocimiento.FeatureExtractionImage;
import reconocimiento.FeatureExtractionImage2;

public class Persona {
	
	private String nombre;
	private String ruta;
	private FeatureExtractionImage featureExtractionImage;
	
	public Persona(String nombre){
		this.nombre = nombre;
		this.ruta = "img/"+this.nombre+".jpg";
		this.featureExtractionImage = new FeatureExtractionImage2(this.ruta);
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public FeatureExtractionImage getFeatureExtractionImage() {
		return featureExtractionImage;
	}

	public void setFeatureExtractionImage(FeatureExtractionImage featureExtractionImage) {
		this.featureExtractionImage = featureExtractionImage;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
	
}
