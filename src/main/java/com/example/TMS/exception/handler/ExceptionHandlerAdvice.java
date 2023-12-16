package com.example.TMS.exception.handler;

import com.example.TMS.dto.Response;
import com.example.TMS.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response handleEmailAlreadyExistException(EmailAlreadyExistException exception) {
        return new Response(String.valueOf(HttpStatus.BAD_REQUEST.value()), exception.getMessage(), null);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Response handleAdminNotFoundException(AdminNotFoundException exception) {
        return new Response(String.valueOf(HttpStatus.NOT_FOUND.value()), exception.getMessage(), null);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Response handleDeveloperNotFoundException(DeveloperNotFoundException exception) {
        return new Response(String.valueOf(HttpStatus.NOT_FOUND.value()), exception.getMessage(), null);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response handleCredentialsAreWrongException(CredentialsAreWrongException exception) {
        return new Response(String.valueOf(HttpStatus.BAD_REQUEST.value()), exception.getMessage(), null);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Response handleProjectNotFoundException(ProjectNotFoundException exception) {
        return new Response(String.valueOf(HttpStatus.NOT_FOUND.value()), exception.getMessage(), null);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Response handleTaskNotFoundException(TaskNotFoundException taskNotFoundException) {
        return new Response(String.valueOf(HttpStatus.NOT_FOUND.value()), taskNotFoundException.getMessage(), null);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response handleAllotedTimeIsOverException(AllotedTimeIsOverException exception) {
        return new Response(String.valueOf(HttpStatus.BAD_REQUEST.value()), exception.getMessage(), null);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Response handleSprintNotFoundException(SprintNotFoundException exception) {
        return new Response(String.valueOf(HttpStatus.NOT_FOUND.value()), exception.getMessage(), null);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Response handleBodyNotFoundException(BodyNotFoundException exception) {
        return new Response(String.valueOf(HttpStatus.NOT_FOUND.value()), exception.getMessage(), null);
    }

    @ExceptionHandler(DeveloperNameAlreadyTakenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody Response handleDeveloperNameAlreadyTakenException(final DeveloperNameAlreadyTakenException e) {
        Response response = new Response();
        response.setStatus(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        response.setMessage("Developer name already taken");
        response.setData(null);
        return response;
    }
}

