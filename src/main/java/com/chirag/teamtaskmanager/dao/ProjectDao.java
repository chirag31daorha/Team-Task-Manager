package com.chirag.teamtaskmanager.dao;

import com.chirag.teamtaskmanager.entity.Project;
import com.chirag.teamtaskmanager.entity.User;
import com.chirag.teamtaskmanager.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class ProjectDao {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    public List<Project> findAllProjectsByUser(User user) {
        return projectRepository.findAllProjectsByUser(user);
    }

    public void deleteProject(Project project) {
        projectRepository.delete(project);
    }
}
