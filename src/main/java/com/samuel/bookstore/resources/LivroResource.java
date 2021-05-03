package com.samuel.bookstore.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.samuel.bookstore.domain.Livro;
import com.samuel.bookstore.dtos.LivroDTO;
import com.samuel.bookstore.service.LivroService;

@CrossOrigin("*")
@RestController
@RequestMapping("/livros")
public class LivroResource {

	@Autowired
	private LivroService livroService;

	@GetMapping("/{livroId}")
	public ResponseEntity<Livro> findById(@PathVariable Integer livroId) {
		Livro livro = livroService.findById(livroId);
		return ResponseEntity.ok().body(livro);
	}

	@GetMapping
	public ResponseEntity<List<LivroDTO>> findAll(
			@RequestParam(value = "categoria", defaultValue = "0") Integer categoriaId) {
		List<Livro> list = livroService.findAll(categoriaId);
		List<LivroDTO> listDTO = list.stream().map(livro -> new LivroDTO(livro)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@PutMapping("/{livroId}")
	public ResponseEntity<Livro> update(@PathVariable Integer livroId, @Valid @RequestBody Livro livro) {
		livro = livroService.update(livroId, livro);
		return ResponseEntity.ok().body(livro);
	}
	
	@PatchMapping("/{livroId}")
	public ResponseEntity<Livro> updatePatch(@PathVariable Integer livroId, @RequestBody Livro livro) {
		livro = livroService.update(livroId, livro);
		return ResponseEntity.ok().body(livro);
	}
	
	@PostMapping
	public ResponseEntity<Livro> create(@RequestParam(value = "categoria", defaultValue = "0") Integer categoriaId, @Valid @RequestBody Livro novoLivro) {
		novoLivro = livroService.create(categoriaId, novoLivro);
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/livros/{id}").buildAndExpand(novoLivro.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping ("/{livroId}")
	public ResponseEntity<Void> delete(@PathVariable Integer livroId) {
		livroService.delete(livroId);
		return ResponseEntity.noContent().build();
	}
}
