package br.edu.ifba.embarcados.javaapp.janela;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.edu.ifba.embarcados.javaapp.ListenerConsole;
import br.edu.ifba.embarcados.javaapp.asincexec.AsincExec;
import br.edu.ifba.embarcados.javaapp.asincexec.IListenerBussola;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListenerJanela extends JFrame implements IListenerBussola {

	private JPanel contentPane;
	private static AsincExec asinc;
	private static Thread t;
	private JButton btnIniciar;
	private JButton btnParar;
	private JLabel oeste;
	private JLabel leste;
	private JLabel sul;
	private JLabel norte;
	private JLabel nordeste;
	private JLabel noroeste;
	private JLabel sudoeste;
	private JLabel sudeste;

	/**
	 * Launch the application.
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		ListenerJanela listener = new ListenerJanela();
		listener.setVisible(true);
		ListenerConsole listener2 = new ListenerConsole();
		
		asinc = new AsincExec ("COM4"); 
		
		asinc.addListener(listener);
		asinc.addListener(listener2);
		
	}
	
	public void encerrar() {
		btnIniciar.setVisible(true);
		btnIniciar.setEnabled(true);
		btnParar.setVisible(false);
		btnParar.setEnabled(false);
		
		asinc.setContinuar(false); // encerra a thread
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public ListenerJanela() {
		setBackground(Color.WHITE);
		setTitle("B\u00FAssola");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 730, 709);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		norte = new JLabel("");
		norte.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		norte.setIcon(null);
		norte.setBounds(234, 11, 215, 270);
		contentPane.add(norte);
		
		oeste = new JLabel("");
		oeste.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		oeste.setIcon(null);
		oeste.setBounds(36, 241, 256, 191);
		contentPane.add(oeste);
		
		leste = new JLabel("");
		leste.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		leste.setIcon(null);
		leste.setBounds(427, 238, 256, 191);
		contentPane.add(leste);
		
		sul = new JLabel("");
		sul.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		sul.setIcon(null);
		sul.setBounds(229, 401, 215, 270);
		contentPane.add(sul);
		
		nordeste = new JLabel("");
		nordeste.setIcon(null);
		nordeste.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		nordeste.setBounds(365, 198, 120, 110);
		contentPane.add(nordeste);
		
		noroeste = new JLabel("");
		noroeste.setIcon(null);
		noroeste.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		noroeste.setBounds(193, 193, 132, 110);
		contentPane.add(noroeste);
		
		sudoeste = new JLabel("");
		sudoeste.setIcon(null);
		sudoeste.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		sudoeste.setBounds(189, 366, 132, 110);
		contentPane.add(sudoeste);
		
		sudeste = new JLabel("");
		sudeste.setIcon(null);
		sudeste.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		sudeste.setBounds(362, 370, 132, 110);
		contentPane.add(sudeste);
		
		JLabel buss = new JLabel("");
		buss.setIcon(new ImageIcon(ListenerJanela.class.getResource("/br/edu/ifba/embarcados/javaapp/imagem/bussola.png")));
		buss.setBounds(36, 12, 648, 651);
		contentPane.add(buss);
		
		btnParar = new JButton("Parar");
		btnParar.setEnabled(false);
		btnParar.setVisible(false);
		btnParar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				encerrar();
			}
		});
		btnParar.setBounds(624, 11, 89, 23);
		contentPane.add(btnParar);
		
		btnIniciar = new JButton("Iniciar");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t = new Thread(asinc);
				t.start();
				btnParar.setVisible(true);
				btnParar.setEnabled(true);
				btnIniciar.setVisible(false);
				btnIniciar.setEnabled(false);
			}
		});
		btnIniciar.setBounds(525, 11, 89, 23);
		contentPane.add(btnIniciar);
	}

	@Override
	public void notificarMovimento(int x, int y, int z) {
		double heading = Math.atan2(y, x);
		heading += 0.4072;
		if(heading < 0)
			heading += 2*Math.PI;

		// Check for wrap due to addition of declination.
		if(heading > 2*Math.PI)
			heading -= 2*Math.PI;
		
		// Convert radianos para graus
		double headingDegrees = heading * 180/Math.PI;
		
		tirarPosicoes();
		mostrarPosicoes((int) headingDegrees);
		
	}
	
	public void mostrarPosicoes(int graus) {
		if (graus < 45 && graus > 0) {
			nordeste.setIcon(new ImageIcon(ListenerJanela.class.getResource("/br/edu/ifba/embarcados/javaapp/imagem/nordeste.png")));
		} else if (graus < 90 && graus > 45) {
			leste.setIcon(new ImageIcon(ListenerJanela.class.getResource("/br/edu/ifba/embarcados/javaapp/imagem/leste.png")));
		} else if (graus < 135 && graus > 90) {
			sudeste.setIcon(new ImageIcon(ListenerJanela.class.getResource("/br/edu/ifba/embarcados/javaapp/imagem/sudeste.png")));
		} else if (graus < 180 && graus > 135) {
			sul.setIcon(new ImageIcon(ListenerJanela.class.getResource("/br/edu/ifba/embarcados/javaapp/imagem/sul.png")));
		} else if (graus < 225 && graus > 180) {
			sudoeste.setIcon(new ImageIcon(ListenerJanela.class.getResource("/br/edu/ifba/embarcados/javaapp/imagem/sudoeste.png")));//txtSudoeste.setForeground(Color.RED);
		} else if (graus < 270 && graus > 225) {
			oeste.setIcon(new ImageIcon(ListenerJanela.class.getResource("/br/edu/ifba/embarcados/javaapp/imagem/oeste.png")));
		} else if (graus < 315 && graus > 270) {
			noroeste.setIcon(new ImageIcon(ListenerJanela.class.getResource("/br/edu/ifba/embarcados/javaapp/imagem/noroeste.png")));
		} else if (graus < 360 && graus > 315) {
			norte.setIcon(new ImageIcon(ListenerJanela.class.getResource("/br/edu/ifba/embarcados/javaapp/imagem/norte.png")));
		}
	}
	
	public void tirarPosicoes() {
		nordeste.setIcon(null);
		leste.setIcon(null);
		sudeste.setIcon(null);
		sul.setIcon(null);
		sudoeste.setIcon(null);
		oeste.setIcon(null);
		noroeste.setIcon(null);
		norte.setIcon(null);
	}
}
