package com.david.eurocopa.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.david.eurocopa.models.entity.Selecciones;
import com.david.eurocopa.models.services.ISeleccionesService;

@CrossOrigin(origins={"*"})
@RestController
@RequestMapping("/api")
public class SeleccionesController {
	@Autowired
	ISeleccionesService seleccionesService;
	
	@GetMapping("/selecciones")
	public ResponseEntity<?> index(){
		List<Selecciones> listaSelecciones = null;
		Map<String,Object> response = new HashMap<>();
		try {
			listaSelecciones = seleccionesService.findAll();
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<Selecciones>>(listaSelecciones,HttpStatus.OK);
	}
	
	@GetMapping("/selecciones/{id}")
	public ResponseEntity<?> index(@PathVariable String id){
		Selecciones selecciones = null;
		Map<String,Object> response = new HashMap<>();
		try {
			selecciones = seleccionesService.findById(id);  // comprueba acceso a la base de datos
		} catch (DataAccessException e) { // Falla al acceder a la base de datos
			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		// Accede a la base de datos pero la id no existe
		if(selecciones==null) {
			response.put("mensaje", "La seleccion con ID: ".concat(id.toString()).concat(" no existe en la base de datos"));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		// Ha podido acceder a la base de dato y la selecciones existe
		return new ResponseEntity<Selecciones>(selecciones,HttpStatus.OK);
	}
	
	@PostMapping("/selecciones")
	public ResponseEntity<?> create(@RequestBody Selecciones selecciones, BindingResult result) {
		Selecciones seleccionesNew = null;
		Map<String,Object> response = new HashMap<>();
		if(result.hasErrors()) {
			List<String> errores = result.getFieldErrors()
					.stream()
					.map(error->"El campo '" + error.getField() + "' " + error.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errores", errores);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		try {
			seleccionesNew = seleccionesService.save(selecciones);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La selección ha sido insertado con éxito");
		response.put("selecciones", seleccionesNew);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	@PutMapping("/selecciones/{id}")

	public ResponseEntity<?> update(@RequestBody Selecciones selecciones, BindingResult result, @PathVariable String id) {
		Selecciones seleccionesUpdated = null;
		Map<String,Object> response = new HashMap<>();
		Selecciones seleccionesParaModificar = null;
		if(result.hasErrors()) {
			List<String> errores = result.getFieldErrors()
					.stream()
					.map(error->"El campo '" + error.getField() + "' " + error.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errores", errores);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		try {
			seleccionesParaModificar = seleccionesService.findById(id);
		} catch (DataAccessException e1) {
			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error", e1.getMessage().concat(": ").concat(e1.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(seleccionesParaModificar==null) { // No existe
			response.put("mensaje", "La seleccion con ID: ".concat(id.toString()).concat(" no existe en la base de datos"));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}				
		try {
			seleccionesParaModificar.setId(selecciones.getId());
			seleccionesParaModificar.setJugadors(selecciones.getJugadors());
			seleccionesParaModificar.setNombre(selecciones.getNombre());

		} catch (DataAccessException e) {	
			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
		response.put("mensaje", "La seleccion ha sido modificado con exito");
		response.put("selecciones", seleccionesUpdated);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/selecciones/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) {
		Map<String,Object> response = new HashMap<>();
		try {
			seleccionesService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La seleccion ha sido borrado con exito");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
}
