package com.alibaba.alisonar.domain;

public class Project {
	private String name;
	private Integer stars;
	private Integer forks;
	private String description;
	

	public Project() {
		super();
	}

	public Project(String name, Integer stars, Integer forks, String description) {
		super();
		this.name = name;
		this.stars = stars;
		this.forks = forks;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStars() {
		return stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}

	public Integer getForks() {
		return forks;
	}

	public void setForks(Integer forks) {
		this.forks = forks;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Project [name=" + name + ", stars=" + stars + ", forks=" + forks + ", description=" + description + "]";
	}
	
	

}
