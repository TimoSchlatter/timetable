package de.nordakademie.iaa.model;

import javax.persistence.Entity;

@Entity
public class Lecture extends Subject {

	private Course course;
	private boolean isExam;

	public Lecture() {}

	public Lecture(Course course, boolean isExam) {
		this.course = course;
		this.isExam = isExam;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public boolean isExam() {
		return isExam;
	}

	public void setExam(boolean exam) {
		isExam = exam;
	}

	@Override
	public String getTitle() {
		return null;
	}
}