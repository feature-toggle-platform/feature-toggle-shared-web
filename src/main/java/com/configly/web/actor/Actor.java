package com.configly.web.actor;


import com.configly.web.exception.MissingRoleException;

import java.util.Collection;
import java.util.Set;

public record Actor(
        ActorId actorId,
        Set<Role> roles,
        Username username
) {

    public static Actor system() {
        return new Actor(ActorId.system(), Set.of(), Username.system());
    }

    public static Actor create(String actorId, String username){
        return new Actor(ActorId.create(actorId), Set.of(), Username.create(username));
    }

    public static Actor create(ActorId actorId, Username username, Role... roles) {
        return new Actor(actorId, Set.of(roles), username);
    }

    public static Actor create(ActorId actorId, Username username, Collection<Role> roles) {
        return new Actor(actorId, Set.copyOf(roles), username);
    }

    public boolean has(Role role) {
        return roles.contains(role);
    }

    public void require(Role role) {
        if (!has(role)) {
            throw new MissingRoleException(this);
        }
    }

    public boolean isSystem() {
        return actorId.equals(ActorId.system());
    }

    public String idAsString() {
        return actorId.value();
    }

    public String usernameAsString() {
        return username.value();
    }
}
