package br.edu.uepb.example.alunoconsumer.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOEmail {
    
    private String emailTo;
    private String subject;
    private String text;
}
