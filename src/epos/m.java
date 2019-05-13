package epos;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.json.JSONException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class m extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					m frame = new m();
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
	public m() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 419);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		String title = "Management System";
		Border border = BorderFactory.createTitledBorder(title);
		contentPane.setBorder(border);
		
		
		JButton btnOpenEpos = new JButton("Open Epos");
		btnOpenEpos.addMouseListener(new MouseAdapter() {
			@Override
			
			public void mouseClicked(MouseEvent e) {
				eposGui eposObj = new eposGui();
				eposObj.setVisible(true);
			}
		});
		btnOpenEpos.setBounds(12, 28, 126, 40);
		contentPane.add(btnOpenEpos);
		
		
		JButton btnAddItem = new JButton("Add Item");
		btnAddItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				addItem addObj = null;
				try {
					addObj = new addItem();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				addObj.setVisible(true);
			}
		});
		btnAddItem.setBounds(12, 96, 126, 40);
		contentPane.add(btnAddItem);
		
		
	}
}
