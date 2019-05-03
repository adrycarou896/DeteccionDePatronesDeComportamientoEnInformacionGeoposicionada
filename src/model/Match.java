package model;

import java.io.Serializable;
import java.util.Date;

import org.json.JSONObject;

public class Match implements Serializable {
	
	private static final long serialVersionUID = -3709323805785851011L;
	private Camera camera;
	private Person person;
	private Date date;
	
	public Match() {}
	
	public Match(Camera camera, Person person, Date date) {
		this.camera = camera;
		this.person = person;
		this.date = date;
		
		//java.sql.Date date2 = new java.sql.Date(d.getTime());
		
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public JSONObject getJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("camera", this.camera.getJson());
		jsonObject.put("person", this.person.getJson());
		jsonObject.put("date", this.date.getTime());
		return jsonObject;
	}
	
}
