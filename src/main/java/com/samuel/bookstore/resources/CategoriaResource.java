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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.samuel.bookstore.domain.Categoria;
import com.samuel.bookstore.dtos.CategoriaDTO;
import com.samuel.bookstore.service.CategoriaService;

@CrossOrigin("*")
@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping("/{categoriaId}")
	public ResponseEntity<Categoria> findById(@PathVariable Integer categoriaId){
		Categoria categoria = categoriaService.findById(categoriaId);
		return ResponseEntity.ok().body(categoria);
	}
	
	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> lista = categoriaService.findAll();
		List<CategoriaDTO> listDTO = lista.stream().map(categoria -> new CategoriaDTO(categoria)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@PostMapping
	public ResponseEntity<Categoria> create(@Valid @RequestBody Categoria categoria) {
		categoria = categoriaService.create(categoria);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{categoriaId}")
	public ResponseEntity<CategoriaDTO> update(@Valid @PathVariable Integer categoriaId, @RequestBody CategoriaDTO categoriaDTO) {
		Categoria categoria = categoriaService.update(categoriaId, categoriaDTO);
		return ResponseEntity.ok().body(new CategoriaDTO(categoria));
	}
	
	@DeleteMapping("/{categoriaId}")
	public ResponseEntity<Void> delete(@PathVariable Integer categoriaId) {
		categoriaService.delete(categoriaId);
		return ResponseEntity.noContent().build();
	}
}