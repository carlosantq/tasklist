package br.com.acme.tasklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.acme.tasklist.model.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {
	
}
