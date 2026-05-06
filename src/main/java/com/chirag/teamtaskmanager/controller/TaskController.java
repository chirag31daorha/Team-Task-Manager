package com.chirag.teamtaskmanager.controller;

import com.chirag.teamtaskmanager.dto.ResponseStructure;
import com.chirag.teamtaskmanager.entity.Task;
import com.chirag.teamtaskmanager.entity.TaskStatus;
import com.chirag.teamtaskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/project/{projectId}")
    public ResponseEntity<ResponseStructure<Task>> createTask(@RequestBody Task task,
            @PathVariable Long projectId,
            @RequestParam(required = false) Long assigneeId,
            @AuthenticationPrincipal UserDetails userDetails) {
        ResponseStructure<Task> response = taskService.createTask(task, projectId, assigneeId, userDetails.getUsername());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ResponseStructure<List<Task>>> getTasksByProject(@PathVariable Long projectId) {
        ResponseStructure<List<Task>> response = taskService.getTasksByProject(projectId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<ResponseStructure<Task>> updateTask(@PathVariable Long taskId,
            @RequestBody Task task) {
        ResponseStructure<Task> response = taskService.updateTask(taskId, task);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<ResponseStructure<Task>> updateTaskStatus(@PathVariable Long taskId,
            @RequestBody Map<String, String> request) {
        TaskStatus status = TaskStatus.valueOf(request.get("status"));
        ResponseStructure<Task> response = taskService.updateTaskStatus(taskId, status);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<ResponseStructure<String>> deleteTask(@PathVariable Long taskId) {
        ResponseStructure<String> response = taskService.deleteTask(taskId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<ResponseStructure<Map<String, Object>>> getDashboard(
            @AuthenticationPrincipal UserDetails userDetails) {
        ResponseStructure<Map<String, Object>> response = taskService.getDashboard(userDetails.getUsername());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}