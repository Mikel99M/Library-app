package com.library.mapper;

import com.library.domain.Copy;
import com.library.domain.CopyDto;
import com.library.domain.Title;
import com.library.repository.CopyRepository;
import com.library.repository.TitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CopyMapper {

    private final TitleRepository titleRepository;
    private final CopyRepository copyRepository;

    public Copy mapToCopy(final CopyDto copyDto) {
        if (copyDto == null || copyDto.getTitleId() == null) {
            throw new IllegalArgumentException("CopyDto and titleId cannot be null");
        }
        Title title = titleRepository.findById(copyDto.getTitleId()).orElse(null);

        return new Copy(
                copyDto.getId(),
                title,
                copyDto.getStatus()
        );
    }

    public CopyDto mapToCopyDto(final Copy copy) {
        if (copy == null) {
            throw new IllegalArgumentException("Copy cannot be null");
        }

        return new CopyDto(
                copy.getId(),
                copy.getTitle().getId(),
                copy.getStatus()
        );
    }

    public List<Copy> mapToCopyList(final List<CopyDto> copyDtos) {
        return copyDtos.stream()
                .map(this::mapToCopy)
                .collect(Collectors.toList());
    }

    public List<CopyDto> mapToCopyDtoList(final List<Copy> copies) {
        return copies.stream()
                .map(this::mapToCopyDto)
                .collect(Collectors.toList());
    }
}
