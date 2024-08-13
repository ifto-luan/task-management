package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import model.Task;
import model.User;
import repository.Db;
import repository.DbException;

public class TaskDaoImpl implements TaskDao {

	private Connection connection;

	public TaskDaoImpl(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Task getById(int id) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = connection.prepareStatement("SELECT * FROM public.task WHERE id = ? ORDER BY id");
			st.setInt(1, id);

			rs = st.executeQuery();

			if (rs.next()) {

				return instantiateTask(rs);
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
	public List<Task> getAll() {

		PreparedStatement st = null;
		ResultSet rs = null;
		List<Task> tasks = new LinkedList<>();

		try {

			st = connection.prepareStatement("SELECT * FROM public.task ORDER BY id");

			rs = st.executeQuery();

			while (rs.next()) {

				tasks.add(instantiateTask(rs));

			}

		} catch (SQLException e) {

			throw new DbException(e.getMessage());

		} finally {

			Db.closeStatement(st);
			Db.closeResultSet(rs);

		}

		return tasks;

	}

	@Override
	public void insert(Task t) {

		PreparedStatement st = null;

		try {

			st = connection.prepareStatement(
					"INSERT INTO public.task (description, user_id, done, add_date) " + "VALUES (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, t.getDescription());
			st.setInt(2, t.getUser().getId());
			st.setBoolean(3, t.isDone());
			st.setDate(4, Date.valueOf(LocalDate.now()));

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {

				ResultSet rs = st.getGeneratedKeys();

				if (rs.next()) {

					t.setId(rs.getInt(1));

				}

				Db.closeResultSet(rs);

			} else {

				throw new DbException("Unexpected error! No rows affected!");
			}

		} catch (SQLException e) {

			throw new DbException(e.getMessage());

		} finally {

			Db.closeStatement(st);

		}

	}

	@Override
	public void update(Task t) {

		PreparedStatement st = null;

		try {

			st = connection
					.prepareStatement("UPDATE public.task " + "SET description = ?, " + "done = ? " + "WHERE id = ?");

			st.setString(1, t.getDescription());
			st.setBoolean(2, t.isDone());
			st.setInt(3, t.getId());

			st.executeUpdate();

		} catch (SQLException e) {

			throw new DbException(e.getMessage());

		} finally {

			Db.closeStatement(st);

		}

	}

	@Override
	public List<Task> getByUser(User u) {

		PreparedStatement st = null;
		ResultSet rs = null;
		List<Task> tasks = new LinkedList<>();

		try {

			st = connection.prepareStatement("SELECT * FROM public.task WHERE user_id = ? ORDER BY id");
			st.setInt(1, u.getId());

			rs = st.executeQuery();

			while (rs.next()) {

				tasks.add(instantiateTask(rs));

			}

		} catch (SQLException e) {

			throw new DbException(e.getMessage());

		} finally {

			Db.closeStatement(st);
			Db.closeResultSet(rs);

		}

		return tasks;

	}

	@Override
	public List<Task> getByStatus(boolean done) {

		PreparedStatement st = null;
		ResultSet rs = null;
		List<Task> tasks = new LinkedList<>();

		try {

			st = connection.prepareStatement("SELECT * FROM public.task WHERE done = ? ORDER BY id");
			st.setBoolean(1, done);

			rs = st.executeQuery();

			while (rs.next()) {

				tasks.add(instantiateTask(rs));

			}

		} catch (SQLException e) {

			throw new DbException(e.getMessage());

		} finally {

			Db.closeStatement(st);
			Db.closeResultSet(rs);

		}

		return tasks;

	}

	@Override
	public void delete(Task t) {

		PreparedStatement st = null;

		try {

			st = connection.prepareStatement("DELETE from public.task WHERE id = ?");

			st.setInt(1, t.getId());

			st.executeUpdate();

		} catch (SQLException e) {

			throw new DbIntegrityException(e.getMessage());

		} finally {

			Db.closeStatement(st);

		}

	}

	private Task instantiateTask(ResultSet rs) {

		Task t = new Task();

		try {

			t.setId(rs.getInt("id"));
			t.setDescription(rs.getString("description"));
			t.setDone(rs.getBoolean("done"));
			t.setAddDate(rs.getDate("add_date").toLocalDate());
			t.setUser(DaoFactory.createUserDao().getById(rs.getInt("user_id")));

		} catch (SQLException e) {

			throw new DbException(e.getMessage());

		}

		return t;

	}

}
