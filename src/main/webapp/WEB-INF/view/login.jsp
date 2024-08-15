<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/assets/css/styles.css">
<link rel="icon" href="${pageContext.request.contextPath}/assets/favicon.ico" type="image/x-icon">
<title>Task Manager | Welcome!</title>
</head>
<body>

	<header>
	
		<c:if test="${not empty errorMessage}">		
			<div class="error-message">
				<p>${errorMessage}</p>
			</div>
		</c:if>
	
	</header>

	<main>

		<form class="form-box" action="${pageContext.request.contextPath}/login" method="post">

			<div class="form-header">
				<h1>Task Manager</h1>
			</div>

			<div class="form-input">

				<input type="text" id="login" name="login" required
					placeholder="Type your username"> <input type="password"
					id="password" name="password" required
					placeholder="Type your password">

			</div>

			<div class="form-button">

				<input type="submit" value="Sign-in">

			</div>

			<div class="form-options">
				<a href="register">Sign-up</a> <a href="update-password">Forgot
					your password?</a>
			</div>

		</form>


	</main>

</body>
</html>