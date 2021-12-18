package com.jeonggolee.helpanimal.domain.user.controller;

import com.jeonggolee.helpanimal.domain.user.dto.UserSignupDto;
import com.jeonggolee.helpanimal.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/sign-up")
    public ResponseEntity userSignup(@RequestBody UserSignupDto signUpDto) throws Exception {
        boolean existUser = userService.signUpUser(signUpDto);
        if (existUser) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

}
