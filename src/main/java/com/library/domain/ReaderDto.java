package com.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private List<Borrowed> borrowedBooks;
}
