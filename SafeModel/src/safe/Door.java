package safe;

/**
 *
 * @author justin
 */
public class Door implements DoorInterface{

    //Declariation of boolean field isClosed
    private boolean isClosed = true;
    
    /*Simple method to open the door
     * sets boolean variable isClosed to false
     */
    @Override
    public void open() {
        isClosed = false;
    }

    /*Simple method to close the door
     * sets boolean field isClosed to true
     */
    @Override
    public void close() {
        isClosed = true;
    }

    /*Simple method to return true/false if the door is open or closed
     * return isClosed field
     */
    @Override
    public boolean checkClosed() {
        return isClosed;
    }
    
}//End of class Door
