package gioco;

import java.util.ArrayList;
import java.util.List;

public class Giocatore {
	private String proprietario; //possibili valori: utente1 (server), utente2 (client), cpu. Se si gioca in singolo utente1 o cpu
	public String getProprietario() {
		return proprietario;
	}

	public void setProprietario(String proprietario) {
		this.proprietario = proprietario;
	}
	private String nomeGiocatore;
	private int civilt�; //0 romani, 1 britanni, 2 galli, 3 sassoni
	private int oro;
	private int materiali;
	private int puntiRicerca;
	private int bonusEconomia;
	private int bonusMilitare;
	private int bonusRicerca;
	
	private List<UnitaMilitare> unitaMunicipio; //unit� attualmente nel municipio
	private List<GruppoMilitare> gruppiInAttacco; //gruppi militari attualmente all'esterno del municipio
	
	private List<String> ricercheEffettuate; //contiene tutte le ricerche effettuate dal giocatore
	private List<String> storicoPossedimenti; //contiene tutti i possedimenti (case etc) del giocatore
	
	Giocatore(int civilt�, String proprietario, String nomeGiocatore) {
		unitaMunicipio = new ArrayList<UnitaMilitare>();
		gruppiInAttacco = new ArrayList<GruppoMilitare>();
		ricercheEffettuate = new ArrayList<String>();
		storicoPossedimenti = new ArrayList<String>();
		
		oro = 0;
		materiali = 0;
		puntiRicerca = 0;
		
		bonusEconomia = 1;
		bonusMilitare = 1;
		bonusRicerca = 1;
		
		this.proprietario = proprietario;
		this.nomeGiocatore = nomeGiocatore;
	}
	
	public List<String> getStoricoPossedimenti() {
		return storicoPossedimenti;
	}

	public void setStoricoPossedimenti(List<String> storicoPossedimenti) {
		this.storicoPossedimenti = storicoPossedimenti;
	}

	public int getCivilt�() {
		return civilt�;
	}
	public void setCivilt�(int civilt�) {
		this.civilt� = civilt�;
	}
	public int getPuntiRicerca() {
		return puntiRicerca;
	}
	public String getNomeGiocatore() {
		return nomeGiocatore;
	}
	
	public List<UnitaMilitare> getUnitaMunicipio() {
		return unitaMunicipio;
	}

	public void setUnitaMunicipio(List<UnitaMilitare> unitaMunicipio) {
		this.unitaMunicipio = unitaMunicipio;
	}

	public void setNomeGiocatore(String nomeGiocatore) {
		this.nomeGiocatore = nomeGiocatore;
	}

	public int getOro() {
		return oro;
	}

	public void setOro(int oro) {
		this.oro = oro;
	}

	public int getMateriali() {
		return materiali;
	}

	public void setMateriali(int materiali) {
		this.materiali = materiali;
	}

	public int getBonusEconomia() {
		return bonusEconomia;
	}

	public void setBonusEconomia(int bonusEconomia) {
		this.bonusEconomia = bonusEconomia;
	}

	public int getBonusMilitare() {
		return bonusMilitare;
	}

	public void setBonusMilitare(int bonusMilitare) {
		this.bonusMilitare = bonusMilitare;
	}

	public int getBonusRicerca() {
		return bonusRicerca;
	}

	public void setBonusRicerca(int bonusRicerca) {
		this.bonusRicerca = bonusRicerca;
	}

	public List<GruppoMilitare> getGruppiInAttacco() {
		return gruppiInAttacco;
	}

	public void setGruppiInAttacco(List<GruppoMilitare> gruppiInAttacco) {
		this.gruppiInAttacco = gruppiInAttacco;
	}

	public void setPuntiRicerca(int puntiRicerca) {
		this.puntiRicerca = puntiRicerca;
	}
	public List<String> getRicercheEffettuate() {
		return ricercheEffettuate;
	}
	public void setRicercheEffettuate(List<String> ricercheEffettuate) {
		this.ricercheEffettuate = ricercheEffettuate;
	}
}
