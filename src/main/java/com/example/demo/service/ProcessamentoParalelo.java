package com.example.demo.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Cliente;
import com.example.demo.respository.ClienteRepository;

@Service
public class ProcessamentoParalelo {

	@Autowired
	ClienteRepository clienteRepository;

	public List<String> processamentoStart() {
		System.out.println("Tarefa iniciada em " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

		List<Cliente> clientes = clienteRepository.findAll();
		List<String> listaRetorno = new ArrayList<>();

		clientes.stream().parallel().forEach(c -> {
			c.setStatus("OK");
			clienteRepository.save(c);
			listaRetorno.add("Cliente salvo: " + c.getId() + "-" + c.getNome());			
		});
		
		listaRetorno.parallelStream().forEach(l -> System.out.println(l));
		System.out.println("Tarefa concluída em " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		return listaRetorno;
	}

	public List<String> resetStatusClientes() {
		System.out.println("Tarefa iniciada em " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		List<Cliente> clientes = clienteRepository.findAll();
		List<String> listaRetorno = new ArrayList<>();
		clientes.stream().parallel().forEach(c -> {
			c.setStatus("");
			clienteRepository.save(c);
			listaRetorno.add("Status limpo: " + c.getId() + "-" + c.getNome());
		});
		
		listaRetorno.parallelStream().forEach(l -> System.out.println(l));
		System.out.println("Tarefa concluída em " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		return listaRetorno;
	}

}
