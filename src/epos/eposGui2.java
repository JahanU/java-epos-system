package epos;

import java.awt.*;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.management.modelmbean.ModelMBean;
import javax.swing.AbstractListModel;
import java.awt.List;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.Scrollbar;
import java.awt.ScrollPane;

public class eposGui2 {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					eposGui window = new eposGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public eposGui2() {
		initialize();
	}

	/**
	 * Initialise the contents of the frame.
	 */
	
	
		//stores all the products for the combo box within an array
		String burgerNames[] = {"Chicken burger", "Cheese burger", "Lamb burger"};//list of all burger
		int burgerQuanity[] = { 0, 0, 0};//Quanity for each burger
		double burgerPrices[] = {3.00, 2.00, 4.00}; 

	private void initialize() {
		
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.BOLD, 12));
		frame.setBounds(100, 100, 880, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JComboBox cmbMessageListburger = new JComboBox (burgerNames);//creates and initialises JCombo box
		cmbMessageListburger.setBounds(481, 31, 110, 51);
		cmbMessageListburger.setSelectedIndex(0);
		frame.getContentPane().add(cmbMessageListburger);			
		
		JList orderList = new JList();
		orderList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String selectedItem = orderList.getSelectedValue().toString();
			}
		});
		orderList.setBounds(10, 31, 262, 227);
		frame.getContentPane().add(orderList);
		
		
		DefaultListModel model = new DefaultListModel();//having this outside the functions means it will recreate the values again and reput them in the list // this creates the values and basically stores the List elements
		//used to iteract with the Jlist also

		JButton btnVoidAll = new JButton("Void all");
		btnVoidAll.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				model.clear();
				orderList.setModel(model);
				for (int i : burgerQuanity)
					burgerQuanity[i] = 0;
				
			}
		});
		btnVoidAll.setBounds(282, 235, 89, 23);
		frame.getContentPane().add(btnVoidAll);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//model.removeElementAt(orderList.getSelectedIndex());
				int selectedIndex = orderList.getSelectedIndex();
					if (selectedIndex != -1) {
					    model.remove(selectedIndex);
					    burgerQuanity[selectedIndex] = 0;
					}
			}
		});
		btnClear.setBounds(282, 198, 89, 23);
		frame.getContentPane().add(btnClear);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(20, 346, 198, 122);
		frame.getContentPane().add(lblNewLabel);


		cmbMessageListburger.addActionListener(new ActionListener() {//action listener for JCombo box
			public void actionPerformed(ActionEvent e) {

				if (e.getSource() == cmbMessageListburger) {
					JComboBox cbObj = (JComboBox)e.getSource();
					String foodChoice = (String) cbObj.getSelectedItem();
					
					for (int i = 0; i < burgerNames.length; i++) {
						
						if (foodChoice == burgerNames[i]) {
							if (burgerQuanity[i] > 0) {
								burgerQuanity[i] += 1;
								model.setElementAt(burgerNames[i] + "          " +  burgerQuanity[i] + "          " + burgerPrices[i], i);
								orderList.setModel(model);
							}
							else {
								burgerQuanity[i] += 1;
								model.addElement(burgerNames[i] + "          " +  burgerQuanity[i] + "          " + burgerPrices[i]);
								orderList.setModel(model);
							}
						}
					}
				}
			}
		});
		
	
	}
}	
	


