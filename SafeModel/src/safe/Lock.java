package safe;

/**
 *
 * @author justin
 */
public class Lock implements LockInterface{

    /*Declaration of isLocked field
     * boolean that keeps track of whether the lock is locked or not
     */
    private boolean isLocked = true;
    
    /*Simple lock method
     * sets isLocked field to true
     */
    @Override
    public void lock() {
        isLocked = true;
    }

    /*Simple unlock method
     * sets isLocked to false
     */
    @Override
    public void unlock() {
        isLocked = false;
    }
    
    /*Simple checkLocked method
     * returns field isLocked
     */
    @Override
    public boolean checkLocked(){
        return isLocked;
    }
    
    /*Simple setIsLocked method 
     * sets isLocked to given boolean value
     */
    @Override
    public void setIsLocked(boolean bool){
        isLocked = bool;
    }

}//End of class Lock
