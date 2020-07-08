package br.com.acme.tasklist.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "task")
public class Task {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_TASK")
	@SequenceGenerator(name="SEQ_TASK", sequenceName="id_seq_task", allocationSize=1)
	private Integer id;
	
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tasklist_id", nullable = false)
    private TaskList taskList;
	
	public Task() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TaskList getTaskList() {
		return taskList;
	}

	public void setTaskList(TaskList taskList) {
		this.taskList = taskList;
	}

}