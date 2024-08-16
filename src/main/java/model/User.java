package model;

import java.util.List;

import model.dao.DaoFactory;
import model.enums.Role;

public class User {

	private int id;
	private String name;
	private String username;
	private String password;
	private Role role;
	private List<Task> tasks;
	
	public User() {
		
	}
	
	public User(int id, String name, String username, String password, Role role) {

		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Task> getTasks() {
		
		return tasks == null ? DaoFactory.createTaskDao().getByUser(this) : tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public boolean isAdmin() {
		return role == Role.ADMIN;
	}
	
	public boolean isSupervisor() {
		return role == Role.SUPERVISOR;
	}
	
	public boolean isNormalUser() {
		return role == Role.USER;
	}
	
	@Override
	public String toString() {
		return id + " - " + name + " - " + username + " - " + password + " - " + role;
	}

}
