
package guiexample;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
//import javax.swing.ImageIcon;
//import javax.swing.Icon;


public class GuiCalculator extends JFrame {

//Declare JButton and bool variables and SimpleCalculator object
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
   private JButton addButton;
   private JButton subButton;
   private JButton equalButton;
   
   private boolean first = true;
   
   SimpleCalculator calculator = new SimpleCalculator();
   
   //construct the Gui
   public GuiCalculator(){
       //set title and layout
       super("Calculator");
       setLayout(new FlowLayout());
     
       //Add the buttons to the gui
       
       //Icon one = new ImageIcon(getClass().getResource("1.jpg"));
       button1 = new JButton("1");
       add(button1);
       //Icon two = new ImageIcon(getClass().getResource("2.jpg"));
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
       addButton = new JButton("+");
       add(addButton);
       subButton = new JButton("-");
       add(subButton);
       equalButton = new JButton("=");
       add(equalButton);
        
       //Handle for the numbers
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
        
        //Handle for addition and subtraction buttons
        HandleMath handler2 = new HandleMath();
        addButton.addActionListener(handler2);
        subButton.addActionListener(handler2);
        
        //Handle for equals sign
        HandleEqual handler3 = new HandleEqual();
        equalButton.addActionListener(handler3);
}
        //Listener for numbers
        private class HandleNums implements ActionListener{
        @Override
            public void actionPerformed(ActionEvent event){
                //JOptionPane.showMessageDialog(null,String.format("%s",event.getActionCommand()));
                if(first == true){
                    calculator.fnum = Integer.parseInt(event.getActionCommand());
                    first = false;
                }
                else{
                    calculator.snum = Integer.parseInt(event.getActionCommand());
                    //JOptionPane.showMessageDialog(null, calculator.fnum + calculator.snum);
                    first = true;
                }
                
            }
        }
        
        //Listener for addition and subtraction 
        private class HandleMath implements ActionListener{
        @Override
            public void actionPerformed(ActionEvent event2){
                calculator.math = event2.getActionCommand();
            }
        }
        
        //Listener for equals
        private class HandleEqual implements ActionListener{
        @Override
            public void actionPerformed(ActionEvent event3){
                JOptionPane.showMessageDialog(null,String.format("%d %s %d = %d", calculator.fnum, calculator.math, calculator.snum, calculator.equal()));
            }
        }
}
