package com.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TitleDto {

    private Long id;
    private String title;
    private String author;
    private LocalDate publicationDate;

}
