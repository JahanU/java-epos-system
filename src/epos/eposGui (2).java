package epos;

import java.awt.*;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.JTable;
import javax.swing.SwingConstants;
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
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.management.modelmbean.ModelMBean;
import javax.swing.AbstractListModel;
import java.awt.List;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.html.HTMLEditorKit.Parser;
import javax.xml.bind.ValidationEvent;

import org.json.simple.parser.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.mysql.cj.xdevapi.JsonParser;

import java.awt.Scrollbar;
import java.awt.ScrollPane;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JCheckBox;
import java.sql.*;
import javax.swing.UIManager;


public class eposGuiMain {

		protected JFrame frame;
			
		ArrayList<String> burgerNamesAL = new ArrayList<String>();//Stores all names of products		
		ArrayList<String> burgerToppingsAL = new ArrayList<String>();//Stores all topping of products
		ArrayList<String> burgerSaucesAL = new ArrayList<String>();//Stores all sauces of products
		
		ArrayList<Integer> burgerQuanityAL = new ArrayList<Integer>();//Stores all quantity of products
		ArrayList<Double> burgerPricesAL = new ArrayList<Double>();//Stores all prices of products
		
		ArrayList<Double> burgerPriceToQtyAL = new ArrayList<Double>(); //stores the price of each item when you add multiple items
		ArrayList<Integer> burgerPosAL = new ArrayList<Integer>(); //stores the price of each item when you add multiple items
		double totalPrice;//stores the total price of items bought

		
		JButton btnCalc[] = new JButton[12];//Stores list of all calc buttons
		String lblCalc[] = {"1","2","3","4","5","6","7","8","9",".", "0","Delete"};	
		
		JLabel lblPaid = new JLabel("Paid: £");
		JLabel lblChange = new JLabel("Change: £");

		String paidNum = "";//stores the number of the amount paid 
		double paidNumInt;//converts the string into an int - work out the change
		

		/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					eposGuiMain window = new eposGuiMain();
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
	public eposGuiMain() {
		initialize();
		calculator2();
	}

	/**
	 * Initialise the contents of the frame.
	 */
	
	
	public void setVisible(boolean b) {
		frame.setVisible(true);
	}

		int listCount = 0;

	private void initialize() {
	
		//stores all the products for the combo box within an array
		String burgerNames[] = {"Chicken burger", "Cheese burger", "beef burger"};//list of all items
		String burgerToppings[] = {"Lettuce", "Tomato", "Cucumber"};
		String burgerSauces[] = {"Mayo", "ketchup", "Spicey mayo"};
		
		Integer burgerQuanity	[] =	{0, 0, 0};//Quanity for each burger
		Double burgerPrices		[] = 	{3.00, 2.00, 4.00}; 
		Double burgerPriceToQty	[] = 	{0.0, 0.0, 0.0}; //stores the price of each item when you add multiple items
		Integer burgerPos		[] =	{ 0, 0, 0};//position for each burger

		Collections.addAll(burgerNamesAL, burgerNames);
		Collections.addAll(burgerToppingsAL, burgerToppings);
		Collections.addAll(burgerSaucesAL, burgerSauces);
		Collections.addAll(burgerQuanityAL, burgerQuanity);
		Collections.addAll(burgerPricesAL, burgerPrices);
		Collections.addAll(burgerPriceToQtyAL, burgerPriceToQty);
		Collections.addAll(burgerPosAL, burgerPos);
		
		
		frame = new JFrame("Till");
		frame.getContentPane().setFont(new Font("Tahoma", Font.BOLD, 12));
		frame.setBounds(100, 100, 1086, 623);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		
			//	-	-	-	-	-	-	-	-	-	-	Jlabels
		lblChange.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblChange.setBounds(114, 274, 161, 41);
		frame.getContentPane().add(lblChange);
		lblChange.setForeground(new Color(0, 128, 0));

		lblPaid.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPaid.setBounds(9, 291, 94, 31);
		frame.getContentPane().add(lblPaid);
		
		JLabel lbltotal = new JLabel("Total: £");
		lbltotal.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbltotal.setBounds(9, 254, 94, 42);
		frame.getContentPane().add(lbltotal);
		
		
		JLabel labelOrderHeading = new JLabel("Name            Quantity           Price");
		labelOrderHeading.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelOrderHeading.setBounds(20, 0, 260, 31);
		frame.getContentPane().add(labelOrderHeading);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 31, 262, 227);
		frame.getContentPane().add(scrollPane);
		
		
		//-	-	-	-	-	-	-	-	JLists
	
		JList orderList = new JList();
		scrollPane.setViewportView(orderList);
		orderList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String selectedItem = orderList.getSelectedValue().toString();
			}
		});
		
		
		DefaultListModel model = new DefaultListModel();//having this outside the functions means it will recreate the values again and reput them in the list // this creates the values and basically stores the List elements
		//used to interact with the Jlist also

		
		//	-	-	-	-	-	-	-	-	-	-	JButtons
		
	

		JButton btnVoidAll = new JButton("Void all");
		btnVoidAll.setBackground(Color.RED);
		btnVoidAll.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
			
				for (int i = 0; i < burgerQuanityAL.size(); i++) //resets all quantity
					burgerQuanityAL.set(i, 0);
				
				for (int i = 0; i < burgerPosAL.size(); i++) //resets all positions in the burgerPos Arraylist
					burgerPosAL.set(i, -1);
				
				for (int i = 0; i < burgerPriceToQtyAL.size(); i++) //resets all positions in the burgerPos Arraylist
					burgerPriceToQtyAL.set(i, 0.0);

				listCount = 0;
				lblPaid.setText("Paid: £" + (paidNum = "") + (paidNumInt = 0));
				lblChange.setText("Change: £");
				lbltotal.setText(String.valueOf("Total: £" + (totalPrice = 0)));
				
				model.clear();
				orderList.setModel(model);

			}
		});
		btnVoidAll.setBounds(282, 235, 89, 23);
		frame.getContentPane().add(btnVoidAll);
		
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBackground(Color.orange);
		btnClear.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int selectedIndex = orderList.getSelectedIndex();
				String item = orderList.getSelectedValue().toString();
				
				if (selectedIndex != -1) {
				    model.remove(selectedIndex);
				    for (int i = 0; i < burgerNamesAL.size(); i++) {
				    	
				    	if (item.toLowerCase().indexOf(burgerNamesAL.get(i).toLowerCase()) != -1 ) {
				    	
				    		burgerPosAL.set(i, 0);
				    		totalPrice -= (burgerPricesAL.get(i) * burgerQuanityAL.get(i));
				   		    burgerQuanityAL.set(i, 0);
				   			burgerPriceToQtyAL.set(i, (double) 0);
				    	}
				    }				    
					listCount --;
					lbltotal.setText(String.valueOf("Total: £" + totalPrice));
					orderList.setModel(model);

				}
			}
		});
	
		btnClear.setBounds(282, 199, 89, 23);
		frame.getContentPane().add(btnClear);
		
		
		JButton btnPrint = new JButton("Print");
		btnPrint.setBackground(new Color(0, 128, 0));
		btnPrint.setBounds(284, 163, 89, 23);
		frame.getContentPane().add(btnPrint);
		
			JButton btnAddCustomer = new JButton("Add customer");
		btnAddCustomer.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				customerAdd addObj = new customerAdd();
				addObj.setVisible(true);
			}
		});
		btnAddCustomer.setBounds(867, 411, 144, 44);
		frame.getContentPane().add(btnAddCustomer);

		
		
		
		
		//	-	-	-	-	-	-	-	-	-	-	Combo boxes
		
		JComboBox comboBoxListburger = new JComboBox(burgerNamesAL.toArray());//creates and initialises JCombo box
		comboBoxListburger.setBounds(573, 31, 135, 51);
		frame.getContentPane().add(comboBoxListburger);			
		comboBoxListburger.setSelectedIndex(0);
		
		JComboBox comboBoxBurgerToppings = new JComboBox(burgerToppingsAL.toArray());
		comboBoxBurgerToppings.setBounds(731, 31, 110, 51);
		frame.getContentPane().add(comboBoxBurgerToppings);
		comboBoxBurgerToppings.setSelectedIndex(0);
		
		JComboBox comboBoxSauces = new JComboBox(burgerSaucesAL.toArray());
		comboBoxSauces.setBounds(867, 31, 111, 51);
		frame.getContentPane().add(comboBoxSauces);
		comboBoxSauces.setSelectedIndex(0);
		
		
		

		
		comboBoxSauces.addActionListener(new ActionListener() {//combo box for sauce
			
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == comboBoxSauces) {
					JComboBox cbObj = (JComboBox)e.getSource();
					String SauceOption = (String) cbObj.getSelectedItem();
					
					for (int i = 0; i < burgerSaucesAL.size(); i++) {
						
						if (SauceOption == burgerSaucesAL.get(i)) {
							model.add(listCount, "   -   " + burgerSaucesAL.get(i));
							listCount++;
							orderList.setModel(model);
						}
					}
				}
			}
		});
		
		comboBoxBurgerToppings.addActionListener(new ActionListener() {//combo box for toppings
			
			public void actionPerformed(ActionEvent e) {
				
				if (e.getSource() == comboBoxBurgerToppings) {
					JComboBox cbObj = (JComboBox)e.getSource();
					String toppingOption = (String) cbObj.getSelectedItem();
					
					for (int i = 0; i < burgerToppingsAL.size(); i++) {
						
						if (toppingOption == burgerToppingsAL.get(i)) {
							model.add(listCount, "   -   " + burgerToppingsAL.get(i));
							listCount++;
							orderList.setModel(model);
						}
					}
				}
			}
		});
	
		comboBoxListburger.addActionListener(new ActionListener() {//combo box for food item
			public void actionPerformed(ActionEvent e) {
			//	DefaultListCellRenderer renderer = (DefaultListCellRenderer) orderList.getCellRenderer();
				//renderer.setHorizontalAlignment(SwingConstants.CENTER);
				

				if (e.getSource() == comboBoxListburger) {
					JComboBox cbObj = (JComboBox)e.getSource();
					String foodChoice = (String) cbObj.getSelectedItem();
					
					for (int i = 0; i < burgerNamesAL.size(); i++) {	//for x in range(0, burgernames.length, 1) 
						if (foodChoice == burgerNamesAL.get(i)) {
							
							if (burgerQuanityAL.get(i) >= 1) {
								burgerQuanityAL.set(i, burgerQuanityAL.get(i) + 1);				
								burgerPriceToQtyAL.set(i, burgerPricesAL.get(i) + burgerPriceToQtyAL.get(i) );
								model.setElementAt(burgerNamesAL.get(i) + "             " +  burgerQuanityAL.get(i) + "                   £" + burgerPriceToQtyAL.get(i), burgerPosAL.get(i));//updating a value in the list
							}

							else {//if qty is 0 than:
								burgerQuanityAL.set(i, burgerQuanityAL.get(i) + 1);//++ to qty
								burgerPosAL.set(i, listCount);//set the item position based on list count
								listCount++;//iterate list count so next item selected is 1 position lower
								String s = String.format("Item Qty Price", "%-15s %5s % 10s\n");
								burgerPriceToQtyAL.set(i, burgerPricesAL.get(i) + burgerPriceToQtyAL.get(i));//updates the price of the item
								model.addElement(s); //adds to Jlist
							}
							totalPrice += burgerPricesAL.get(i);
							lbltotal.setText(String.valueOf("Total: £" + totalPrice));
							orderList.setModel(model);//updates Jlist
							lblChange.setText("Change: "+ (paidNumInt - totalPrice));

						}
					}
				}
			}
		});
		
	}//initalize end
	

	public void calculator2() {
	

		for (int i = 0; i < lblCalc.length; i++) {
			btnCalc[i] = new JButton(); //creates calc buttons
			btnCalc[i].setText(lblCalc[i]);
			btnCalc[i].addActionListener(new calculator(this));
			frame.getContentPane().add(btnCalc[i]);//adds to JFrame/panel
		}
		
		btnCalc[0].setBounds(29, 354, 50, 40);//1,2,3,4,5,6,7,8,9
		btnCalc[1].setBounds(98, 354, 50, 40);
		btnCalc[2].setBounds(163, 354, 50, 40);
		
		btnCalc[3].setBounds(29, 411, 50, 40);
		btnCalc[4].setBounds(98, 411, 50, 40);
		btnCalc[5].setBounds(163, 411, 50, 40);
	
		btnCalc[6].setBounds(29, 468, 50, 40);
		btnCalc[7].setBounds(98, 468, 50, 40);
		btnCalc[8].setBounds(163, 468, 50, 40);
		
		btnCalc[9].setBounds(29, 525, 50, 40);//"."
		btnCalc[10].setBounds(98, 525, 50, 40);//0
		btnCalc[11].setBounds(163, 525, 70, 40);//delete
		
	
	}
	
	//public void fillAL() { //Fill the arraylist with data from the Json file
	//	JsonParser parser = new JsonParser();
	//	try {
	//		Object obj = parser.parse(new FileReader("my_jsonEpos.json"));
	//	}
		
		
		
	}
}	


