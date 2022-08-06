package br.edu.uepb.example.secondmicroservice.domain;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "professores")
public class Professor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "matricula", unique = true)
    private String matricula;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "formacao")
    private String formacao;

    public Professor(String name, String matricula, String email, String formacao) {
        this.name = name;
        this.matricula = matricula;
        this.email = email;
        this.formacao = formacao;
    }
}