package com.chirag.teamtaskmanager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chirag.teamtaskmanager.entity.Project;
import com.chirag.teamtaskmanager.entity.User;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p FROM Project p WHERE p.owner = :user OR :user MEMBER OF p.members")
    List<Project> findAllProjectsByUser(@Param("user") User user);
}
