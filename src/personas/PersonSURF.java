package personas;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class PersonSURF {
	
	private List<String> imagenes;
	
	private String nombre;
	
	private String ruta;
	
	public PersonSURF(String nombre){
		this.nombre = nombre;
		
		this.ruta = "img/"+this.nombre;
		File directorio=new File(this.ruta); 
		directorio.mkdir(); 
		
		this.imagenes = new ArrayList<String>();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<String> getImagenes() {
		return imagenes;
	}

	public void setImagenes(List<String> imagenes) {
		this.imagenes = imagenes;
	}
	
	public void addImagen(String rutaImagenAnterior, Mat frame){
		String nuevaRuta = ruta+"/imagen"+imagenes.size()+".jpg";
		Imgcodecs.imwrite(nuevaRuta, frame);
		this.imagenes.add(nuevaRuta);
	}
	
	
	
}
