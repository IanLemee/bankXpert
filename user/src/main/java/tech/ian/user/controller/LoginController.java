package tech.ian.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.ian.user.dto.LoginRequestDto;
import tech.ian.user.dto.LoginResponseDto;
import tech.ian.user.entity.User;
import tech.ian.user.service.TokenService;
import tech.ian.user.service.UserService;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/")
    public ResponseEntity login(@RequestBody LoginRequestDto loginRequestDto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequestDto.email(), loginRequestDto.password());

        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken( (User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }
}
