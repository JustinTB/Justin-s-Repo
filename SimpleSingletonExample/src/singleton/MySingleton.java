/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package singleton;

/**
 *
 * @author justin
 */
public class MySingleton {
    
    private static MySingleton unique;
    
    public static String name;
    
    private MySingleton(String name){
        this.name = name;
    }
    
    public static synchronized MySingleton getSingleton(){
        if(unique == null){
            unique = new MySingleton("Bob");
        }
        return unique;
    }
    
    public Object clone() throws CloneNotSupportedException{
        throw new CloneNotSupportedException();
    }
}
