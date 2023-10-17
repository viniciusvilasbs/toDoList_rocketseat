package com.rocketseatviny.todolist.controller;

import com.rocketseatviny.todolist.domain.TaskModel;
import com.rocketseatviny.todolist.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/createTask")
    public ResponseEntity<TaskModel> createTask(@RequestBody TaskModel taskModel, HttpServletRequest request){
        TaskModel createdTask = taskService.createTask(taskModel, request);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping(path = "/findTask/{id}")
    public ResponseEntity<TaskModel> findTaskById(@PathVariable Long id){
        return new ResponseEntity<>(taskService.findTaskByIdOrThrowBadRequestException(id), HttpStatus.OK);
    }

    @GetMapping(path = "/listAllTasks")
    public ResponseEntity<List<TaskModel>> findAllTasksByUserId(HttpServletRequest request){
        return new ResponseEntity<>(taskService.findAllTasksByUserId(request), HttpStatus.OK);
    }

    @PutMapping(path = "/updateTask/{taskId}")
    public ResponseEntity<TaskModel> updateTask(@RequestBody TaskModel taskModel,
                                                @PathVariable Long taskId, HttpServletRequest request){
        return new ResponseEntity<>(taskService.update(taskModel, taskId, request), HttpStatus.OK);
    }

    @DeleteMapping(path = "/deleteTask/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId){
        taskService.delete(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
