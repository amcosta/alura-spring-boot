package br.com.alura.forum.controller;

import br.com.alura.forum.dto.TopicoDto;
import br.com.alura.forum.form.TopicoForm;
import br.com.alura.forum.form.TopicoUpdateForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

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
    @Transactional
    public ResponseEntity<TopicoDto> create(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
        Topico topico = form.converter(this.cursoRespository);
        this.topicoRepository.save(topico);

        URI uri = uriBuilder
                .path("/topicos/{id}")
                .buildAndExpand(topico.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        Optional<Topico> optional = this.topicoRepository.findById(id);
        if (!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optional.get());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoDto> update(@PathVariable Long id, @RequestBody @Valid TopicoUpdateForm form) {
        Optional<Topico> optional = this.topicoRepository.findById(id);
        if (!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        form.setRepository(this.topicoRepository);
        Topico topico = form.update(id);
        return ResponseEntity.ok().body(new TopicoDto(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Topico> optional = this.topicoRepository.findById(id);
        if (!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        this.topicoRepository.deleteById(id);
        return ResponseEntity.accepted().build();
    }
}
