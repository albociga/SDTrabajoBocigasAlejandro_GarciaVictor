package GUI;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import javax.swing.JRadioButton;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class ElegirNumJugadores extends JFrame {

	private JPanel contentPane;
	private JRadioButton rdbtnMultijugador;
	private JRadioButton rdbtnIndividual;
	private BufferedWriter buffer;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public ElegirNumJugadores(BufferedWriter bw) {
		buffer=bw;
		setTitle("Tipo de juego");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnAceptar = new JButton("Aceptar");
		//Cuando le da aceptar, dependiendo del item seleccionado, envia la opcion de jugar individual o multijugador
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(rdbtnIndividual.isSelected()) {
						buffer.write("1\r\n");
					}
					else buffer.write("2\r\n");
					buffer.flush();
					btnAceptar.setVisible(false);
					rdbtnMultijugador.setVisible(false);
					rdbtnIndividual.setVisible(false);
				} catch (IOException e1) {
						// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		JLabel lblNewLabel = new JLabel("Elige un modo de juego");
		//He creado y añadido los items a un ButtonGroup para que solo se puede seleccionar uno de ellos
		ButtonGroup b=new ButtonGroup();
		rdbtnMultijugador = new JRadioButton("Multijugador");
		b.add(rdbtnMultijugador);
		
		rdbtnIndividual = new JRadioButton("Individual");
		rdbtnIndividual.setSelected(true);
		b.add(rdbtnIndividual);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(148)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnAceptar)
						.addComponent(rdbtnMultijugador)
						.addComponent(rdbtnIndividual)
						.addComponent(lblNewLabel))
					.addContainerGap(165, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(54)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnIndividual)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rdbtnMultijugador)
					.addGap(18)
					.addComponent(btnAceptar)
					.addContainerGap(89, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
	public boolean multijugador() {
		return rdbtnMultijugador.isSelected();
	}
}