package br.edu.uepb.example.alunoconsumer.controller;

import java.util.List;
import java.util.Optional;

import br.edu.uepb.example.alunoconsumer.dto.*; 
import br.edu.uepb.example.alunoconsumer.domain.*;
import br.edu.uepb.example.alunoconsumer.repository.*;

//import org.ietf.jgss.MessageProp;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/alunos")
public class ControllerAluno {

    @Autowired
    private RepositoryAluno repositoryAluno;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.exchange}")
    public String EXCHANGE_NAME;

    @GetMapping
    public List<Aluno> getAlunos() {
        return repositoryAluno.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Aluno> getAlunoById(@PathVariable Long id) {
        return repositoryAluno.findById(id);
    }

    @PostMapping(consumes = {"*/*"})
    public Aluno createAluno(@RequestBody Aluno aluno) {

        try {
            DTOEmail emailDTO = new DTOEmail("leandrol@gmail.com", "Aluno criado", "Aluno foi criado!");
            String json = new ObjectMapper().writeValueAsString(emailDTO);
            Message message = MessageBuilder.withBody(json.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, "", message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return repositoryAluno.save(aluno);
    }
    
    @DeleteMapping("/{id}")
    public void deleteAluno(@PathVariable Long id) {
        repositoryAluno.delete(repositoryAluno.findById(id).get());
    }  

    @PutMapping("/{id}")
    public Aluno updateAluno(@PathVariable("id") Long id, @RequestBody Aluno aluno) {

        try {
            DTOEmail emailDTO = new DTOEmail("leandrol@gmail.com", "Aluno alterado", "O aluno foi alterado!");
             String json = new ObjectMapper().writeValueAsString(emailDTO);
             Message message = MessageBuilder.withBody(json.getBytes())
                     .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                     .build();
             rabbitTemplate.convertAndSend(EXCHANGE_NAME, "", message);
         } catch (JsonProcessingException e) {
             e.printStackTrace();
         }
         aluno.setId(id);
         return repositoryAluno.save(aluno);
    }
}
