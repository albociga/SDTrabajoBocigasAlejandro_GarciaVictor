package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PruebaMusica extends JFrame {

	private JPanel contentPane;
	private JTextPane txtPista;
	private JTextField txtRespuesta;
	private JButton btnResponder;
	private JPanel panelRespuesta;
	private JLabel lblReproduciendo;
	private BufferedWriter buffer;
	private JLabel lblPtsJug1;
	private JLabel lblPtsJug2;
	private JLabel lblContJug1;
	private JLabel lblContJug2;
	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the frame.
	 */
	public PruebaMusica(BufferedWriter bw) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 495, 266);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		this.buffer=bw;
		txtPista = new JTextPane();
		txtPista.setEditable(false);
		txtPista.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		panelRespuesta = new JPanel();
		panelRespuesta.setVisible(false);
		
		lblReproduciendo = new JLabel("REPRODUCIENDO");
		lblReproduciendo.setVisible(false);
		
		lblPtsJug1 = new JLabel("Jugador 1:");
		
		lblPtsJug2 = new JLabel("Jugador 2:");
		
		lblContJug1 = new JLabel("0");
		
		lblContJug2 = new JLabel("0");
		
		JLabel lblNewLabel = new JLabel("Puntuaci\u00F3n");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(20)
							.addComponent(txtPista, GroupLayout.PREFERRED_SIZE, 384, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(34)
							.addComponent(panelRespuesta, GroupLayout.PREFERRED_SIZE, 349, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(152)
							.addComponent(lblReproduciendo))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(308)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblPtsJug2)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(lblContJug2))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblPtsJug1)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(lblContJug1)))))
					.addContainerGap(65, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(322, Short.MAX_VALUE)
					.addComponent(lblNewLabel)
					.addGap(101))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(10)
					.addComponent(lblReproduciendo)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txtPista, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(panelRespuesta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(15)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPtsJug1)
						.addComponent(lblContJug1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPtsJug2)
						.addComponent(lblContJug2, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addGap(19))
		);
		
		txtRespuesta = new JTextField();
		txtRespuesta.setColumns(10);
		
		btnResponder = new JButton("Responder");
		btnResponder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					buffer.write(txtRespuesta.getText()+"\r\n");
					buffer.flush(); 
					txtRespuesta.setText("");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		GroupLayout gl_panelRespuesta = new GroupLayout(panelRespuesta);
		gl_panelRespuesta.setHorizontalGroup(
			gl_panelRespuesta.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelRespuesta.createSequentialGroup()
					.addGap(19)
					.addComponent(txtRespuesta, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
					.addGap(67)
					.addComponent(btnResponder)
					.addGap(25))
		);
		gl_panelRespuesta.setVerticalGroup(
			gl_panelRespuesta.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelRespuesta.createSequentialGroup()
					.addGap(6)
					.addGroup(gl_panelRespuesta.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtRespuesta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnResponder))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panelRespuesta.setLayout(gl_panelRespuesta);
		contentPane.setLayout(gl_contentPane);
	}
	public void setPista(String s) {
		txtPista.setText(s);
	}
	public void ocultarReproduciendo() {
		lblReproduciendo.setVisible(false);
	}
	public void mostrarReproduciendo() {
		lblReproduciendo.setVisible(true);
	}
	public void activarRespuesta() {
		panelRespuesta.setVisible(true);
	}
	public void desactivarRespuesta() {
		panelRespuesta.setVisible(false);
	}
	public void setPuntuacionJug1(String s) {
		lblContJug1.setText(s);
	}
	public void setPuntuacionJug2(String s) {
		lblContJug2.setText(s);
	}
}