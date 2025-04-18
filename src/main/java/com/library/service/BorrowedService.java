package com.library.service;

import com.library.domain.Borrowed;
import com.library.domain.BorrowedDto;
import com.library.domain.Copy;
import com.library.domain.CopyStatus;
import com.library.exception.BorrowedBookNotFoundException;
import com.library.exception.CopyNotFoundException;
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
    public BorrowedDto addBorrowedBook(final BorrowedDto borrowedDto) throws CopyNotFoundException {
        //This checks whether Copy we want is AVAILABLE and if so changes its status to BORROWED
        Copy copy = copyRepository.findById(borrowedDto.getCopyId()).orElseThrow(() -> new CopyNotFoundException("Copy with id " + borrowedDto.getCopyId() + " not found"));
        if(copy.getStatus().equals(CopyStatus.AVAILABLE)) {

            copy.setStatus(CopyStatus.BORROWED);

            Borrowed borrowed = borrowedMapper.mapToBorrowed(borrowedDto);
            return borrowedMapper.mapToBorrowedDto(borrowedRepository.save(borrowed));
        } else {
            throw new IllegalArgumentException("Borrowed book is not available");
        }
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
    public void deleteBorrowedBook(final Long id) throws BorrowedBookNotFoundException, CopyNotFoundException {
        //This changes book back to AVAILABLE when deleted
        Borrowed borrowed = borrowedRepository.findById(id).orElseThrow(()-> new BorrowedBookNotFoundException("Book with id " + id + " not found"));
        Copy copy = copyRepository.findById(borrowed.getCopy().getId()).orElseThrow(()-> new CopyNotFoundException("Copy with id " + borrowed.getCopy().getId() + " not found"));
        if(copy.getStatus().equals(CopyStatus.BORROWED)) {
            copy.setStatus(CopyStatus.AVAILABLE);
        }
        borrowedRepository.delete(borrowed);
    }
}
