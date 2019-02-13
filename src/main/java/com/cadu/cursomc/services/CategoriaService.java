package com.cadu.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.cadu.cursomc.domain.Categoria;
import com.cadu.cursomc.repositories.CategoriaRepository;
import com.cadu.cursomc.services.exception.DataIntegrityException;
import com.cadu.cursomc.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {

			Optional<Categoria> obj = repo.findById(id);
			return obj.orElseThrow(() -> new ObjectNotFoundException(
					"Objeto nao encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repo.save(obj);
		
	}
	
	public void delete(Integer id) {
        find(id);
        try {
        	repo.deleteById(id);
        }		
		catch (DataIntegrityViolationException e ){
			throw new DataIntegrityException("Nao e possivel excluir uma categoria com produtos associados.");
		}
	}
	
	public List<Categoria> findAll() {
		
		return repo.findAll();
	}
}
