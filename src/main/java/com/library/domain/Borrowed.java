package com.library.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "BORROWED_BOOKS")
public class Borrowed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COPY_ID", referencedColumnName = "ID")
    private Copy copy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "READER_ID", referencedColumnName = "ID")
    private Reader reader;

    @Column(name = "LOAN_DATE")
    private LocalDateTime loanDate;

    @Column(name = "DUE_DATE")
    private LocalDateTime dueDate;

}
