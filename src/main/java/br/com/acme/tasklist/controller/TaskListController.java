package br.com.acme.tasklist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.acme.tasklist.model.TaskList;
import br.com.acme.tasklist.service.TaskListService;
import br.com.acme.tasklist.service.TaskService;

@RestController
@RequestMapping("/api/tasklist")
public class TaskListController {
	
	@Autowired
	private TaskListService taskListService;
	
	@Autowired
	private TaskService taskService;
	
	@PostMapping
	public ResponseEntity saveTaskList(@RequestBody TaskList taskList) {
		
		if (taskList.getName() == null || taskList.getName().isEmpty()) {
			return new ResponseEntity<>("A name is required.", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(taskListService.save(taskList), HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<TaskList>> getAllTaskLists(){
		return new ResponseEntity<List<TaskList>>(taskListService.getAllTaskLists(), HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("/{idTaskList}")
	public ResponseEntity deleteTaskList(@PathVariable Integer idTaskList){
		if (taskListService.getById(idTaskList).isPresent()) {
			taskListService.delete(idTaskList);
			taskService.removeTasksFromList(idTaskList);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
