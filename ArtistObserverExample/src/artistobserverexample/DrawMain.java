/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package artistobserverexample;

/**
 *
 * @author justin
 */
public class DrawMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        ObjectRequest object1 = new ObjectRequest("Kirby");
        ObjectRequest object2 = new ObjectRequest("sword");
        ObjectRequest object3 = new ObjectRequest("craYon");
        
        Artist Pablo = new Artist();
        
        object1.addObjectListener(Pablo);
        object2.addObjectListener(Pablo);
        object3.addObjectListener(Pablo);
        
        object1.draw();
        object2.draw();
        object3.draw();
        
        
    }
}
