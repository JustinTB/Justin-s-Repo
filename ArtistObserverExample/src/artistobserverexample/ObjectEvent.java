
package artistobserverexample;

/**
 *
 * @author justin
 */
public class ObjectEvent {
    private ObjectRequest source;
    
    public ObjectEvent(ObjectRequest source){
        this.source = source;
    }
    
    public ObjectRequest getSource(){
        return source;
    }
}
