package com.chirag.teamtaskmanager.dao;

import com.chirag.teamtaskmanager.entity.Task;
import com.chirag.teamtaskmanager.entity.TaskStatus;
import com.chirag.teamtaskmanager.entity.User;
import com.chirag.teamtaskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskDao {

    @Autowired
    private TaskRepository taskRepository;

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> findByProjectId(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    public List<Task> findByAssignee(User user) {
        return taskRepository.findByAssignee(user);
    }

    public List<Task> findAllTasksByUser(User user) {
        return taskRepository.findAllTasksByUser(user);
    }

    public List<Task> findOverdueTasksByUser(User user, LocalDate today) {
        return taskRepository.findOverdueTasksByUser(user, today);
    }

    public long countByProjectId(Long projectId) {
        return taskRepository.countByProjectId(projectId);
    }

    public long countByProjectIdAndStatus(Long projectId, TaskStatus status) {
        return taskRepository.countByProjectIdAndStatus(projectId, status);
    }

    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }
}