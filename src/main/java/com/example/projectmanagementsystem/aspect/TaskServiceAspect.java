package com.example.projectmanagementsystem.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class TaskServiceAspect {

	@AfterReturning("execution(* com.example.projectmanagementsystem.service.impl.TaskServiceImpl.update(Long,..))")
	public void beforeUpdateTask(JoinPoint joinPoint) {
		log.info("Update task with Id {}", joinPoint.getArgs()[0]);
	}

	@AfterReturning("execution(* com.example.projectmanagementsystem.service.impl.TaskServiceImpl.delete(Long))")
	public void beforeDeleteTask(JoinPoint joinPoint) {
		log.info("Delete task with Id {}", joinPoint.getArgs()[0]);
	}
}
