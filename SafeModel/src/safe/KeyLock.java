package safe;

import javax.swing.JOptionPane;

public class KeyLock extends Lock{

    /*Declaration of String field key that
     * holds the design of the key as a string
     */
    private String key;
    
    //Constructors
    public KeyLock(){
        this("(o)^^^^>"); //default key design
    }
    
    public KeyLock(String key){
        this.key = key;
    }
    
    /*Overridden unlock method for the KeyLock
     * prompts to "insert key" which for now asks for a String input
     * representing the key.
     */
    @Override
    public void unlock(){
        
        String keyDesign = JOptionPane.showInputDialog(null, "Insert Key.");
        
        //while loop that goes through attempts at different keys
        while(!keyDesign.equals(key)){
            keyDesign = JOptionPane.showInputDialog(null, "Wrong key. Try another. \nAt any time press cancel if you are done trying to unlock the key lock.");
            if(keyDesign == null){
                return;
            }
        }//End of while loop
        
        if(keyDesign.equals(key)){
            setIsLocked(false);
        }
        
    }//End of method unlock()
}//End of class KeyLock
