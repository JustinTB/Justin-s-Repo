/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package artistobserverexample;

/**
 *
 * @author justin
 */
public class Artist implements ObjectListener{
    public void crayon(ObjectEvent e){
        System.out.println(e.getSource().getTitle() + ": (CRAYOLA)]>");
    }
    public void kirby(ObjectEvent e){
        System.out.println(e.getSource().getTitle() + ": <(^-^<)");
    }
    public void sword(ObjectEvent e){
        System.out.println(e.getSource().getTitle() + ": []xxxx[]///////////>");
    }
}
