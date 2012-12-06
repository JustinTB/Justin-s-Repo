/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.jme3.walkingavatar.state.walkingavatar;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.ChaseCamera;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.shadow.PssmShadowRenderer;
import de.lessvoid.nifty.Nifty;
import edu.isocial.jme3.walkingavatar.builders.SkyBuilder;
import edu.isocial.jme3.walkingavatar.builders.TerrainBuilder;
import edu.isocial.jme3.walkingavatar.core.Avatar;
import edu.isocial.jme3.walkingavatar.core.WalkingAvatarTest;
import edu.isocial.jme3.walkingavatar.state.mainmenu.MainMenuController;

/**
 *
 * @author justin
 */
public class WalkingAvatarState extends AbstractAppState implements ActionListener {

    private BulletAppState bulletAppState;
    private Node rootNode;
    private AssetManager assetManager;
    private Avatar avatar;
    private ChaseCamera chaseCam;
    private FlyByCamera flyCam;
    private InputManager inputManager;
    private PssmShadowRenderer shadowRenderer;
    private ViewPort viewPort;
    private WalkingAvatarTest app;
    private boolean left = false, right = false, up = false, down = false;
    private Vector3f walkDirection = new Vector3f(0, 0, 0);
    private Camera cam;
    private float airTime = 0;
    private boolean paused = false;
    private Nifty nifty;
    private PauseMenuController pauseMenuController;
    private PauseMenuBuilder pauseMenuBuilder = PauseMenuBuilder.getInstance();

    public WalkingAvatarState(WalkingAvatarTest app) {
        this.app = app;
        this.rootNode = new Node("Root Node");
        this.assetManager = app.getAssetManager();
        this.flyCam = app.getFlyByCamera();
        this.inputManager = app.getInputManager();
        this.viewPort = app.getViewPort();
        this.cam = app.getCamera();
        

    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        app.getRenderer().applyRenderState(RenderState.DEFAULT);
        paused = false;
        
        super.initialize(stateManager, app);
        app.getRenderer().applyRenderState(RenderState.DEFAULT);
        initPhysics();//Activate physics by adding a BulletAppState
        initScene();//Initialize the scene
        initLight();//Create directional sunlight
        initCharacter();//Create animated character and add it to rootNode
        initChaseCam();//Setup the chase camera for the character      
        initKeyBindings();//Initialize the key bindings for navigation/jump/attack
        initShadows();//Create the shadows for the scene
        initNifty();
        
    }

    @Override
    public void update(float tpf) {
        if (paused == false) {
            rootNode.updateLogicalState(tpf);
             rootNode.updateGeometricState();
            Vector3f camDir = cam.getDirection().clone().multLocal(0.085f);
            Vector3f camLeft = cam.getLeft().clone().multLocal(0.085f);

            camDir.y = 0;
            camLeft.y = 0;
            camDir.normalize();
            camLeft.normalize();
            walkDirection.set(0, 0, 0);


            if (left) {
                walkDirection.addLocal(camLeft).normalize();
            }
            if (right) {
                walkDirection.addLocal(camLeft.negate()).normalize();
            }
            if (up) {
                walkDirection.addLocal(camDir).normalize();
            }
            if (down) {
                walkDirection.addLocal(camDir.negate()).normalize();
            }
            walkDirection.normalize();

            if (!avatar.getControl().onGround()) {
                airTime = airTime + tpf;
            } else {
                airTime = 0;
            }

            avatar.setAnimationSpeed(1.5f);

            if (walkDirection.length() == 0) {
                if (!"idle".equals(avatar.getBodyChannel().getAnimationName())) {
                    avatar.setAnimation("idle", 1f);
                }
            } else {
                avatar.getControl().setViewDirection(walkDirection);

                if (airTime > .3f) {
                    if (!"idle".equals(avatar.getBodyChannel().getAnimationName())) {
                        avatar.setAnimation("idle");
                    }
                } else if (!"walk".equals(avatar.getBodyChannel().getAnimationName())) {
                    avatar.setAnimation("walk", 0.7f);
                }
            }

            avatar.getControl().setWalkDirection(walkDirection);
        }
    }

    private void initPhysics() {
        bulletAppState = new BulletAppState();
        app.getStateManager().attach(bulletAppState);
//        bulletAppState.getPhysicsSpace().enableDebug(assetManager);
    }

    private void initScene() {
        SkyBuilder.buildSky(rootNode, assetManager);
        TerrainBuilder.buildTerrain(rootNode, assetManager, bulletAppState, app.getCamera());
    }

    private void initLight() {
        DirectionalLight light = new DirectionalLight();
        light.setColor(ColorRGBA.White.mult(2));
        light.setDirection(new Vector3f(-.5f, -.5f, -.5f).normalizeLocal());
        rootNode.addLight(light);
    }

    private void initCharacter() {
        avatar = new Avatar(rootNode, app.getAssetManager());
        avatar.getControl().setPhysicsLocation(new Vector3f(0f, -17.875402f, 0f));
        avatar.updateState();
        avatar.setLocalScale(4f);
        avatar.getBody().setLocalTranslation(0f, -1f, 0f);
        avatar.setParentNode(rootNode);
        bulletAppState.getPhysicsSpace().add(avatar.getControl());
    }

    private void initChaseCam() {
        chaseCam = new ChaseCamera(app.getCamera(), avatar, inputManager);
        chaseCam.setUpVector(new Vector3f(0.0f, 1.0f, 0.0f));
        chaseCam.setSmoothMotion(false);
        flyCam.setEnabled(false);
    }

    private void initKeyBindings() {
        inputManager.addMapping("CharLeft", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("CharRight", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("CharForward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("CharBackward", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("CharJump", new KeyTrigger(KeyInput.KEY_RETURN));
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addListener(this, "CharLeft", "CharRight");
        inputManager.addListener(this, "CharForward", "CharBackward");
        inputManager.addListener(this, "CharJump", "CharAttack");
        inputManager.addListener(this, "Pause");
    }

    private void initShadows() {
        shadowRenderer = new PssmShadowRenderer(assetManager, 1024, 3);
        shadowRenderer.setDirection(new Vector3f(-.5f, -.5f, -.5f).normalizeLocal());
//        shadowRenderer.setFilterMode(PssmShadowRenderer.FilterMode.PCF4);
        viewPort.addProcessor(shadowRenderer);
//        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
//        SSAOFilter ssaoFilter = new SSAOFilter(12.94f, 43.92f, 0.33f, 0.61f);
//        fpp.addFilter(ssaoFilter);
//        viewPort.addProcessor(fpp);
        rootNode.setShadowMode(RenderQueue.ShadowMode.Off);
        avatar.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
    }

    public void onAction(String binding, boolean isPressed, float tpf) {
        if (binding.equals("CharLeft")) {
            if (isPressed) {
                left = true;
            } else {
                left = false;
            }
        } else if (binding.equals("CharRight")) {
            if (isPressed) {
                right = true;
            } else {
                right = false;
            }
        } else if (binding.equals("CharForward")) {
            if (isPressed) {
                up = true;
            } else {
                up = false;
            }
        } else if (binding.equals("CharBackward")) {
            if (isPressed) {
                down = true;
            } else {
                down = false;
            }
        } else if (binding.equals("CharJump")) {
            avatar.getControl().jump();
        } else if (binding.equals("Pause")) {
            if(!isPressed){
            handlePause();
            }
        }
    }

    private void initNifty() {
//        flyCam.setDragToRotate(true);
        nifty = app.getNifty();
        pauseMenuController = new PauseMenuController();
        pauseMenuController.initialize(app.getStateManager(), app, this);
        nifty.registerScreenController(pauseMenuController);
    }

    @Override
    public void stateAttached(AppStateManager stateManager) {
        System.out.println("WALKING AVATAR STATE ATTACHED!");
        app.getViewPort().attachScene(rootNode);
    }

    @Override
    public void stateDetached(AppStateManager stateManager) {
        app.getViewPort().clearScenes();
        rootNode.detachAllChildren();
        app.getStateManager().detach(bulletAppState);
    }

    private void pauseGame() {
        paused = true;
        inputManager.reset();
        pauseMenuBuilder.buildPauseMenu(nifty, pauseMenuController);
    }

    private void unpauseGame() {
        paused = false;
        initKeyBindings();
        nifty.removeScreen("PauseMenu");
    }

    private void handlePause() {
        if(!paused){
            pauseGame();
        }else{
            unpauseGame();
        }
    }
    
    public void resume(){
        if(paused){
            unpauseGame();
        }
    }
}
