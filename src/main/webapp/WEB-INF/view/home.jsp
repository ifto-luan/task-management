<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="${pageContext.request.contextPath}/assets/js/tasks.js"></script>
<link
	href="${pageContext.request.contextPath}/assets/css/fontawesome.css"
	rel="stylesheet" />
<link href="/${pageContext.request.contextPath}/assets/css/brands.css"
	rel="stylesheet" />
<link href="${pageContext.request.contextPath}/assets/css/solid.css"
	rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/assets/css/home.css">
<link rel="icon"
	href="${pageContext.request.contextPath}/assets/favicon.ico"
	type="image/x-icon">
<title>Task Manager | Your tasks</title>
</head>
<body>

	<header>
		<div class="welcome-message">
			<h2>Welcome, ${sessionScope.user.name}</h2>
		</div>

		<div class="logout-button">
			<form action="${pageContext.request.contextPath}/logout"
				method="post">
				<button type="submit" title="Logout">
					<i class="fa-solid fa-right-from-bracket"></i>
				</button>
			</form>

		</div>
	</header>

	<main>
		<div class="new-task">
			<input type="text" name="newTaskDescription" placeHolder="New Task"
				required maxlength="100" id="newTaskDescription">
			<button type="button"
				onclick="addTask(document.getElementById('newTaskDescription').value)">
				<i class="fa-solid fa-plus"></i>
			</button>
		</div>

		<div class="pending-tasks" id="pending-tasks">
			<hr>
			<c:forEach var="task" items="${pendingTasks}">
				<div id="task-${task.id}" class="task">
					<div class="task-status">
						<button type="submit" onclick="markTaskAsDone(${task.id})">
							<i class="fa-solid fa-check"></i>
						</button>
					</div>
					<div class="task-description">
						<input type="hidden" value="${task.id}">
						<p>${task.description}</p>
					</div>
					<div class="task-delete">
						<button type="submit" onclick="deleteTask(${task.id})">
							<i class="fa-solid fa-trash"></i>
						</button>
					</div>
				</div>
			</c:forEach>
		</div>
		<div class="done-tasks" id="done-tasks">
			<hr>
			<c:forEach var="task" items="${doneTasks}">
				<div class="task" id="task-${task.id}">
					<div class="task-status">
						<button type="submit" onclick="markTaskAsPending(${task.id})">
							<i class="fa-solid fa-rotate-right"></i>
						</button>
					</div>
					<div class="task-description">
						<input type="hidden" value="${task.id}">
						<p style="text-decoration: line-through">${task.description}</p>
					</div>
					<div class="task-delete">
						<button type="submit" onclick="deleteTask(${task.id})">
							<i class="fa-solid fa-trash"></i>
						</button>
					</div>
				</div>
			</c:forEach>
		</div>
	</main>

</body>
</html>