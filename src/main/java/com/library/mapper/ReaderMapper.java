package com.library.mapper;

import com.library.domain.Reader;
import com.library.domain.ReaderDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReaderMapper {

    public Reader mapToReader(final ReaderDto readerDto) {
        return new Reader(
                readerDto.getId(),
                readerDto.getName(),
                readerDto.getLastName(),
                readerDto.getDateOfCreation(),
                readerDto.getBorrowedBooks()
        );
    }

    public ReaderDto mapToReaderDto(final Reader reader) {
        return new ReaderDto(
                reader.getId(),
                reader.getName(),
                reader.getLastName(),
                reader.getDateOfCreation(),
                reader.getBorrowedBooks()
        );
    }

    public List<ReaderDto> mapToReaderDtoList(final List<Reader> readers) {
        return readers.stream()
                .map(this::mapToReaderDto)
                .collect(Collectors.toList());
    }

    public List<Reader> mapToReaderList(final List<ReaderDto> readerDtos) {
        return readerDtos.stream()
                .map(this::mapToReader)
                .collect(Collectors.toList());
    }
}