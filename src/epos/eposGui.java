package epos;

import java.awt.*;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

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
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.text.html.HTMLEditorKit.Parser;
import javax.xml.bind.ValidationEvent;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.simple.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.ls.LSInput;

import com.mysql.cj.xdevapi.JsonParser;
import java.awt.Scrollbar;
import java.awt.ScrollPane;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JCheckBox;
import java.sql.*;
import javax.swing.UIManager;
import org.apache.commons.io.*;
import org.apache.commons.*;

public class eposGui {

		protected JFrame frame;
			
		ArrayList<String> burgerNamesAL = new ArrayList<String>();//Stores all names of products		

		ArrayList<String> burgerToppingsAL = new ArrayList<String>();//Stores all topping of products
		String toppings = " ";
		int topExtra = 1;

		ArrayList<String> burgerSaucesAL = new ArrayList<String>();//Stores all sauces of products
		String sauces = " ";
		int sauceExtra = 1;

		ArrayList<Integer> burgerQuanityAL = new ArrayList<Integer>();//Stores all quantity of products
		ArrayList<Double> burgerPricesAL = new ArrayList<Double>();//Stores all prices of products
		
		ArrayList<Double> burgerPriceToQtyAL = new ArrayList<Double>(); //stores the price of each item when you add multiple items
		ArrayList<Integer> burgerPosAL = new ArrayList<Integer>(); //stores the price of each item when you add multiple items
		double totalPrice;//stores the total price of items bought
	
		Object item; //to store the string after clearing item
		
		JButton btnCalc[] = new JButton[12];//Stores list of all calc buttons
		String lblCalc[] = {"1","2","3","4","5","6","7","8","9",".","0","Delete"};	
		
		JLabel lblPaid = new JLabel("Paid: £");
		JLabel lblChange = new JLabel("Change: £");

		String paidNum = "";//stores the number of the amount paid 
		double paidNumInt;//converts the string into an int - work out the change
		
		int burgerCount = 0;
		int toppingCount = 0;

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
	public eposGui() {
		//fillAL();
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
		String burgerNames[] = {"Chicken burger", "Cheese burger", "beef burger", "Ham burger", "BBQ burger", "Philly burger", "Big whooper burger"};//list of all items
		String burgerToppings[] = {"Lettuce", "Tomato", "Cucumber", "cheese", "Coleslaw", "Onion"};
		String burgerSauces[] = {"Mayo", "ketchup", "Spicey mayo", "Garlic mayo", "Mustard"};
		
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
		frame.setBounds(100, 100, 1086, 680);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
	//	JTable orderJtable = new JTable(data, columnNames);
	//	scrollPane_1.setViewportView(orderJtable);
		
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
		
		
		DefaultListModel model = new DefaultListModel();//having this outside the functions means it will recreate the values again and reput them in the list // this creates the values and basically stores the List elements
		//used to interact with the Jlist also

	   	   
	   	   //	JTABLE
		  DefaultTableModel modelNew = new DefaultTableModel();
		   modelNew.addColumn("Name");
		   modelNew.addColumn("Qty");
		   modelNew.addColumn("Price");
		   modelNew.addColumn("Toppings");
		   modelNew.addColumn("Sauces");
	       JScrollPane sp = new JScrollPane();
	       sp.setBounds(19, 36, 580, 227);
	       frame.getContentPane().add(sp);
	       
   	       JTable table = new JTable(modelNew);
   	       
	   	   DefaultTableModel modelNew2 = (DefaultTableModel) table.getModel();

   	     
   	       sp.setViewportView(table);
   	       table.setFillsViewportHeight(true);
   	       table.setRowHeight(20);
   	       
  	       TableColumnModel columnModel = table.getColumnModel();
  	     //table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); 
   	       columnModel.getColumn(1).setPreferredWidth(5);
   	       columnModel.getColumn(2).setPreferredWidth(5);
   	       

   	      
		//	-	-	-	-	-	-	-	-	-	-	JButtons
		
	

		JButton btnVoidAll = new JButton("Void all");
		btnVoidAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
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

				toppings = " ";
				
				modelNew2.setRowCount(0);
				model.clear();

			}
		});
		btnVoidAll.setBounds(500, 273, 89, 23);
		frame.getContentPane().add(btnVoidAll);
		
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBackground(Color.orange);
		
		btnClear.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
			//removes the row
				int[] selectedRows = table.getSelectedRows();
		        if (selectedRows.length > 0) {
		            for (int i = selectedRows.length - 1; 	i >= 0;	 i--) {
						item = table.getValueAt(selectedRows[i], 0).toString();
						System.out.println(item);

						modelNew2.removeRow(selectedRows[i]);
		            }
		        }
			
		        //update corresponding values
			    for (int i = 0; i < burgerNamesAL.size(); i++) {
				
					if (item == burgerNamesAL.get(i)) {
						burgerPosAL.set(i, 0);
			    		totalPrice -= (burgerPricesAL.get(i) * burgerQuanityAL.get(i));
			   		    burgerQuanityAL.set(i, 0);
			   			burgerPriceToQtyAL.set(i, (double) 0);

			    	}	
			    }   
			    
				toppings = " ";
				lbltotal.setText(String.valueOf("Total: £" + totalPrice));
				lblChange.setText("Change: £" + (paidNumInt - totalPrice));
				listCount --;
		    
			}
		});
	
		btnClear.setBounds(400, 274, 89, 23);
		frame.getContentPane().add(btnClear);
		
		
		JButton btnPrint = new JButton("Print");
		btnPrint.setBackground(new Color(0, 128, 0));
		btnPrint.setBounds(300, 274, 89, 23);
		frame.getContentPane().add(btnPrint);
		
			JButton btnAddCustomer = new JButton("Add customer");
		btnAddCustomer.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				customerAdd addObj = new customerAdd();
				addObj.setVisible(true);
			}
		});
		btnAddCustomer.setBounds(865, 466, 144, 44);
		frame.getContentPane().add(btnAddCustomer);

		
		
		
		 


		//	-	-	-	-	-	-	-	-	-	-	Combo boxes
		
		JComboBox comboBoxListburger = new JComboBox(burgerNamesAL.toArray());//creates and initialises JCombo box
		comboBoxListburger.setBounds(625, 33, 135, 51);
		frame.getContentPane().add(comboBoxListburger);			
		comboBoxListburger.setSelectedIndex(0);
		
		JComboBox comboBoxBurgerToppings = new JComboBox(burgerToppingsAL.toArray());
		comboBoxBurgerToppings.setBounds(783, 33, 110, 51);
		frame.getContentPane().add(comboBoxBurgerToppings);
		comboBoxBurgerToppings.setSelectedIndex(0);
		
		JComboBox comboBoxSauces = new JComboBox(burgerSaucesAL.toArray());
		comboBoxSauces.setBounds(919, 33, 111, 51);
		frame.getContentPane().add(comboBoxSauces);
		comboBoxSauces.setSelectedIndex(0);

		
		comboBoxSauces.addActionListener(new ActionListener() {//combo box for sauce
			
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == comboBoxSauces) {
					JComboBox cbObj = (JComboBox)e.getSource();
					String SauceOption = (String) cbObj.getSelectedItem();
					
					for (int i = 0; i < burgerSaucesAL.size(); i++) {
						
						if (SauceOption == burgerSaucesAL.get(i)) {
							
			   	       
						
			   	       		modelNew2.setValueAt(burgerSaucesAL.get(i), modelNew2.getRowCount() - 1, 4);//adds to JTable
							String newSauces = (String) table.getValueAt(modelNew2.getRowCount() - 1, 4);//Stores the item that was entered 
							sauces += newSauces + ", ";//adds every selected to a string
							sauceExtra++;//increments the count ( used for adjusting the cel size
					   	    columnModel.getColumn(3).setPreferredWidth(sauces.length() * sauceExtra);//updates the extras
							modelNew2.setValueAt(sauces, modelNew2.getRowCount() - 1, 4);//stores the new updated extra with every option
							
					
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
							
						//find a way to make this run once and not the other part
						  table.addMouseListener(new MouseAdapter() {
					   	       	public void mouseClicked(MouseEvent arg0) {
									for (int i = 0; i < burgerToppingsAL.size(); i++) {	
										if (toppingOption == burgerToppingsAL.get(i)) {
	
							   	       		String selectedCell = table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()).toString();
							   	       		System.out.println(selectedCell + "\tSelected item");
								   	       	modelNew2.setValueAt(burgerToppingsAL.get(i), table.getSelectedRow(), 3);
								   	       	
										}
									}
					   	       	}
					   	  });
							
						  
						  	toppingCount++;
				   	       	System.out.println(toppingCount);
							modelNew2.setValueAt(burgerToppingsAL.get(i), modelNew2.getRowCount() - 1, 3);//adds to JTable
							String newTop = (String) table.getValueAt(modelNew2.getRowCount() - 1, 3);//Stores the item that was entered 
							System.out.println("Selected topping: " + newTop);
							
							toppings += newTop + ", ";//adds every selected to a string
					
							topExtra++;//increments the count ( used for adjusting the cel size )
					   	    columnModel.getColumn(3).setPreferredWidth(toppings.length() * topExtra);//updates the extras
							modelNew2.setValueAt(toppings, modelNew2.getRowCount() - 1, 3);//stores the new updated extra with every option
						//	listCount++;
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
					
					for (int i = 0; i < burgerNamesAL.size(); i++) {
						if (foodChoice == burgerNamesAL.get(i)) {
							
							if (burgerQuanityAL.get(i) >= 1) {
								burgerQuanityAL.set(i, burgerQuanityAL.get(i) + 1);				
								burgerPriceToQtyAL.set(i, burgerPricesAL.get(i) + burgerPriceToQtyAL.get(i) );
								model.setElementAt(burgerNamesAL.get(i) + "             " +  burgerQuanityAL.get(i) + "                   £" + burgerPriceToQtyAL.get(i), burgerPosAL.get(i));//updating a value in the list
								modelNew2.setValueAt(burgerQuanityAL.get(i), burgerPosAL.get(i), 1);//Object, row, column		

							}

							else {//if qty is 0 than:
								burgerQuanityAL.set(i, burgerQuanityAL.get(i) + 1);//++ to qty
								burgerPosAL.set(i, listCount);//set the item position based on list count
								listCount++;//iterate list count so next item selected is 1 position lower
								burgerPriceToQtyAL.set(i, burgerPricesAL.get(i) + burgerPriceToQtyAL.get(i));//updates the price of the item
								model.addElement(burgerNamesAL.get(i) + "           " +  burgerQuanityAL.get(i) + "                   £" + burgerPriceToQtyAL.get(i)); //adds to Jlist
								modelNew2.addRow(new Object[] {burgerNamesAL.get(i), burgerQuanityAL.get(i), "£" + burgerPriceToQtyAL.get(i)} );

								burgerCount++;
					   	       	System.out.println(burgerCount);
							}
							totalPrice += burgerPricesAL.get(i);
							lbltotal.setText(String.valueOf("Total: £" + totalPrice));
							lblChange.setText("Change: "+ (paidNumInt - totalPrice));

						}
					}
				}
			}
		});
		
	
			
			
	}//initalize end
	
	public void resizeColumnWidth(JTable table) {
	    final TableColumnModel columnModel = table.getColumnModel();
	    for (int column = 0; column < table.getColumnCount(); column++) {
	        int width = 15; // Min width
	        for (int row = 0; row < table.getRowCount(); row++) {
	            TableCellRenderer renderer = table.getCellRenderer(row, column);
	            Component comp = table.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width +1 , width);
	        }
	        if(width > 300)
	            width=300;
	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
	}

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
	
	public void fillAL() { //Fill the arraylist with data from the Json file
		JSONParser parser = new JSONParser();
        try
        {
        	//the parser is able to read files
            Object obj = parser.parse(new FileReader("my_jsonEpos.json"));
            
            //convert Object to JSONObject, now it is able to read json files
            JSONObject jsonObj = (JSONObject) obj;
            
            //Reading the String	-	gets individual values stored
     //       String name = (String) jsonObj.get("burgerNames");
            
            //Reading the array
            JSONArray burger = (JSONArray)jsonObj.get("burger");
            JSONArray burgerToppings = (JSONArray)jsonObj.get("burgerToppings");
            JSONArray burgerSauces = (JSONArray)jsonObj.get("burgerSauces");

            
            //Printing all the values
     //       System.out.println("Name: " + name);
            	System.out.println(burger);
        
           for (Object b : burger)       	{
            	burgerNamesAL.add(b.toString());
            	System.out.println(b);
           }
       //     for (Object burgerPrice : burger)       	
        //    	burgerPricesAL.add(burgerPrice.toString());
            	
            for (Object topping : burgerToppings)      	
                 burgerToppingsAL.add(topping.toString());
                 
            for (Object sauce : burgerSauces)      	
                 burgerSaucesAL.add(sauce.toString());
   
        }
        catch (FileNotFoundException fe){ fe.printStackTrace();}
        catch(Exception e) { e.printStackTrace();}
	}
}	


