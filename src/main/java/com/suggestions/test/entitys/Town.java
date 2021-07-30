package com.suggestions.test.entitys;

public class Town implements Comparable<Town>{

	private String name;
	private String late;
	private String lon;
	private Double score;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLate() {
		return late;
	}
	public void setLate(String late) {
		this.late = late;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public Town( String name, String late, String lon, Double score) {
		super();
		this.name = name;
		this.late = late;
		this.lon = lon;
		this.score = score;
	}

	public Town() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int compareTo(Town comparestu) {
		
		int auxComp = (int) Math.round(comparestu.getScore() * 10);
		int auxCompThis = (int) Math.round(this.score * 10);
		
		return auxComp - auxCompThis;
    }
}
