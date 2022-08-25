package com.david.eurocopa.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.david.eurocopa.models.dao.IJugadorDAO;
import com.david.eurocopa.models.entity.Jugador;

@Service
public class JugadorServiceImpl implements IJugadorService{

	@Autowired
	private IJugadorDAO jugadorDao;
	
	
	@Override
	@Transactional(readOnly=true)
	public List<Jugador> findAll() {
		return (List<Jugador>)jugadorDao.findAll();
	}


	@Override
	@Transactional(readOnly=true)
	public Jugador findById(Integer id) {
		return jugadorDao.findById(id).orElse(null);
	}


	@Override
	@Transactional
	public Jugador save(Jugador jugador) {
		return jugadorDao.save(jugador);
	}


	@Override
	@Transactional
	public void delete(Integer id) {
		jugadorDao.deleteById(id);
		
	}
	
	
}
