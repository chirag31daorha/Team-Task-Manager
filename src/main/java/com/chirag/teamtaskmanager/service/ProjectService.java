package com.chirag.teamtaskmanager.service;

import com.chirag.teamtaskmanager.dao.ProjectDao;
import com.chirag.teamtaskmanager.dao.UserDao;
import com.chirag.teamtaskmanager.dto.ResponseStructure;
import com.chirag.teamtaskmanager.entity.Project;
import com.chirag.teamtaskmanager.entity.User;
import com.chirag.teamtaskmanager.exception.ProjectNotFoundException;
import com.chirag.teamtaskmanager.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private UserDao userDao;

    public ResponseStructure<Project> createProject(Project project, String email) {
        User owner = userDao.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        project.setOwner(owner);
        project.setCreatedAt(LocalDateTime.now());
        Project savedProject = projectDao.saveProject(project);
        ResponseStructure<Project> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Project created successfully");
        response.setData(savedProject);
        return response;
    }

    public ResponseStructure<List<Project>> getAllProjects(String email) {
        User user = userDao.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        List<Project> projects = projectDao.findAllProjectsByUser(user);
        ResponseStructure<List<Project>> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Projects fetched successfully");
        response.setData(projects);
        return response;
    }

    public ResponseStructure<Project> getProjectById(Long id) {
        Project project = projectDao.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + id));
        ResponseStructure<Project> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Project fetched successfully");
        response.setData(project);
        return response;
    }

    public ResponseStructure<Project> updateProject(Long id, Project updatedProject) {
        Project project = projectDao.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + id));
        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        Project saved = projectDao.saveProject(project);
        ResponseStructure<Project> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Project updated successfully");
        response.setData(saved);
        return response;
    }

    public ResponseStructure<String> addMemberToProject(Long projectId, Long userId) {
        Project project = projectDao.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + projectId));
        User user = userDao.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        project.getMembers().add(user);
        projectDao.saveProject(project);
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Member added successfully");
        response.setData(null);
        return response;
    }

    public ResponseStructure<String> deleteProject(Long id) {
        Project project = projectDao.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + id));
        projectDao.deleteProject(project);
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Project deleted successfully");
        response.setData(null);
        return response;
    }
}