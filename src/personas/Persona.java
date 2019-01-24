package personas;

import org.opencv.core.Rect;

public class Persona {
	
	private String nombre;
	private Rect rostro;
	private String rutaImagen;
	
	public Persona(String nombre, Rect rostro, String rutaImagen){
		this.nombre = nombre;
		this.rostro = rostro;
		this.rutaImagen = rutaImagen;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Rect getRostro() {
		return rostro;
	}

	public void setRostro(Rect rostro) {
		this.rostro = rostro;
	}

	public String getRutaImagen() {
		return rutaImagen;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}
	
	
}
