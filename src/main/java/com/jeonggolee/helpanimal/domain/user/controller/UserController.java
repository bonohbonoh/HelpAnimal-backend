package com.jeonggolee.helpanimal.domain.user.controller;

import com.jeonggolee.helpanimal.common.dto.JwtTokenDto;
import com.jeonggolee.helpanimal.domain.user.dto.UserInfoReadDto;
import com.jeonggolee.helpanimal.domain.user.dto.UserLoginDto;
import com.jeonggolee.helpanimal.domain.user.dto.UserSignupDto;
import com.jeonggolee.helpanimal.domain.user.service.UserService;
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
        boolean existUser = userService.signUpUser(signUpDto);
        if (existUser) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtTokenDto> userLogin(@RequestBody @Valid UserLoginDto dto) throws Exception {
        String token = userService.loginUser(dto);
        return new ResponseEntity<JwtTokenDto>(new JwtTokenDto(token), HttpStatus.OK);
    }

    @GetMapping(value = "/auth/my-page")
    public ResponseEntity<UserInfoReadDto> userInfoReadDtoResponseEntity() throws Exception {
        UserInfoReadDto userInfoReadDto = userService.userInfoReadDto();
        return new ResponseEntity<UserInfoReadDto>(userInfoReadDto, HttpStatus.OK);
    }

    @PostMapping(value = "/auth/{email}")
    public String emailSendResponseEntity() throws Exception {
        return userService.sendEmail();
    }

    @PostMapping(value = "/auth/emailverify")
    public ResponseEntity<String> emailAuthResponseEntity(@RequestBody String code) throws Exception {
        userService.authEmail(code);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
