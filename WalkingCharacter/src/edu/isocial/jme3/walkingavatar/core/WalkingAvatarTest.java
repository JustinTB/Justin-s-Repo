package edu.isocial.jme3.walkingavatar.core;

/**
 *
 * @author justin
 */
import edu.isocial.jme3.walkingavatar.state.walkingavatar.WalkingAvatarState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppState;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.Camera;
import de.lessvoid.nifty.Nifty;
import edu.isocial.jme3.walkingavatar.state.avatareditor.AvatarEditorState;
import edu.isocial.jme3.walkingavatar.state.login.LoginState;
import edu.isocial.jme3.walkingavatar.state.mainmenu.MainMenuState;
import java.util.logging.Level;
import java.util.logging.Logger;
//import java.util.logging.Level;
//import java.util.logging.Logger;

/**
 * Sample 7 - how to load an OgreXML model and play an animation, using
 * channels, a controller, and an AnimEventListener.
 */
public class WalkingAvatarTest extends SimpleApplication {

    private Nifty nifty;
    private static WalkingAvatarTest app;
    private Avatar avatar;
    private AvatarEditorState avatarEditorState = null;
    private WalkingAvatarState walkingAvatarState = null;
    private AppState loginState;
    private MainMenuState mainMenuState;
    private AbstractAppState currentState;
    
    public static void main(String[] args) {
        Logger.getLogger("").setLevel(Level.SEVERE);            
        app = new WalkingAvatarTest();
        app.start();
    }

    public static WalkingAvatarTest getInstance() {
        return app;
        
    }
    

    public void simpleInitApp() {
        avatar = new Avatar(rootNode, assetManager);
        initNifty();
        goToLoginScreen();
    }

    public void goToWalkingCharacter() {
        stateManager.detach(currentState);
        stateManager.attach(walkingAvatarState);
        currentState = walkingAvatarState;
    }

    private void initNifty() {
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(
                assetManager, inputManager, audioRenderer, guiViewPort);
        nifty = niftyDisplay.getNifty();
        guiViewPort.addProcessor(niftyDisplay);
        flyCam.setDragToRotate(true);

        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");
        
        mainMenuState = new MainMenuState(this);
        loginState = new LoginState(this);
        avatarEditorState = new AvatarEditorState(this);
        walkingAvatarState = new WalkingAvatarState(this);
    }

    public void goToAvatarEditor() {
        getStateManager().detach(currentState);
        getStateManager().attach(avatarEditorState);
        currentState = avatarEditorState;
    }

    public AvatarEditorState getAvatarEditor() {
        return avatarEditorState;
    }

    public void goToMainMenu() {
        getStateManager().detach(currentState);
        getStateManager().attach(mainMenuState);
        currentState = (AbstractAppState) mainMenuState;
    }

    private void goToLoginScreen() {
        getStateManager().attach(loginState);
        currentState = (AbstractAppState) loginState;     
    }

    public Nifty getNifty() {
        return nifty;
    }
}
