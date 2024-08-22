package tech.ian.user.dto;

import tech.ian.user.entity.User;

public record CreateUserRequest(String name, String email, String password, String document) {

    public User toModel(){
        return new User(name,email,password,document);
    }
}
