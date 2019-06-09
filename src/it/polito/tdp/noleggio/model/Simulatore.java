package it.polito.tdp.noleggio.model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.PriorityQueue;

public class Simulatore {
	
	private PriorityQueue<Evento> queue = new PriorityQueue<>() ;
			
	// stato del mondo auto noleggiate e non (parmatri con cui ti so dire cosa succede nella mia sim)
	private int autoTotali;
	private int autoDisponibili;  // da 0 a auto totali
	
	//parametri di simulazione
	// possono essere anche costanti che mi consentono di gestire il mondo
	//l'orario di inizio quello di fine e l'intervallo
	private LocalTime oraInizio;
	private LocalTime oraFine;
	private Duration intervalloArrivo;
}
