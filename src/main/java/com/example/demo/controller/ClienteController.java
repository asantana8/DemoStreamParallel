package com.example.demo.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.entity.Cliente;
import com.example.demo.service.ClienteService;
import com.example.demo.service.ProcessamentoParalelo;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	ClienteService clienteService;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	ProcessamentoParalelo paralelo;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente salvar(@RequestBody Cliente cliente) {
		return clienteService.salvar(cliente);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Cliente> listaCliente() {
		return clienteService.listaCliente();
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Cliente buscarCliente(@PathVariable("id") Long id) {
		return clienteService.buscarPorId(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado."));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerCliente(@PathVariable("id") Long id) {
		clienteService.buscarPorId(id).map(cliente -> {
			clienteService.removerPorId(id);
			return Void.TYPE;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não entroncado."));

	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarCliente(@PathVariable("id") Long id, @RequestBody Cliente cliente) {
		clienteService.buscarPorId(id).map(clienteBase -> {
			clienteService.buscarPorId(id);
			modelMapper.map(cliente, clienteBase);
			clienteService.salvar(clienteBase);
			return Void.TYPE;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não entroncado."));
	}

	@GetMapping("/atualizarVarios")
	@ResponseStatus(HttpStatus.OK)
	public void atualizarVarios() {
		clienteService.atualizarVarios();
	}

	@GetMapping("/paralelo")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> listaClienteParalelo() {		
		var retorno = paralelo.processamentoStart();
		return (retorno.isEmpty()) ? ResponseEntity.ok().body(HttpStatus.NOT_FOUND) : ResponseEntity.ok().body(retorno);
	}

	@GetMapping("/resetStatus")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> resetClienteParalelo() {
		var retorno = paralelo.resetStatusClientes();
		return (retorno.isEmpty()) ? ResponseEntity.ok().body(HttpStatus.NOT_FOUND) : ResponseEntity.ok().body(retorno);
	}

}
