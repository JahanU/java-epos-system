import java.util.*;
public class cesarCipher {
	/***
	 * @Jahan Ulhaque 
	 * ID:	201272455 
	 * BreakCeasar
	 * 
	 ***/
	
	public static void main(String[] args) {
		
		 try {
			 String s = "a";
				decryptString(s);
		 }
		 catch (ArrayIndexOutOfBoundsException arrayError) {
				System.out.println("Oops, you have not given enough parameters!\n Usage: java BreakCaesar \"string\"");
		 }
		 catch (Exception e) {
			 System.out.println("Invalid input" + e);
		 }
	}
	
	public static void decryptString(String getEncryptedMessage) {
			
				String[] shiftedCiphers = new String[26];//stores every cipher message per shift
				int[] getLetterFreq = new int[26];//stores the letter freq per shift
				double[] chiArray = new double [26];//stores the chi score value per shift
				
				for (int shifts = 0; shifts < 26; shifts++ ) {
					String decryptedMessage = " ";


					for(int i = 0; i < getEncryptedMessage.length(); i++){
						char ch = getEncryptedMessage.charAt(i);
						
						if (ch == ' ') { //if there is a space in the encrypted message then it will add a space between words
							decryptedMessage += ' ';
						}
						
						if (ch >= 'a' && ch <= 'z') {
							ch =  (char) (ch - shifts);
							if (ch < 'a') {
							ch = (char) (ch + 26);
							}
					    decryptedMessage += ch;
					    getLetterFreq[getEncryptedMessage.charAt(i)-'a']++; //stores the letter in its allocated array position
					    
					    System.out.println(getEncryptedMessage.charAt(i)-'a');
						System.out.println(Arrays.toString(getLetterFreq));

						}
					
					
						 if	(ch >= 'A' && ch <= 'Z') {
							ch = (char)(ch - shifts);
							if (ch < 'A') {
								ch = (char) (ch + 26);
							}
						    decryptedMessage += ch;
						    getLetterFreq[getEncryptedMessage.charAt(i)-'A']++; 
						}
					}//end loop for string length

					shiftedCiphers[shifts] = decryptedMessage;//stores each shifted string into array

				}//end loop for 26 times
				
				System.out.println(Arrays.toString(getLetterFreq));
				loopChi(chiArray, shiftedCiphers, getLetterFreq);
	}
	
	public static void loopChi(double[] chiArray, String[] shiftedCiphers, int[] getLetterFreq) {
		
		
		int minChi = 0;
		for (int shiftNum = 0; shiftNum < 26; shiftNum ++ ) {
			chiArray[shiftNum] = calcChi(shiftedCiphers[0], getLetterFreq, shiftNum);//each shifted cipher score is stored
		}
		minChi = findMinIndex(chiArray);//stores the lowest score

		System.out.println(shiftedCiphers[minChi]);//prints the shifted cipher with the lowest chi value.
	}
	
	
	static int findMinIndex(double[] array) {//returns smallest array value, used to find the lowest chi value
		int minValue = 0;
		double minIndex = array[0];
		int i = 0;
		
		while (i < 26) {
			if (array[i] < minIndex) {
				minIndex = array[i];
				minValue = i;
			}	
			i++;
		}	
		return minValue;
	}
	
	  public static double calcChi(String newDecryptedMessage, int letterFreq[], int shift) {
		  
		 double[] knownFreq = {0.0855, 0.0160, 0.0316, 0.0387, 0.1210,
				0.0218, 0.0209, 0.0496, 0.0733, 0.0022,
				0.0081, 0.0421, 0.0253, 0.0717, 0.0747,
				0.0207, 0.0010, 0.0633, 0.0673, 0.0894,
				0.0268, 0.0106, 0.0183, 0.0019, 0.0172,
				0.0011
		};
		  double total = 0;
		  
				  for (int a = 0; a < 26; a++ ) {//loops through known freq, and every letter in alphabet
					  int indexValue = (a + shift) % 26;//used to loop through the letter freq and an element value between 0 - 25
					  total += Math.pow(((letterFreq[indexValue]/newDecryptedMessage.length()) - knownFreq[a]), 2.0) / (knownFreq[a]);
				  }
			
			return total;
	  }
	  
	  
}

	

	
	

