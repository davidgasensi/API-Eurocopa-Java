package com.david.eurocopa.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.david.eurocopa.models.dao.ISeleccionesDAO;

import com.david.eurocopa.models.entity.Selecciones;

@Service
public class SeleccionesServiceImpl implements ISeleccionesService{

	@Autowired
	private ISeleccionesDAO seleccionesDao;
	
	
	@Override
	@Transactional(readOnly=true)
	public List<Selecciones> findAll() {
		return (List<Selecciones>)seleccionesDao.findAll();
	}


	@Override
	@Transactional(readOnly=true)
	public Selecciones findById(String id) {
		return seleccionesDao.findById(id).orElse(null);
	}


	@Override
	@Transactional
	public Selecciones save(Selecciones selecciones) {
		return seleccionesDao.save(selecciones);
	}


	@Override
	@Transactional
	public void delete(String id) {
		seleccionesDao.deleteById(id);
		
	}


	
	
}
