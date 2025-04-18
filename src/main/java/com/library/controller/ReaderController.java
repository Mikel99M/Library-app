package com.library.controller;

import com.library.domain.ReaderDto;
import com.library.exception.ReaderNotFoundException;
import com.library.service.ReaderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/readers")
@RequiredArgsConstructor
public class ReaderController {

    private final ReaderService service;

    @GetMapping
    public ResponseEntity<List<ReaderDto>> getReaders() {
        return ResponseEntity.ok(service.getAllReaders());
    }

    @GetMapping(value = "/with-borrowed-books")
    public ResponseEntity<List<ReaderDto>> getReadersWithBorrowedBooks() {
        return ResponseEntity.ok(service.getAllReadersWithBorrowedBooks());
    }

    @GetMapping(value = "/without-borrowed-books")
    public ResponseEntity<List<ReaderDto>> getReadersWithoutBorrowedBooks() {
        return ResponseEntity.ok(service.getAllReadersWithoutBorrowedBooks());
    }

    @GetMapping(value = "/{readerId}")
    public ResponseEntity<ReaderDto> getReader(@PathVariable Long readerId) throws ReaderNotFoundException{
        return ResponseEntity.ok(service.getReaderById(readerId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReaderDto> createReader(@Valid @RequestBody ReaderDto readerDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addReader(readerDto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ReaderDto> updateReader(@PathVariable Long id, @RequestBody ReaderDto readerDto) throws ReaderNotFoundException {
        return ResponseEntity.ok(service.updateReader(readerDto, id));
    }

    @DeleteMapping(value = "/{readerId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long readerId) throws ReaderNotFoundException {
        service.deleteReader(readerId);
        return ResponseEntity.noContent().build();
    }
}