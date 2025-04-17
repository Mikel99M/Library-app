package com.library.mapper;

import com.library.domain.Borrowed;
import com.library.domain.BorrowedDto;
import com.library.repository.CopyRepository;
import com.library.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class BorrowedMapper {

    private final CopyRepository copyRepository;
    private final ReaderRepository readerRepository;

    public BorrowedDto mapToBorrowedDto(final Borrowed borrowed) {
        if (borrowed == null) {
            throw new IllegalArgumentException("borrowed cannot be null");
        }

        return new BorrowedDto(
                borrowed.getId(),
                borrowed.getCopy().getId(),
                borrowed.getReader().getId(),
                borrowed.getLoanDate(),
                borrowed.getLoanDate()
        );
    }

    public Borrowed mapToBorrowed(final BorrowedDto borrowedDto) {
        if (borrowedDto == null) {
            throw new IllegalArgumentException("dto cannot be null");
        }


        return new Borrowed(
                borrowedDto.getId(),
                copyRepository.findById(borrowedDto.getCopyId()).orElse(null),
                readerRepository.findById(borrowedDto.getReaderId()).orElse(null),
                borrowedDto.getLoanDate(),
                borrowedDto.getDueDate()
        );
    }

    public List<Borrowed> mapToBorrowedList(final List<BorrowedDto> borrowedDtoList) {
        if (borrowedDtoList == null) {
            throw new IllegalArgumentException("dtoList cannot be null");
        }

        return borrowedDtoList.stream()
                .map(this::mapToBorrowed)
                .collect(Collectors.toList());
    }

    public List<BorrowedDto> mapToBorrowedDtoList(final List<Borrowed> borrowedList) {
        return borrowedList.stream()
                .map(this::mapToBorrowedDto)
                .collect(Collectors.toList());
    }

}
