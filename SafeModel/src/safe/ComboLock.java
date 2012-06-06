package safe;

/**
 *
 * @author justin
 */
import javax.swing.JOptionPane;

public class ComboLock extends Lock {
    
    /*Variables to represent the three spins of a combo lock 
     * each holding an integer representation of one of the three numbers
     * in the combination.
     */
    private int spin1, spin2, spin3;
    
    //Constructors:
    public ComboLock(){
        this(0, 0, 0);
    }
    
    public ComboLock(int spin1){
        this(spin1, 0, 0);
    }
    
    public ComboLock(int spin1, int spin2){
        this(spin1, spin2, 0);
    }
    
    public ComboLock(int spin1, int spin2, int spin3){
        this.spin1 = spin1;
        this.spin2 = spin2;
        this.spin3 = spin3;
    }

    /*Overridden unlock method for the comboLock
     * allows attempts at unlocking the combo lock 
     * by asking for three numbers representing
     * the three numbers of the combination
     */
    @Override
    public void unlock(){
        String num1, num2, num3;
         
        num1 = JOptionPane.showInputDialog(null, "What is the first number of the combination?");
            if(num1 == null){
                return;
            }
        num2 = JOptionPane.showInputDialog(null, "What is the second number of the combination?");
            if(num2 == null){
                return;
            }
        num3 = JOptionPane.showInputDialog(null, "What is the third number of the combination?");
            if(num3 == null){
                return;
            }
        
        //While loop to go though the attempts at the combination, pressing cancel cancels the loop    
        while(Integer.parseInt(num1) != spin1 || Integer.parseInt(num2) != spin2 || Integer.parseInt(num3) != spin3){
            
            JOptionPane.showMessageDialog(null, "The combo you tried is incorrect. Try again. At any time press cancel if you are done trying to unlock the combo lock.");
            
            num1 = JOptionPane.showInputDialog(null, "What is the first number of the combination?");
            if(num1 == null){
                return;
            }
            num2 = JOptionPane.showInputDialog(null, "What is the second number of the combination?");
            if(num2 == null){
                return;
            }
            num3 = JOptionPane.showInputDialog(null, "What is the third number of the combination?");
            if(num3 == null){
                return;
            }    
        }//End of while loop
        
         if(Integer.parseInt(num1) == spin1 && Integer.parseInt(num2) == spin2 && Integer.parseInt(num3) == spin3){
            setIsLocked(false);
         }
        
        
        
    }//End of method unlock()

}//End of class
