/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package guiexample;

import javax.swing.JFrame;

class MainCalculator {
    public static void main(String[] args){
        GuiCalculator go = new GuiCalculator();
        go.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        go.setSize(400,400);
        go.setVisible(true);
    }
}
