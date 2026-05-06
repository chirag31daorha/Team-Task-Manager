package com.chirag.teamtaskmanager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chirag.teamtaskmanager.entity.Task;
import com.chirag.teamtaskmanager.entity.TaskStatus;
import com.chirag.teamtaskmanager.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByProjectId(Long projectId);

    List<Task> findByAssignee(User assignee);

    long countByProjectId(Long projectId);

    long countByProjectIdAndStatus(Long projectId, TaskStatus status);

    @Query("SELECT t FROM Task t JOIN t.project p WHERE p.owner = :user OR :user MEMBER OF p.members")
    List<Task> findAllTasksByUser(@Param("user") User user);

    @Query("SELECT t FROM Task t JOIN t.project p WHERE (p.owner = :user OR :user MEMBER OF p.members) AND t.dueDate < :today AND t.status != 'DONE'")
    List<Task> findOverdueTasksByUser(@Param("user") User user, @Param("today") LocalDate today);
}
