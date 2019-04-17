package model;

import java.io.Serializable;

import org.json.JSONObject;

public class Match implements Serializable {
	
	private static final long serialVersionUID = -3709323805785851011L;
	private Camera camera;
	private Person person;
	private String day;
	private String hour;
	
	public Match() {}
	
	public Match(Camera camera, Person person, String day, String hour) {
		this.camera = camera;
		this.person = person;
		this.day = day;
		this.hour = hour;
		
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

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}
	
	public JSONObject getJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("camera", this.camera.getJson());
		jsonObject.put("person", this.person.getJson());
		jsonObject.put("day", this.day);
		jsonObject.put("hour", this.hour);
		return jsonObject;
	}
	
}
