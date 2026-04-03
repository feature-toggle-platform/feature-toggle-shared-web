package com.configly.web.exception;


import com.configly.web.actor.Actor;

public class MissingRoleException extends RuntimeException {
    public MissingRoleException(Actor actor) {
        super("User" + actor.username().value() + " does not have required role");
    }
}
