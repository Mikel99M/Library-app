package com.library.mapper;

import com.library.domain.Title;
import com.library.domain.TitleDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TitleMapper {

    public Title mapToTitle(final TitleDto titleDto) {
        return new Title(
                titleDto.getId(),
                titleDto.getTitle(),
                titleDto.getAuthor(),
                titleDto.getPublicationDate()
        );
    }

    public TitleDto mapToTitleDto(final Title title) {
        return new TitleDto(
                title.getId(),
                title.getTitle(),
                title.getAuthor(),
                title.getPublicationDate()
        );
    }

    public List<TitleDto> mapToTitleDtoList(final List<Title> titles) {
        return titles.stream()
                .map(this::mapToTitleDto)
                .collect(Collectors.toList());
    }

    public List<Title> mapToTitleList(final List<TitleDto> titleDtos) {
        return titleDtos.stream()
                .map(this::mapToTitle)
                .collect(Collectors.toList());
    }

}
