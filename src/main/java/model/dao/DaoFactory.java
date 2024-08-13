package model.dao;

import repository.Db;

public class DaoFactory {

	public static UserDao createUserDao() {
		return new UserDaoImpl(Db.getConnection());
	}
	
	public static TaskDao createTaskDao() {
		return new TaskDaoImpl(Db.getConnection());
	}

}
