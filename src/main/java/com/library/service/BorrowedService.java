package com.library.service;

import com.library.domain.Borrowed;
import com.library.domain.BorrowedDto;
import com.library.exception.BorrowedBookNotFoundException;
import com.library.mapper.BorrowedMapper;
import com.library.repository.BorrowedRepository;
import com.library.repository.CopyRepository;
import com.library.repository.ReaderRepository;
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
    private final CopyRepository copyRepository;
    private final ReaderRepository readerRepository;

    public List<BorrowedDto> getAllBorrowed() {
        return borrowedMapper.mapToBorrowedDtoList(borrowedRepository.findAll());
    }

    public BorrowedDto getBorrowedById(final Long id) throws BorrowedBookNotFoundException {
        return borrowedMapper.mapToBorrowedDto(borrowedRepository.findById(id).orElseThrow(()-> new BorrowedBookNotFoundException("Borrowed book with id " + id + " not found")));
    }

    @Transactional
    public BorrowedDto addBorrowedBook(final BorrowedDto borrowedDto) {
        Borrowed borrowed = borrowedMapper.mapToBorrowed(borrowedDto);
        return borrowedMapper.mapToBorrowedDto(borrowedRepository.save(borrowed));
    }

    @Transactional
    public BorrowedDto updateBorrowedBook(final BorrowedDto borrowedDto, final Long id) throws BorrowedBookNotFoundException {
        Borrowed borrowed = borrowedRepository.findById(id).orElseThrow(()-> new BorrowedBookNotFoundException("Book with id " + id + " not found"));
        borrowed.setCopy(copyRepository.findById(borrowedDto.getCopyId()).orElseThrow(IllegalArgumentException::new));
        borrowed.setReader(readerRepository.findById(borrowedDto.getReaderId()).orElseThrow(IllegalArgumentException::new));
        borrowed.setLoanDate(borrowedDto.getLoanDate());
        borrowed.setDueDate(borrowedDto.getDueDate());
        return borrowedMapper.mapToBorrowedDto(borrowedRepository.save(borrowed));
    }

    @Transactional
    public void deleteBorrowedBook(final Long id) throws BorrowedBookNotFoundException {
        Borrowed borrowed = borrowedRepository.findById(id).orElseThrow(()-> new BorrowedBookNotFoundException("Book with id " + id + " not found"));
        borrowedRepository.delete(borrowed);
    }
}
