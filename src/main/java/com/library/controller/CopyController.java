package com.library.controller;

import com.library.domain.CopyDto;
import com.library.exception.CopyNotFoundException;
import com.library.service.CopyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/copies")
@RequiredArgsConstructor
public class CopyController {

    private final CopyService service;

    @GetMapping
    public ResponseEntity<List<CopyDto>> getCopies() {
        return ResponseEntity.ok(service.getAllCopies());
    }

    @GetMapping(value = "/{copyId}")
    public ResponseEntity<CopyDto> getCopy(@PathVariable Long copyId) throws CopyNotFoundException {
        return ResponseEntity.ok(service.getCopyById(copyId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CopyDto> createCopy(@Valid @RequestBody CopyDto copyDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createCopy(copyDto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CopyDto> updateCopy(@PathVariable Long id, @RequestBody CopyDto copyDto) throws CopyNotFoundException {
        return ResponseEntity.ok(service.updateCopy(copyDto, id));
    }

    @DeleteMapping(value = "/{copyId}")
    public ResponseEntity<Void> deleteCopy(@PathVariable Long copyId) throws CopyNotFoundException {
        service.deleteCopy(copyId);
        return ResponseEntity.noContent().build();
    }

}