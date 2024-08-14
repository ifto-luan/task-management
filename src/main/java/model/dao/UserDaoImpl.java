package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import model.User;
import model.enums.Role;
import repository.Db;
import repository.DbException;

public class UserDaoImpl implements UserDao {

	private Connection connection;

	public UserDaoImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public User getById(int id) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = connection
					.prepareStatement("SELECT u.id, u.name, u.username, u.password, r.description AS role_description "
							+ "FROM public.user u " + "INNER JOIN public.role r ON r.id = u.role_id "
							+ "WHERE u.id = ? " + "ORDER BY u.id");

			st.setInt(1, id);

			rs = st.executeQuery();

			if (rs.next()) {

				return instantiateUser(rs);

			}

			return null;

		} catch (SQLException e) {

			throw new DbException(e.getMessage());

		} finally {

			Db.closeStatement(st);
			Db.closeResultSet(rs);

		}

	}

	@Override
	public List<User> getAll() {

		PreparedStatement st = null;
		ResultSet rs = null;
		List<User> users = new LinkedList<>();

		try {

			st = connection
					.prepareStatement("SELECT u.id, u.name, u.username, u.password, r.description AS role_description "
							+ "FROM public.user u " + "INNER JOIN public.role r ON r.id = u.role_id "
							+ "ORDER BY u.id");

			rs = st.executeQuery();

			while (rs.next()) {

				users.add(instantiateUser(rs));

			}

			return users;

		} catch (SQLException e) {

			throw new DbException(e.getMessage());

		} finally {

			Db.closeStatement(st);
			Db.closeResultSet(rs);

		}

	}

	@Override
	public void insert(User user) {

		PreparedStatement st = null;

		try {

			st = connection.prepareStatement(
					"INSERT INTO public.user (name, username, password, role_id) "
							+ "VALUES (? , ?, ?, (SELECT id FROM role WHERE description = ?))",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, user.getName());
			st.setString(2, user.getUsername());
			st.setString(3, user.getPassword());
			st.setString(4, user.getRole().toString());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {

				ResultSet rs = st.getGeneratedKeys();

				if (rs.next()) {

					int id = rs.getInt(1);
					user.setId(id);

				}

				Db.closeResultSet(rs);

			} else {

				throw new DbException("Unexpected error! No rows inserted!");

			}

		} catch (SQLException e) {

			throw new DbException(e.getMessage());

		} finally {

			Db.closeStatement(st);

		}

	}

	@Override
	public void update(User user) {

		PreparedStatement st = null;

		try {

			st = connection.prepareStatement("UPDATE public.user " + "SET name = ?, " + "username = ?, " + "password = ?, "
					+ "role_id = (SELECT r.id FROM public.role r WHERE r.description = ?) " + "WHERE id = ?");

			st.setString(1, user.getName());
			st.setString(2, user.getUsername());
			st.setString(3, user.getPassword());
			st.setString(4, user.getRole().toString());
			st.setInt(5, user.getId());

			st.executeUpdate();

		} catch (SQLException e) {

			throw new DbException(e.getMessage());

		} finally {

			Db.closeStatement(st);

		}

	}

	@Override
	public void delete(User u) {

		PreparedStatement st = null;

		try {

			st = connection.prepareStatement("DELETE from public.user WHERE id = ?");

			st.setInt(1, u.getId());

			st.executeUpdate();

		} catch (SQLException e) {

			throw new DbIntegrityException(e.getMessage());

		} finally {

			Db.closeStatement(st);

		}

	}

	@Override
	public List<User> getByRole(Role role) {

		PreparedStatement st = null;
		ResultSet rs = null;
		List<User> users = new LinkedList<>();

		try {

			st = connection
					.prepareStatement("SELECT u.id, u.name, u.username, u.password, r.description AS role_description "
							+ "FROM public.user u INNER JOIN public.role r ON r.id = u.role_id "
							+ "WHERE r.description = ? " + "ORDER BY u.id");

			st.setString(1, role.toString());

			rs = st.executeQuery();

			while (rs.next()) {

				users.add(instantiateUser(rs));

			}

			return users;

		} catch (SQLException e) {

			throw new DbException(e.getMessage());

		} finally {

			Db.closeStatement(st);

		}

	}

	private User instantiateUser(ResultSet rs) {

		User u = new User();

		try {

			u.setId(rs.getInt("id"));
			u.setName(rs.getString("name"));
			u.setUsername(rs.getString("username"));
			u.setPassword(rs.getString("password"));
			u.setRole(Role.getRole(rs.getString("role_description")));
			
		} catch (SQLException e) {

			throw new DbException(e.getMessage());

		}

		return u;

	}

	@Override
	public User getByUsername(String username) {
		
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = connection
					.prepareStatement("SELECT u.id, u.name, u.username, u.password, r.description AS role_description "
							+ "FROM public.user u " + "INNER JOIN public.role r ON r.id = u.role_id "
							+ "WHERE u.username = ? " + "ORDER BY u.id");

			st.setString(1, username);

			rs = st.executeQuery();

			if (rs.next()) {

				return instantiateUser(rs);

			}

			return null;

		} catch (SQLException e) {

			throw new DbException(e.getMessage());

		} finally {

			Db.closeStatement(st);
			Db.closeResultSet(rs);

		}
		
	}

}
