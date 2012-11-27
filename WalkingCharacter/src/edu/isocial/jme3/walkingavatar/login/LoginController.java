/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.jme3.walkingavatar.login;

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
public class LoginController extends AbstractAppState implements ScreenController {

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
    
    public void authenticate(){
        String username = nifty.getScreen("LoginScreen").findNiftyControl("UsernameTextField", TextField.class).getText();
        String password = nifty.getScreen("LoginScreen").findNiftyControl("PasswordTextField", TextField.class).getText();
        String serverURL = nifty.getScreen("LoginScreen").findNiftyControl("ServerTextField", TextField.class).getText();

        if(username.equals("Justin") && password.equals("justin")){
            nifty.removeScreen("LoginScreen");         
            WalkingAvatarTest.getInstance().goToMainMenu();
        }else{
            JOptionPane.showMessageDialog(null, "INCORRECT USERNAME/PASSWORD");
            nifty.getScreen("LoginScreen").findNiftyControl("UsernameTextField", TextField.class).setText("");
            nifty.getScreen("LoginScreen").findNiftyControl("PasswordTextField", TextField.class).setText("");

        }    
                       
    }

    public void quitGame() {
        app.stop();
    }
}
