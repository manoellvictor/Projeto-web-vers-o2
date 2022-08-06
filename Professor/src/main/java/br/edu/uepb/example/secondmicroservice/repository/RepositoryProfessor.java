package br.edu.uepb.example.secondmicroservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.uepb.example.secondmicroservice.domain.Professor;

@Repository
public interface RepositoryProfessor extends JpaRepository<Professor, Long> {
    Optional<Professor> findByName(String name);
}