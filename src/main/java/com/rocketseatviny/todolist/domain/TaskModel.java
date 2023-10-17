package com.rocketseatviny.todolist.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_tasks")
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String title;
    private String description;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String priority;

    private Long userId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setTitle(String title) throws DataIntegrityViolationException {
        if(title.length() > 50) {
            throw new DataIntegrityViolationException("O campo title deve conter no máximo 50 caracteres!");
        }
        this.title = title;
    }
}
