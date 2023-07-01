package com.Unicash.issuehandling.exception;

import com.Unicash.issuehandling.model.Module;

import java.util.List;

public class NotFondException extends RuntimeException {
    public NotFondException(String message) {
        super(message);
    }

    public NotFondException(Class<?> cls, long id) {
        super(String.format("Entity of %s with id %d not found.", cls.getSimpleName(), id));
    }
    public NotFondException(Class<?> cls, String id) {
        super(String.format("Entity of %s with id %s not found.", cls.getSimpleName(), id));
    }

/*    public NotFondException(Class<?> cls, List<Long> Ids) {
        super(String.format("Entities of  %s with id %s not found.", cls.getSimpleName(), String.join(",", (CharSequence) Ids)));
    }*/
}
