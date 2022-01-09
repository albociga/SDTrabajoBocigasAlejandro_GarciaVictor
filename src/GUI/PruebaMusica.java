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
	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the frame.
	 */
	public PruebaMusica(BufferedWriter bw) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 206);
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
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
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
							.addComponent(lblReproduciendo)))
					.addContainerGap(20, Short.MAX_VALUE))
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
					.addContainerGap(69, Short.MAX_VALUE))
		);
		
		txtRespuesta = new JTextField();
		txtRespuesta.setColumns(10);
		
		btnResponder = new JButton("Responder");
		btnResponder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					bw.write(txtRespuesta.getText()+"\r\n");
					bw.flush(); 
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
	public void activarRespuesta() {
		panelRespuesta.setVisible(true);
		lblReproduciendo.setVisible(false);
	}
	public void desactivarRespuesta() {
		panelRespuesta.setVisible(false);
		lblReproduciendo.setVisible(true);
	}
}
