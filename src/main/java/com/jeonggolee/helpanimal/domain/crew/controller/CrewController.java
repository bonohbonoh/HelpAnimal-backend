package com.jeonggolee.helpanimal.domain.crew.controller;

import com.jeonggolee.helpanimal.domain.crew.dto.CreateCrewDto;
import com.jeonggolee.helpanimal.domain.crew.dto.ReadCrewDetailDto;
import com.jeonggolee.helpanimal.domain.crew.dto.ReadCrewDto;
import com.jeonggolee.helpanimal.domain.crew.dto.UpdateCrewDto;
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
    public ResponseEntity<?> createCrew(@RequestBody CreateCrewDto createCrewDto) {
        String requesterEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        return new ResponseEntity<>(crewService.createCrew(createCrewDto, requesterEmail), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> readCrewList(@RequestParam int page, @RequestParam int size, @RequestParam(required = false) String name) {
        return (name == null)
                ? new ResponseEntity<>(crewService.readCrewList(page, size), HttpStatus.OK)
                : new ResponseEntity<>(crewService.readCrewListByName(page, size, name), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readCrewDetail(@PathVariable Long id) {
        return new ResponseEntity<>(crewService.readCrewDetail(id), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<?> updateCrew(@RequestBody UpdateCrewDto updateCrewDto){
        return new ResponseEntity<>(crewService.updateCrew(updateCrewDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCrew(@PathVariable("id") Long id){
        crewService.deleteCrew(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
