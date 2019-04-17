package model;
import java.io.Serializable;

import org.json.JSONObject;

public class Person implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7915536599168677189L;
	
	private long identificador;
	
	public Person() {}
	
	public Person(long identificador) {
		this.identificador = identificador;
	}

	public long getIdentificador() {
		return identificador;
	}

	public void setIdentificador(long identificador) {
		this.identificador = identificador;
	}
	
	public JSONObject getJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("identificador", this.identificador);
		return jsonObject;
	}

}

