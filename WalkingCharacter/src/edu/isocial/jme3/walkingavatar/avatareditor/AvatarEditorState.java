/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.jme3.walkingavatar.avatareditor;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.FlyByCamera;
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
import edu.isocial.jme3.walkingavatar.builders.FloorBuilder;
import edu.isocial.jme3.walkingavatar.core.Avatar;
import edu.isocial.jme3.walkingavatar.core.AvatarHolder;
import edu.isocial.jme3.walkingavatar.core.WalkingAvatarTest;


/**
 *
 * @author justin
 */
public class AvatarEditorState extends AbstractAppState{
    
    private WalkingAvatarTest app;
    
    private PssmShadowRenderer shadowRenderer;
    private Nifty nifty;
    private AvatarEditorHUDBuilder screenBuilder = AvatarEditorHUDBuilder.getInstance();
    AvatarEditorHUDController editorController;
    private Node scene1, scene2, holderScene;
    private Camera cam2, holderCam;
    private ViewPort view2, holderView;
    private Node rootNode = new Node("Root Node");
    private ViewPort viewPort;
    private Camera cam;
    private RenderManager renderManager;
    private Avatar avatar;
    private FlyByCamera flyCam;
    
    public AvatarEditorState(WalkingAvatarTest app) {
        this.app = app;
        viewPort = app.getViewPort();
        cam = app.getCamera();
        renderManager = app.getRenderManager();
        flyCam = app.getFlyByCamera();
        nifty = app.getNifty();
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app){
        super.initialize(stateManager, app);
        app.getRenderer().applyRenderState(RenderState.DEFAULT);
         
        intiNifty();
        initScenes();//Initialize the scene
        initLight();//Create directional sunlight
        initCharacter();//Create animated character and add it to rootNode
        initCameras();//Setup the chase camera for the character        
        initShadows();//Create the shadows for the scene
        FloorBuilder.createFloor(scene1, app.getAssetManager());
        System.out.println("DONE WITH INITIALIZATION!");
    }
    
    @Override
    public void update(float tpf){
        rootNode.updateLogicalState(tpf);
        rootNode.updateGeometricState();
        if (!"idle".equals(avatar.getBodyChannel().getAnimationName())) {
            avatar.setAnimation("idle", 1f);
        }
    }
    
    private void initScenes() {  
        System.out.println("INITIALZING SCENES!");
        scene1 = new Node("Scene1");
        scene2 = new Node("Scene2");
        holderScene = new Node();
        rootNode.attachChild(scene1);
        rootNode.attachChild(scene2);
        rootNode.attachChild(holderScene);

        viewPort.detachScene(rootNode);
        viewPort.attachScene(scene1);
        viewPort.attachScene(holderScene);

        cam.setViewPort(0.0f, .7f, 0.0f, 1.0f);
        cam2 = cam.clone();
        cam2.setViewPort(0.7f, 1.0f, 0.65f, .95f);
        holderCam = cam.clone();
        holderCam.setViewPort(0.7f, 1f, 0.0f, 0.7f);

        view2 = renderManager.createMainView("View of camera 2", cam2);
        view2.setClearFlags(true, true, true);
        holderView = renderManager.createMainView("View of holderCam", holderCam);
        holderView.setClearFlags(true, true, true);

        view2.attachScene(scene2);
        holderView.attachScene(holderScene);

        viewPort.setBackgroundColor(ColorRGBA.DarkGray);
        holderView.setBackgroundColor(ColorRGBA.Black);

        view2.setBackgroundColor(ColorRGBA.White);
    }

    private void initLight() {
         System.out.println("INITIALZING LIGHT!");
       
        DirectionalLight light = new DirectionalLight();
        light.setDirection(new Vector3f(-.5f, -.5f, -.5f).normalizeLocal());
        rootNode.addLight(light);
        scene1.addLight(light);
        DirectionalLight light2 = (DirectionalLight) light.clone();
        scene2.addLight(light2);
    }

    private void initCharacter() {
         System.out.println("INITIALZING CHARACTER!");
       
        avatar = AvatarHolder.getAvatar();
        avatar.setParentNode(scene1);
    }


    private void initCameras() {
                System.out.println("INITIALZING CAMERAS!");
                        System.out.println("CAM LOCATION: " + cam.getLocation());

        app.getFlyByCamera().setDragToRotate(true);

        app.getCamera().setLocation(new Vector3f(0f, 2f, 4f));
        Vector3f lookAt = new Vector3f(0f, 1f, 0f);
        app.getCamera().lookAt(lookAt, Vector3f.UNIT_Y);
        
        cam2.setLocation(new Vector3f(0f, 1f, 3f));
        Vector3f lookAt2 = new Vector3f(0f, 1f, 0f);
        cam2.lookAt(lookAt2, Vector3f.UNIT_Y);

    }

    private void initShadows() {
                System.out.println("INITIALZING SHADOWS!");

        shadowRenderer = new PssmShadowRenderer(app.getAssetManager(), 1024, 3);
        shadowRenderer.setDirection(new Vector3f(-.5f, -.5f, -.5f).normalizeLocal());
        viewPort.addProcessor(shadowRenderer);
        scene1.setShadowMode(RenderQueue.ShadowMode.Off);
        avatar.setShadowMode(RenderQueue.ShadowMode.Cast);
    }
    
    public Node getClothingDisplayNode(){
        return scene2;
    }

    void setClothingDisplay(Node node) {
        scene2.detachAllChildren();
        scene2.attachChild(node);
    }

    void setPants(Node pants) {
        avatar.setPants(pants);
    }

    void setShirt(Node shirt) {
        avatar.setShirt(shirt);
    }

    @Override
    public void stateAttached(AppStateManager stateManager) {
    }

    @Override
    public void stateDetached(AppStateManager stateManager) {
        app.getViewPort().clearScenes();
        renderManager.removeMainView(view2);
        renderManager.removeMainView(holderView);
        cam.setViewPort(0, 1, 0, 1);
        AvatarHolder.setAvatar(avatar);
        nifty.removeScreen("AvatarEditor");
    }

    private void intiNifty() {
        flyCam.setDragToRotate(true);
        editorController = new AvatarEditorHUDController(); 
        editorController.initialize(app.getStateManager(), app);
        nifty.registerScreenController(editorController);
        screenBuilder.buildHUD(nifty, editorController);
        editorController.setAvatar(avatar);
    }

    
    
}
