/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package safe;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 *
 * @author justin
 */

//*****************************NOT USED FOR NOW*****************************//

public class KeyPadGUI extends JFrame{
   
   private JButton button1;
   private JButton button2;
   private JButton button3;
   private JButton button4;
   private JButton button5;
   private JButton button6;
   private JButton button7;
   private JButton button8;
   private JButton button9;
   private JButton button0;
   private JButton enter;
   
   public String password;
   public String attempt = "";
   private String num;
   public boolean first = true;
   String concat;
  
    
    public KeyPadGUI(String password){
        
        super("Keypad");
        this.password = password;
         
       setLayout(new FlowLayout());
        
       button1 = new JButton("1");
       add(button1);
       button2 = new JButton("2");
       add(button2);
       button3 = new JButton("3");
       add(button3); 
        button4 = new JButton("4");
       add(button4);
        button5 = new JButton("5");
       add(button5);
        button6 = new JButton("6");
       add(button6);
        button7 = new JButton("7");
       add(button7);
        button8 = new JButton("8");
       add(button8);
        button9 = new JButton("9");
       add(button9);
        button0 = new JButton("0");
       add(button0);
       enter = new JButton("ENTER");
       add(enter);
       
       HandleNums handler1 = new HandleNums();
        button1.addActionListener(handler1);
        button2.addActionListener(handler1);
        button3.addActionListener(handler1);
        button4.addActionListener(handler1);
        button5.addActionListener(handler1);
        button6.addActionListener(handler1);
        button7.addActionListener(handler1);
        button8.addActionListener(handler1);
        button9.addActionListener(handler1);
        button0.addActionListener(handler1);
        
       HandleEnter handler2 = new HandleEnter();
       enter.addActionListener(handler2);
    }
    
    private class HandleNums implements ActionListener{
        @Override
            public void actionPerformed(ActionEvent event){
                num = event.getActionCommand();
                if(first == true){
                    attempt = num;
                    first = false;
                }
                else{
                concat = attempt.concat(num);
               
                }
                 System.out.println("attempt: " + attempt);

            }
        }
    
     private class HandleEnter implements ActionListener{
        @Override
            public void actionPerformed(ActionEvent event){
                 attempt = concat;
                 if(attempt.equals(password)){
                       JOptionPane.showMessageDialog(null, "Lock unlocked!");
                 }
            }
        }
     public String getAttempt(){
         return attempt;
     }

}
