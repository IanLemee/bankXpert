package tech.ian.user.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.ian.user.dto.CreateUserRequest;
import tech.ian.user.dto.CreateUserResponse;
import tech.ian.user.entity.User;
import tech.ian.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<CreateUserResponse> create(@Validated @RequestBody CreateUserRequest createUserRequest) throws Exception{
        User user = createUserRequest.toModel();

        var userSaverd = this.userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSaverd);
    }

    @GetMapping
    public String teste() {
        return "Hello, world";
    }
}
