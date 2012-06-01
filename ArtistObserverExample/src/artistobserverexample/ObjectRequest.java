/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package artistobserverexample;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author justin
 */
public class ObjectRequest {
    private String title;
    private Set<ObjectListener> listeners;
    
    public ObjectRequest(String title){
        this.title = title;
        listeners = new HashSet<ObjectListener>();    
    }
    
    public String getTitle(){
        return title;
    }
    
    public void addObjectListener(ObjectListener listener){
        listeners.add(listener);
    }
    
    public void removeObjectListener(ObjectListener listener){
        listeners.remove(listener);
    }
    
    public void fireCrayonEvent(){
        System.out.println("The artist is asked to draw a crayon." );
        ObjectEvent e = new ObjectEvent(this);
        for(ObjectListener listener: listeners){
            listener.crayon(e);
        }
    }
    
    public void fireKirbyEvent(){
        System.out.println("The artist is asked to draw Kirby." );
        ObjectEvent e = new ObjectEvent(this);
        for(ObjectListener listener: listeners){
            listener.kirby(e);
        }
    }
    
    public void fireSwordEvent(){
        System.out.println("The artist is asked to draw a sword." );
        ObjectEvent e = new ObjectEvent(this);
        for(ObjectListener listener: listeners){
            listener.sword(e);
        }
    }
    
    public void draw(){
         if(title.toLowerCase().equals("kirby")){
            fireKirbyEvent();
        }
        else if(title.toLowerCase().equals("crayon")){
            fireCrayonEvent();
        }
        else{
            fireSwordEvent();
        }
    }
}
