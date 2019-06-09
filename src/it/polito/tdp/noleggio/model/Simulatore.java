package it.polito.tdp.noleggio.model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import it.polito.tdp.noleggio.model.Evento.TipoEvento;

public class Simulatore {

	private PriorityQueue<Evento> queue = new PriorityQueue<>();

	// stato del mondo auto noleggiate e non (parmatri con cui ti so dire cosa
	// succede nella mia sim)
	private int autoTotali;
	private int autoDisponibili; // da 0 a auto totali

	// parametri di simulazione
	// possono essere anche costanti che mi consentono di gestire il mondo
	// l'orario di inizio quello di fine e l'intervallo
	// non i fa il new
	private LocalTime oraInizio = LocalTime.of(8, 0);
	private LocalTime oraFine = LocalTime.of(20, 0);
	private Duration intervalloArrivo = Duration.ofMinutes(10);
	
	
	
	//variablili interne random 
	//classe più flessibile 
	private Random  rand= new Random();

	// quando un clinete prende un auto può stare fuori per alcune ore
	private List<Duration> durateNoleggio; // da inizializzare nel costruttore

	// statistiche raccolte
	private int numeroClienti;
	private int numeroInsoddisfatti;

	// non posso inizializzare una lista fuori dal costruttore quindi
	// costruisco il costruttore e mi inizializzo la lista
	public Simulatore() {

		// lista di durate (ore) per simulare il rientro dell'auto
		durateNoleggio = new ArrayList<Duration>();
		durateNoleggio.add(Duration.ofHours(1));
		durateNoleggio.add(Duration.ofHours(2));
		durateNoleggio.add(Duration.ofHours(3));

	}

	// metodo per inizializzare
	public void init(int autoTotali) {
		// imposta le strutture dati e le variabili (no parametri)
		// ogni volta gli dico le auto totali
		this.autoTotali = autoTotali;
		// immagino che all'inizio tutte le auto siano disponibili
		this.autoDisponibili = autoTotali;
		// resetto le statistiche
		this.numeroClienti = 0;
		this.numeroInsoddisfatti = 0;

		// resetto la coda
		this.queue.clear();

		// cariche gli eventi iniziali
		// ora arrivano uno ogni 10 minuti, parto dall'ora iniziale e poi metto un
		// evento ogni 10 minuti
		// fino alla fine

		// faccio un ciclo for che mi inserice un svento ogni 10 minuti
		for (LocalTime ora = this.oraInizio; ora.isBefore(oraFine); ora = ora.plus(intervalloArrivo)) {
			// ora mi rappresenta l'istante i tempo in cui arriva il cliente
			queue.add(new Evento(ora, TipoEvento.CLIENTE_ARRIVA));
			// li inserisco in ordine ma anche la coda li ordina per tempo

		}

	}

	// metodo per definire la simulazione
	// dopo aver inizializzato le variabili e riempito la coda
	public void run() {
		// finchè la coda è piena faccio qualcosa
		while (!queue.isEmpty()) {
			// ne entraggo uno togliendolo dalla lista
			Evento ev = queue.poll();

			// poi in base al tipo di eveno che ho lo gestisco
			switch (ev.getTipo()) {
			case CLIENTE_ARRIVA:
				this.numeroClienti++;

				// vedo se ho un'auto disponibile e se è cosi gliela do aggiorno i clienti
				// soddisfatti
			if(	this.autoDisponibili>=1) {
				this.autoDisponibili--;
				
				// un intero tra 0 e il numero che inserisco in questo caso la lunghezz della lista 
				int i= rand.nextInt(durateNoleggio.size());
				Duration noleggio= durateNoleggio.get(i);
				queue.add(new Evento(ev.getTempo().plus(noleggio), TipoEvento.AUTO_RESTITUITA));
				//questo evento si inserisce in ordine nella queue e lo estrae al momento giusto rimettendo la macchina disponibile 
				
			}else this.numeroInsoddisfatti++;

				// genero un altro evento(auto_restituita) dopo un tempo (della lista)
				// se non ho auto aggiorno clienti insoddisfati

				break;

			case AUTO_RESTITUITA:
				this.autoDisponibili++;
				break;

			}

		}

	}

	public LocalTime getOraInizio() {
		return oraInizio;
	}

	public void setOraInizio(LocalTime oraInizio) {
		this.oraInizio = oraInizio;
	}

	public LocalTime getOraFine() {
		return oraFine;
	}

	public void setOraFine(LocalTime oraFine) {
		this.oraFine = oraFine;
	}

	public Duration getIntervalloArrivo() {
		return intervalloArrivo;
	}

	public void setIntervalloArrivo(Duration intervalloArrivo) {
		this.intervalloArrivo = intervalloArrivo;
	}

	public List<Duration> getDurateNoleggio() {
		return durateNoleggio;
	}

	public void setDurateNoleggio(List<Duration> durateNoleggio) {
		this.durateNoleggio = durateNoleggio;
	}

	public int getAutoTotali() {
		return autoTotali;
	}

	public int getAutoDisponibili() {
		return autoDisponibili;
	}

	public int getNumeroClienti() {
		return numeroClienti;
	}

	public int getNumeroInsoddisfatti() {
		return numeroInsoddisfatti;
	}
}
