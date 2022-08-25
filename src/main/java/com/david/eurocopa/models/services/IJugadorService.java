package com.david.eurocopa.models.services;

import java.util.List;

import com.david.eurocopa.models.entity.Jugador;

public interface IJugadorService {

	public List<Jugador> findAll();  // buscar todos los clientes
	
	public Jugador findById(Integer id);  // buscar un cliente por Id
	
	public Jugador save(Jugador jugador); // Inserta un cliente en la base de datos

	public void delete(Integer id); // Borrara un cliente de la base de datos
	
}
