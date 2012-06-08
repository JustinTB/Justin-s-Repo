
package wordguessinggame;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;
/**
 *
 * @author justin
 */
public class Word {
    
    private String word;
    private String[] listOfWords = new String[851];
    Random generator = new Random();
    String urltext = "words.txt";
    {
    try{
        URL url = Word.class.getResource(urltext);
        FileInputStream fstream = new FileInputStream(url.getPath());
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        for(int i = 0; i < 851; i++){
            listOfWords[i] = br.readLine();
        }
        in.close();
    
    }catch(Exception e){
    System.err.println("Error: " + e.getMessage());
    }
    }

    
    
    public Word(){
        word = listOfWords[generator.nextInt(850)];
    }
    
    public String getWord(){
        return word;
    }
    
}
