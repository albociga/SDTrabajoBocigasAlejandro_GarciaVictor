package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.Panel;
import javax.swing.border.BevelBorder;

public class Rosco extends JFrame {

	private JPanel contentPane;
	private JTextField txtRespuesta;
	private JTextField txtPregunta;
	private JLabel lblA;
	private JLabel lblB;
	private JLabel lblC;
	private JLabel lblD;
	private JLabel lblE;
	private JLabel lblF;
	private JLabel lblG;
	private JLabel lblH;
	private JLabel lblI;
	private JLabel lblJ;
	private JLabel lblK;
	private JLabel lblL;
	private JLabel lblM;
	private JLabel lblN;
	private JLabel lblO;
	private JLabel lblP;
	private JLabel lblQ;
	private JLabel lblR;
	private JLabel lblS;
	private JLabel lblT;
	private JLabel lblU;
	private JLabel lblV;
	private JLabel lblW;
	private JLabel lblX;
	private JLabel lblY;
	private JLabel lblZ;
	private JButton btnResponder;
	private JPanel panelRosco;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Rosco frame = new Rosco();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Rosco() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 583, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		txtRespuesta = new JTextField();
		txtRespuesta.setColumns(10);
		
		panelRosco = new JPanel();
		panelRosco.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JPanel panelTiempo = new JPanel();
		
		JPanel panelAciertos = new JPanel();
		
		JPanel panelFallos = new JPanel();
		
		JLabel lblFallos = new JLabel("Fallos:");
		panelFallos.add(lblFallos);
		
		JLabel lblContAciertos_1 = new JLabel("0");
		panelFallos.add(lblContAciertos_1);
		
		JPanel panelBotones = new JPanel();
		
		txtPregunta = new JTextField();
		txtPregunta.setEditable(false);
		txtPregunta.setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(txtPregunta, Alignment.LEADING)
							.addComponent(panelRosco, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE))
						.addComponent(txtRespuesta, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE))
					.addGap(7)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(6)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(panelAciertos, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
								.addComponent(panelFallos, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
								.addComponent(panelTiempo, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE))
							.addContainerGap())
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
							.addComponent(panelBotones, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
							.addGap(26))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panelTiempo, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panelAciertos, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(panelFallos, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
						.addComponent(panelRosco, GroupLayout.PREFERRED_SIZE, 309, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addComponent(txtPregunta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(txtRespuesta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(panelBotones, GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		JButton btnPasapalabra = new JButton("Pasapalabra");
		panelBotones.add(btnPasapalabra);
		
		btnResponder = new JButton("Responder");
		panelBotones.add(btnResponder);
		
		JLabel lblAciertos = new JLabel("Aciertos:");
		panelAciertos.add(lblAciertos);
		
		JLabel lblContAciertos = new JLabel("0");
		panelAciertos.add(lblContAciertos);
		
		JLabel lblTiempoTxt = new JLabel("Tiempo:");
		panelTiempo.add(lblTiempoTxt);
		
		JLabel lblTiempoSeg = new JLabel("200");
		panelTiempo.add(lblTiempoSeg);
		GroupLayout gl_panelRosco = new GroupLayout(panelRosco);
		gl_panelRosco.setHorizontalGroup(
			gl_panelRosco.createParallelGroup(Alignment.LEADING)
				.addGap(0, 379, Short.MAX_VALUE)
		);
		gl_panelRosco.setVerticalGroup(
			gl_panelRosco.createParallelGroup(Alignment.LEADING)
				.addGap(0, 305, Short.MAX_VALUE)
		);
		panelRosco.setLayout(gl_panelRosco);
		contentPane.setLayout(gl_contentPane);
		
		
	}
}
