package br.edu.uepb.example.secondmicroservice.controllers;

import java.util.List;
import java.util.Optional;

import br.edu.uepb.example.secondmicroservice.domain.Professor;
import br.edu.uepb.example.secondmicroservice.dtos.DTOEmail;
import br.edu.uepb.example.secondmicroservice.repository.RepositoryProfessor;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping(value = "/professores")
public class ControllerProfessor {

    @Autowired
    private RepositoryProfessor professorRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.exchange}")
    public String EXCHANGE_NAME;


    @GetMapping
    public List<Professor> getProfessors() {
        return professorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Professor> getProfessorById(@PathVariable Long id) {
        return professorRepository.findById(id);
    }

    @PostMapping(consumes = {"*/*"})
    public Professor createProfessor(@RequestBody Professor professor) {

        try {
           DTOEmail emailDTO = new DTOEmail("leandrol@gmail.com", "Professor criado", "Sucesso!");
            String json = new ObjectMapper().writeValueAsString(emailDTO);
            Message message = MessageBuilder.withBody(json.getBytes())
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .build();
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, "", message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return professorRepository.save(professor);
    }

    @PutMapping("/{id}")
    public Professor updateProfessor(@PathVariable("id") Long id, @RequestBody Professor professor) {
        
        try {
            DTOEmail emailDTO = new DTOEmail("leandrol@gmail.com", "Professor Editado", "Sucesso!");
            String json = new ObjectMapper().writeValueAsString(emailDTO);
            Message message = MessageBuilder.withBody(json.getBytes())
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .build();
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, "", message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        professor.setId(id);
        return professorRepository.save(professor);
    }

    @DeleteMapping("/{id}")
    public void deleteProfessor(@PathVariable Long id) {
        professorRepository.delete(professorRepository.findById(id).get());
    }
}
