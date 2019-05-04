package model;

import java.io.Serializable;

import org.json.JSONObject;

public class Camera implements Serializable {
	
	 private static final long serialVersionUID = 3560972546182458142L;
	 
	 private String name;
	 
	 public Camera() {}
	 
	 public Camera(String name) {
		 this.name = name;
	 }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public JSONObject getJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", this.name);
		return jsonObject;
	}
}

