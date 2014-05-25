package org.code2bytes.samples.lambda;

public class Person {

	private String name;

	private int ageInYears;

	public Person(String name, int ageInYears) {
		this.name = name;
		this.ageInYears = ageInYears;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAgeInYears() {
		return ageInYears;
	}

	public void setAgeInYears(int ageInYears) {
		this.ageInYears = ageInYears;
	}
}
