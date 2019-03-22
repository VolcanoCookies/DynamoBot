package com.github.volcanocookies.dynamo2.objects;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.user.User;

public class NicknameChangeRequest {

    User user;
    String currentNickname;
    String requestedNickname;
    Message message;

    public NicknameChangeRequest(User user, String currentNickname, String requestedNickname, Message message){
        this.user = user;
        this.currentNickname = currentNickname;
        this.requestedNickname = requestedNickname;
        this.message = message;
    }
    public User getUser() {
        return user;
    }
    public String getCurrentNickname() {
        return currentNickname;
    }
    public String getRequestedNickname() {
        return requestedNickname;
    }
    public Message getMessage() {
        return message;
    }
}
