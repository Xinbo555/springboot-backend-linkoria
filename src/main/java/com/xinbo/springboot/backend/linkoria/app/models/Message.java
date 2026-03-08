package com.xinbo.springboot.backend.linkoria.app.models;

import java.time.Instant;
import java.util.List;

public class Message {
    private String id;
    private String from;
    private String chatId;
    private String message;
    private Instant timeStamp;
    private String status;
    private String type;
    private List<String> readBy;
    private String repliedTo;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getReadBy() {
        return readBy;
    }

    public void setReadBy(List<String> readBy) {
        this.readBy = readBy;
    }

    public String getRepliedTo() {
        return repliedTo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRepliedTo(String repliedTo) {
        this.repliedTo = repliedTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
}
