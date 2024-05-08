package cz.vse.java4it353.api;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {

    private LocalDateTime timestamp;

    private String body;

    public Message(LocalDateTime timestamp, String body) {
        this.timestamp = timestamp;
        this.body = body;
    }

    public Message(String body) {
        this.timestamp = LocalDateTime.now();
        this.body = body;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getBody() {
        return body;
    }
}
