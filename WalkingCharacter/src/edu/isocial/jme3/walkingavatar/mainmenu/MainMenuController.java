/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.jme3.walkingavatar.mainmenu;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import edu.isocial.jme3.walkingavatar.core.WalkingAvatarTest;
import javax.swing.JOptionPane;

/**
 *
 * @author justin
 */
public class MainMenuController extends AbstractAppState implements ScreenController {

    private Nifty nifty;
    private Screen screen;
    private SimpleApplication app;

    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;

    }

    public void onStartScreen() {}

    public void onEndScreen() {}

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

    public void startGame(String nextScreen) {
        nifty.gotoScreen(nextScreen);  // switch to another screen
        // start the game and do some more stuff...
    }
    
    public void displayMessage(){
        JOptionPane.showMessageDialog(null, "MESSAGE!");
    }
    
    public void startAvatarEditor(){
        WalkingAvatarTest.getInstance().goToAvatarEditor();
    }
    
    public void startGame(){
        nifty.removeScreen("MainMenu");
        WalkingAvatarTest.getInstance().initWorld();
    }

    public void quitGame() {
        app.stop();
    }
}
