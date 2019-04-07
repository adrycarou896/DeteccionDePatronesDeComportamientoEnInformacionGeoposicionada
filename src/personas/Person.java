package personas;

public class Person {
	
	private int label;
	private String nombre;
	private boolean detectado = true;
	private long ultimaVezDetectado = 0;
	private boolean enClase = false;
	private int numVecesNoApareceRostro = 0;
	
	public Person(int label, long ultimaVezDetectado) {
		this.label = label;
		this.ultimaVezDetectado = ultimaVezDetectado;
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

	public long getUltimaVezDetectado() {
		return ultimaVezDetectado;
	}

	public void setUltimaVezDetectado(long ultimaDate) {
		this.ultimaVezDetectado = ultimaDate;
	}

	public boolean isEnClase() {
		return enClase;
	}

	public void setEnClase(boolean enClase) {
		this.enClase = enClase;
	}

	public int getNumVecesNoApareceRostro() {
		return numVecesNoApareceRostro;
	}

	public void setNumVecesNoApareceRostro(int numVecesNoApareceRostro) {
		this.numVecesNoApareceRostro = numVecesNoApareceRostro;
	}
	
	
}
