package com.cadu.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadu.cursomc.domain.Categoria;
import com.cadu.cursomc.repositories.CategoriaRepository;
import com.cadu.cursomc.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {

			Optional<Categoria> obj = repo.findById(id);
			return obj.orElseThrow(() -> new ObjectNotFoundException(
					"Objeto n�o encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
}
