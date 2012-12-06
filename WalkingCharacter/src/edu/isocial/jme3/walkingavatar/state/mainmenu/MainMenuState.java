/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.jme3.walkingavatar.state.mainmenu;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.light.DirectionalLight;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.shadow.PssmShadowRenderer;
import de.lessvoid.nifty.Nifty;
import edu.isocial.jme3.walkingavatar.state.avatareditor.AvatarEditorHUDBuilder;
import edu.isocial.jme3.walkingavatar.builders.FloorBuilder;
import edu.isocial.jme3.walkingavatar.core.Avatar;
import edu.isocial.jme3.walkingavatar.core.WalkingAvatarTest;
import edu.isocial.jme3.walkingavatar.state.login.LoginController;
import edu.isocial.jme3.walkingavatar.state.login.LoginScreenBuilder;

/**
 *
 * @author justin
 */
public class MainMenuState extends AbstractAppState{
     
    private WalkingAvatarTest app;
    
    
    private Nifty nifty;
    private FlyByCamera flyCam;
    private MainMenuScreenBuilder mainMenuBuilder = MainMenuScreenBuilder.getInstance();
    private MainMenuController mainMenuController;
    private Node rootNode = new Node("Root Node");
    private ViewPort viewPort;
    private Camera cam;
    private Avatar avatar;
    private PssmShadowRenderer shadowRenderer;
    
    public MainMenuState(WalkingAvatarTest app) {
        this.app = app;
        flyCam = app.getFlyByCamera();
        nifty = app.getNifty();
        cam = app.getCamera();
        viewPort = app.getViewPort();
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app){
        super.initialize(stateManager, app);
        app.getRenderer().applyRenderState(RenderState.DEFAULT);
        
        initNifty();
//        initScenes();//Initialize the scene
//        initLight();//Create directional sunlight
//        initCharacter();//Create animated character and add it to rootNode
//        initCameras();//Setup the chase camera for the character        
//        initShadows();//Create the shadows for the scene
//        FloorBuilder.createFloor(rootNode, app.getAssetManager());
    }
//    
//    @Override
//    public void update(float tpf){
//        rootNode.updateLogicalState(tpf);
//        rootNode.updateGeometricState();
//        if (!"idle".equals(avatar.getBodyChannel().getAnimationName())) {
//            avatar.setAnimation("idle", 1f);
//        }
//    }

   private void initNifty(){
        flyCam.setDragToRotate(true);

        mainMenuController = new MainMenuController();   
        mainMenuController.initialize(app.getStateManager(), app);
        nifty.registerScreenController(mainMenuController);
        mainMenuBuilder.buildMainMenuScreen(nifty, mainMenuController);
   }
   
//    private void initScenes() {  
//        System.out.println("INITIALZING SCENES!");
//        viewPort.clearScenes();
//        viewPort.attachScene(rootNode);
//
//        cam.setViewPort(0.25f, .75f, 0.25f, .75f);
//        
//        viewPort.setBackgroundColor(ColorRGBA.DarkGray);
//
//    }
//
//    private void initLight() {
//         System.out.println("INITIALZING LIGHT!");
//       
//        DirectionalLight light = new DirectionalLight();
//        light.setDirection(new Vector3f(-.5f, -.5f, -.5f).normalizeLocal());
//        rootNode.addLight(light);
//       
//    }
//
//    private void initCharacter() {
//         System.out.println("INITIALZING CHARACTER!");
//       
//        avatar = AvatarHolder.getAvatar();
//        avatar.setParentNode(rootNode);
//    }
//
//
//    private void initCameras() {
//                System.out.println("INITIALZING CAMERAS!");
//                        System.out.println("CAM LOCATION: " + cam.getLocation());
//
//        app.getFlyByCamera().setDragToRotate(true);
//
//        app.getCamera().setLocation(new Vector3f(0f, 2f, 4f));
//        Vector3f lookAt = new Vector3f(0f, 1f, 0f);
//        app.getCamera().lookAt(lookAt, Vector3f.UNIT_Y);
//        
//    }
//
//    private void initShadows() {
//                System.out.println("INITIALZING SHADOWS!");
//
//        shadowRenderer = new PssmShadowRenderer(app.getAssetManager(), 1024, 3);
//        shadowRenderer.setDirection(new Vector3f(-.5f, -.5f, -.5f).normalizeLocal());
//        viewPort.addProcessor(shadowRenderer);
//        avatar.setShadowMode(RenderQueue.ShadowMode.Cast);
//    }

    @Override
    public void stateAttached(AppStateManager stateManager) {
    }

    @Override
    public void stateDetached(AppStateManager stateManager) {
//         app.getViewPort().clearScenes();
//        cam.setViewPort(0, 1, 0, 1);
//        AvatarHolder.setAvatar(avatar);
        nifty.removeScreen("MainMenu");
    }

}
