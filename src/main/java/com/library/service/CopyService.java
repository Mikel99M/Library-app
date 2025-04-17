package com.library.service;

import com.library.domain.Copy;
import com.library.domain.CopyDto;
import com.library.domain.Title;
import com.library.exception.CopyNotFoundException;
import com.library.mapper.CopyMapper;
import com.library.repository.CopyRepository;
import com.library.repository.TitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CopyService {

    private final CopyRepository copyRepository;
    private final CopyMapper copyMapper;
    private final TitleRepository titleRepository;

    public List<CopyDto> getAllCopies() {
        return copyMapper.mapToCopyDtoList(copyRepository.findAll());
    }

    public CopyDto getCopyById(final Long copyId) throws CopyNotFoundException {
        return copyMapper.mapToCopyDto(copyRepository.findById(copyId).orElseThrow(() -> new CopyNotFoundException("Copy with id " + copyId + " not found")));
    }

    @Transactional
    public CopyDto createCopy(final CopyDto copydto) {
        Copy copy = copyMapper.mapToCopy(copydto);
        return copyMapper.mapToCopyDto(copyRepository.save(copy));
    }

    @Transactional
    public CopyDto updateCopy(final CopyDto copyDto, final Long id) throws CopyNotFoundException {
        Copy copy = copyRepository.findById(id).orElseThrow(() -> new CopyNotFoundException("Copy with id " + copyDto.getId() + " not found"));
        Title title = titleRepository.findById(copyDto.getTitleId()).orElseThrow(() -> new IllegalArgumentException("Title with id " + copyDto.getTitleId() + " not found"));
        copy.setTitle(title);
        copy.setStatus(copyDto.getStatus());
        return copyMapper.mapToCopyDto(copyRepository.save(copy));
    }

    @Transactional
    public void deleteCopy(final Long copyId) throws CopyNotFoundException {
        Copy copy = copyRepository.findById(copyId).orElseThrow(() -> new CopyNotFoundException("Copy with id " + copyId + " not found"));
        copyRepository.delete(copy);
    }
}
