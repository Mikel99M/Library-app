package com.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CopyDto {

    private Long id;
    private Long titleId;
    private CopyStatus status;

}
