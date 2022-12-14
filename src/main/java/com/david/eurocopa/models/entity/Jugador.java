// default package
// Generated 30 jun. 2021 19:58:00 by Hibernate Tools 5.2.12.Final
package com.david.eurocopa.models.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;



/**
 * Jugador generated by hbm2java
 */
@Entity
@Table(name = "jugador")
public class Jugador implements java.io.Serializable {


	private static final long serialVersionUID = 1L;
	private int id;

	@JsonBackReference
	private Selecciones selecciones;
	private String nombre;
	private Integer edad;
	private String pais;
	private String altura;
	private String peso;
	private String posicion;
	private String club;
	private String valor;
	private String imagen;

	public Jugador() {
	}

	public Jugador(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public Jugador(int id, Selecciones selecciones, String nombre, Integer edad, String pais, String altura,
			String peso, String posicion, String club, String valor, String imagen) {
		this.id = id;
		this.selecciones = selecciones;
		this.nombre = nombre;
		this.edad = edad;
		this.pais = pais;
		this.altura = altura;
		this.peso = peso;
		this.posicion = posicion;
		this.club = club;
		this.valor = valor;
		this.imagen = imagen;
	}

	@Id

	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idselecciones")
	public Selecciones getSelecciones() {
		return this.selecciones;
	}
	
	
	public void setSelecciones(Selecciones selecciones) {
		this.selecciones = selecciones;
	}

	@Column(name = "nombre", nullable = false)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "edad")
	public Integer getEdad() {
		return this.edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	@Column(name = "pais")
	public String getPais() {
		return this.pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	@Column(name = "altura")
	public String getAltura() {
		return this.altura;
	}

	public void setAltura(String altura) {
		this.altura = altura;
	}

	@Column(name = "peso")
	public String getPeso() {
		return this.peso;
	}

	public void setPeso(String peso) {
		this.peso = peso;
	}

	@Column(name = "posicion")
	public String getPosicion() {
		return this.posicion;
	}

	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}

	@Column(name = "club")
	public String getClub() {
		return this.club;
	}

	public void setClub(String club) {
		this.club = club;
	}

	@Column(name = "valor")
	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Column(name = "imagen")
	public String getImagen() {
		return this.imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

}
