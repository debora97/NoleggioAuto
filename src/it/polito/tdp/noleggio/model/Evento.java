package it.polito.tdp.noleggio.model;

import java.time.LocalTime;

public class Evento implements Comparable<Evento> {
	
	public enum TipoEvento {
		CLIENTE_ARRIVA,
		AUTO_RESTITUITA
	}
	
	private LocalTime tempo ;
	private TipoEvento tipo ;
	
	//perch� la coda deve essere ordinata per tempo
	@Override
	public int compareTo(Evento other) {
		return this.tempo.compareTo(other.tempo);
	}

}
