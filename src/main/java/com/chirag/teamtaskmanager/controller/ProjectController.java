package com.chirag.teamtaskmanager.controller;

import com.chirag.teamtaskmanager.dto.ResponseStructure;
import com.chirag.teamtaskmanager.entity.Project;
import com.chirag.teamtaskmanager.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<ResponseStructure<Project>> createProject(@RequestBody Project project,
            @AuthenticationPrincipal UserDetails userDetails) {
        ResponseStructure<Project> response = projectService.createProject(project, userDetails.getUsername());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<Project>>> getAllProjects(
            @AuthenticationPrincipal UserDetails userDetails) {
        ResponseStructure<List<Project>> response = projectService.getAllProjects(userDetails.getUsername());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Project>> getProjectById(@PathVariable Long id) {
        ResponseStructure<Project> response = projectService.getProjectById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseStructure<Project>> updateProject(@PathVariable Long id,
            @RequestBody Project project) {
        ResponseStructure<Project> response = projectService.updateProject(id, project);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/{projectId}/members/{userId}")
    public ResponseEntity<ResponseStructure<String>> addMember(@PathVariable Long projectId,
            @PathVariable Long userId) {
        ResponseStructure<String> response = projectService.addMemberToProject(projectId, userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> deleteProject(@PathVariable Long id) {
        ResponseStructure<String> response = projectService.deleteProject(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}