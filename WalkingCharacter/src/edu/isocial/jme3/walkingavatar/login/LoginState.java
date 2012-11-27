/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.jme3.walkingavatar.login;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.material.RenderState;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.TextField;
import edu.isocial.jme3.walkingavatar.avatareditor.AvatarEditorHUDController;
import edu.isocial.jme3.walkingavatar.core.WalkingAvatarTest;
import edu.isocial.jme3.walkingavatar.mainmenu.MainMenuController;

/**
 *
 * @author justin
 */
public class LoginState extends AbstractAppState{
    private WalkingAvatarTest app;
    
    
    private Nifty nifty;
    private FlyByCamera flyCam;
    private LoginScreenBuilder loginScreenBuilder = LoginScreenBuilder.getInstance();
    private LoginController loginController;
    
    public LoginState(WalkingAvatarTest app) {
        this.app = app;
        flyCam = app.getFlyByCamera();
        nifty = app.getNifty();
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app){
        super.initialize(stateManager, app);
        app.getRenderer().applyRenderState(RenderState.DEFAULT);
        
        initNifty();
    }

   private void initNifty(){
        flyCam.setDragToRotate(true);
        
        loginController = new LoginController();       
        loginController.initialize(app.getStateManager(), app);
        nifty.registerScreenController(loginController);
        loginScreenBuilder.buildLoginScreen(nifty, loginController);
        nifty.getScreen("LoginScreen")
                .findNiftyControl("PasswordTextField", TextField.class)
                .enablePasswordChar('*');
   }

    @Override
    public void stateAttached(AppStateManager stateManager) {        
    }

    @Override
    public void stateDetached(AppStateManager stateManager) {
        nifty.removeScreen("LoginScreen");
    }
}
