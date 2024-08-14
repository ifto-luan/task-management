package model.dao;

import java.util.List;

import model.Task;
import model.User;

public interface TaskDao extends GenericDao<Task> {

	List<Task> getByUser(User u);
	List<Task> getByStatus(boolean done);
	List<Task> getByUserAndStatus(User u, boolean done);
	void updateTaskStatusById(int taskId, boolean status);

}
