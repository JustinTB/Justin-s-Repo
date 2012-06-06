/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package safe;

import javax.swing.JOptionPane;

/**
 *
 * @author justin
 */
public class PasswordLock extends Lock{
    private String password;
    private String attempt;
    
    //Constructors: 
    public PasswordLock(){
        this("0000");
    }
    
    public PasswordLock(String password){
        this.password = password;
    }
    
    
    /*Overridden unlock() method
     * takes in an attempt at the password
     */
    @Override
    public void unlock(){
       attempt = JOptionPane.showInputDialog(null, "Keypad Password?");
       if(attempt == null){
           return;
       }
       
       //While loop to loop over password attempts
       while(!attempt.equals(password)){
           attempt = JOptionPane.showInputDialog(null, "Wrong password. \nTry Again or press cancel.");
           if(attempt == null){
               return;
           }
       }//End of while loop
       
       if(attempt.equals(password)){
           setIsLocked(false);
       }
    }//End of unlock() method
    
}//End of class PasswordLock
