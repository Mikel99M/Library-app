package com.library.service;

import com.library.domain.Title;
import com.library.domain.TitleDto;
import com.library.exception.TitleNotFoundException;
import com.library.mapper.TitleMapper;
import com.library.repository.TitleRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TitleService {

    private final TitleRepository titleRepository;
    private final TitleMapper titleMapper;

    public List<TitleDto> getAllTitles() {
        return titleMapper.mapToTitleDtoList(titleRepository.findAll());
    }

    public TitleDto getTitleById(final Long titleId) throws TitleNotFoundException {
        return titleMapper.mapToTitleDto(titleRepository.findById(titleId).orElseThrow(()-> new TitleNotFoundException("Title with id " + titleId + " not found")));
    }

    @Transactional
    public TitleDto addTitle(final TitleDto titleDto) {
        return titleMapper.mapToTitleDto(titleRepository.save(titleMapper.mapToTitle(titleDto)));
    }

    @Transactional
    public TitleDto updateTitle(final TitleDto titleDto, final Long titleId) throws TitleNotFoundException {
        Title title = titleRepository.findById(titleId).orElseThrow(()-> new TitleNotFoundException("Title with id " + titleId + " not found"));
        title.setTitle(titleDto.getTitle());
        title.setAuthor(titleDto.getAuthor());
        title.setPublicationDate(titleDto.getPublicationDate());
        return titleMapper.mapToTitleDto(titleRepository.save(title));
    }

    @Transactional
    public void deleteTitle(final Long titleID) throws TitleNotFoundException {
        Title title = titleRepository.findById(titleID).orElseThrow(()-> new TitleNotFoundException("Title with id " + titleID + " not found"));
        titleRepository.delete(title);
    }
}
