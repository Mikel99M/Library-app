package com.library.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "READERS")
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private Long id;

    @Column(name = "NAME")
    @NotNull
    private String name;

    @Column(name = "LAST_NAME")
    @NotNull
    private String lastName;

    @Column(name = "DATE_OF_CREATION")
    private LocalDateTime dateOfCreation;

    @OneToMany(mappedBy = "reader", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Borrowed> borrowedBooks = new ArrayList<>();

}