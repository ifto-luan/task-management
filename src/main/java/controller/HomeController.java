package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Task;
import model.User;
import model.dao.DaoFactory;
import model.dao.TaskDao;
import model.dao.UserDao;

@WebServlet("/home")
public class HomeController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private TaskDao taskDao;
	private UserDao userDao;

	@Override
	public void init() throws ServletException {
		taskDao = DaoFactory.createTaskDao();
		userDao = DaoFactory.createUserDao();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getParameter("action");
		User u = (User) req.getSession().getAttribute("user");

		if ("add".equals(action)) {

			String taskDescription = req.getParameter("newTaskDescription");

			Task t = new Task();
			t.setDescription(taskDescription);
			t.setDone(false);
			t.setUser(userDao.getById(u.getId()));

			taskDao.insert(t);
			
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.getWriter().print(t.getId());

		} else if ("markAsDone".equals(action)) {

			int taskId = Integer.parseInt(req.getParameter("taskId"));

			taskDao.updateTaskStatusById(taskId, true);
			resp.setStatus(HttpServletResponse.SC_OK);

		} else if ("markAsPending".equals(action)) {

			int taskId = Integer.parseInt(req.getParameter("taskId"));

			taskDao.updateTaskStatusById(taskId, false);
			resp.setStatus(HttpServletResponse.SC_OK);

		} else if ("delete".equals(action)) {
			
			int taskId = Integer.parseInt(req.getParameter("taskId"));
			
			taskDao.delete(taskId);
			resp.setStatus(HttpServletResponse.SC_OK);			
			
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");

		if (session != null && user != null) {

			List<Task> pendingTasks = taskDao.getByUserAndStatus(user, false);
			List<Task> doneTasks = taskDao.getByUserAndStatus(user, true);

			req.setAttribute("pendingTasks", pendingTasks);
			req.setAttribute("doneTasks", doneTasks);

			req.getRequestDispatcher("/WEB-INF/view/home.jsp").forward(req, resp);

		} else {

			resp.sendRedirect("login");

		}

	}

}
