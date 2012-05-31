/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package guiexample;

/**
 *
 * @author justin
 */
public class SimpleCalculator {
    
    public int fnum;
    public int snum;
    public String math;
    
    public SimpleCalculator(){
        this(0,0);
    }
    
    public SimpleCalculator(int fnum){
        this(fnum, 0);
    }
    public SimpleCalculator(int fnum, int snum){
        this.fnum = fnum;
        this.snum = snum;
    }
    public int equal(){
      
        if(math.equals("+")){
            return fnum + snum;
        }
        else{
            return fnum - snum;
        }
    }
    
    
}
