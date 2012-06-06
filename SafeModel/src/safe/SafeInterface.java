package safe;

/**
 *
 * @author justin
 */
public interface SafeInterface {

    /*Checks to see if the given item is in the safe
     *Returns either the index if found, else returns -1
     *Could be used by the getItem() method to determine if the item you want 
     * to get is in the safe
     */
    public int checkInSafe(String item);
    
    /*Checks to see if the safe is empty using the checkEmpty() method
     * and if the object is in the safe using the checkInSafe() method
     *If checkEmpty returns false and checkInSafe() returns true, 
     * gets the object from the safe and updates the capacity and itemArray[]
     *Otherwise, display message accordingly
     */    
    public void getItem(String item);
    
    /*Checks to see if the safe is at max capacity and if the capacity 
     * exceeds the max capacity when the item is placed inside the safe
     *If the inside of the safe is not at max capacity and will not be at 
     * max capacity when the item is placed inside the safe, then add the 
     * item to the safe and update the capacity and itemArray[]
     *Otherwise, display a message accordingly
     */
    public void addItem(String item);
    
    //Sets what kind of lock the safe uses
    public void setLock(Lock lock);
    
    //adds the door to the safe
    public void addDoor(Door door);
    
}//End of SafeInterface

/*Possible Fields:
 * capacity - keeps track of the capacity of the safe
 * MAX_CAPACITY - a "final" field that holds the max capacity of the safe
 * isEmpty - boolean that keeps track of if the safe is empty
 * atMax - booleans that keeps track of if the safe is at max capacity
 * itemArray[] - either array or data structure that keeps track of the items 
 * that are in the safe
 */
