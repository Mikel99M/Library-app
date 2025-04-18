package com.library.controller;

import com.library.domain.BorrowedDto;
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

    @GetMapping(value = "/{id}")
    public ResponseEntity<BorrowedDto> getBorrowedBookById(@PathVariable long id) {
        return ResponseEntity.ok(service.getBorrowedById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BorrowedDto> addBorrowedBook(@Valid @RequestBody BorrowedDto borrowedDto) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addBorrowedBook(borrowedDto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BorrowedDto> updateBorrowedBook(@PathVariable long id, @Valid @RequestBody BorrowedDto borrowedDto) {
        return ResponseEntity.ok(service.updateBorrowedBook(borrowedDto, id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteBorrowedBook(@PathVariable long id) throws Exception {
        service.deleteBorrowedBook(id);
        return ResponseEntity.noContent().build();
    }

}
