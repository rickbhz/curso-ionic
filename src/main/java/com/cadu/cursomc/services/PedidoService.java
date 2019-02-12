package com.cadu.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadu.cursomc.domain.Pedido;
import com.cadu.cursomc.repositories.PedidoRepository;
import com.cadu.cursomc.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	public Pedido find(Integer id) {

			Optional<Pedido> obj = repo.findById(id);
			return obj.orElseThrow(() -> new ObjectNotFoundException(
					"Objeto n�o encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
}
