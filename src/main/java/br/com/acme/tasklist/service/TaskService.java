package br.com.acme.tasklist.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.acme.tasklist.model.Task;
import br.com.acme.tasklist.repository.TaskRepository;

@Service
@Transactional
public class TaskService {
	
	@Autowired
	private TaskRepository taskRepository;

	public Task save(Task task) {
		return taskRepository.save(task);
	}

	public Optional<Task> getById(Integer id) {
		return taskRepository.findById(id);
	}

	public void removeFromList(Integer id) {
		taskRepository.removeFromList(id);
	}

	public Task updateTaskStatus(Task task) {
		task.setDone(!task.isDone());
		return taskRepository.save(task);
	}

	public List<Task> getAllTasks() {
		return taskRepository.findAllTasks();
	}
	
	public List<Task> getAllTasksFromTaskList(Integer idTaskList){
		return taskRepository.findAllTasksFromTaskList(idTaskList);
	}

	public void removeTasksFromList(Integer idTaskList) {
		taskRepository.removeTasksFromList(idTaskList);
	}
	

}
