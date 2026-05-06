package com.chirag.teamtaskmanager.service;

import com.chirag.teamtaskmanager.dao.ProjectDao;
import com.chirag.teamtaskmanager.dao.TaskDao;
import com.chirag.teamtaskmanager.dao.UserDao;
import com.chirag.teamtaskmanager.dto.ResponseStructure;
import com.chirag.teamtaskmanager.entity.Project;
import com.chirag.teamtaskmanager.entity.Task;
import com.chirag.teamtaskmanager.entity.TaskStatus;
import com.chirag.teamtaskmanager.entity.User;
import com.chirag.teamtaskmanager.exception.ProjectNotFoundException;
import com.chirag.teamtaskmanager.exception.TaskNotFoundException;
import com.chirag.teamtaskmanager.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private UserDao userDao;

    public ResponseStructure<Task> createTask(Task task, Long projectId, Long assigneeId, String email) {
        Project project = projectDao.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + projectId));
        User createdBy = userDao.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        task.setProject(project);
        task.setCreatedBy(createdBy);
        task.setCreatedAt(LocalDateTime.now());
        task.setStatus(TaskStatus.TODO);
        if (assigneeId != null) {
            User assignee = userDao.findById(assigneeId)
                    .orElseThrow(() -> new UserNotFoundException("Assignee not found with id: " + assigneeId));
            task.setAssignee(assignee);
        }
        Task savedTask = taskDao.saveTask(task);
        ResponseStructure<Task> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Task created successfully");
        response.setData(savedTask);
        return response;
    }

    public ResponseStructure<List<Task>> getTasksByProject(Long projectId) {
        projectDao.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + projectId));
        List<Task> tasks = taskDao.findByProjectId(projectId);
        ResponseStructure<List<Task>> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Tasks fetched successfully");
        response.setData(tasks);
        return response;
    }

    public ResponseStructure<Task> updateTaskStatus(Long taskId, TaskStatus status) {
        Task task = taskDao.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));
        task.setStatus(status);
        Task saved = taskDao.saveTask(task);
        ResponseStructure<Task> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Task status updated successfully");
        response.setData(saved);
        return response;
    }

    public ResponseStructure<Task> updateTask(Long taskId, Task updatedTask) {
        Task task = taskDao.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setPriority(updatedTask.getPriority());
        task.setDueDate(updatedTask.getDueDate());
        Task saved = taskDao.saveTask(task);
        ResponseStructure<Task> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Task updated successfully");
        response.setData(saved);
        return response;
    }

    public ResponseStructure<String> deleteTask(Long taskId) {
        Task task = taskDao.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));
        taskDao.deleteTask(task);
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Task deleted successfully");
        response.setData(null);
        return response;
    }

    public ResponseStructure<Map<String, Object>> getDashboard(String email) {
        User user = userDao.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        List<Task> allTasks = taskDao.findAllTasksByUser(user);
        List<Task> overdueTasks = taskDao.findOverdueTasksByUser(user, LocalDate.now());

        long todo = allTasks.stream().filter(t -> t.getStatus() == TaskStatus.TODO).count();
        long inProgress = allTasks.stream().filter(t -> t.getStatus() == TaskStatus.IN_PROGRESS).count();
        long inReview = allTasks.stream().filter(t -> t.getStatus() == TaskStatus.IN_REVIEW).count();
        long done = allTasks.stream().filter(t -> t.getStatus() == TaskStatus.DONE).count();

        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("totalTasks", allTasks.size());
        dashboard.put("todo", todo);
        dashboard.put("inProgress", inProgress);
        dashboard.put("inReview", inReview);
        dashboard.put("done", done);
        dashboard.put("overdue", overdueTasks.size());
        dashboard.put("overdueTasks", overdueTasks);

        ResponseStructure<Map<String, Object>> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Dashboard fetched successfully");
        response.setData(dashboard);
        return response;
    }
}