package personas;

public class Person {
	
	private int label;
	private String nombre;
	private boolean detectado = false;
	private long ultimaDate = 0;
	private boolean estaEnClase = false;
	
	public Person(int label) {
		this.label = label;
		this.nombre = "usuario"+label; 
	}

	public int getLabel() {
		return label;
	}

	public void setLabel(int label) {
		this.label = label;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isDetectado() {
		return detectado;
	}

	public void setDetectado(boolean detectado) {
		this.detectado = detectado;
	}

	public long getUltimaDate() {
		return ultimaDate;
	}

	public void setUltimaDate(long ultimaDate) {
		this.ultimaDate = ultimaDate;
	}

	public boolean isEstaEnClase() {
		return estaEnClase;
	}

	public void setEstaEnClase(boolean estaEnClase) {
		this.estaEnClase = estaEnClase;
	}
	
	
}
