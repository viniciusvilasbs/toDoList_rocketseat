package com.rocketseatviny.todolist.service;

import com.rocketseatviny.todolist.domain.TaskModel;
import com.rocketseatviny.todolist.exception.BadRequestException;
import com.rocketseatviny.todolist.repository.TaskRepository;
import com.rocketseatviny.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    @Transactional
    public TaskModel createTask(@RequestBody TaskModel taskModel, HttpServletRequest request){
        var userId = request.getAttribute("userId");
        taskModel.setUserId((Long) userId);

        var currentDate = LocalDateTime.now();
        if(currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())){
            throw new BadRequestException("As datas de início/término devem ser maiores do que a data atual!");
        }

        if(taskModel.getStartAt().isAfter(taskModel.getEndAt())){
            throw new BadRequestException("A data de início deve ser menor do que a data de término!");
        }

        return taskRepository.save(taskModel);
    }

    public TaskModel findTaskByIdOrThrowBadRequestException(Long id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Task não encontrada!"));
    }

    public List<TaskModel> findAllTasksByUserId(HttpServletRequest request){
        var userId = request.getAttribute("userId");
        return taskRepository.findByUserId((Long) userId);
    }

    @Transactional
    public TaskModel update(@RequestBody TaskModel taskModel, Long taskId, HttpServletRequest request){

        var task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BadRequestException("Task não encontrada!"));

        var userId = request.getAttribute("userId");

        if(!task.getUserId().equals(userId)){
            throw new BadRequestException("Usuário não tem permissão para alterar essa task!");
        }

        Utils.copyNonNullProperties(taskModel, task);

        return taskRepository.save(task);
    }

    @Transactional
    public void delete(Long id){
        taskRepository.delete(findTaskByIdOrThrowBadRequestException(id));
    }
}
