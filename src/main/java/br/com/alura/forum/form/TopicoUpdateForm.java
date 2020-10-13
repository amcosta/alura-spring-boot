package br.com.alura.forum.form;

import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.TopicoRepository;

public class TopicoUpdateForm {
    private String titulo;
    private String mensagem;
    private TopicoRepository topicoRepository;

    public TopicoUpdateForm(String titulo, String mensagem) {
        this.titulo = titulo;
        this.mensagem = mensagem;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setRepository(TopicoRepository topicoRepository) {
        this.topicoRepository = topicoRepository;
    }

    public Topico update(Long id) {
        Topico topico = this.topicoRepository.getOne(id);
        topico.setTitulo(this.titulo);
        topico.setMensagem(this.mensagem);
        return topico;
    }
}
