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

@WebServlet("/forgotPassword")
public class ChangePasswordController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private UserDao userDao;

	@Override
	public void init() throws ServletException {

		userDao = DaoFactory.createUserDao();

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.getRequestDispatcher("/WEB-INF/view/forgotPassword.jsp").forward(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String username = req.getParameter("username");
		String newPassword = req.getParameter("new-password");
		String confirmNewPassword = req.getParameter("confirm-new-password");

		if (username != null && newPassword != null && confirmNewPassword != null && !username.isBlank()
				&& !newPassword.isBlank() && !confirmNewPassword.isBlank()) {

			User u = userDao.getByUsername(username);

			if (u != null) {

				if (newPassword.equals(confirmNewPassword)) {

					try {

						if (!PasswordHasher.verifyPassword(u.getPassword(), newPassword)) {

							String hashedNewPassword = PasswordHasher.hashPassword(newPassword, PasswordHasher.getSalt());
							u.setPassword(hashedNewPassword);
							userDao.update(u);

							HttpSession session = req.getSession();
							User loggedUser = (User) session.getAttribute("user");

							if (session != null && loggedUser != null) {

								session.invalidate();
								
							}

							resp.sendRedirect("login?successMessage=Password+updated");

						} else {

							req.setAttribute("errorMessage", "Cannot use your current password");
							req.getRequestDispatcher("/WEB-INF/view/forgotPassword.jsp").forward(req, resp);
						}
						
					} catch (NoSuchAlgorithmException e) {

						throw new ServletException("The encryption algorithm is not support by the environment");

					}

				} else {

					req.setAttribute("errorMessage", "Passwords don't match");
					req.getRequestDispatcher("/WEB-INF/view/forgotPassword.jsp").forward(req, resp);

				}

			} else {

				req.setAttribute("errorMessage", "Invalid user");
				req.getRequestDispatcher("/WEB-INF/view/forgotPassword.jsp").forward(req, resp);

			}

		} else {

			req.setAttribute("errorMessage", "Pleasem, inform all fields to proceed");
			req.getRequestDispatcher("/WEB-INF/view/forgotPassword.jsp").forward(req, resp);

		}

	}

}
