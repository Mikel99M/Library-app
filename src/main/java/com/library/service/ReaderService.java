package com.library.service;

import com.library.domain.Reader;
import com.library.domain.ReaderDto;
import com.library.exception.ReaderNotFoundException;
import com.library.mapper.ReaderMapper;
import com.library.repository.ReaderRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReaderService {

    private final ReaderRepository readerRepository;
    private final ReaderMapper readerMapper;

    public List<ReaderDto> getAllReaders() {
        return readerMapper.mapToReaderDtoList(readerRepository.findAll());
    }

    public List<ReaderDto> getAllReadersWithBorrowedBooks() {
        List<Reader> readers = readerRepository.findAll();
        List<ReaderDto> dtos = new ArrayList<>();
        for (Reader reader : readers) {
            if (!reader.getBorrowedBooks().isEmpty()) {
                dtos.add(readerMapper.mapToReaderDto(reader));
            }
        }
        return dtos;
    }

    public List<ReaderDto> getAllReadersWithoutBorrowedBooks() {
        List<Reader> readers = readerRepository.findAll();
        List<ReaderDto> dtos = new ArrayList<>();
        for (Reader reader : readers) {
            if (reader.getBorrowedBooks().isEmpty()) {
                dtos.add(readerMapper.mapToReaderDto(reader));
            }
        }
        return dtos;
    }

    public ReaderDto getReaderById(final Long readerId) throws ReaderNotFoundException {
        return readerMapper.mapToReaderDto(readerRepository.findById(readerId).orElseThrow(() -> new ReaderNotFoundException("Reader with id " + readerId + " not found")));
    }

    @Transactional
    public ReaderDto addReader(@Valid final ReaderDto readerDto) {
        return readerMapper.mapToReaderDto(readerRepository.save(readerMapper.mapToReader(readerDto)));
    }

    @Transactional
    public ReaderDto updateReader(ReaderDto readerDto, Long id) throws ReaderNotFoundException {
        Reader reader = readerRepository.findById(id).orElseThrow(()-> new ReaderNotFoundException("Reader with id " + id + " not found"));
        reader.setName(readerDto.getName());
        reader.setLastName(readerDto.getLastName());
        reader.setDateOfCreation(readerDto.getDateOfCreation());
        return readerMapper.mapToReaderDto(readerRepository.save(reader));
    }

    @Transactional
    public void deleteReader(final Long readerId) throws ReaderNotFoundException {
        Reader reader = readerRepository.findById(readerId).orElseThrow(() -> new ReaderNotFoundException("Reader with id " + readerId + " not found"));
        readerRepository.delete(reader);
    }


}
