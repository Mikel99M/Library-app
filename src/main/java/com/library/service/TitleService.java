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

    private final TitleRepository repository;
    private final TitleMapper mapper;

    public List<TitleDto> getAllTitles() {
        return mapper.mapToTitleDtoList(repository.findAll());
    }

    public Title getTitleById(final Long titleId) {
        return repository.findById(titleId).orElse(null);
    }

    @Transactional
    public Title saveTitle(final Title title) {
        return repository.save(title);
    }

    @Transactional
    public void deleteTitle(final Long titleID) throws TitleNotFoundException {
        Title title = repository.findById(titleID).orElseThrow(TitleNotFoundException::new);
        repository.delete(title);
    }
}
