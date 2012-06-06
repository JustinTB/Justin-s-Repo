/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package safe;

import java.util.Scanner;
/**
 *
 * @author justin
 */
public class SafePasswordMainExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Declare new safe, new key pad lock, new door
        Safe mySafe = new Safe();
        PasswordLock myLock = new PasswordLock("1234");
        Door myDoor = new Door();
        
        //Set the lock and door to the new safe
        mySafe.setLock(myLock);
        mySafe.addDoor(myDoor);
   
        //Declaration of choice and itemName bariables
        int choice;
        String itemName;
        
        //Declaration of scanner variables
        Scanner scanner = new Scanner(System.in);
        Scanner scanner2 = new Scanner(System.in);
        
        //Print the menu instructions
        printInstructions();
        
        //do while loop to control menu options
        do{
            
            System.out.println("?");
            choice = scanner.nextInt();
            
            //Switch to go through cases of choice variable
            switch(choice){
                case 1:
                    if(mySafe.lock.checkLocked()){
                        System.out.println("Your safe is locked. Try unlocking it.");
                    }
                    else if(mySafe.door.checkClosed()){
                        System.out.println("Your safe is unlocked, but the door is closed. Try opening it");
                    }
                    else{
                        System.out.println("What item would you like to add?");
                        itemName = scanner2.nextLine();
                        mySafe.addItem(itemName);
                    }
                    break;
                case 2:
                    if(mySafe.lock.checkLocked()){
                        System.out.println("Your safe is locked. Try unlocking it.");
                    }
                    else if(mySafe.door.checkClosed()){
                        System.out.println("Your safe is unlocked, but the door is closed. Try opening it");
                    }
                    else if(mySafe.numItems != 0){
                        System.out.println("What item would you like to remove?");
                        itemName = scanner2.nextLine();
                        mySafe.getItem(itemName);
                    }
                    break;
                case 3:
                    if(mySafe.lock.checkLocked()){
                        System.out.println("Your safe is already locked.");
                    }
                    else if(!mySafe.door.checkClosed()){
                        System.out.println("Your safe door is open. Try closing it.");
                    }
                    else{
                        mySafe.lock.lock();
                        System.out.println("Your safe has been locked.");
                    }
                    break;
                case 4:
                    if(!mySafe.lock.checkLocked()){
                        System.out.println("Your safe is already unlocked.");
                    }
                    else{
                        mySafe.lock.unlock();
                        if(!mySafe.lock.checkLocked()){
                            System.out.println("Your safe has been unlocked.");
                        }
                    }
                    break;
                case 5:
                    if(mySafe.lock.checkLocked()){
                        System.out.println("Your safe is locked. Try unlocking it.");
                    }
                    else if(!mySafe.door.checkClosed()){
                        System.out.println("Your safe door is already open.");
                    }
                    else{
                       mySafe.door.open();
                       System.out.println("Your safe door has been opened.");
                    }
                    break;
                case 6:
                    if(mySafe.door.checkClosed()){
                        System.out.println("Your safe door is already closed.");
                    }
                    else{
                        mySafe.door.close();
                        System.out.println("Your safe door has been closed.");
                    }
                    break;
                case 7:
                    printInstructions();
                    break;
                case 8:
                    break;
                default:
                    System.out.println("You must enter a number 1-8.");
                    break;
            }//End of switch
        }while(choice != 8);//End of do while    
    }
      //method to print the menu instructions
    static void printInstructions(){
        System.out.println("What would you like to do?"
                + "\n1. Add Item to Safe"
                + "\n2. Remove Item from Safe"
                + "\n3. Lock Safe"
                + "\n4. Unlock Safe"
                + "\n5. Open Safe"
                + "\n6. Close Safe"
                + "\n7. Print Instructions Again"
                + "\n8. Exit");
        
    }//End of printInstructions
    
}
