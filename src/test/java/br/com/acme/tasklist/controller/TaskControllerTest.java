package br.com.acme.tasklist.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import br.com.acme.tasklist.model.Task;
import br.com.acme.tasklist.model.TaskList;
import br.com.acme.tasklist.service.TaskListService;
import br.com.acme.tasklist.service.TaskService;

@SpringBootTest
public class TaskControllerTest {
	
	@InjectMocks
	TaskController taskController;
	
	@Mock
	TaskService taskService;
	
	@Mock
	TaskListService taskListService;
	
	Task task;
	
	TaskList taskList;
	
	@BeforeEach
	public void init() {
		taskList = new TaskList(1, "Task List #1");
		
		task = new Task();
		task.setActive(true);
		task.setCreationDate(new Date());
		task.setDescription("Task #1");
		task.setDone(false);
		task.setId(1);
		task.setModificationDate(new Date());
		task.setTaskList(taskList);
	}
	
	@Test
	public void testSaveTask() {
		taskListService.save(taskList);
		
		when(taskListService.getById(task.getTaskList().getId())).thenReturn(Optional.of(task.getTaskList()));
		when(taskListService.save(taskList)).thenReturn(taskList);
		when(taskService.save(task)).thenReturn(task);
		
		ResponseEntity<Task> responseEntity = taskController.saveTask(task);
		
		assertTrue(task.toString().equals(responseEntity.getBody().toString()));
	}
	
	@Test
	public void testSaveTaskWithoutTaskListData() {
		taskList = new TaskList();
		task.setTaskList(taskList);
		
		when(taskListService.getById(taskList.getId())).thenReturn(Optional.empty());
		
		ResponseEntity<Task> responseEntity = taskController.saveTask(task);
		
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(400);
	}
	
	@Test
	public void testRemoveFromList() {
		when(taskService.getById(task.getId())).thenReturn(Optional.of(task));

		ResponseEntity responseEntity = taskController.removeFromList(task);
		
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void testRemoveNonExistentTask() {
		when(taskService.getById(task.getId())).thenReturn(Optional.empty());

		ResponseEntity responseEntity = taskController.removeFromList(task);
		
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(400);
	}
	
	@Test
	public void testUpdateTaskStatus(){
		when(taskService.getById(task.getId())).thenReturn(Optional.of(task));
		Task updatedTask = task;
		updatedTask.setDone(!task.isDone());
		when(taskService.updateTaskStatus(task)).thenReturn(updatedTask);
		
		ResponseEntity<Task> responseEntity = taskController.updateTaskStatus(task);
		
		assertTrue(updatedTask.toString().equals(responseEntity.getBody().toString()));
	}
	
	@Test
	public void testUpdateNonExistentTask(){
		when(taskService.getById(task.getId())).thenReturn(Optional.empty());
		
		ResponseEntity<Task> responseEntity = taskController.updateTaskStatus(task);
		
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(400);
	}
	
	@Test
	public void testGetAllTasks() {
		List<Task> list = new ArrayList<Task>();
		list.add(task);
		when(taskService.getAllTasks()).thenReturn(list);
		
		ResponseEntity<List<Task>> responseEntity = taskController.getAllTasks();
		
		assertThat(responseEntity.getBody().toString()).isEqualTo(list.toString());
	}
	
	@Test
	public void testGetAllTasksFromTaskList() {
		List<Task> list = new ArrayList<Task>();
		list.add(task);
		when(taskService.getAllTasksFromTaskList(taskList.getId())).thenReturn(list);
		
		ResponseEntity<List<Task>> responseEntity = taskController.getAllTasksFromTaskList(taskList.getId());
		
		assertThat(responseEntity.getBody().toString()).isEqualTo(list.toString());
	}
	
	@Test
	public void testGetInactiveOrActiveTaskById() {
		when(taskService.getById(task.getId())).thenReturn(Optional.of(task));
		
		ResponseEntity<Task> responseEntity = taskController.getInactiveOrActiveTaskById(task.getId());
		
		assertThat(responseEntity.getBody().toString()).isEqualTo(task.toString());
	}
	
	@Test
	public void testGetNonExistentTaskById() {
		when(taskService.getById(task.getId())).thenReturn(Optional.empty());
		
		ResponseEntity<Task> responseEntity = taskController.getInactiveOrActiveTaskById(task.getId());
		
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(400);
	}
	
	

}
