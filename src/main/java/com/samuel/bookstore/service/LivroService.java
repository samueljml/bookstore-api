package com.samuel.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samuel.bookstore.domain.Categoria;
import com.samuel.bookstore.domain.Livro;
import com.samuel.bookstore.repositories.LivroRepository;
import com.samuel.bookstore.service.exceptions.ObjectNotFoundException;

@Service
public class LivroService {

	@Autowired
	private LivroRepository repository;
	
	@Autowired
	private CategoriaService categoriaService;

	public Livro findById(Integer livroId) {
		Optional<Livro> livro = repository.findById(livroId);
		return livro.orElseThrow(() -> new ObjectNotFoundException(Livro.class.getSimpleName(), livroId));
	}

	public List<Livro> findAll(Integer categoriaId) {
		categoriaService.findById(categoriaId);
		return repository.findAllByCategoria(categoriaId);
	}

	public Livro update(Integer livroId, Livro livroAtualizado) {
		Livro livro = findById(livroId);
		updateData(livro, livroAtualizado);
		return repository.save(livro);
	}
	public void updateData (Livro livro, Livro livroAtualizado) {
		livro.setTitulo(livroAtualizado.getTitulo());
		livro.setNomeAutor(livroAtualizado.getNomeAutor());
		livro.setTexto(livroAtualizado.getTexto());
	}

	public Livro create(Integer categoriaId, Livro livro) {
		livro.setId(null);
		Categoria categoria = categoriaService.findById(categoriaId);
		livro.setCategoria(categoria);
		return repository.save(livro);
	}

	public void delete(Integer livroId) {
		Livro livro = findById(livroId);
		repository.delete(livro);
	}
}
