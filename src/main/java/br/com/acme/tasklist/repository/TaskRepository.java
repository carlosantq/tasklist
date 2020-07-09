package br.com.acme.tasklist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.acme.tasklist.model.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

	@Modifying
	@Query(value = "UPDATE public.task SET active = FALSE WHERE id = :idTask", nativeQuery = true)
	void removeFromList(@Param("idTask") Integer idTask);

	@Query(value = "SELECT t.* FROM public.task t WHERE t.tasklist_id = :idTaskList AND t.active = TRUE", nativeQuery = true)
	List<Task> findTasksFromTaskList(@Param("idTaskList") Integer idTaskList);

	@Query(value = "SELECT t.* FROM public.task t WHERE t.active = TRUE ORDER BY t.id", nativeQuery = true)
	List<Task> findAllTasks();
	
	@Query(value = "SELECT t.* FROM public.task t WHERE t.tasklist_id = :idTaskList AND t.active = TRUE ORDER BY t.id", nativeQuery = true)
	List<Task> findAllTasksFromTaskList(@Param("idTaskList") Integer idTaskList);
	
}
