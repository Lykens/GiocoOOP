package gioco;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * GUI principale che rappresenta la partita al giocatore
 * @author Werther e Lorenzo
 *
 */
public class GUIPartita extends JFrame{

	private JPanel contentPane;
	private JPanel panelTop;
	private JPanel panelBottom;
	private JPanel panelCenter;
	private JPanel panelBtnGoRight;
	private JPanel panelMsg;
	private JPanel panelStats;
	private JPanel panelTurno;
	private JPanel panelPulsanti;
	private JLabel lblOro;
	private JLabel lblOrov;
	private JLabel lblMateriali;
	private JLabel lblMaterialiv;
	private JLabel lblPuntiRicerca;
	private JLabel lblPuntiRicercav;
	private JLabel lblTurno;
	private RoundedCornerButton btnGoUp;
	private RoundedCornerButton btnGoRight;
	private RoundedCornerButton btnGoDown;
	private RoundedCornerButton btnGoLeft;
	private JPanel panelBtnGoUp;
	private JPanel panelBtnGoDown;
	private JPanel panelBtnGoLeft;
	private JLabel[][] lblsGiocoFondo;
	private JLabel[][] lblsGioco;
	private String azioneLblsGioco;
	private String elemLblsGioco;
	private int ioldLblsGioco, joldLblsGioco;
	private JPanel panelGioco;
	private Image scalelblPartita;
	private int partitaHeight = 16, partitaWidth = 31;
	private Font fontFuturist;
	private JButton btnApriMsg;
	private JButton btnPassaTurno;
	private JTextField txtMsg;
	private JButton btnCostruisci;
	private JButton btnRicerca;
	private JButton btnInfoPartita;
	private JButton btnImpostazioni;
	private Window[] finestreAttive;
	private GUIPartitaOpzioni frmOpzioni;
	private int posSchermataX = 31, posSchermataY = 16;
	private Clip audio;

	private GUIPartita guiPartita; //utilizzato per avere un riferimento a questa classe nelle chiamate a thread esterni
	private GUIPartitaInformazioni frmInfo;
	private GUIPartitaRicerca frmRicerca;

	private Partita partita;

	static int su = KeyEvent.VK_W;                                        //Codice dei tasti
	static int giu = KeyEvent.VK_S;
	static int dest = KeyEvent.VK_D;
	static int sinist = KeyEvent.VK_A;
	static int frecciasu = KeyEvent.VK_UP;                                       
	static int frecciagiu = KeyEvent.VK_DOWN;
	static int frecciadest = KeyEvent.VK_RIGHT;
	static int frecciasinist = KeyEvent.VK_LEFT;

	private int oldLblsGiocoWidth;  //Variabile per ricordare la vecchia dimensione delle lblsGioco
	private int oldLblsGiocoHeight; //Variabile per ricordare la vecchia dimensione delle lblsGioco

	private ValoriDiGioco valoriDiGioco;
	private GUIPartitaCostruisci frmCostruisci;
	private IconeGrafiche iconeGrafiche;

	private String proprietario; //INDICA IL PROPRIETARIO DELLA GUIPartita, in multiplayer utilizzato per distinguere i due giocatori
	private int indiceProprietario; //INDICA L'INDICE DEL PROPRIETARIO

	private GUIArruolaUnita guiArruolaUnita; //Schermata per arruolare nuove unit�, invocata se clicchiamo su un edificio militare
	private GUIMunicipio guiMunicipio; //Schermata del muncipio
	private GUIGruppoMilitare guiGruppoMilitare; //Schermata del gruppo militare
	
	private GruppoMilitare gruppoMilitare; //Individua il gruppo militare corrente
	
	/**
	 * Crea l'interfaccia grafica della partita e inizializza tutte le variabili per poterle presentare al giocatore
	 * @param nomeGiocatore Nome del giocatore
	 * @param tutorial Tutorial si/no
	 * @param difficolta Difficolt� scelta dal giocatore
	 * @param mappa Mappa scelta dal giocatore
	 * @param civilta Civilt� scelta dal giocatore
	 */
	GUIPartita(String nomeGiocatore, int tutorial, int difficolta, int mappa, int civilta, Partita partita, ValoriDiGioco valoriDiGioco)
	{
		setTitle("Empire Conquerors");

		ImageIcon icona = new ImageIcon("media/Icona.png");                  //Carichiamo l'icona personalizzata
		Image scaledicona = icona.getImage().getScaledInstance(80, 60, Image.SCALE_SMOOTH);
		setIconImage(scaledicona);  

		Toolkit t1 = Toolkit.getDefaultToolkit();                                 //Cursore personalizzato
		Image img = t1.getImage("media/cursore.png");
		Point point = new Point(0,0);
		Cursor cursor = t1.createCustomCursor(img, point, "Cursore Personalizzato");
		setCursor(cursor);  

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //blocco di codice per evitare la chiusura immediata alla chiusura del programma
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				int scelta =0;
				scelta = JOptionPane.showConfirmDialog(
						null, Global.getLabels("s54"),Global.getLabels("a6") ,JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				finestreAttive = Frame.getWindows();
				if(scelta == 0)
				{
					disattivaThread(); //disattiva tutti i thread prima di chiudere la partita
					finestreAttive[0].setVisible(true);
					dispose();
					File musica= new File("media/MusicaMenu.wav");
					Music.playSound();
				}
			}
		});
		setMinimumSize(new Dimension(1280,720));   

		setBounds(0, 0, 1280, 720);
		setLocationRelativeTo(null);

		try {
			//Creo un font custom
			fontFuturist = Font.createFont(Font.TRUETYPE_FONT, new File("media\\font_futurist_fixed.ttf")).deriveFont(12f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			//registro il font
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("media\\font_futurist_fixed.ttf")));
		} catch (IOException e) {
			e.printStackTrace();
		} catch(FontFormatException e) {
			e.printStackTrace();
		}

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		contentPane = new JPanel();
		contentPane.setLayout(new GridLayout2(3, 1, 0, 0));
		contentPane.setBorder(new EmptyBorder(0 , 0 , 0 , 0 ));
		panelTop = new JPanel(new GridLayout(1, 3, 0, 0));
		panelBottom = new JPanel(new GridLayout(1, 3, 0, 0));
		panelCenter = new JPanel(new GridLayout2(1, 3, 0, 0));
		panelBtnGoRight = new JPanel();
		panelBtnGoLeft = new JPanel();
		this.valoriDiGioco = valoriDiGioco;
		this.partita = partita;
		
		iconeGrafiche = new IconeGrafiche();
		
		proprietario = "utente1";  
		indiceProprietario = civilta;
		azioneLblsGioco = "";

		//Posiziono la schermata iniziale a seconda della civilt� scelta dal giocatore
		switch(civilta)
		{
		case 0: //romani
			posSchermataX = 31;
			posSchermataY = 32;
			break;
		case 1: //britanni
			posSchermataX = 31;
			posSchermataY = 0;
			break;
		case 2: //galli
			posSchermataX = 0;
			posSchermataY = 16;
			break;
		case 3: //sassoni
			posSchermataX = 62;
			posSchermataY = 16;
			break;
		}

		lblOro = new JLabel();
		lblOro.setFont(fontFuturist.deriveFont(15f));
		lblOro.setIcon(iconeGrafiche.newiconOro);
		lblOro.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lblOrov = new JLabel("0");
		lblOrov.setFont(fontFuturist.deriveFont(15f));
		lblOrov.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lblMateriali = new JLabel();
		lblMateriali.setFont(fontFuturist.deriveFont(15f));
		lblMateriali.setIcon(iconeGrafiche.newiconMat);
		lblMateriali.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lblMaterialiv = new JLabel("0");
		lblMaterialiv.setFont(fontFuturist.deriveFont(15f));
		lblMaterialiv.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lblPuntiRicerca = new JLabel();
		lblPuntiRicerca.setFont(fontFuturist.deriveFont(15f));
		lblPuntiRicerca.setIcon(iconeGrafiche.newiconCosto);
		lblPuntiRicerca.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lblPuntiRicercav = new JLabel("0");
		lblPuntiRicercav.setFont(fontFuturist.deriveFont(15f));
		lblPuntiRicercav.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		panelStats = new JPanel(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.WEST;
		panelStats.add(new JLabel(" "));
		c.gridx ++;
		panelStats.add(lblOro, c);
		c.gridx ++;
		panelStats.add(lblOrov, c);
		c.gridx ++;
		panelStats.add(new JLabel("   "), c);
		c.gridx ++;
		panelStats.add(lblMateriali, c);
		c.gridx ++;
		panelStats.add(lblMaterialiv, c);
		c.gridx ++;
		panelStats.add(new JLabel("   "), c);
		c.gridx ++;
		panelStats.add(lblPuntiRicerca, c);
		c.gridx ++;
		panelStats.add(lblPuntiRicercav, c);

		panelTop.add(panelStats);

		btnGoUp = new RoundedCornerButton();
		btnGoRight = new RoundedCornerButton();
		btnGoDown = new RoundedCornerButton();
		btnGoLeft = new RoundedCornerButton();
		guiPartita = this;



		//MOVIMENTO SCHERMATA DA TASTIERA CON WASD

		KeyboardFocusManager.getCurrentKeyboardFocusManager() 
		.addKeyEventDispatcher(new KeyEventDispatcher() {

			ThreadScorrimentoSchermata threadScorrimentoSu = null;
			ThreadScorrimentoSchermata threadScorrimentoGiu = null;
			ThreadScorrimentoSchermata threadScorrimentoSinist = null;
			ThreadScorrimentoSchermata threadScorrimentoDest = null;

			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				if(KeyEvent.KEY_PRESSED == e.getID()&&(Global.getTastiSchermata()==2))
				{
					if(e.getKeyCode()==su){
						if(threadScorrimentoSu == null)
						{
							threadScorrimentoSu = new ThreadScorrimentoSchermata(guiPartita, 1);
							threadScorrimentoSu.start();
						}
					}
					if(e.getKeyCode()==giu){
						if(threadScorrimentoGiu == null)
						{
							threadScorrimentoGiu = new ThreadScorrimentoSchermata(guiPartita, 3);
							threadScorrimentoGiu.start();
						}
					}
					if(e.getKeyCode()==sinist){
						if(threadScorrimentoSinist == null)
						{
							threadScorrimentoSinist = new ThreadScorrimentoSchermata(guiPartita, 4);
							threadScorrimentoSinist.start();
						}
					}
					if(e.getKeyCode()==dest){
						if(threadScorrimentoDest == null)
						{
							threadScorrimentoDest = new ThreadScorrimentoSchermata(guiPartita, 2);
							threadScorrimentoDest.start();
						}
					}
				}
				if(KeyEvent.KEY_RELEASED == e.getID()&&(Global.getTastiSchermata()==2))
				{
					if(e.getKeyCode()==su) {
						if(threadScorrimentoSu != null)
						{
							threadScorrimentoSu.setScorri(false);
							threadScorrimentoSu = null;
						}
					}
					if(e.getKeyCode()==giu) {
						if(threadScorrimentoGiu != null)
						{
							threadScorrimentoGiu.setScorri(false);
							threadScorrimentoGiu = null;
						}
					}
					if(e.getKeyCode()==sinist) {
						if(threadScorrimentoSinist != null)
						{
							threadScorrimentoSinist.setScorri(false);
							threadScorrimentoSinist = null;
						}
					}
					if(e.getKeyCode()==dest) {
						if(threadScorrimentoDest != null)
						{
							threadScorrimentoDest.setScorri(false);
							threadScorrimentoDest = null;
						}
					}
				}
				return false; //Rilascia il key dispatcher
			}
		});
		//FINE MOVIMENTO SCHERMATA CON WASD

		//MOVIMENTO SCHERMATA DA TASTIERA CON FRECCE DIREZIONALI
		KeyboardFocusManager.getCurrentKeyboardFocusManager() 
		.addKeyEventDispatcher(new KeyEventDispatcher() {

			ThreadScorrimentoSchermata threadScorrimentoSu = null;
			ThreadScorrimentoSchermata threadScorrimentoGiu = null;
			ThreadScorrimentoSchermata threadScorrimentoSinist = null;
			ThreadScorrimentoSchermata threadScorrimentoDest = null;

			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				if(KeyEvent.KEY_PRESSED == e.getID()&&(Global.getTastiSchermata()==1))
				{
					if(e.getKeyCode()==frecciasu){
						if(threadScorrimentoSu == null)
						{
							threadScorrimentoSu = new ThreadScorrimentoSchermata(guiPartita, 1);
							threadScorrimentoSu.start();
						}
					}
					if(e.getKeyCode()==frecciagiu){
						if(threadScorrimentoGiu == null)
						{
							threadScorrimentoGiu = new ThreadScorrimentoSchermata(guiPartita, 3);
							threadScorrimentoGiu.start();
						}
					}
					if(e.getKeyCode()==frecciasinist){
						if(threadScorrimentoSinist == null)
						{
							threadScorrimentoSinist = new ThreadScorrimentoSchermata(guiPartita, 4);
							threadScorrimentoSinist.start();
						}
					}
					if(e.getKeyCode()==frecciadest){
						if(threadScorrimentoDest == null)
						{
							threadScorrimentoDest = new ThreadScorrimentoSchermata(guiPartita, 2);
							threadScorrimentoDest.start();
						}
					}
				}
				if(KeyEvent.KEY_RELEASED == e.getID()&&(Global.getTastiSchermata()==1))
				{
					if(e.getKeyCode()==frecciasu) {
						if(threadScorrimentoSu != null)
						{
							threadScorrimentoSu.setScorri(false);
							threadScorrimentoSu = null;
						}
					}
					if(e.getKeyCode()==frecciagiu) {
						if(threadScorrimentoGiu != null)
						{
							threadScorrimentoGiu.setScorri(false);
							threadScorrimentoGiu = null;
						}
					}
					if(e.getKeyCode()==frecciasinist) {
						if(threadScorrimentoSinist != null)
						{
							threadScorrimentoSinist.setScorri(false);
							threadScorrimentoSinist = null;
						}
					}
					if(e.getKeyCode()==frecciadest) {
						if(threadScorrimentoDest != null)
						{
							threadScorrimentoDest.setScorri(false);
							threadScorrimentoDest = null;
						}
					}
				}
				return false; //Rilascia il key dispatcher
			}
		});
		//FINE MOVIMENTO SCHERMATA CON FRECCE DIREZIONALI

		ImageIcon iconbtnGoUp = new ImageIcon("media/btnGoUp.png");
		Image scalebtnGoUp = iconbtnGoUp.getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT);
		ImageIcon newiconbtnGoUp = new ImageIcon(scalebtnGoUp);

		btnGoUp.setIcon(newiconbtnGoUp);

		btnGoUp.addMouseListener(new MouseAdapter() {
			ThreadScorrimentoSchermata threadScorrimento;
			@Override
			public void mousePressed(MouseEvent arg0) {
				if(Global.getTastiSchermata()==0){
					threadScorrimento = new ThreadScorrimentoSchermata(guiPartita, 1);
					threadScorrimento.start();
				}
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if(Global.getTastiSchermata()==0)
					threadScorrimento.setScorri(false);
			}
		});
		panelBtnGoUp = new JPanel();
		panelBtnGoUp.add(btnGoUp);

		panelTop.add(panelBtnGoUp);

		panelTurno = new JPanel(new GridLayout2(1, 1, 0, 0));
		lblTurno = new JLabel();
		ImageIcon iconlblTurno = new ImageIcon("media/mappa.png");
		Image scalelblTurno = iconlblTurno.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		ImageIcon newiconlblTurno = new ImageIcon(scalelblTurno);
		lblTurno.setIcon(newiconlblTurno);
		lblTurno.setHorizontalAlignment(SwingConstants.RIGHT);
		panelTop.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panelTop.add(lblTurno);

		btnGoRight = new RoundedCornerButton();
		ImageIcon iconbtnGoRight = new ImageIcon("media/btnGoRight.png");
		Image scalebtnGoRight = iconbtnGoRight.getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT);
		ImageIcon newiconbtnGoRight = new ImageIcon(scalebtnGoRight);

		btnGoRight.setIcon(newiconbtnGoRight);
		btnGoRight.addMouseListener(new MouseAdapter() {
			ThreadScorrimentoSchermata threadScorrimento;
			@Override
			public void mousePressed(MouseEvent arg0) {
				if(Global.getTastiSchermata()==0){
					threadScorrimento = new ThreadScorrimentoSchermata(guiPartita, 2);
					threadScorrimento.start();
				}
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if(Global.getTastiSchermata()==0)
					threadScorrimento.setScorri(false);
			}
		});

		panelBtnGoRight.setLayout(new BoxLayout(panelBtnGoRight, BoxLayout.PAGE_AXIS));
		panelBtnGoRight.add(Box.createVerticalGlue());
		panelBtnGoRight.add(btnGoRight);
		panelBtnGoRight.add(Box.createVerticalGlue());

		btnGoLeft = new RoundedCornerButton();
		ImageIcon iconbtnGoLeft = new ImageIcon("media/btnGoLeft.png");
		Image scalebtnGoLeft = iconbtnGoLeft.getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT);
		ImageIcon newiconbtnGoLeft = new ImageIcon(scalebtnGoLeft);

		btnGoLeft.setIcon(newiconbtnGoLeft);
		btnGoLeft.addMouseListener(new MouseAdapter() {
			ThreadScorrimentoSchermata threadScorrimento;
			@Override
			public void mousePressed(MouseEvent arg0) {
				if(Global.getTastiSchermata()==0){
					threadScorrimento = new ThreadScorrimentoSchermata(guiPartita, 4);
					threadScorrimento.start();
				}
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if(Global.getTastiSchermata()==0)
					threadScorrimento.setScorri(false);
			}
		});

		panelBtnGoLeft.setLayout(new BoxLayout(panelBtnGoLeft, BoxLayout.PAGE_AXIS));
		panelBtnGoLeft.add(Box.createVerticalGlue());
		panelBtnGoLeft.add(btnGoLeft);
		panelBtnGoLeft.add(Box.createVerticalGlue());

		panelCenter.add(panelBtnGoLeft);
		lblsGioco = new JLabel[partitaHeight][partitaWidth];
		panelGioco = new JPanel(new GridLayout(16, 31, 0, 0));

		for(int i = 0; i < partitaHeight; i++)
		{
			for(int j = 0; j < partitaWidth; j++)
			{
				lblsGioco[i][j] = new JLabel();
				lblsGioco[i][j].addMouseListener(new MouseAdapter() {

					@Override
					public void mouseReleased(MouseEvent arg0) {
						for(int i = 0; i < partitaHeight; i++)
						{
							for(int j = 0; j < partitaWidth; j++)
							{
								if(lblsGioco[i][j] == arg0.getSource())
									gestoreClickLblGioco(j, i);
							}
						}
					}
				});
				panelGioco.add(lblsGioco[i][j]);
			}	
		}

		aggiornaSchermata(); //prende la situazione attuale da Scenario e parsando la matrice di stringhe crea il corrispondente grafico


		oldLblsGiocoWidth = 0;
		oldLblsGiocoHeight = 0;

		panelGioco.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e)
			{
				if(lblsGioco[0][0].getSize().width != oldLblsGiocoWidth || lblsGioco[0][0].getSize().height != oldLblsGiocoHeight)
				{
					oldLblsGiocoWidth = lblsGioco[0][0].getSize().width;
					oldLblsGiocoHeight = lblsGioco[0][0].getSize().height;
					iconeGrafiche.caricaIconeScenario(lblsGioco[0][0].getSize().width, lblsGioco[0][0].getSize().height+1);
					aggiornaSchermata();
				}
			}
		});

		panelCenter.add(panelGioco);

		panelCenter.add(panelBtnGoRight);

		panelMsg = new JPanel(new GridBagLayout());
		btnApriMsg = new JButton(">");
		txtMsg = new JTextField(20);
		txtMsg.setEditable(false);
		GridBagConstraints d = new GridBagConstraints();
		d.gridx = 0;
		d.gridy = 0;
		panelMsg.add(btnApriMsg, d);
		d.gridx ++;
		panelMsg.add(txtMsg, d);
		d.gridx ++;
		btnPassaTurno = new JButton(Global.getLabels("s55"));
		btnPassaTurno.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				try {
					audio = AudioSystem.getClip();
					audio.open(AudioSystem.getAudioInputStream(new File("media/suonoiniziopartita.wav")));
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				}
				FloatControl gainControl = (FloatControl) audio.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(Global.getLivVolume()); 
				audio.start();
				if(!partita.getGiocatore().get(partita.getTurnoCorrente()).getProprietario().equals("cpu")){
					partita.getThreadCicloPartita().setTurnoPersona(false);
				}
			}
		});
		panelMsg.add(btnPassaTurno, d);

		panelBottom.add(panelMsg);

		btnGoDown = new RoundedCornerButton();
		ImageIcon iconbtnGoDown = new ImageIcon("media/btnGoDown.png");
		Image scalebtnGoDown = iconbtnGoDown.getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT);
		ImageIcon newiconbtnGoDown = new ImageIcon(scalebtnGoDown);

		btnGoDown.setIcon(newiconbtnGoDown);
		btnGoDown.addMouseListener(new MouseAdapter() {
			ThreadScorrimentoSchermata threadScorrimento;
			@Override
			public void mousePressed(MouseEvent arg0) {
				if(Global.getTastiSchermata()==0){
					threadScorrimento = new ThreadScorrimentoSchermata(guiPartita, 3);
					threadScorrimento.start();
				}
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if(Global.getTastiSchermata()==0)
					threadScorrimento.setScorri(false);
			}
		});

		panelBtnGoDown = new JPanel();
		panelBtnGoDown.add(btnGoDown);

		panelBottom.add(panelBtnGoDown);

		panelPulsanti = new JPanel(new GridBagLayout());
		btnCostruisci = new JButton();
		ImageIcon iconbtnCostruisci = new ImageIcon("media/btnCostruisci.png");
		Image scalebtnCostruisci = iconbtnCostruisci.getImage().getScaledInstance(30, 33, Image.SCALE_DEFAULT);
		ImageIcon newiconbtnCostruisci = new ImageIcon(scalebtnCostruisci);

		btnCostruisci.setIcon(newiconbtnCostruisci);
		btnCostruisci.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				try {
					audio = AudioSystem.getClip();
					audio.open(AudioSystem.getAudioInputStream(new File("media/bottonepremuto.wav")));
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				}
				FloatControl gainControl = (FloatControl) audio.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(Global.getLivVolume()); 
				audio.start();
				frmCostruisci = new GUIPartitaCostruisci(partita, valoriDiGioco, guiPartita);
				frmCostruisci.setVisible(true);
			}
		});
		btnCostruisci.setSize(30, 33);

		btnRicerca = new JButton();
		ImageIcon iconbtnRicerca = new ImageIcon("media/btnRicerca.png");
		Image scalebtnRicerca = iconbtnRicerca.getImage().getScaledInstance(30, 33, Image.SCALE_DEFAULT);
		ImageIcon newiconbtnRicerca = new ImageIcon(scalebtnRicerca);

		btnRicerca.setIcon(newiconbtnRicerca);

		btnRicerca.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				try {
					audio = AudioSystem.getClip();
					audio.open(AudioSystem.getAudioInputStream(new File("media/bottonepremuto.wav")));
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				}
				FloatControl gainControl = (FloatControl) audio.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(Global.getLivVolume()); 
				audio.start();
				frmRicerca = new GUIPartitaRicerca(partita, guiPartita, valoriDiGioco, proprietario);
				frmRicerca.setVisible(true);
			}
		});

		btnInfoPartita = new JButton();
		ImageIcon iconbtnInfoPartita = new ImageIcon("media/btnInfoPartita.png");
		Image scalebtnInfoPartita = iconbtnInfoPartita.getImage().getScaledInstance(30, 33, Image.SCALE_DEFAULT);
		ImageIcon newiconbtnInfoPartita = new ImageIcon(scalebtnInfoPartita);

		btnInfoPartita.setIcon(newiconbtnInfoPartita);
		btnInfoPartita.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				try {
					audio = AudioSystem.getClip();
					audio.open(AudioSystem.getAudioInputStream(new File("media/bottonepremuto.wav")));
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				}
				FloatControl gainControl = (FloatControl) audio.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(Global.getLivVolume()); 
				audio.start();
				frmInfo = new GUIPartitaInformazioni(partita);
				frmInfo.setVisible(true);
			}
		});

		btnImpostazioni = new JButton();
		ImageIcon iconbtnImpostazioni = new ImageIcon("media/btnImpostazioni.png");
		Image scalebtnImpostazioni = iconbtnImpostazioni.getImage().getScaledInstance(30, 33, Image.SCALE_DEFAULT);
		ImageIcon newiconbtnImpostazioni = new ImageIcon(scalebtnImpostazioni);

		btnImpostazioni.setIcon(newiconbtnImpostazioni);
		btnImpostazioni.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				try {
					audio = AudioSystem.getClip();
					audio.open(AudioSystem.getAudioInputStream(new File("media/bottonepremuto.wav")));
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				}
				FloatControl gainControl = (FloatControl) audio.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(Global.getLivVolume()); 
				audio.start();
				frmOpzioni = new GUIPartitaOpzioni(guiPartita);
				frmOpzioni.setVisible(true);
			}
		});

		GridBagConstraints f = new GridBagConstraints();
		f.gridx = 0;
		f.gridy = 0;
		panelPulsanti.add(btnCostruisci, f);
		f.gridx ++;
		panelPulsanti.add(btnRicerca, f);
		f.gridx ++;
		panelPulsanti.add(btnInfoPartita, f);
		f.gridx ++;
		panelPulsanti.add(btnImpostazioni, f);

		panelBottom.add(panelPulsanti);

		contentPane.add(panelTop);
		contentPane.add(panelCenter);
		contentPane.add(panelBottom);
		this.add(contentPane);

		pack();
	}

	/**
	 * In base alla stringa situazione di gioco presa da una vecchia partita salvata
	 * ricrea la partita
	 * @param stringa Stringa situazione di gioco
	 */
	public GUIPartita(String stringa) {
		
	}

	/**
	 * Aggiorna la schermata in base alla posizione di visualizzazione corrente
	 */
	public void aggiornaSchermata() {
		StringTokenizer st;
		List<ImageIcon> daAggiungere = new ArrayList<ImageIcon>();
		CompoundIcon cmpIcon;

		panelGioco.setVisible(false);
		for(int y = posSchermataY; y < posSchermataY + 16; y++)
		{
			for(int x = posSchermataX; x < posSchermataX + 31; x++)
			{
				st = new StringTokenizer(partita.getScenario().getScenario()[y][x]);
				daAggiungere.clear();
				while(st.hasMoreTokens()) {
					String str = st.nextToken();
					daAggiungere.add(iconeGrafiche.getIconeScenario().get(str));
				}
				
				cmpIcon = new CompoundIcon(CompoundIcon.Axis.Z_AXIS, daAggiungere.toArray(new ImageIcon[daAggiungere.size()]));

				lblsGioco[y-posSchermataY][x-posSchermataX].setIcon(cmpIcon);
			}
		}
		panelGioco.setVisible(true);
	}

	public int getPosSchermataX() {
		return posSchermataX;
	}

	public void setPosSchermataX(int posSchermataX) {
		this.posSchermataX = posSchermataX;
	}

	public int getPosSchermataY() {
		return posSchermataY;
	}

	public int getIndiceProprietario() {
		return indiceProprietario;
	}

	public void setIndiceProprietario(int indiceProprietario) {
		this.indiceProprietario = indiceProprietario;
	}

	public void setPosSchermataY(int posSchermataY) {
		this.posSchermataY = posSchermataY;
	}

	/**
	 * Aggiorna i dati che compongono la GUI
	 */
	public void aggiornaDatiGUI()
	{
		lblOrov.setText(Integer.toString(partita.getGiocatore().get(indiceProprietario).getOro()));
		lblMaterialiv.setText(Integer.toString(partita.getGiocatore().get(indiceProprietario).getMateriali()));
		lblPuntiRicercav.setText(Integer.toString(partita.getGiocatore().get(indiceProprietario).getPuntiRicerca()));
		switch(partita.getTurnoCorrente())
		{
		case 0:
			lblTurno.setIcon(iconeGrafiche.newiconRomani);
			break;
		case 1:
			lblTurno.setIcon(iconeGrafiche.newiconInglesi);
			break;
		case 2:
			lblTurno.setIcon(iconeGrafiche.newiconFrancesi);
			break;
		case 3:
			lblTurno.setIcon(iconeGrafiche.newiconSassoni);
			break;
		}
	}

	/**
	 * Disattiva tutti i thread in esecuzione (prima di chiudere solitamente). Metodo stop antico e deprecated ma efficace
	 */
	public void disattivaThread()
	{
		partita.getThreadCicloPartita().stop();
	}

	/**
	 * Metodo che permette di comprare un elemento dal negozio
	 * @param elemento Elemento da posizionare e comprare
	 */
	public void posizionaECompra(String elemento)
	{
		azioneLblsGioco = Global.getLabels("s56"); //Indica che la prossima azione di click su una lblGioco � per comprare
		elemLblsGioco = elemento; //Indica l'elemento da comprare
	}

	/**
	 * Metodo che permette di vendere una costruzione
	 */
	public void vendiCostruzione()
	{
		azioneLblsGioco = Global.getLabels("s57"); //Indica che la prossima azione di click � per vendere
	}


	/**
	 * Metodo che permette di muovere una costruzione
	 */
	public void muoviCostruzione()
	{
		azioneLblsGioco = Global.getLabels("s58");
	}
	
	/**
	 * Metodo che permette di piazzare un esercito sul campo di battaglia
	 * @param gruppoMilitare gruppo militare da piazzarre
	 */
	public void piazzaEsercito(GruppoMilitare gruppoMilitare)
	{
		this.gruppoMilitare = gruppoMilitare;
		azioneLblsGioco = Global.getLabels("s59");
		elemLblsGioco = Global.getLabels("s60");
	}
	
	/**
	 * Metodo che permette di richiamare un esercito all'interno del municipio
	 */
	public void richiamaEsercito()
	{
		azioneLblsGioco = Global.getLabels("s61");
	}
	
	/**
	 * Metodo che permette di attaccare con un gruppo militare
	 * @param gruppoMilitare gruppo militare in attacco
	 */
	public void gruppoMilitareAttacca(GruppoMilitare gruppoMilitare)
	{
		azioneLblsGioco = Global.getLabels("s62");
		elemLblsGioco = Global.getLabels("s60");
		this.gruppoMilitare = gruppoMilitare;
	}
	
	/**
	 * Metodo che permette di muovere un gruppo militare
	 * @param gruppoMilitare gruppo militare da muovere
	 */
	public void gruppoMilitareMuovi(GruppoMilitare gruppoMilitare)
	{
		azioneLblsGioco = Global.getLabels("s63");
		elemLblsGioco = Global.getLabels("s60");
		this.gruppoMilitare = gruppoMilitare;
	}

	/**
	 * Gestisce tutti i click che avvengono sulle labels lblsGioco
	 * @param i X corrente (senza tenere in conto della pos schermata)
	 * @param j Y corrente (senza tenere in conto della pos schermata)
	 */
	public void gestoreClickLblGioco(int i, int j) //i � la x, j � la y
	{
		if(azioneLblsGioco.equals(Global.getLabels("s56")))
		{
			if(partita.isPiazzamentoPossibile(elemLblsGioco, i+posSchermataX, j+posSchermataY)) //compra e piazza
			{
				
				try {
					audio = AudioSystem.getClip();
					audio.open(AudioSystem.getAudioInputStream(new File("media/costruisci.wav")));
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				}
				FloatControl gainControl = (FloatControl) audio.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(Global.getLivVolume()); 
				audio.start();
				
				partita.compraEPiazza(elemLblsGioco, i+posSchermataX, j+posSchermataY);
			}
			else //il posto dove vuole piazzarlo � gi� occupato
			{
				JOptionPane.showMessageDialog(null, Global.getLabels("e9")+ elemLblsGioco +" "+Global.getLabels("e10"),Global.getLabels("e7"), JOptionPane.DEFAULT_OPTION);
			}

			//Resetto i dati di azione e elemento in memoria
			azioneLblsGioco = "";
			elemLblsGioco = "";

			aggiornaSchermata();
			aggiornaDatiGUI();
		}
		else
		if(azioneLblsGioco.equals(Global.getLabels("s57")))
		{
			//Controllo che sulla lbl ci sia un elemento vendibile
			String nome = partita.individuaOggetto(i+posSchermataX, j+posSchermataY, true); //ritorna l'oggetto contenuto nella lbl che pu� essere venduto

			if(nome == null || nome.contains(Global.getLabels("i49")))
			{
				JOptionPane.showMessageDialog(null, Global.getLabels("s64"),Global.getLabels("e7"), JOptionPane.DEFAULT_OPTION);
			}
			else //chiedi se vuole vendere
			{
				int scelta =0;
				scelta = JOptionPane.showConfirmDialog(
						null,Global.getLabels("s65") , Global.getLabels("a6"),JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(scelta == 0) //se si
				{
					try {
						audio = AudioSystem.getClip();
						audio.open(AudioSystem.getAudioInputStream(new File("media/vendi.wav")));
					} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
						e1.printStackTrace();
					}
					FloatControl gainControl = (FloatControl) audio.getControl(FloatControl.Type.MASTER_GAIN);
					gainControl.setValue(Global.getLivVolume()); 
					audio.start();
					
					partita.vendiERimuovi(nome, i+posSchermataX, j+posSchermataY);
				}
			}

			//Resetto i dati di azione e elemento in memoria
			azioneLblsGioco = "";
			elemLblsGioco = "";

			aggiornaSchermata();
			aggiornaDatiGUI();
		}
		else
		if(azioneLblsGioco.equals(Global.getLabels("s58")))
		{
			String nome = partita.individuaOggetto(i+posSchermataX, j+posSchermataY, true);

			if(nome == null)
			{
				JOptionPane.showMessageDialog(null,Global.getLabels("e11"),Global.getLabels("e7"), JOptionPane.DEFAULT_OPTION);
			}
			else
			{
				azioneLblsGioco = Global.getLabels("s66");
				elemLblsGioco = nome;
				ioldLblsGioco = i+posSchermataX;
				joldLblsGioco = j+posSchermataY;
			}
		}
		else
		if(azioneLblsGioco.equals(Global.getLabels("s66")))
		{
			if(!partita.isPiazzamentoPossibile(elemLblsGioco, i+posSchermataX, j+posSchermataY))
			{
				JOptionPane.showMessageDialog(null,Global.getLabels("e12"),Global.getLabels("e7"), JOptionPane.DEFAULT_OPTION);
			}
			else //piazzamento possibile
			{
				partita.posizionaElemento(elemLblsGioco, i+posSchermataX, j+posSchermataY, ioldLblsGioco, joldLblsGioco);

				//Resetto i dati di azione e elemento in memoria
				azioneLblsGioco = "";
				elemLblsGioco = "";

				aggiornaSchermata();
				aggiornaDatiGUI();
			}
		}
		else
		if(azioneLblsGioco.equals(Global.getLabels("s62")))
		{
			int civiltaDaAttaccare;
			
			//2 casi possibili: o attacca un municipio oppure un gruppo mil. nemico
			civiltaDaAttaccare = partita.esercitoPresente(i+posSchermataX, j+posSchermataY);
			
			if(civiltaDaAttaccare != -1) //presente un esercito
			{
				if(civiltaDaAttaccare != partita.getGiocatore().get(indiceProprietario).getCivilt�()) //se esercito � nemico
				{
					//controllo che i due gruppi militari siano adiacenti
					int distanza = 0, tmp;
					tmp = gruppoMilitare.getPosX() - (i+posSchermataX);
					if(tmp < 0)
						tmp *= -1;
					distanza += tmp;
					tmp = gruppoMilitare.getPosY() - (j+posSchermataY);
					if(tmp < 0)
						tmp *= -1;
					distanza += tmp;
					
					if(distanza == 1) //attacca
					{
						int esito = partita.esercitoAttaccaEsercito(gruppoMilitare, i+posSchermataX, j+posSchermataY);
						if(esito == 1){
							try {
								audio = AudioSystem.getClip();
								audio.open(AudioSystem.getAudioInputStream(new File("media/suonovittoria.wav")));
							} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
								e1.printStackTrace();
							}
							FloatControl gainControl = (FloatControl) audio.getControl(FloatControl.Type.MASTER_GAIN);
							gainControl.setValue(Global.getLivVolume()); 
							audio.start();
							JOptionPane.showMessageDialog(null,Global.getLabels("s122"),Global.getLabels("e7"), JOptionPane.DEFAULT_OPTION);
						}
						else
						if(esito == 0){
							JOptionPane.showMessageDialog(null,Global.getLabels("s123"),Global.getLabels("e7"), JOptionPane.DEFAULT_OPTION);}
						try {
							audio = AudioSystem.getClip();
							audio.open(AudioSystem.getAudioInputStream(new File("media/morte.wav")));
						} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
							e1.printStackTrace();
						}
						FloatControl gainControl = (FloatControl) audio.getControl(FloatControl.Type.MASTER_GAIN);
						gainControl.setValue(Global.getLivVolume()); 
						audio.start();
					}
					else
						JOptionPane.showMessageDialog(null, Global.getLabels("s124"),Global.getLabels("e7"), JOptionPane.DEFAULT_OPTION);
				}
				else
					JOptionPane.showMessageDialog(null, Global.getLabels("s125"),Global.getLabels("e7"), JOptionPane.DEFAULT_OPTION);
			}
			else
			{
				//controllo se municipio nemico
				int municipioPresente = partita.municipioPresente(i+posSchermataX, j+posSchermataY);
				if(municipioPresente != -1)
				{
					if(municipioPresente != partita.getGiocatore().get(indiceProprietario).getCivilt�())
					{
						if(partita.isVicinoACitta(gruppoMilitare.getPosX(), gruppoMilitare.getPosY()) == municipioPresente) //attacco municipio permesso
						{
							int esito = partita.esercitoAttaccaMunicipio(gruppoMilitare, municipioPresente);
							if(esito == -1)
								JOptionPane.showMessageDialog(null, Global.getLabels("s126"),Global.getLabels("e7"), JOptionPane.DEFAULT_OPTION);
							else
							if(esito == 1){
								try {
									audio = AudioSystem.getClip();
									audio.open(AudioSystem.getAudioInputStream(new File("media/suonovittoria.wav")));
								} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
									e1.printStackTrace();
								}
								FloatControl gainControl = (FloatControl) audio.getControl(FloatControl.Type.MASTER_GAIN);
								gainControl.setValue(Global.getLivVolume()); 
								audio.start();
								JOptionPane.showMessageDialog(null,Global.getLabels("s127"),Global.getLabels("e7"), JOptionPane.DEFAULT_OPTION);
							}
							else
							if(esito == 0){
								JOptionPane.showMessageDialog(null, Global.getLabels("s123"),Global.getLabels("e7"), JOptionPane.DEFAULT_OPTION);
								try {
									audio = AudioSystem.getClip();
									audio.open(AudioSystem.getAudioInputStream(new File("media/morte.wav")));
								} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
									e1.printStackTrace();
								}
								FloatControl gainControl = (FloatControl) audio.getControl(FloatControl.Type.MASTER_GAIN);
								gainControl.setValue(Global.getLivVolume()); 
								audio.start();
							}
						}
					}
					else
						JOptionPane.showMessageDialog(null, Global.getLabels("s128"),Global.getLabels("e7"), JOptionPane.DEFAULT_OPTION);
				}
				else
					JOptionPane.showMessageDialog(null,Global.getLabels("s129"),Global.getLabels("e7"), JOptionPane.DEFAULT_OPTION);
			}
			
			//Resetto i dati di azione e elemento in memoria
			azioneLblsGioco = "";
			elemLblsGioco = "";

			aggiornaSchermata();
			aggiornaDatiGUI();
		}
		else
		if(azioneLblsGioco.equals(Global.getLabels("s63"))) //gruppo militare muovi
		{
			if(partita.isPiazzamentoPossibile(elemLblsGioco, i+posSchermataX, j+posSchermataY)) //controlla che la casella sia vuota (solo la base)
			{
				if(!partita.getScenario().getScenario()[j+posSchermataY][i+posSchermataX].substring(0, 1).equals("g")) //se la base di questa casella NON � ghiaia
				{
					int unitaPiuLenta = 100;
					int tmp, distanza = 0;
					
					//Calcolo la velocit� a cui pu� muoversi il gruppo militare
					for(String unita: gruppoMilitare.getGruppoMilitare())
					{
						if(valoriDiGioco.getVelUnita().get(unita) < unitaPiuLenta)
							unitaPiuLenta = valoriDiGioco.getVelUnita().get(unita);
					}
					
					tmp = gruppoMilitare.getPosX() - (i+posSchermataX);
					if(tmp < 0)
						tmp *= -1;
					distanza += tmp;
					
					tmp = gruppoMilitare.getPosY() - (j+posSchermataY);
					if(tmp < 0)
						tmp *= -1;
					distanza += tmp;
					
					if(distanza <= unitaPiuLenta) 
					{
						if(gruppoMilitare.isMovimentoPossibile())//si puo muovere
						{
							try {
								audio = AudioSystem.getClip();
								audio.open(AudioSystem.getAudioInputStream(new File("media/marcia.wav")));
							} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
								e1.printStackTrace();
							}
							FloatControl gainControl = (FloatControl) audio.getControl(FloatControl.Type.MASTER_GAIN);
							gainControl.setValue(Global.getLivVolume()); 
							audio.start();
							
							partita.gruppoMilitareMuovi(gruppoMilitare, i+posSchermataX, j+posSchermataY);
						}
						else
							JOptionPane.showMessageDialog(null, Global.getLabels("s68"),Global.getLabels("e7"), JOptionPane.DEFAULT_OPTION);
					}
					else
						JOptionPane.showMessageDialog(null, Global.getLabels("s69") + Integer.toString(unitaPiuLenta),Global.getLabels("e7"), JOptionPane.DEFAULT_OPTION);
				}
				else
					JOptionPane.showMessageDialog(null, Global.getLabels("s70"),Global.getLabels("e7"), JOptionPane.DEFAULT_OPTION);
			}
			else
				JOptionPane.showMessageDialog(null, Global.getLabels("s71"),Global.getLabels("e7"), JOptionPane.DEFAULT_OPTION);
			
			//Resetto i dati di azione e elemento in memoria
			azioneLblsGioco = "";
			elemLblsGioco = "";

			aggiornaSchermata();
			aggiornaDatiGUI();
		}
		else
		if(azioneLblsGioco.equals("")) //Se � un edificio militare apri schermata arruola truppe
		{
			String edificio = partita.individuaOggetto(i+posSchermataX, j+posSchermataY, false);

			if(edificio != null)
			{
				char ultimoChar = edificio.charAt(edificio.length() - 1);
				if(ultimoChar == '1' || ultimoChar == '2' || ultimoChar == '3' || ultimoChar == '4') {
					edificio = edificio.substring(0, edificio.length() - 1);
				}
				
				if(edificio.equals(Global.getLabels("i0")) || edificio.equals(Global.getLabels("i24")) || edificio.equals(Global.getLabels("i27")) || 
						edificio.equals(Global.getLabels("i32")) || edificio.equals(Global.getLabels("i35")) || edificio.equals(Global.getLabels("i41")) || 
						edificio.equals(Global.getLabels("i39")) || edificio.equals(Global.getLabels("i42")))
				{
					guiArruolaUnita = new GUIArruolaUnita(partita, this, valoriDiGioco, iconeGrafiche, edificio);
					guiArruolaUnita.setVisible(true);
				}
				else
				if(edificio.equals(Global.getLabels("i50")) || edificio.equals(Global.getLabels("i51")) || edificio.equals(Global.getLabels("i52")))
				{
					guiMunicipio = new GUIMunicipio(partita, guiPartita, valoriDiGioco, iconeGrafiche);
					guiMunicipio.setVisible(true);
				}
				else
				if(edificio.contains(Global.getLabels("s72")))
				{
					GruppoMilitare gruppoMilitareCercato = null;
					
					//individuo il gruppo militare associato alla posizione i, j
					for(GruppoMilitare g: partita.getGruppiMilitariSchierati())
					{
						if(g.getPosX() == i + posSchermataX && g.getPosY() == j + posSchermataY)
						{
							gruppoMilitareCercato = g;
						}
					}
					
					guiGruppoMilitare = new GUIGruppoMilitare(partita, guiPartita, valoriDiGioco, iconeGrafiche, gruppoMilitareCercato);
					guiGruppoMilitare.setVisible(true);
				}
			}
		}
		else
		if(azioneLblsGioco.equals(Global.getLabels("s59")))
		{
			if(partita.isPiazzamentoPossibile(elemLblsGioco, i+posSchermataX, j+posSchermataY))
			{
				int vicinanza = partita.isVicinoACitta(i + posSchermataX, j + posSchermataY);
				
				int civilta = partita.getGiocatore().get(indiceProprietario).getCivilt�();
				
				if(vicinanza == civilta) //ok, pu� piazzare qui
				{
					try {
						audio = AudioSystem.getClip();
						audio.open(AudioSystem.getAudioInputStream(new File("media/marcia.wav")));
					} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
						e1.printStackTrace();
					}
					FloatControl gainControl = (FloatControl) audio.getControl(FloatControl.Type.MASTER_GAIN);
					gainControl.setValue(Global.getLivVolume()); 
					audio.start();
					
					partita.piazzaEsercito(gruppoMilitare, civilta, i+posSchermataX, j+posSchermataY);
				}
				else
					JOptionPane.showMessageDialog(null, Global.getLabels("s73"),Global.getLabels("e7"), JOptionPane.DEFAULT_OPTION);
			}
			
			//Resetto i dati di azione e elemento in memoria
			azioneLblsGioco = "";
			elemLblsGioco = "";

			aggiornaSchermata();
		}
		else
		if(azioneLblsGioco.equals(Global.getLabels("s61")))
		{
			int vicinanza = partita.isVicinoACitta(i + posSchermataX, j + posSchermataY);
			
			int civilta = partita.getGiocatore().get(indiceProprietario).getCivilt�();
			
			if(vicinanza == civilta) //l'esercito � adiacente alla citt�
			{
				int nazionEsercito = partita.esercitoPresente(i + posSchermataX, j + posSchermataY);
				
				if(nazionEsercito == civilta) //possiamo prelevare l'esercito e inserirlo nel municipio
				{
					try {
						audio = AudioSystem.getClip();
						audio.open(AudioSystem.getAudioInputStream(new File("media/marcia.wav")));
					} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
						e1.printStackTrace();
					}
					FloatControl gainControl = (FloatControl) audio.getControl(FloatControl.Type.MASTER_GAIN);
					gainControl.setValue(Global.getLivVolume()); 
					audio.start();
					
					partita.prelevaEsercito(i+posSchermataX, j+posSchermataY);
				}
				else
					JOptionPane.showMessageDialog(null, Global.getLabels("s74"),Global.getLabels("e7"), JOptionPane.DEFAULT_OPTION);
			}
			else
				JOptionPane.showMessageDialog(null, Global.getLabels("s136"),Global.getLabels("e7"), JOptionPane.DEFAULT_OPTION);
			
			//Resetto i dati di azione e elemento in memoria
			azioneLblsGioco = "";
			elemLblsGioco = "";

			aggiornaSchermata();
		}
	}

	public String getAzioneLblsGioco() {
		return azioneLblsGioco;
	}

	public void setAzioneLblsGioco(String azioneLblsGioco) {
		this.azioneLblsGioco = azioneLblsGioco;
	}

	public String getElemLblsGioco() {
		return elemLblsGioco;
	}

	public void setElemLblsGioco(String elemLblsGioco) {
		this.elemLblsGioco = elemLblsGioco;
	}
}
