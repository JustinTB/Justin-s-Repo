
package wordguessinggame;

/**
 *
 * @author justin
 */
public class Trail {
    
    private boolean[] boolTrail;
    private int trueCount = 0;
    private int trailNum = 0;
    
    public Trail(String word){
        boolTrail = new boolean[word.length()];
        for(int i = 0; i < boolTrail.length; i++){
            boolTrail[i] = false;
        }
    }//End of constructor
    
    public void updateTrail(String word, String guess){
        
        if(guess.length() == 1){
            for(int i = 0; i < word.length(); i++){
                if(word.charAt(i) == guess.charAt(0)){
                    boolTrail[i] = true;
                    trueCount++;
                }
            }
        }//End of if
        else if(word.equals(guess)){
                    for(int i = 0; i < boolTrail.length; i++){
                        boolTrail[i] = true;                 
                    } 
                    trueCount = boolTrail.length;
        }//End of else if
    }//End of updateTrail
    
    public boolean isGuessed(){
        if(trueCount == boolTrail.length){
            return true;
        }
        
        return false;
    }//End of isGuessed()
    
    public void printTrail(String word){
        if(isGuessed() == false){
            System.out.print("Trail " + ++trailNum + ": ");
            for(int i = 0; i < boolTrail.length; i++){
               if(boolTrail[i] == false){
                    System.out.print("*");       
                }
                else{
                    System.out.print(word.charAt(i));
               }
            }
            System.out.print("\n");
        }
        else{
            System.out.println("\nCONGRATULATIONS!!!! YOU GUESSED THE WORD!!!!");
            System.out.println("The word was " + word + ".");
        }
    }//End of printTrail
}//End of class Trail
