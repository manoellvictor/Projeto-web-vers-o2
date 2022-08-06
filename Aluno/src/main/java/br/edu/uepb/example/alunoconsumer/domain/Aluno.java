package br.edu.uepb.example.alunoconsumer.domain;

import javax.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "alunos")
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "matricula", unique = true)
    private String matricula;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "curso")
    private String curso;
     
    public Aluno(String name, String matricula, String email, String curso) {
        this.name = name;
        this.matricula = matricula;
        this.email = email;
        this.curso = curso;
    }
}
