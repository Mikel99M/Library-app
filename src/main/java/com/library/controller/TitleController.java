package com.library.controller;

import com.library.domain.TitleDto;
import com.library.exception.TitleNotFoundException;
import com.library.service.TitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/titles")
@RequiredArgsConstructor
public class TitleController {

    private final TitleService service;

    @GetMapping
    public ResponseEntity<List<TitleDto>> getTitles() {
        return ResponseEntity.ok(service.getAllTitles());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TitleDto> getTitle(@PathVariable Long id) throws TitleNotFoundException {
        return ResponseEntity.ok(service.getTitleById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TitleDto> createTitle(@RequestBody TitleDto titleDto) {
        return ResponseEntity.ok(service.addTitle(titleDto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TitleDto> updateTitle(@PathVariable Long id, @RequestBody TitleDto titleDto) throws TitleNotFoundException {
        return ResponseEntity.ok(service.updateTitle(titleDto, id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteTitle(@PathVariable Long id) throws TitleNotFoundException {
        service.deleteTitle(id);
        return ResponseEntity.noContent().build();
    }

}
