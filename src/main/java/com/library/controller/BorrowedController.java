package com.library.controller;

import com.library.domain.BorrowedDto;
import com.library.mapper.BorrowedMapper;
import com.library.service.BorrowedService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/borrowed")
@AllArgsConstructor
public class BorrowedController {

    private final BorrowedService service;

    @GetMapping
    public ResponseEntity<List<BorrowedDto>> getAllBorrowedBooks() {
        return ResponseEntity.ok(service.getAllBorrowed());
    }

    @GetMapping(value = "/{borrowedId}")
    public ResponseEntity<BorrowedDto> getBorrowedBookById(@PathVariable long borrowedId) {
        return ResponseEntity.ok(service.getBorrowedById(borrowedId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BorrowedDto> addBorrowedBook(@Valid @RequestBody BorrowedDto borrowedDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addBorrowedBook(borrowedDto));
    }

}
