package br.com.acme.tasklist.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.acme.tasklist.model.TaskList;
import br.com.acme.tasklist.repository.TaskListRepository;

@Service
@Transactional
public class TaskListService {
	
	@Autowired
	private TaskListRepository taskListRepository;

	public TaskList save(TaskList taskList) {
		return taskListRepository.save(taskList);
	}

	public List<TaskList> getAllTaskLists() {
		return taskListRepository.findAllTaskLists();
	}

	public Optional<TaskList> getById(Integer idTaskList) {
		return taskListRepository.findById(idTaskList);
	}

	public void delete(Integer idTaskList) {
		taskListRepository.deleteById(idTaskList);
	}

}
