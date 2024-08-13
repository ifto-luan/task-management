package model.dao;

import java.util.List;

import model.User;
import model.enums.Role;

public interface UserDao extends GenericDao<User> {

	List<User> getByRole(Role role);

}
