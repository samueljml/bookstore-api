package com.samuel.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samuel.bookstore.domain.Categoria;
import com.samuel.bookstore.dtos.CategoriaDTO;
import com.samuel.bookstore.repositories.CategoriaRepository;
import com.samuel.bookstore.service.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepo;

	public Categoria findById(Integer categoriaId) {
		Optional<Categoria> categoria = categoriaRepo.findById(categoriaId);
		
		return categoria.orElseThrow(() -> new ObjectNotFoundException(Categoria.class.getSimpleName(), categoriaId));
	}

	public List<Categoria> findAll() {
		return categoriaRepo.findAll();
	}

	public Categoria create(Categoria categoria) {
		categoria.setId(null);
		return categoriaRepo.save(categoria);
	}

	public Categoria update(Integer categoriaId, CategoriaDTO categoriaDTO) {
		Categoria categoria = findById(categoriaId);
		categoria.setNome(categoriaDTO.getNome());
		categoria.setDescricao(categoriaDTO.getDescricao());
		return categoriaRepo.save(categoria);
	}

	public void delete(Integer categoriaId) {
		findById(categoriaId);
		try {
			categoriaRepo.deleteById(categoriaId);
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			throw new com.samuel.bookstore.service.exceptions.DataIntegrityViolationException(
					"Categoria não pode ser deletada! Possui livros associados");
		}
	}
}