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

@WebServlet("/home")
public class HomeController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private TaskDao taskDao;
	
	@Override
	public void init() throws ServletException {
		taskDao = DaoFactory.createTaskDao();
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
