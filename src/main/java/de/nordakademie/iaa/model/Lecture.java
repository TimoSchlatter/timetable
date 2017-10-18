package de.nordakademie.iaa.model;

public class Lecture extends Subject {

	private Course course;
	private boolean isExam;

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