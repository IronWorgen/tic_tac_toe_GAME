package org.example.game.exceptions;

public class WrongPlayerNameException extends RuntimeException {
    public WrongPlayerNameException(String message) {
        super(message);
    }
}
