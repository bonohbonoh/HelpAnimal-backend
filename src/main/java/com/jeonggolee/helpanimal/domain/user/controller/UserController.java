package com.jeonggolee.helpanimal.domain.user.controller;

import com.jeonggolee.helpanimal.common.dto.JwtTokenDto;
import com.jeonggolee.helpanimal.domain.user.dto.UserLoginDto;
import com.jeonggolee.helpanimal.domain.user.dto.UserSignupDto;
import com.jeonggolee.helpanimal.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/sign-up")
    public ResponseEntity userSignup(@RequestBody UserSignupDto signUpDto) throws Exception {
        boolean existUser = userService.signUpUserRequiredCheck(signUpDto);
        if (existUser) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/login",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtTokenDto> userLogin(@RequestBody UserLoginDto dto) throws Exception {
        String token = userService.logInUserRequiredCheck(dto);
        return new ResponseEntity<JwtTokenDto>(new JwtTokenDto(token),HttpStatus.OK);
    }

}
