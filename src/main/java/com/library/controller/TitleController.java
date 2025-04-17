package com.library.controller;

import com.library.domain.Title;
import com.library.domain.TitleDto;
import com.library.exception.TitleNotFoundException;
import com.library.mapper.TitleMapper;
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
    private final TitleMapper mapper;

    @GetMapping
    public ResponseEntity<List<TitleDto>> getTitles() {
        return ResponseEntity.ok(service.getAllTitles());
    }

    @GetMapping(value = "/{titleId}")
    public ResponseEntity<TitleDto> getTitle(@PathVariable Long titleId) {
        return ResponseEntity.ok(mapper.mapToTitleDto(service.getTitleById(titleId)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TitleDto> createTitle(@RequestBody TitleDto titleDto) {
        Title title = mapper.mapToTitle(titleDto);
        service.saveTitle(title);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TitleDto> updateTitle(@PathVariable Long id, @RequestBody TitleDto titleDto) {
        Title title = mapper.mapToTitle(titleDto);
        title.setId(id);
        Title updatedTitle = service.saveTitle(title);
        return ResponseEntity.ok(mapper.mapToTitleDto(updatedTitle));
    }

    @DeleteMapping(value = "/{titleId}")
    public ResponseEntity<Void> deleteTitle(@PathVariable Long titleId) throws TitleNotFoundException {
        service.deleteTitle(titleId);
        return ResponseEntity.noContent().build();
    }

}
