<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link
	href="${pageContext.request.contextPath}/resources/css/fontawesome.css"
	rel="stylesheet" />
<link
	href="/${pageContext.request.contextPath}/resources/css/brands.css"
	rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resources/css/solid.css"
	rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/home.css">
<link rel="icon"
	href="${pageContext.request.contextPath}/resources/favicon.ico"
	type="image/x-icon">
<title>Task Manager | Welcome!</title>
</head>
<body>

	<header>
		<div class="welcome-message">
			<h1>Welcome, ${sessionScope.user.name}</h1>
		</div>
		
		<div class="logout-button">
			<form action="${pageContext.request.contextPath}/logout"
				method="post">
				<button type="submit" title="Logout"><i class="fa-solid fa-right-from-bracket"></i></button>
			</form>

		</div>
	</header>

	<main>



		<div class="new-task">
			<form action="${pageContext.request.contextPath}/home" method="post">
				<input type="text" name="taskDescription" placeHolder="New Task"
					required maxlength="100">
				<button type="submit" name="action" value="Add">
					<i class="fa-solid fa-plus"></i>
				</button>
			</form>
		</div>

		<div class="pending-tasks">
			<h5>To do</h5>
			<form action="${pageContext.request.contextPath}/home" method="post">
				<table>
					<tbody>
						<c:forEach var="task" items="${pendingTasks}">
							<tr>
								<td>
									<input type="hidden" name="taskId" value="${task.id}">	
								</td>
								<td>
									<p contenteditable="true">${task.description }</p>						
								</td>
								<td>
									<input type="checkbox" name="taskStatus" value="${task.done}">
								</td>
								<td>
									<button type="submit"><i class="fa-solid fa-trash"></i></button>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>
		</div>
		<div class="done-tasks">
			<h5>Done</h5>

			<form action="${pageContext.request.contextPath}/home" method="post">
				<table>
					<c:forEach var="task" items="${doneTasks}">
						<tr>
							<td>
								<input type="hidden" name="taskId" value="${task.id}">	
							</td>
							<td>
								<p contenteditable="true">${task.description }</p>						
							</td>
							<td>
								<input type="checkbox" name="taskStatus" value="${task.done}">
							</td>
						</tr>
					</c:forEach>
				</table>
			</form>

		</div>
	</main>

</body>
</html>