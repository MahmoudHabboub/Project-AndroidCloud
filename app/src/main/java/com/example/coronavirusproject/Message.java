package com.example.coronavirusproject;

public class Message {
    private String text;
    private String sender;
    private String receiver;
    private String CreatedAt;
    private String RoomId;

    public String getRoomId() {
        return RoomId;
    }

    public void setRoomId(String roomId) {
        RoomId = roomId;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }

    public Message() {
    }

    public Message(String text, String sender, String receiver,String CreatedAt,String RoomId) {
        this.text = text;
        this.sender = sender;
        this.receiver = receiver;
        this.CreatedAt = CreatedAt;
        this.RoomId = RoomId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
