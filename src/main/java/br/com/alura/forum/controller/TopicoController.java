package br.com.alura.forum.controller;

import br.com.alura.forum.dto.TopicoDto;
import br.com.alura.forum.form.TopicoForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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
    public ResponseEntity<TopicoDto> create(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
        Topico topico = form.converter(this.cursoRespository);
        this.topicoRepository.save(topico);

        URI uri = uriBuilder
                .path("/topicos/{id}")
                .buildAndExpand(topico.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

//    @GetMapping
//    public ResponseEntity<?> detail(Long id) {
//        Optional<Topico> result = this.topicoRepository.findById(id);
//
//        if (result.isPresent()) {
//            return new ResponseEntity();
//        }
//
//        return ResponseEntity.notFound();
//    }
}
