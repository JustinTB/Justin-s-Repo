package safe;

/**
 *
 * @author justin
 */
public interface SafeItem {
    
    /*Sets the size of the safe item
     * updatas the value of size
     */
    public void setSize(int size);
    
    /*Gets the size of the safe item
     *Returns size
     *Here I made size an integer as an example, but could be a different type
     */
    public int getSize();
    
    /*Sets the name of the safe item
     *Update name
     */
    public void setName(String name);
    
    /*Gets the name of the safe item
     *Returns name
     */
    public String getName();
}

/*Possible Fields:
 * size - holds the size of an item, can be used to see if it will fit in the safe
 * inSafe - boolean that keeps track of if the given item is in the safe
 * name - name of the item
 */