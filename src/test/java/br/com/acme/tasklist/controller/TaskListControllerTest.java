package br.com.acme.tasklist.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import br.com.acme.tasklist.model.TaskList;
import br.com.acme.tasklist.service.TaskListService;
import br.com.acme.tasklist.service.TaskService;

@SpringBootTest
public class TaskListControllerTest {
	
	@InjectMocks
	TaskListController taskListController;
	
	@Mock
	TaskListService taskListService;
	
	@Mock
	TaskService taskService;
	
	TaskList taskList;
	
	@BeforeEach
	public void init() {
		taskList = new TaskList(1, "Task List #1");
	}
	
	@Test
	public void testSaveTaskList() {
		when(taskListService.save(taskList)).thenReturn(taskList);
		
		ResponseEntity<TaskList> responseEntity = taskListController.saveTaskList(taskList);
		
		assertTrue(taskList.toString().equals(responseEntity.getBody().toString()));
	}
	
	@Test
	public void testSaveTaskWithoutTaskListData() {
		taskList = new TaskList();
		
		ResponseEntity<TaskList> responseEntity = taskListController.saveTaskList(taskList);
		
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(400);
	}
	
	@Test
	public void testDeleteTaskList() {
		when(taskListService.getById(taskList.getId())).thenReturn(Optional.of(taskList));

		ResponseEntity responseEntity = taskListController.deleteTaskList(taskList.getId());
		
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void testDeleteNonExistentTaskList() {
		when(taskListService.getById(taskList.getId())).thenReturn(Optional.empty());

		ResponseEntity responseEntity = taskListController.deleteTaskList(taskList.getId());
		
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(400);
	}
	
	@Test
	public void testGetAllTaskLists() {
		List<TaskList> list = new ArrayList<TaskList>();
		list.add(taskList);
		when(taskListService.getAllTaskLists()).thenReturn(list);
		
		ResponseEntity<List<TaskList>> responseEntity = taskListController.getAllTaskLists();
		
		assertThat(responseEntity.getBody().toString()).isEqualTo(list.toString());
		
	}

}
