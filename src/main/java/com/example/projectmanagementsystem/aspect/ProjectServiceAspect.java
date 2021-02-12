package com.example.projectmanagementsystem.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ProjectServiceAspect {

    @Before("execution(* com.example.projectmanagementsystem.service.impl.ProjectServiceImpl.findById(Long))")
    public void beforeFindById(JoinPoint joinPoint) {
        log.info("Searching Project with Id {}", joinPoint.getArgs()[0]);
    }
}
