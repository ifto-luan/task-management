package controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import model.dao.DaoFactory;
import model.dao.UserDao;
import util.PasswordHasher;

@WebServlet("/login")
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private UserDao userDao;

	@Override
	public void init() throws ServletException {

		userDao = DaoFactory.createUserDao();

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String username = request.getParameter("login");
		String password = request.getParameter("password");

		if (session.getAttribute("user") == null) {

			if (username != null && !username.isBlank() && password != null & !password.isBlank()) {

				User u = userDao.getByUsername(username);

				if (u != null) {

					try {

						if (PasswordHasher.verifyPassword(u.getPassword(), password)) {

							session.setAttribute("user", u);
							
							if (u.isAdmin()) {
								
								response.sendRedirect("admin/home");
								
							} else {
								
								response.sendRedirect("home");								
								
							}
							

						} else {

							request.setAttribute("errorMessage", "Incorrect password");
							request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);

						}

					} catch (NoSuchAlgorithmException e) {

						throw new ServletException(
								"Unexpected error! The hash algorithm is not avaliable in this environment!");

					}

				} else {

					request.setAttribute("errorMessage", "Invalid username");
					request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);

				}

			} else {

				request.setAttribute("errorMessage", "Please, inform both fields");
				request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);

			}

		} else {

			response.sendRedirect("home");
		}
	}

}
