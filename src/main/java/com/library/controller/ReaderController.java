package com.library.controller;

import com.library.domain.Reader;
import com.library.domain.ReaderDto;
import com.library.exception.ReaderNotFoundException;
import com.library.mapper.ReaderMapper;
import com.library.service.ReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/readers")
@RequiredArgsConstructor
public class ReaderController {

    private final ReaderMapper mapper;
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
        return ResponseEntity.ok(mapper.mapToReaderDto(service.getReaderById(readerId)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReaderDto> createReader(@RequestBody ReaderDto readerDto) {
        Reader reader = mapper.mapToReader(readerDto);
        service.saveReader(reader);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ReaderDto> updateReader(@PathVariable Long id, @RequestBody ReaderDto readerDto) {
        Reader reader = mapper.mapToReader(readerDto);
        reader.setId(id);
        Reader updatedReader = service.saveReader(reader);
        return ResponseEntity.ok(mapper.mapToReaderDto(updatedReader));
    }

    @DeleteMapping(value = "/{readerId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long readerId) throws ReaderNotFoundException {
        service.deleteReader(readerId);
        return ResponseEntity.noContent().build();
    }

}
