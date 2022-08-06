package com.edu.consumerService.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOEmail {
    private String emailTo;
    private String subject;
    private String text;
}