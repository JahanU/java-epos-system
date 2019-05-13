package epos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

class calculator implements ActionListener {
	
	  private eposGui epos;

	    public calculator(eposGui epos) {
	        this.epos = epos;
	}
	  
	int decCount = 0;
	
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < epos.lblCalc.length; i++) {
			epos.lblChange.setText("Change: "+ (epos.paidNumInt - epos.totalPrice));

	
			if (e.getSource() == epos.btnCalc[i]) {
				
				if (e.getSource() == epos.btnCalc[11]) {//delete button
					epos.paidNum = "";//clear the string
					epos.paidNumInt = 0;//resets the int
					epos.lblPaid.setText("Paid: £");//resets the Paid & Change Jlabel
					epos.lblChange.setText("Change: £");
				}
				
				else {
					epos.paidNum += epos.btnCalc[i].getText();//gets the text of the button pressed	
					epos.lblPaid.setText("Paid: £" + epos.paidNum);
					
					epos.paidNumInt = Double.parseDouble(epos.paidNum);//converts the string into an int - to work out change
					epos.lblChange.setText("Change: £" + (epos.paidNumInt - epos.totalPrice));

				}
			}
		}		
	}
	
	public void removeDecimal() {//removes the decimal from the String number // allows to convert to int easily
		for (int i = 0; i < epos.paidNum.length(); i++) {
			if (epos.paidNum.contains(".")) {
				epos.paidNum.replace(".", "");
				decCount++;//records the amount of times a decimal has been found
			}
		}
		if (decCount == 1) 
			epos.paidNumInt /= 10;
		else if (decCount == 2) 
			epos.paidNumInt /= 100;

	}
	
	
}
	
