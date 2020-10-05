package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.form.TopicoForm;
import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private CursoRepository cursoRespository;

    @GetMapping
    public List<TopicoDto> index() {
        List<Topico> topicos = this.topicoRepository.findAll();
        return TopicoDto.createFromArray(topicos);
    }

    @PostMapping
    public Topico create(@RequestBody TopicoForm form) {
        Topico topico = form.converter(this.cursoRespository);
        return this.topicoRepository.save(topico);
    }
}
