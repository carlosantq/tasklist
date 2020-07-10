package br.com.acme.tasklist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.acme.tasklist.model.Task;
import br.com.acme.tasklist.service.TaskListService;
import br.com.acme.tasklist.service.TaskService;

@RestController
@RequestMapping("/api/task")
public class TaskController {

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private TaskListService taskListService;
	
	@PostMapping
	public ResponseEntity<Task> saveTask(@RequestBody Task task) {
		if (!taskListService.getById(task.getTaskList().getId()).isPresent()) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(taskService.save(task), HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("/{idTask}")
	public ResponseEntity removeFromList(@PathVariable Integer idTask){
		if (taskService.getById(idTask).isPresent()) {
			taskService.removeFromList(idTask);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("/taskList/{idTaskList}")
	public ResponseEntity removeTasksFromList(@PathVariable Integer idTaskList){
		if (taskListService.getById(idTaskList).isPresent()) {
			taskService.removeTasksFromList(idTaskList);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping
	public ResponseEntity<Task> updateTaskStatus(@RequestBody Task task) {
		if (!taskService.getById(task.getId()).isPresent()) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(taskService.updateTaskStatus(task), HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Task>> getAllTasks(){
		return new ResponseEntity<List<Task>>(taskService.getAllTasks(), HttpStatus.OK);
	}
	
	@GetMapping("/tasklist/{idTaskList}")
	public ResponseEntity<List<Task>> getAllTasksFromTaskList(@PathVariable Integer idTaskList){
		return new ResponseEntity<List<Task>>(taskService.getAllTasksFromTaskList(idTaskList), HttpStatus.OK);
	}

	public ResponseEntity<Task> getInactiveOrActiveTaskById(Integer id) {
		if (!taskService.getById(id).isPresent()) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(taskService.getById(id).get(), HttpStatus.OK);
	}
	
}
