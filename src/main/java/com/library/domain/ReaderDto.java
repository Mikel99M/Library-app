package com.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReaderDto {

    private Long id;
    private String name;
    private String lastName;
    private LocalDateTime dateOfCreation;
    private List<Borrowed> borrowedBooks;
}
