<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/assets/css/forgotPassword.css">
<link rel="icon"
	href="${pageContext.request.contextPath}/assets/favicon.ico"
	type="image/x-icon">

<meta charset="UTF-8">
<title>Task Manager | Password Recovery</title>
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
			action="${pageContext.request.contextPath}/forgotPassword"
			method="post">

			<div class="form-header">
				<h3>Password Recovery</h3>
			</div>

			<div class="form-input">
				<c:choose>
					<c:when test="${sessionScope.user != null}">
						<input type="text" id="login" name="username"
							value="${sessionScope.user.username}" readonly>
					</c:when>
					<c:otherwise>
						<input type="text" id="login" name="username" required
							placeholder="Type your username">
					</c:otherwise>
				</c:choose>

				<input type="password" id="new-password" name="new-password"
					required placeholder="Type your new password"> <input
					type="password" id="confirm-new-password"
					name="confirm-new-password" required
					placeholder="Confirm your new password">

			</div>

			<div class="form-button">
				<input type="submit" value="Change Password">
			</div>
		</form>
	</main>

</body>
</html>