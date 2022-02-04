package com.jeonggolee.helpanimal.domain.user.controller;

import com.jeonggolee.helpanimal.common.dto.JwtTokenDto;
import com.jeonggolee.helpanimal.domain.user.dto.UserInfoReadDto;
import com.jeonggolee.helpanimal.domain.user.dto.UserLoginDto;
import com.jeonggolee.helpanimal.domain.user.dto.UserSignupDto;
import com.jeonggolee.helpanimal.domain.user.service.UserService;
import com.jeonggolee.helpanimal.domain.user.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/")
    public ResponseEntity userSignup(@RequestBody @Valid UserSignupDto signUpDto) throws Exception {
        userService.signUpUser(signUpDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtTokenDto> userLogin(@RequestBody @Valid UserLoginDto dto) throws Exception {
        return new ResponseEntity<JwtTokenDto>(userService.loginUser(dto), HttpStatus.OK);
    }

    @GetMapping(value = "/auth/")
    public ResponseEntity<UserInfoReadDto> userInfoReadDtoResponseEntity() throws Exception {
        return new ResponseEntity<UserInfoReadDto>(userService.getUserInfo(), HttpStatus.OK);
    }

    @PostMapping(value = "/auth/")
    public ResponseEntity<String> emailSendResponseEntity() throws Exception {
        return new ResponseEntity<String>(userService.sendEmail(), HttpStatus.OK);
    }

    @PostMapping(value = "/auth/email-verify")
    public ResponseEntity<String> emailAuthResponseEntity(@RequestBody String url) throws Exception {
        return new ResponseEntity<String>(userService.authEmail(url), HttpStatus.OK);
    }
}
