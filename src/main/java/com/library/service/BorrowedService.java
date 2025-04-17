package com.library.service;

import com.library.domain.Borrowed;
import com.library.domain.BorrowedDto;
import com.library.mapper.BorrowedMapper;
import com.library.repository.BorrowedRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BorrowedService {

    private final BorrowedRepository borrowedRepository;
    private final BorrowedMapper borrowedMapper;

    public List<BorrowedDto> getAllBorrowed() {
        return borrowedMapper.mapToBorrowedDtoList(borrowedRepository.findAll());
    }

    public BorrowedDto getBorrowedById(final Long id) {
        return borrowedMapper.mapToBorrowedDto(borrowedRepository.findById(id).orElse(null));
    }

    @Transactional
    public BorrowedDto addBorrowedBook(final BorrowedDto borrowedDto) {
        Borrowed borrowed = borrowedMapper.mapToBorrowed(borrowedDto);
        return borrowedMapper.mapToBorrowedDto(borrowedRepository.save(borrowed));
    }
}
