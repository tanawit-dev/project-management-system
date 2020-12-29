package com.example.projectmanagementsystem.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class ProjectServiceAspect {

	@Before("execution(* com.example.projectmanagementsystem.service.impl.ProjectServiceImpl.findById(Long))")
	public void beforeFindById(JoinPoint joinPoint) {
		log.info("Searching Project with Id {}", joinPoint.getArgs()[0]);
	}
}
