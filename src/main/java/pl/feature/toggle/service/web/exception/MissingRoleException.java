package pl.feature.toggle.service.web.exception;


import pl.feature.toggle.service.web.actor.Actor;

public class MissingRoleException extends RuntimeException {
    public MissingRoleException(Actor actor) {
        super("User" + actor.username().value() + " does not have required role");
    }
}
