package com.david.eurocopa.models.services;

import java.util.List;
import com.david.eurocopa.models.entity.Selecciones;

public interface ISeleccionesService {

public List<Selecciones> findAll();  // buscar todos los clientes
	
	public Selecciones findById(String id);  // buscar un cliente por Id
	
	public Selecciones save(Selecciones selecciones); // Inserta un cliente en la base de datos

	public void delete(String id); // Borrara un cliente de la base de datos
}
