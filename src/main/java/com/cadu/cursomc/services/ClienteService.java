package com.cadu.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.cadu.cursomc.domain.Cidade;
import com.cadu.cursomc.domain.Cliente;
import com.cadu.cursomc.domain.Endereco;
import com.cadu.cursomc.domain.enums.TipoCliente;
import com.cadu.cursomc.dto.ClienteDTO;
import com.cadu.cursomc.dto.ClienteNewDTO;
import com.cadu.cursomc.repositories.ClienteRepository;
import com.cadu.cursomc.services.exception.DataIntegrityException;
import com.cadu.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {

			Optional<Cliente> obj = repo.findById(id);
			return obj.orElseThrow(() -> new ObjectNotFoundException(
					"Objeto nao encontrado! Id: " + id + ", Nome: " + Cliente.class.getName()));
	}

	public Cliente insert(Cliente obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
		
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		
	}

	public void delete(Integer id) {
	    find(id);
	    try {
	    	repo.deleteById(id);
	    }		
		catch (DataIntegrityViolationException e ){
			throw new DataIntegrityException("Nao e possivel excluir um Cliente.");
		}
	}
	
	public List<Cliente> findAll() {
		
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
		
	}
	
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(),objDTO.getNome(),objDTO.getEmail(),null,null);
	}	
	
	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cli = new Cliente(null, objDTO.getNome(),objDTO.getEmail(),objDTO.getCpfOuCnpj(),TipoCliente.toEnum(objDTO.getTipoCliente()));
		Cidade cidade = new Cidade(objDTO.getCidadeId(),null,null);	
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getBairro(), objDTO.getComplemento(), objDTO.getCep(),cidade, cli);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDTO.getTelefone1());
		if (objDTO.getTelefone2()!=null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		if (objDTO.getTelefone3()!=null) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}	
		
		return cli;
		
	}		
}
