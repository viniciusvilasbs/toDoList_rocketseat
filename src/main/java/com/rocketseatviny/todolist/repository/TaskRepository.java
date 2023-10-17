package com.rocketseatviny.todolist.repository;

import com.rocketseatviny.todolist.domain.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, Long> {
    List<TaskModel> findByUserId(Long userId);
}
