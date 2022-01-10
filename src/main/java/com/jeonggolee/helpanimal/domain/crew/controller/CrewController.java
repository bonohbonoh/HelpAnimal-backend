package com.jeonggolee.helpanimal.domain.crew.controller;

import com.jeonggolee.helpanimal.domain.crew.dto.CreateCrewDto;
import com.jeonggolee.helpanimal.domain.crew.dto.ReadCrewDetailDto;
import com.jeonggolee.helpanimal.domain.crew.dto.ReadCrewDto;
import com.jeonggolee.helpanimal.domain.crew.service.CrewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/crew")
public class CrewController {
    private final CrewService crewService;

    @PostMapping("")
    public ResponseEntity<Long> createCrew(@RequestBody CreateCrewDto createCrewDto) {
        String requesterEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Long createdCrewId = crewService.createCrew(createCrewDto, requesterEmail);

        return new ResponseEntity<>(createdCrewId, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<ReadCrewDto>> readCrewList(@RequestParam int page, @RequestParam int size, @RequestParam(required = false) String name) {
        List<ReadCrewDto> result;
        if (name == null) {
            result = crewService.readCrewList(page, size);
        }
        else {
            result = crewService.readCrewListByName(page, size, name);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadCrewDetailDto> readCrewDetail(@PathVariable Long id) {
        ReadCrewDetailDto result;
        result = crewService.readCrewDetail(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
