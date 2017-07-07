package gioco;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Partita {

	List<Giocatore> giocatore;
	private String situazioneDiGioco;

	List<Integer> ordineGioco;
	private int turnoCorrente; //0, 1, 2, 3

	private int tutorial; 
	private int difficolta; //da 0 a 3
	private int mappa; //0 predefinita, 1 casuale, 2 estiva, 3 invernale
	
	private ThreadCicloPartita threadCicloPartita;
	private GUIPartita guiPartita;
	private List<GruppoMilitare> gruppiMilitariSchierati;
	
	Partita(String situazioneDiGioco, String nomeGiocatore, int tutorial, int difficolta, int mappa, int civilta, GUIPartita guiPartita)
	{
		this.guiPartita = guiPartita;
		gruppiMilitariSchierati = new ArrayList<GruppoMilitare>();
		
		if(situazioneDiGioco != null) //non � una nuova partita
		{
			this.situazioneDiGioco = situazioneDiGioco;
			this.tutorial = tutorial;
			this.difficolta = difficolta;
			this.mappa = mappa;
		}
		else //E' una nuova partita, bisogna crearla
		{
			this.situazioneDiGioco = "setup " + nomeGiocatore + " " + Integer.toString(tutorial) + " " + Integer.toString(difficolta) + " "
					+ Integer.toString(mappa) + " " + Integer.toString(civilta);
		
			this.tutorial = tutorial;
			this.difficolta = difficolta;
			this.mappa = mappa;
			creaPartita(nomeGiocatore, civilta);
		
		}
		
	}
	
	public void creaPartita(String nomeGiocatore, int civilta)
	{
		giocatore = new ArrayList<Giocatore>();
		
		for(int i = 0; i < 4; i++)
		{
			if(civilta == i) //giocatore umano
				giocatore.add(i, new Giocatore(civilta, "utente1", nomeGiocatore));
			else //cpu
				giocatore.add(i, new Giocatore(i, "cpu", "CPU"));
			
			giocatore.get(i).setOro(2000000);
			giocatore.get(i).setMateriali(2000000000);
			giocatore.get(i).setPuntiRicerca(50000);
		}
		
		//Randomizzo ordine di gioco
		ordineGioco = new ArrayList<Integer>();
		ordineGioco.add(0);
		ordineGioco.add(1);
		ordineGioco.add(2);
		ordineGioco.add(3);
		
		Collections.shuffle(ordineGioco);
		avviaPartita();
	}

	/*
	 * Metodo che scandisce i turni e gestisce il turno con l'ia della cpu (se tocca alla cpu)
	 * altrimenti lascia giocare il giocatore finch� non passa il turno
	 */
	public void avviaPartita()
	{
		threadCicloPartita = new ThreadCicloPartita(this);
		threadCicloPartita.start();
	}
	
	/*
	 * In base a tutti i possedimenti del giocatore corrente, e in base a tutte le cave di materiali, oro e PR calcola
	 * ed eventualmente incrementa le risorse in pi� che spettano al giocatore all'inizio del turno
	 */
	public void calcolaRisorse(boolean incrementa)
	{
		//Variabili di Incremento risorse
		int iOro = 3, iMat = 3, iPr = 3;
		double iEconomia = 1, iMilitare = 1, iRicerca = 1;
		
		/*Utilizzo HashMap per velocizzare la ricerca sui dati*/
		Map<String, Integer> oro = new HashMap<String, Integer>();
		Map<String, Integer> mat = new HashMap<String, Integer>();
		Map<String, Integer> pr = new HashMap<String, Integer>();
		
		Map<String, Double> economia = new HashMap<String, Double>();
		Map<String, Double> militare = new HashMap<String, Double>();
		Map<String, Double> ricerca = new HashMap<String, Double>();
		
		//Et� classica
		oro.put(Global.getLabels("i56"), 2);
		mat.put(Global.getLabels("i64"), 4);
		economia.put(Global.getLabels("i67"), 0.2);
		economia.put(Global.getLabels("i68"), 0.2);
		ricerca.put(Global.getLabels("i24"), 0.5);
		pr.put(Global.getLabels("i24"), 5);
		oro.put(Global.getLabels("i57"), 4);
		ricerca.put(Global.getLabels("i25"), 0.3);
		pr.put(Global.getLabels("i25"), 5);
		oro.put(Global.getLabels("i27"), 6);
		
		//Medioevo
		oro.put(Global.getLabels("i58"), 6);
		ricerca.put(Global.getLabels("i73"), 0.5);
		pr.put(Global.getLabels("i73"), 7);
		militare.put(Global.getLabels("i32"), 0.2);
		oro.put(Global.getLabels("i59"), 8);
		ricerca.put(Global.getLabels("i75"), 0.3);
		pr.put(Global.getLabels("i75"), 7);
		economia.put(Global.getLabels("i69"), 0.3);
		economia.put(Global.getLabels("i70"), 0.3);
		mat.put(Global.getLabels("i65"), 8);
		
		
		//Et� vittoriana
		mat.put(Global.getLabels("i66"), 10);
		ricerca.put(Global.getLabels("i76"), 0.3);
		pr.put(Global.getLabels("i76"), 10);
		oro.put(Global.getLabels("i60"), 10);
		economia.put(Global.getLabels("i71"), 0.5);
		mat.put(Global.getLabels("i42"), 13);
		oro.put(Global.getLabels("i74"), 16);
		economia.put(Global.getLabels("i72"), 0.3);
		oro.put(Global.getLabels("i61"), 12);
		
		for(String s : giocatore.get(turnoCorrente).getStoricoPossedimenti())
		{
			if(oro.containsKey(s))
				iOro += oro.get(s);
			
			if(mat.containsKey(s))
				iMat += mat.get(s);
			
			if(pr.containsKey(s))
				iPr += pr.get(s);
			
			if(economia.containsKey(s))
				iEconomia += economia.get(s);
			
			if(militare.containsKey(s))
				iMilitare += militare.get(s);
			
			if(ricerca.containsKey(s))
				iRicerca += ricerca.get(s);
		}
		
		iOro = (int)((double)iOro * iEconomia);
		iMat = (int)((double)iMat * iEconomia);
		iPr = (int)((double)iPr * iRicerca);
		
		giocatore.get(turnoCorrente).setBonusEconomia(iEconomia);
		giocatore.get(turnoCorrente).setBonusMilitare(iMilitare);
		giocatore.get(turnoCorrente).setBonusRicerca(iRicerca);
		
		giocatore.get(turnoCorrente).setOroXTurno(iOro);
		giocatore.get(turnoCorrente).setMatXTurno(iMat);
		giocatore.get(turnoCorrente).setPrXTurno(iPr);
		
		if(incrementa) //Incrementa le risorse del giocatore
		{
			giocatore.get(turnoCorrente).setOro(giocatore.get(turnoCorrente).getOro() + iOro);
			giocatore.get(turnoCorrente).setMateriali(giocatore.get(turnoCorrente).getMateriali() + iMat);
			giocatore.get(turnoCorrente).setPuntiRicerca(giocatore.get(turnoCorrente).getPuntiRicerca() + iPr);
		}
	}
	
	public void aggiornaDatiGUI()
	{
		guiPartita.aggiornaDatiGUI();
	}

	public List<Giocatore> getGiocatore() {
		return giocatore;
	}

	public void setGiocatore(List<Giocatore> giocatore) {
		this.giocatore = giocatore;
	}

	public int getTurnoCorrente() {
		return turnoCorrente;
	}
	public ThreadCicloPartita getThreadCicloPartita() {
		return threadCicloPartita;
	}

	public void setThreadCicloPartita(ThreadCicloPartita threadCicloPartita) {
		this.threadCicloPartita = threadCicloPartita;
	}

	public List<Integer> getOrdineGioco() {
		return ordineGioco;
	}

	public GUIPartita getGuiPartita() {
		return guiPartita;
	}

	public void setGuiPartita(GUIPartita guiPartita) {
		this.guiPartita = guiPartita;
	}

	public void setOrdineGioco(List<Integer> ordineGioco) {
		this.ordineGioco = ordineGioco;
	}

	public void setTurnoCorrente(int turnoCorrente) {
		this.turnoCorrente = turnoCorrente;
	}
	public String getSituazioneDiGioco() {
		return situazioneDiGioco;
	}
	public void setSituazioneDiGioco(String situazioneDiGioco) {
		this.situazioneDiGioco = situazioneDiGioco;
	}
	
	public List<GruppoMilitare> getGruppiMilitariSchierati() {
		return gruppiMilitariSchierati;
	}

	public void setGruppiMilitariSchierati(List<GruppoMilitare> gruppiMilitariSchierati) {
		this.gruppiMilitariSchierati = gruppiMilitariSchierati;
	}
	
}
