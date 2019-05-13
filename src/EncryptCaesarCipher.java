import java.util.Scanner;

/**
 * 
 * @author Jahan Ulhaque, August 2018 
 * Caesar cipher encrypt
 * 
 */

public class EncryptCaesarCipher {
	   
   public static void main (String args[]) {

	   Scanner input = new Scanner(System.in);
	   
	   System.out.println("Enter a message to encyrpt");
	   String inputMessage = input.nextLine();
	   if (inputMessage.length() < 2) {  
         System.out.println("\nOops, you haven't given enough parameters!");
         System.out.println("   Usage:  java Encrypt \"string\" shift\n");
         System.exit(1);
	   }
	   // User enters message to encrypt

	   
	   System.out.println("Enter the amount of shift");
	   int inputShift = input.nextInt();
	   if (inputShift > 26 || inputShift < 0) {
		   System.out.println("Please enter shfit between 1 - 26");
	   }
	   // User enters shift to encrypt the message by

	   
	   System.out.println("The message you want to encrypt is " + "\"" + inputMessage + "\"" + " And the shift is" + " \"" + inputShift + "\"");

	   
      /*** Encrypt message.
       *   
       */
      String encryptedText = encryptMessage(inputMessage, inputShift);
      System.out.println("The new encrypted caesar messsage is: " + encryptedText);
          
   }  /*  End of "main" method  */     


   public static String encryptMessage(String inputMessage, int inputShift)  {
      
	  String message = "";
      char c;
      char newChar;

      //  Make the shift a positive number
      // so that it works with "module" better
      while (inputShift < 0)  {
    	  inputShift += 26;
      }

      //  loop through the message, getting each character
      for (int i = 0; i < inputMessage.length(); i++)  {
         c = inputMessage.charAt(i);
         
         //  Is it lowercase?  (encrypt it)
         if ( ((c - 'a') >= 0) && ((c - 'a') <= 25) )  {
            newChar = (char) ((((c - 'a') + inputShift) % 26) + 'a');
         }
         
         //  Or uppercase?  (encrypt it) 
         else if ( ((c - 'A') >= 0) && ((c - 'A') <= 25) )  {
            newChar = (char) ((((c - 'A') + inputShift) % 26) + 'A');
         } 
         
         //  Otherwise stores the char.
         else  
           newChar = c;

         message = message + newChar;
      }
      return message;
   }  //  End of "message" method

}

