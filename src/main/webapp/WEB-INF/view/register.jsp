<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/assets/css/register.css">
<link rel="icon"
	href="${pageContext.request.contextPath}/assets/favicon.ico"
	type="image/x-icon">

<meta charset="UTF-8">
<title>Task Manager | Register</title>
</head>
<body>

	<header>
		<h1>Task Manager</h1>
		<c:if test="${not empty errorMessage}">
			<div class="error-message">
				<p>${errorMessage}</p>
			</div>
		</c:if>
	</header>

	<main>

		<form class="form-box"
			action="${pageContext.request.contextPath}/register" method="post">

			<div class="form-header">
				<h3>User Registration</h3>
			</div>

			<div class="form-input">

				<input type="text" id="name" name="name" required
					placeholder="Type your full name">

				<input type="text" id="login" name="username" required
					placeholder="Type your username">
					
				<input type="password"
					id="password" name="password" required
					placeholder="Type new password">
					
				<input type="password"
					id="confirm-password" name="confirm-password" required
					placeholder="Confirm your password">

			</div>

			<div class="form-button">
				<input type="submit" value="Sign-up">
			</div>
		</form>
	</main>

</body>
</html>