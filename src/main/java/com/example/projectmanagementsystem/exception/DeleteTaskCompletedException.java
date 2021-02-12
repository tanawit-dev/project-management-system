package com.example.projectmanagementsystem.exception;

import com.example.projectmanagementsystem.dto.TaskDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Can't delete completed task")
public class DeleteTaskCompletedException extends RuntimeException {
    public DeleteTaskCompletedException(TaskDto taskDto) {
        super("Can't delete completed task (" + taskDto.getName() + ")");
    }

    private static final long serialVersionUID = 1L;
}
