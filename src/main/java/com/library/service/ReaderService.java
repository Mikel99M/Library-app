package com.library.service;

import com.library.domain.Reader;
import com.library.domain.ReaderDto;
import com.library.exception.ReaderNotFoundException;
import com.library.mapper.ReaderMapper;
import com.library.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReaderService {

    private final ReaderRepository repository;
    private final ReaderMapper mapper;

    public List<ReaderDto> getAllReaders() {
        return mapper.mapToReaderDtoList(repository.findAll());
    }

    public List<ReaderDto> getAllReadersWithBorrowedBooks() {
        List<Reader> readers = repository.findAll();
        List<ReaderDto> dtos = new ArrayList<>();
        for (Reader reader : readers) {
            if (!reader.getBorrowedBooks().isEmpty()) {
                dtos.add(mapper.mapToReaderDto(reader));
            }
        }
        return dtos;
    }

    public List<ReaderDto> getAllReadersWithoutBorrowedBooks() {
        List<Reader> readers = repository.findAll();
        List<ReaderDto> dtos = new ArrayList<>();
        for (Reader reader : readers) {
            if (reader.getBorrowedBooks().isEmpty()) {
                dtos.add(mapper.mapToReaderDto(reader));
            }
        }
        return dtos;
    }

    public Reader getReaderById(final Long readerId) throws ReaderNotFoundException {
        return repository.findById(readerId).orElseThrow(() -> new ReaderNotFoundException("Reader with id " + readerId + " not found"));
    }

    @Transactional
    public Reader saveReader(final Reader reader) {
        return repository.save(reader);
    }

    @Transactional
    public void deleteReader(final Long readerId) throws ReaderNotFoundException {
        Reader reader = repository.findById(readerId).orElseThrow(() -> new ReaderNotFoundException("Reader with id " + readerId + " not found"));
        repository.delete(reader);
    }
}
