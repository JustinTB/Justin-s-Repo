package safe;

/**
 *
 * @author justin
 */
public interface LockInterface {
    
    /*Locks the lock(if door is closed)
     *Update isLocked for the lock
     */
    public void lock();
    
    /*Given three numbers for the three spins of the lock, attempts 
     * to unlock the lock (if locked)
     *If the three spins are correct, unlocks the lock and updates 
     * isLocked for the lock 
     *Otherways, display a message accordingly and maybe ask for three new numbers
     */
    public void unlock();
    
    /*Checks to see if the lock is locked
     *Returns either true or false depending on if the lock is locked or not
     *Could be used by the Door or Lock for opening/closing/locking/unlocking
     */
    public boolean checkLocked();
    
    /*Sets variable isLocked to given boolean value
     */
    public void setIsLocked(boolean bool);
}//End of LockInterface

/*Possible Fields:
 *  SPIN1 - "final" number representing the first number 
 *      for the first spin in the combination
 *  SPIN2 - "final" number representing the second number 
 *      for the second spin in the combination
 *  SPIN3 - "final" number representing the third number 
 *      for the third spin in the combination
 *  isLocked - boolean that keeps track of if the lock is locked or not
 */