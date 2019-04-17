package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class Camera implements Serializable {
	
	 private static final long serialVersionUID = 3560972546182458142L;
	 private long identificador;
	 private List<Camera> observers;
	 
	 public Camera() {}
	 
	 public Camera(long identificador) {
		 this.identificador = identificador;
		 this.observers = new ArrayList<Camera>();
	 }
	 
	 public Camera(long identificador, List<Camera> observers) {
		 this.identificador = identificador;
		 this.observers = observers;
	 }

	public long getIdentificador() {
		return identificador;
	}

	public void setIdentificador(long identificador) {
		this.identificador = identificador;
	}

	public List<Camera> getObservers() {
		return observers;
	}

	public void setObservers(List<Camera> observers) {
		this.observers = observers;
	}
	 
	public JSONObject getJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("identificador", this.identificador);
		jsonObject.put("observers", this.observers);
		return jsonObject;
	}
}

