package com.example.demo;

public class Godzilla {
	
	
	
	public Godzilla() {
		super();
	}

	public Godzilla(int id, int edad, String nombre) {
		super();
		this.id = id;
		this.edad = edad;
		this.nombre = nombre;
	}

	private int id;
	
	private int edad;
	
	private String nombre;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	

}
