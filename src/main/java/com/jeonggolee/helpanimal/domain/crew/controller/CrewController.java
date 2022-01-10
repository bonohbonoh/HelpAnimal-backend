package com.jeonggolee.helpanimal.domain.crew.controller;

import com.jeonggolee.helpanimal.domain.crew.dto.CreateCrewDto;
import com.jeonggolee.helpanimal.domain.crew.service.CrewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/crew")
public class CrewController {
    private final CrewService crewService;

    @PostMapping("")
    public ResponseEntity<Long> createCrew(@RequestBody CreateCrewDto createCrewDto){
        String requesterEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Long createdCrewId = crewService.createCrew(createCrewDto, requesterEmail);

        return new ResponseEntity<>(createdCrewId, HttpStatus.OK);
    }

}
