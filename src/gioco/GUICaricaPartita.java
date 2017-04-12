package gioco;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUICaricaPartita extends JPanel {

	private JPanel pnlMenu;
	private GridBagConstraints c, d;
	private Font fontFuturist;
	private JLabel lblNomeSalvataggio, lblNomeSalvataggiov;
	private JLabel lblDataSave, lblDataSavev;
	private JLabel lblNomeGiocatore, lblNomeGiocatorev;
	private JLabel lblTutorial, lblTutorialv;
	private JLabel lblDifficolta, lblDifficoltav;
	private JLabel lblCivilta, lblCiviltav;
	private JLabel lblTurno, lblTurnov;
	private JLabel lblEpoca, lblEpocav;
	private JLabel lblOro, lblOrov;
	private JLabel lblMateriali, lblMaterialiv;
	private JLabel lblPuntiRicerca, lblPuntiRicercav;
	private JPanel pnlInfoPartita;
	private JComboBox<String> cmbSalvataggi;
	private RoundedCornerButton btnIndietro;
	private RoundedCornerButton btnCarica;
	private RoundedCornerButton btnElimina;
	
	GUICaricaPartita() {	
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
		
		pnlMenu = new JPanel(new GridBagLayout());
		setBackground(Color.LIGHT_GRAY);
		setLayout(new BorderLayout(0, 0));
		pnlMenu.setBackground(Color.WHITE);
		
		lblNomeSalvataggio = new JLabel("Salvataggio: ");
		lblNomeSalvataggio.setFont(lblNomeSalvataggio.getFont().deriveFont(20f));
		lblNomeGiocatore = new JLabel("Giocatore: ");
		lblNomeGiocatore.setFont(lblNomeGiocatore.getFont().deriveFont(20f));
		lblTutorial = new JLabel("Tutorial: ");
		lblTutorial.setFont(lblTutorial.getFont().deriveFont(20f));
		lblDifficolta = new JLabel("Difficolt�: ");
		lblDifficolta.setFont(lblDifficolta.getFont().deriveFont(20f));
		lblCivilta = new JLabel("Civilt�: ");
		lblCivilta.setFont(lblCivilta.getFont().deriveFont(20f));
		lblTurno = new JLabel("Turno: ");
		lblTurno.setFont(lblTurno.getFont().deriveFont(20f));
		lblEpoca = new JLabel("Epoca: ");
		lblEpoca.setFont(lblEpoca.getFont().deriveFont(20f));
		lblOro = new JLabel("Oro: ");
		lblOro.setFont(lblOro.getFont().deriveFont(20f));
		lblMateriali = new JLabel("Materiali: ");
		lblMateriali.setFont(lblMateriali.getFont().deriveFont(20f));
		lblPuntiRicerca = new JLabel("Punti ricerca: ");
		lblPuntiRicerca.setFont(lblPuntiRicerca.getFont().deriveFont(20f));
		
		pnlInfoPartita = new JPanel(new GridBagLayout());
		
		c = new GridBagConstraints();
		c.anchor = new GridBagConstraints().LINE_END;
		c.gridx = 0;
		c.gridy = 0;
		
		pnlInfoPartita.add(lblNomeSalvataggio, c);
		c.gridy ++;
		pnlInfoPartita.add(lblNomeGiocatore, c);
		c.gridy ++;
		pnlInfoPartita.add(lblTutorial, c);
		c.gridy ++;
		pnlInfoPartita.add(lblDifficolta, c);
		c.gridy ++;
		pnlInfoPartita.add(lblCivilta, c);
		c.gridy ++;
		pnlInfoPartita.add(lblTurno, c);
		c.gridy ++;
		pnlInfoPartita.add(lblEpoca, c);
		c.gridy ++;
		pnlInfoPartita.add(lblOro, c);
		c.gridy ++;
		pnlInfoPartita.add(lblMateriali, c);
		c.gridy ++;
		pnlInfoPartita.add(lblPuntiRicerca, c);
		
		lblNomeSalvataggiov = new JLabel("Salvataggio 1");
		lblNomeSalvataggiov.setFont(lblNomeSalvataggiov.getFont().deriveFont(16f));
		lblNomeGiocatorev = new JLabel("Werther158");
		lblNomeGiocatorev.setFont(lblNomeGiocatorev.getFont().deriveFont(16f));
		lblTutorialv = new JLabel("NO");
		lblTutorialv.setFont(lblTutorialv.getFont().deriveFont(16f));
		lblDifficoltav = new JLabel("Medio");
		lblDifficoltav.setFont(lblDifficoltav.getFont().deriveFont(16f));
		lblCiviltav = new JLabel("Romani");
		lblCiviltav.setFont(lblCiviltav.getFont().deriveFont(16f));
		lblTurnov = new JLabel("13");
		lblTurnov.setFont(lblTurnov.getFont().deriveFont(16f));
		lblEpocav = new JLabel("Medioevo");
		lblEpocav.setFont(lblEpocav.getFont().deriveFont(16f));
		lblOrov = new JLabel("13000");
		lblOrov.setFont(lblOrov.getFont().deriveFont(16f));
		lblMaterialiv = new JLabel("25000");
		lblMaterialiv.setFont(lblMaterialiv.getFont().deriveFont(16f));
		lblPuntiRicercav = new JLabel("7");
		lblPuntiRicercav.setFont(lblPuntiRicercav.getFont().deriveFont(16f));
		
		c.anchor = new GridBagConstraints().LINE_START;
		c.gridx = 1;
		c.gridy = 0;
		
		pnlInfoPartita.add(lblNomeSalvataggiov, c);
		c.gridy ++;
		pnlInfoPartita.add(lblNomeGiocatorev, c);
		c.gridy ++;
		pnlInfoPartita.add(lblTutorialv, c);
		c.gridy ++;
		pnlInfoPartita.add(lblDifficoltav, c);
		c.gridy ++;
		pnlInfoPartita.add(lblCiviltav, c);
		c.gridy ++;
		pnlInfoPartita.add(lblTurnov, c);
		c.gridy ++;
		pnlInfoPartita.add(lblEpocav, c);
		c.gridy ++;
		pnlInfoPartita.add(lblOrov, c);
		c.gridy ++;
		pnlInfoPartita.add(lblMaterialiv, c);
		c.gridy ++;
		pnlInfoPartita.add(lblPuntiRicercav, c);
		
		d = new GridBagConstraints();
		d.gridx = 2;
		d.gridy = 0;
		pnlMenu.add(pnlInfoPartita,  d);
		
		d.gridy ++;
		pnlMenu.add(new JLabel(" "), d);
		d.gridy ++;
		pnlMenu.add(new JLabel(" "), d);
		d.gridy ++;
		
		cmbSalvataggi = new JComboBox<String>();
		cmbSalvataggi.addItem("...");
		pnlMenu.add(cmbSalvataggi, d);
		
		d.gridy ++;
		pnlMenu.add(new JLabel(" "), d);
		d.gridy ++;
		pnlMenu.add(new JLabel(" "), d);
		d.gridy ++;
		
		d.gridx = 0;
		btnIndietro = new RoundedCornerButton("INDIETRO");
		btnIndietro.setFont(fontFuturist.deriveFont(16f));
		btnIndietro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setVisible(false);
			}
		});
		pnlMenu.add(btnIndietro, d);
		
		d.gridx ++;
		pnlMenu.add(new JLabel("     "), d);
		d.gridx ++;
		
		btnCarica = new RoundedCornerButton("CARICA PARTITA");
		btnCarica.setFont(fontFuturist.deriveFont(16f));
		btnCarica.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println("Carico la partita!");
			}
		});
		pnlMenu.add(btnCarica, d);
		
		d.gridx ++;
		pnlMenu.add(new JLabel("     "), d);
		d.gridx ++;
		
		btnElimina = new RoundedCornerButton("ELIMINA PARTITA");
		btnElimina.setFont(fontFuturist.deriveFont(16f));
		btnElimina.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println("Elimino la partita!");
			}
		});
		pnlMenu.add(btnElimina, d);
		
		add(pnlMenu, BorderLayout.CENTER);
	}
}