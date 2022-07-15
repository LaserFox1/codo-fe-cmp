package com.lkww.codo.codo.exceptions;

import com.lkww.codo.codo.domain.Project;

import javax.persistence.PersistenceException;


public class ServiceException extends RuntimeException {
    private static final String CREATE_DATABASE_PROBLEMS = "Cannot create project due to database problems - ";
    private static final String CREATE_UNDETERMINED_REASON = "Cannot create project due to undetermined reason - ";

    public ServiceException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    public static ServiceException cannotCreateEntity(Project project, PersistenceException pEx) {
        String msg = CREATE_DATABASE_PROBLEMS + project.getClass().getSimpleName() + " : " + project.toString();
        return new ServiceException(msg, pEx);
    }

    public static ServiceException cannotCreateEntityForUndeterminedReason(Project project, Throwable t) {
        String msg = CREATE_UNDETERMINED_REASON + project.getClass().getSimpleName() + " : " + project.toString();
        return new ServiceException(msg, t);
    }
}

