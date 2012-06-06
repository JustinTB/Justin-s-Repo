package safe;

/**
 *
 * @author justin
 */
public class Safe implements SafeInterface {

    //Declaration of Safe fields
    private boolean isEmpty = true;//boolean that keeps track of if the safe is empty
    private boolean isMax = false;//boolean that keeps track of if the safe is at max capacity
    private int maxCapacity;//For now: max number of items
    public int numItems = 0;//keeps track of the number of items in the safe
    public String[] items;//simple array that holds the items as strings
    public Lock lock;
    public Door door;
    
    //Constructors
    public Safe(){
        this(5);//If no max capacity is given, set default to 5
    }
    public Safe(int maxCapacity){
        this.maxCapacity = maxCapacity;
        items = new String[maxCapacity];
    }
    
    @Override
    public void setLock(Lock lock){
        this.lock = lock;
    }
    
    @Override
    public void addDoor(Door door){
        this.door = door;
    }
    
    /*checkInSafe method
     * returns the index of the array where the item is found
     * or returns -1 if the item is not found
     */
    @Override
    public int checkInSafe(String item) {
   
        for(int i = 0; i < maxCapacity; i++){
            if(item.equals(items[i])){
                return i;
            }
        }
        return -1;
    }

    /*getItem method
     * checks to see if the safe is empty
     * then checks to see if the item is in the safe 
     * if it is, removes the item String from the array 
     * and updates numItems and isEmpty fields
     */
    @Override
    public void getItem(String item) {
    
        if(isEmpty == false){
            if(checkInSafe(item) != -1){
                items[checkInSafe(item)] = null;
                System.out.println("Your item " + item + " has been removed from the safe.");
                numItems--;
                isMax = false;
                if(numItems == 0){
                    isEmpty = true;
                }
            }
            else{
                System.out.println("Your item is not in the safe.");
            }
        }
        else{
            System.out.println("No items in the safe. Your item cannot be removed.");
        }
    }//End of method getItem

    /*addItem method
     * checks to see if the safe is at max capacity
     * adds the item as a String to the array if not in the first open index
     * updates numItems and isMax
     */
    @Override
    public void addItem(String item) {
        if(isMax == false){
            for(int i = 0; i < maxCapacity; i++){
                if(items[i] == null){
                    items[i] = item;
                    System.out.println("Your item " + item + " has been added to the safe.");
                    isEmpty = false;
                    if(++numItems == 5){
                        isMax = true;
                    }
                    break;
                }
            }
        }
        else{
            System.out.println("The safe is at max capacity. Cannot add an item");
        }
    }//End of method addItem
    
}//End of class Safe
