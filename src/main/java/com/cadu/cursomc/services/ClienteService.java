package com.cadu.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadu.cursomc.domain.Cliente;
import com.cadu.cursomc.repositories.ClienteRepository;
import com.cadu.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {

			Optional<Cliente> obj = repo.findById(id);
			return obj.orElseThrow(() -> new ObjectNotFoundException(
					"Objeto n�o encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
}
