
package wordguessinggame;
import java.util.Scanner;

/**
 *
 * @author justin
 */
public class WordGuessingGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        Word word = new Word();
        String guess;
        Trail trail = new Trail(word.getWord());
        Guesses listOfGuesses = new Guesses();
        
        System.out.println("Game Started. Your word has " + word.getWord().length() + " letters. Good Luck!\n");
        
        do{
            System.out.println("Guess one character or guess the word: ");
            guess = scanner.nextLine();
            if(guess.length() == 1){
                if(listOfGuesses.addGuess(guess.charAt(0)) == 1){
                    trail.updateTrail(word.getWord(), guess);
                    trail.printTrail(word.getWord()); 
                }
            }
            else{
                trail.updateTrail(word.getWord(), guess);
                trail.printTrail(word.getWord());
            }
            
        }while(trail.isGuessed() == false);
        
        
    }
}
