package com.example.projectmanagementsystem.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

	public static final String DEFAULT_NOT_FOUND_VIEW = "not-found";

	@ExceptionHandler(NotFoundExcetion.class)
	public ModelAndView handleNotFoundException(HttpServletRequest req, NotFoundExcetion ex) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", ex);
		mav.addObject("url", req.getRequestURI());
		mav.setViewName(DEFAULT_NOT_FOUND_VIEW);

		return mav;
	}
}
