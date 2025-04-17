package com.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BorrowedDto {

    private Long id;
    private Long copyId;
    private Long readerId;
    private LocalDateTime loanDate;
    private LocalDateTime dueDate;

}
