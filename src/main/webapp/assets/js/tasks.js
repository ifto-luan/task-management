function addTask(description) {

	var xhr = new XMLHttpRequest();

	xhr.open('POST', 'home', true);

	xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

	xhr.onreadystatechange = function() {

		if (xhr.readyState === 4) {
			if (xhr.status === 200) {

				document.getElementById('newTaskDescription').value = '';
				var newTaskId = xhr.responseText.trim();

				if (newTaskId) {

					var newTask = document.createElement("div");
					newTask.id = "task-" + newTaskId;
					newTask.classList.add('task');

					newTask.innerHTML = `
					                <div class="task-status">
					                    <button type="button" onclick="markTaskAsDone(${newTaskId})">
					                        <i class="fa-solid fa-check"></i>
					                    </button>
					                </div>
					                <div class="task-description">
					                    <input type="hidden" value="${newTaskId}">
					                    <p>${description}</p>
					                </div>
					                <div class="task-delete">
					                    <button type="button" onclick="deleteTask(${newTaskId})">
					                        <i class="fa-solid fa-trash"></i>
					                    </button>
					                </div>
					            `;

					document.querySelector('.pending-tasks').appendChild(newTask);
					console.log("pq nao ta dando certo?");


				} else {
					console.error("Failed to get the task ID from the server.");
				}
			} else {
				console.error("Failed to add task. Server returned status:", xhr.status);
			}

		};

	}

	xhr.send(`action=add&newTaskDescription=${encodeURIComponent(description)}`);

}

function markTaskAsDone(taskId) {

	var xhr = new XMLHttpRequest();
	xhr.open('POST', 'home', true);
	xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			var taskElement = document.getElementById('task-' + taskId);
			document.getElementById('done-tasks').appendChild(taskElement);
			taskElement.querySelector('.task-description p').style.textDecoration = 'line-through';
			taskElement.querySelector('.task-status button').innerHTML = '<i class="fa-solid fa-rotate-right"></i>';
			taskElement.querySelector('.task-status button').setAttribute('onclick', 'markTaskAsPending(' + taskId + ')');
		}
	};

	xhr.send('action=markAsDone&taskId=' + taskId);

}

function markTaskAsPending(taskId) {

	var xhr = new XMLHttpRequest();
	xhr.open('POST', 'home', true);
	xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			var taskElement = document.getElementById('task-' + taskId);
			document.getElementById('pending-tasks').appendChild(taskElement);
			taskElement.querySelector('.task-description p').style.textDecoration = 'none';
			taskElement.querySelector('.task-status button').innerHTML = '<i class="fa-solid fa-check"></i>';
			taskElement.querySelector('.task-status button').setAttribute('onclick', 'markTaskAsDone(' + taskId + ')');
		}
	};

	xhr.send('action=markAsPending&taskId=' + taskId);

}

function deleteTask(taskId) {
	var xhr = new XMLHttpRequest();
	xhr.open('POST', 'home', true);
	xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			var taskElement = document.getElementById('task-' + taskId);
			taskElement.parentNode.removeChild(taskElement);
		}
	};

	xhr.send('action=delete&taskId=' + taskId);
}

document.addEventListener('DOMContentLoaded', function() {
    var taskInput = document.getElementById('newTaskDescription');

    taskInput.addEventListener('keydown', function(event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            var description = taskInput.value.trim();

            if (description) {
                addTask(description);
                taskInput.value = '';
            }
        }
    });
});