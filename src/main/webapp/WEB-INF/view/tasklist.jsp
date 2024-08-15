<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="pending-tasks">
		<hr>
		<div>
			<form action="${pageContext.request.contextPath}/home" method="post" id="pendingTasksForm">
				<c:forEach var="task" items="${pendingTasks}">
					<div class="task">
						<div class="task-status">
							<button type="submit">
								<i class="fa-solid fa-check"></i>
							</button>
						</div>
						<div class="task-description">
							<input type="hidden" value="${task.id}">
							<p>${task.description}</p>
						</div>
						<div class="task-delete">
							<button type="submit">
								<i class="fa-solid fa-trash"></i>
							</button>
						</div>
					</div>
				</c:forEach>
			</form>
		</div>
	</div>
	<div class="done-tasks">
	
		<hr>
		<div>
			<form action="${pageContext.request.contextPath}/home" method="post">
				<c:forEach var="task" items="${doneTasks}">
					<div class="task">
						<div class="task-status">
							<button type="submit">
								<i class="fa-solid fa-rotate-right"></i>
							</button>
						</div>
						<div class="task-description">
							<input type="hidden" value="${task.id}">
							<p style="text-decoration: line-through">${task.description}</p>
						</div>
						<div class="task-delete">
							<button type="submit">
								<i class="fa-solid fa-trash"></i>
							</button>
						</div>
					</div>
				</c:forEach>
			</form>
		</div>
	</div>

</body>
</html>