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


import com.david.eurocopa.models.entity.Jugador;
import com.david.eurocopa.models.services.IJugadorService;

@CrossOrigin(origins= {"*"})
@RestController
@RequestMapping("/api")
public class JugadorController {
		@Autowired
		IJugadorService jugadorService;
		
		@GetMapping("/jugador")
		public ResponseEntity<?> index(){
			List<Jugador> listaJugador = null;
			Map<String,Object> response = new HashMap<>();
			try {
				listaJugador = jugadorService.findAll();
			} catch (DataAccessException e) {
				response.put("mensaje", "Error al acceder a la base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<List<Jugador>>(listaJugador,HttpStatus.OK);
		}
	

	@GetMapping("/jugador/{id}")
	public ResponseEntity<?> index(@PathVariable Integer id){
		Jugador jugador = null;
		Map<String,Object> response = new HashMap<>();
		try {
			jugador = jugadorService.findById(id);  // comprueba acceso a la base de datos
		} catch (DataAccessException e) { // Falla al acceder a la base de datos
			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		// Accede a la base de datos pero la id no existe
		if(jugador==null) {
			response.put("mensaje", "El jugador con ID: ".concat(id.toString()).concat(" no existe en la base de datos"));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		// Ha podido acceder a la base de dato y el jugador existe
		return new ResponseEntity<Jugador>(jugador,HttpStatus.OK);
	}

	@PostMapping("/jugador")
	public ResponseEntity<?> create(@RequestBody Jugador jugador, BindingResult result) {
		Jugador jugadorNew = null;
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
			jugadorNew = jugadorService.save(jugador);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El jugador ha sido insertado con éxito");
		response.put("jugador", jugadorNew);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}

	@PutMapping("/jugador/{id}")

	public ResponseEntity<?> update(@RequestBody Jugador jugador, BindingResult result, @PathVariable Integer id) {
		Jugador jugadorUpdated = null;
		Map<String,Object> response = new HashMap<>();
		Jugador jugadorParaModificar = null;
		if(result.hasErrors()) {
			List<String> errores = result.getFieldErrors()
					.stream()
					.map(error->"El campo '" + error.getField() + "' " + error.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errores", errores);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		try {
			jugadorParaModificar = jugadorService.findById(id);
		} catch (DataAccessException e1) {
			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error", e1.getMessage().concat(": ").concat(e1.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(jugadorParaModificar==null) { // No existe
			response.put("mensaje", "El jugador con ID: ".concat(id.toString()).concat(" no existe en la base de datos"));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}				
		try {
			jugadorParaModificar.setId(jugador.getId());
			jugadorParaModificar.setSelecciones(jugador.getSelecciones());
			jugadorParaModificar.setNombre(jugador.getNombre());
			jugadorParaModificar.setEdad(jugador.getEdad());
			jugadorParaModificar.setPais(jugador.getPais());
			jugadorParaModificar.setAltura(jugador.getAltura());
			jugadorParaModificar.setPeso(jugador.getPeso());
			jugadorParaModificar.setPosicion(jugador.getPosicion());
			jugadorParaModificar.setClub(jugador.getClub());
			jugadorParaModificar.setValor(jugador.getValor());
			jugadorParaModificar.setImagen(jugador.getImagen());
			jugadorUpdated = jugadorService.save(jugadorParaModificar);	// guardos los cambios
		} catch (DataAccessException e) {	
			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
		response.put("mensaje", "El jugador ha sido modificado con exito");
		response.put("jugador", jugadorUpdated);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/jugador/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		Map<String,Object> response = new HashMap<>();
		try {
			jugadorService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al acceder a la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El jugador ha sido borrado con exito");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}

}
