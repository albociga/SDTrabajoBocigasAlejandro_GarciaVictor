package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.GroupLayout;
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
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JPanel panel_1 = new JPanel();
		
		JPanel panel_2 = new JPanel();
		
		JPanel panel_3 = new JPanel();
		
		JLabel lblFallos = new JLabel("Fallos:");
		panel_3.add(lblFallos);
		
		JLabel lblContAciertos_1 = new JLabel("0");
		panel_3.add(lblContAciertos_1);
		
		JPanel panel_4 = new JPanel();
		
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
							.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE))
						.addComponent(txtRespuesta, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE))
					.addGap(7)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(6)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
								.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
								.addComponent(panel_1, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE))
							.addContainerGap())
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
							.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
							.addGap(26))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 309, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addComponent(txtPregunta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(txtRespuesta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		JButton btnPasapalabra = new JButton("Pasapalabra");
		panel_4.add(btnPasapalabra);
		
		JButton btnResponder = new JButton("Responder");
		panel_4.add(btnResponder);
		
		JLabel lblAciertos = new JLabel("Aciertos:");
		panel_2.add(lblAciertos);
		
		JLabel lblContAciertos = new JLabel("0");
		panel_2.add(lblContAciertos);
		
		JLabel lblTiempoTxt = new JLabel("Tiempo:");
		panel_1.add(lblTiempoTxt);
		
		JLabel lblTiempoSeg = new JLabel("200");
		panel_1.add(lblTiempoSeg);
		
		JLabel lblA = new JLabel("A");
		
		JLabel lblB = new JLabel("B");
		
		JLabel lblC = new JLabel("C");
		
		JLabel lblD = new JLabel("D");
		
		JLabel lblE = new JLabel("E");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(181, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addComponent(lblA)
							.addGap(10)
							.addComponent(lblB)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblC)
							.addGap(162))
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addComponent(lblD)
							.addGap(150))
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addComponent(lblE, GroupLayout.PREFERRED_SIZE, 6, GroupLayout.PREFERRED_SIZE)
							.addGap(145))))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblA)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblC))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(19)
							.addComponent(lblB)
							.addGap(15)
							.addComponent(lblD)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblE)))
					.addContainerGap(227, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}
}
