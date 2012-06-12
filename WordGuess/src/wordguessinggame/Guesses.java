/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 package wordguessinggame;

/**
 *
 * @author justin
 */
public class Guesses {
    
    private char[] guesses = new char[26];

    public Guesses(){
        
    }
    
    public int addGuess(char guess){
        int index = (int)guess - 97;
        
        if(guesses[index] != guess){
            guesses[index] = guess;
            return 1;
        }
        else{
            System.out.println("You've already guessed that letter! Guess again!");
            return 0;
        }
    }
}
