package edu.isocial.jme3.walkingavatar.core;

/**
 *
 * @author justin
 */
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppState;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;
import com.jme3.shadow.PssmShadowRenderer;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.TextField;
import edu.isocial.jme3.walkingavatar.avatareditor.AvatarEditorState;
import edu.isocial.jme3.walkingavatar.avatareditor.AvatarEditorHUDBuilder;
import edu.isocial.jme3.walkingavatar.avatareditor.AvatarEditorHUDController;
import edu.isocial.jme3.walkingavatar.builders.FloorBuilder;
import edu.isocial.jme3.walkingavatar.builders.SkyBuilder;
import edu.isocial.jme3.walkingavatar.builders.TerrainBuilder;
import edu.isocial.jme3.walkingavatar.login.LoginController;
import edu.isocial.jme3.walkingavatar.login.LoginScreenBuilder;
import edu.isocial.jme3.walkingavatar.login.LoginState;
import edu.isocial.jme3.walkingavatar.mainmenu.MainMenuController;
import edu.isocial.jme3.walkingavatar.mainmenu.MainMenuScreenBuilder;
import edu.isocial.jme3.walkingavatar.mainmenu.MainMenuState;

/**
 * Sample 7 - how to load an OgreXML model and play an animation, using
 * channels, a controller, and an AnimEventListener.
 */
public class WalkingAvatarTest extends SimpleApplication {

    private Nifty nifty;
    private LoginScreenBuilder loginScreenBuilder = LoginScreenBuilder.getInstance();
    private MainMenuScreenBuilder mainMenuBuilder = MainMenuScreenBuilder.getInstance();
    private AvatarEditorHUDBuilder editorBuilder = AvatarEditorHUDBuilder.getInstance();
    private static WalkingAvatarTest app;
    private Avatar avatar;
    private AvatarEditorState avatarEditorState = null;
    private WalkingAvatarState walkingAvatarState = null;
    private AppState loginState;
    private MainMenuState mainMenuState;
    private AbstractAppState currentState;

    public static void main(String[] args) {
        app = new WalkingAvatarTest();
        app.start();
    }

    public static WalkingAvatarTest getInstance() {
        return app;
    }
    

    public void simpleInitApp() {
        avatar = new Avatar(rootNode, assetManager);
        AvatarHolder.setAvatar(avatar);
        initNifty();
        goToLoginScreen();

    }

    public void initWorld() {
        stateManager.detach(avatarEditorState);
        walkingAvatarState = new WalkingAvatarState(this);
        stateManager.attach(walkingAvatarState);
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
        
      
    }

    public void goToAvatarEditor() {
        getStateManager().detach(currentState);
        getStateManager().attach(avatarEditorState);
        currentState = avatarEditorState;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public AvatarEditorState getAvatarEditor() {
        return avatarEditorState;
    }

    public void goToMainMenu() {
//        nifty.removeScreen("LoginScreen");
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
