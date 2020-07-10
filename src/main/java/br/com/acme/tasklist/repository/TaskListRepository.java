package br.com.acme.tasklist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.acme.tasklist.model.TaskList;

public interface TaskListRepository extends JpaRepository<TaskList, Integer>{
	
	@Modifying
	@Query(value = "UPDATE public.tasklist SET active = false WHERE id = :idTaskList", nativeQuery = true)
	public void deleteById(@Param("idTaskList") Integer idTaskList);

	@Query(value = "SELECT t.* FROM public.tasklist t WHERE t.active = true ORDER BY t.id", nativeQuery = true)
	public List<TaskList> findAllTaskLists();

}
