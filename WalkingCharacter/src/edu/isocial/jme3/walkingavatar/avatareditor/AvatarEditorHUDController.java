/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.jme3.walkingavatar.avatareditor;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.DropDownSelectionChangedEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import edu.isocial.jme3.walkingavatar.core.Avatar;
import edu.isocial.jme3.walkingavatar.core.WalkingAvatarTest;
import javax.swing.JOptionPane;

/**
 *
 * @author justin
 */
public class AvatarEditorHUDController extends AbstractAppState implements ScreenController {


    private Nifty nifty;
    private Screen screen;
    private SimpleApplication app;
    private Node scene;
    private String clothingItemToWear;
    
    private Avatar avatar;
    
    private boolean pants;

    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;

        DropDown dropDown1 = findDropDownControl("DropDownLegs");
        if (dropDown1 != null) {
            dropDown1.addItem("Brown Pants");
            dropDown1.addItem("Jeans");
            dropDown1.addItem("Khakis");
            dropDown1.selectItem("Brown Pants");
        }

        DropDown dropDown2 = findDropDownControl("DropDownTorso");
        if (dropDown2 != null) {
            dropDown2.addItem("Brown Shirt");
            dropDown2.addItem("Green Shirt");
            dropDown2.addItem("Blue Shirt");
            dropDown2.addItem("Brown Cloak");
            dropDown2.addItem("Green Cloak");
            dropDown2.addItem("Blue Cloak");

            dropDown2.selectItem("Brown Shirt");
        }
        
//        DropDown dropDown3 = findDropDownControl("DropDownColor");
//        if (dropDown3 != null) {
//            dropDown3.addItem("Brown");
//            dropDown3.addItem("Blue");
//            dropDown3.addItem("Green");
//
//            dropDown3.selectItem("Original");
//        }

    }

    public void onStartScreen() {
    }

    public void onEndScreen() {
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
    }

    @Override
    public void update(float tpf) {
        /**
         * jME update loop!
         */
    }

    private DropDown findDropDownControl(final String id) {
        return screen.findNiftyControl(id, DropDown.class);
    }

    public void startGame(String nextScreen) {
        nifty.gotoScreen(nextScreen);  // switch to another screen
        // start the game and do some more stuff...
    }
    
    @NiftyEventSubscriber(pattern = "DropDown.*")
    public void onListBoxSelectionChanged(final String id,
            final DropDownSelectionChangedEvent<String> changed) {
        String selection = changed.getSelection();
        if(id.equals("DropDownLegs")){
            pants = true;
        }else if(id.equals("DropDownTorso")){
            pants = false;
        }
        displayClothingItem(selection);     
        
    }

    public void displayClothingItem(String clothingItem){
//        scene = WalkingAvatarTest.getInstance().getAvatarEditor().getClothingDisplayNode();
//        scene.detachAllChildren();
//        scene.attachChild(ClothingItems.INSTANCE.getClothingItems().get(clothingItem));
        WalkingAvatarTest.getInstance().getAvatarEditor().setClothingDisplay(ClothingItems.INSTANCE.getClothingItems().get(clothingItem));
        clothingItemToWear = clothingItem;
    }

    public void putOnClothingItem() {
        if(pants == true){
            WalkingAvatarTest.getInstance().getAvatarEditor().setPants(ClothingItems.INSTANCE.getClothingItems().get(clothingItemToWear));
        }else{
            WalkingAvatarTest.getInstance().getAvatarEditor().setShirt(ClothingItems.INSTANCE.getClothingItems().get(clothingItemToWear));
        }
    }
    
    public void goBackToMainMenu(){
        nifty.removeScreen("AvatarEditor");
        WalkingAvatarTest.getInstance().goToMainMenu();
    }

    public void quitGame() {
        app.stop();
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }
}
