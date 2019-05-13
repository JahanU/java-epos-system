package epos;
import java.sql.*;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import com.mysql.cj.xdevapi.Statement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.sql.*;

public class customerAdd extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					customerAdd frame = new customerAdd();
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
	public customerAdd()  {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		

		String title = "Add Customer";
		Border border = BorderFactory.createTitledBorder(title);
		contentPane.setBorder(border);
		
		// - TextFields (to enter customer details - //
		JTextField textFieldfirstName = new JTextField();
		textFieldfirstName.setBounds(198, 36, 86, 20);
		textFieldfirstName.setColumns(10);
		contentPane.add(textFieldfirstName);
				
				
		JTextField textFieldSecondName = new JTextField();
		textFieldSecondName.setBounds(198, 67, 86, 20);
		textFieldSecondName.setColumns(10);
		contentPane.add(textFieldSecondName);
				
		JTextField textFieldPhoneNumber = new JTextField();
		textFieldPhoneNumber.setBounds(198, 159, 86, 20);
		textFieldPhoneNumber.setColumns(10);
		contentPane.add(textFieldPhoneNumber);
				
		JTextField textFieldAge = new JTextField();
		textFieldAge.setBounds(198, 98, 86, 20);
		textFieldAge.setColumns(10);
		contentPane.add(textFieldAge);
		
		JTextField textFieldEmailAddress = new JTextField();
		textFieldEmailAddress.setBounds(198, 129, 86, 20);
		textFieldEmailAddress.setColumns(10);
		contentPane.add(textFieldEmailAddress);
		

		// - Jlabels - //
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(110, 39, 81, 14);
		contentPane.add(lblFirstName);
		
		JLabel lblSecondName = new JLabel("Second Name");
		lblSecondName.setBounds(110, 70, 93, 14);
		contentPane.add(lblSecondName);
		
		JLabel lblAge = new JLabel("Age");
		lblAge.setBounds(109, 98, 75, 14);
		contentPane.add(lblAge);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(110, 132, 81, 14);
		contentPane.add(lblEmail);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setBounds(110, 162, 93, 14);
		contentPane.add(lblPhoneNumber);

		// - Jbuttons - // 

		JButton btnAddCustomer = new JButton("add");
		btnAddCustomer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				String fn = textFieldfirstName.getText();
				String ln = textFieldSecondName.getText();
				String ageP = textFieldAge.getText();
				String email = textFieldEmailAddress.getText();
				String pn = textFieldPhoneNumber.getText();
				
				try {
					
					Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/eposDB ?useSSL=false&serverTimezone=UTC", "root", "1234");

					java.sql.Statement myStmt = myConn.createStatement();
					
					String query = "INSERT INTO eposDB.customer (firstName, secondName, age, email, phoneNumber)" 
							+ "values ('fn', 'ln', 'ageP', 'email', 'pn')";
					
					
					myStmt.executeUpdate(query);
					
					JOptionPane.showMessageDialog(null, "Sucessefully added");
					setVisible(false);
				}
			
				
				catch (Exception e) {
					System.out.println(e);
					//e.printStackTrace();
				}
			}
		});
		btnAddCustomer.setBounds(198, 204, 86, 35);
		contentPane.add(btnAddCustomer);
	}
}
