package com.moodle.tenant.exception;

/**
 * Created by andrewlarsen on 8/27/17.
 */
public class MoodleStackException extends Exception {
    public MoodleStackException(String message) {
        super(message);
    }

    public MoodleStackException(Throwable cause) {
        super(cause);
    }

    public MoodleStackException(String message, Throwable cause) {
        super(message, cause);
    }
}
