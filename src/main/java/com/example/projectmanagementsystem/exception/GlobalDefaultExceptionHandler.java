package com.example.projectmanagementsystem.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

	public static final String DEFAULT_NOT_FOUND_VIEW = "not-found";
	public static final String DEFAULT_DELETE_TASK_COMPLETED_VIEW = "delete-completed-task";

	@ExceptionHandler(NotFoundExcetion.class)
	public ModelAndView handleNotFoundException(HttpServletRequest req, NotFoundExcetion ex) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", ex);
		mav.addObject("url", req.getRequestURI());
		mav.setViewName(DEFAULT_NOT_FOUND_VIEW);
		return mav;
	}

	@ExceptionHandler(DeleteTaskCompletedException.class)
	public ModelAndView handleDeleteTaskCompletedException(DeleteTaskCompletedException ex) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("msg", ex.getMessage());
		mav.setViewName(DEFAULT_DELETE_TASK_COMPLETED_VIEW);
		return mav;
	}
}
