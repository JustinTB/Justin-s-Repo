/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package safe;

/**
 *
 * @author justin
 */
public interface Door {
    
    /*Opens the door (if closed and unlocked)
     *Sets isClosed to false
     */
    public void open();
    
    /*Closes the door (if open)
     *Sets isClosed to true
     */
    public void close();    
  
    /*Checks to see if the door is closed
     *Returns either true or false depending on if the door is closed or open
     *Could be used when trying to add or get an item from the safe, 
     * or when trying to open/close the door or lock/unlock the lock
     */
    public boolean checkClosed();
    
}

/*Possible Fields:
 * isClosed - boolean that keeps track of if the door is closed or not
 */
