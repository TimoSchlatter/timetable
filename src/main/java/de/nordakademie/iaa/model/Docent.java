package de.nordakademie.iaa.model;

import java.util.Set;

public class Docent extends HasMinChangeoverTime {

	private String email;
	private String forename;
	private String surname;
	private String phoneNumber;
	private String title;
	private Set<Course> courses;
	private boolean isPermanentlyEmployed;

}