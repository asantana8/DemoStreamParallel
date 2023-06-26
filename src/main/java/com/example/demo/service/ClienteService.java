package com.example.demo.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Cliente;
import com.example.demo.respository.ClienteRepository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;
	public List<Cliente> clientes;

	public Cliente salvar(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	public List<Cliente> listaCliente() {
		return clienteRepository.findAll();
	}

	public Optional<Cliente> buscarPorId(Long id) {
		return clienteRepository.findById(id);
	}

	public void removerPorId(Long id) {
		clienteRepository.deleteById(id);
	}

	public void atualizarVarios() {
		System.out.println("Tarefa iniciada em " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		List<Cliente> grupo = clienteRepository.findAll();
		if (!grupo.isEmpty()) {
			grupo.stream().forEach(c -> {
				c.setStatus("OK");
			});			
			clienteRepository.saveAll(grupo);
		}
		System.out.println("Tarefa concluída em " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
	}
}
