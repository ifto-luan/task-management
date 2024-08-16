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
import model.enums.Role;
import util.PasswordHasher;

@WebServlet("/register")
public class RegisterController extends HttpServlet {

	private static final long serialVersionUID = 7804524886360637172L;

	private UserDao userDao;

	@Override
	public void init() throws ServletException {

		userDao = DaoFactory.createUserDao();

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();

		if (session != null && session.getAttribute("user") != null) {
			resp.sendRedirect("home");
			return;
		}

		req.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();

		if (session.getAttribute("user") == null) {

			String name = req.getParameter("name");
			String username = req.getParameter("username");
			String newPassword = req.getParameter("password");
			String confirmNewPassword = req.getParameter("confirm-password");

			if (name != null && username != null && newPassword != null && confirmNewPassword != null &&  !name.isBlank() && !username.isBlank()
					&& !newPassword.isBlank() && !confirmNewPassword.isBlank()) {

				if (userDao.getByUsername(username) == null) {

					if (newPassword.equals(confirmNewPassword)) {

						try {

							String hashedPassword = PasswordHasher.hashPassword(newPassword, PasswordHasher.getSalt());

							User u = new User();
							u.setName(name);
							u.setUsername(username);
							u.setRole(Role.USER);
							u.setPassword(hashedPassword);
							userDao.insert(u);
							resp.sendRedirect("login?successMessage=User+created");

						} catch (NoSuchAlgorithmException e) {

							throw new ServletException("The encryption algorithm is not support by the environment");

						}

					} else {

						req.setAttribute("errorMessage", "Passwords don't match");
						req.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(req, resp);

					}

				} else {

					req.setAttribute("errorMessage", "Username already in use");
					req.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(req, resp);

				}

			} else {

				req.setAttribute("errorMessage", "Please, inform all fields");
				req.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(req, resp);

			}

		} else {

			resp.sendRedirect("home");
		}

	}

}
