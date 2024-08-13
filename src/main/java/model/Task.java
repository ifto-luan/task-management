package model;

import java.time.LocalDate;

public class Task {

	private int id;
	private String description;
	private User user;
	private boolean done;
	private LocalDate addDate;

	public Task() {

		this.done = false;

	}

	public Task(String description, User user, LocalDate addDate) {

		this.description = description;
		this.user = user;
		this.done = false;
		this.addDate = addDate;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public void toggleDone() {
		this.done = !this.done;
	}

	public LocalDate getAddDate() {
		return addDate;
	}

	public void setAddDate(LocalDate addDate) {
		this.addDate = addDate;
	}

	@Override
	public String toString() {
		return this.user.getName() + " - " + this.description + " - " + (this.done ? "Done" : "Not Done");
	}

}
