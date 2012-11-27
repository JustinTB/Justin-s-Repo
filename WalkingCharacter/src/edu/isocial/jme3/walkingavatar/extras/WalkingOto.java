/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.jme3.walkingavatar.extras;

/**
 *
 * @author justin
 */
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.FogFilter;
import com.jme3.post.ssao.SSAOFilter;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl;
import com.jme3.scene.control.CameraControl.ControlDirection;
import com.jme3.shadow.PssmShadowRenderer;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;

/**
 * Sample 7 - how to load an OgreXML model and play an animation, using
 * channels, a controller, and an AnimEventListener.
 */
public class WalkingOto extends SimpleApplication
        implements ActionListener, AnimEventListener {

    private BulletAppState bulletAppState;
    private CharacterControl character;
    private Node model;
    private AnimChannel animationChannel;
    private AnimChannel attackChannel;
    private AnimChannel headChannel;
    private AnimControl animationControl;
    private AnimControl headControl;
    private ChaseCamera chaseCam;
    private boolean left = false, right = false, up = false, down = false;
    private float airTime = 0;
    private Vector3f walkDirection = new Vector3f(0, 0, 0); // stop
    private PssmShadowRenderer shadowRenderer;
    private CameraNode camNode;
    private Node charNode;
    private TerrainQuad terrain;
    private Material terrainMat;
    private Node head;
    private Node avatar;
    private Node body;

    public static void main(String[] args) {
        WalkingOto app = new WalkingOto();
        app.start();
    }

    public void simpleInitApp() {
        activatePhysics();//Activate physics by adding a BulletAppState
        initScene();//Initialize a simple physics test scene from jme3 source code
        initLight();//Create ambient light
        createCharacter();//Create animated character and add it to rootNode
        setupAnimControl();//Create the animation control
        setupAnimChannels();//Create multiple animation channels that can happen simulataneously
        createChaseCam();//Setup the chase camera for the character        
        initKeyBindings();//Initialize the key bindings for navigation/jump/attack
        createShadows();
    
    }

    public void simpleUpdate(float tpf) {
        Vector3f camDir = cam.getDirection().clone().multLocal(0.25f);
        Vector3f camLeft = cam.getLeft().clone().multLocal(0.25f);
        camDir.y = 0;
        camLeft.y = 0;
        walkDirection.set(0, 0, 0);

        if (left) {
            walkDirection.addLocal(camLeft);
        }
        if (right) {
            walkDirection.addLocal(camLeft.negate());
        }
        if (up) {
            walkDirection.addLocal(camDir);
        }
        if (down) {
            walkDirection.addLocal(camDir.negate());
        }

        if (!character.onGround()) {
            airTime = airTime + tpf;
        } else {
            airTime = 0;
        }

        if (walkDirection.length() == 0) {
            if (!"stand".equals(animationChannel.getAnimationName())) {
                animationChannel.setAnim("stand", 1f);
            }
        } else {
            character.setViewDirection(walkDirection);

            if (airTime > .3f) {
                if (!"stand".equals(animationChannel.getAnimationName())) {
                    animationChannel.setAnim("stand");
                }
            } else if (!"Walk".equals(animationChannel.getAnimationName())) {
                animationChannel.setAnim("Walk", 0.7f);
            }
        }

        character.setWalkDirection(walkDirection);


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
            character.jump();
        }
        if (binding.equals("CharAttack")) {
            attack();
        }
    }

    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        if (channel == attackChannel) {
            channel.setAnim("stand");
        }
    }

    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
    }

    private void activatePhysics() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
    }

    private void initScene() {
//        PhysicsTestHelper.createPhysicsTestWorld(rootNode,
//                assetManager, bulletAppState.getPhysicsSpace());

        rootNode.attachChild(SkyFactory.createSky(assetManager,
                "Textures/Sky/Bright/BrightSky.dds", false));


        terrainMat = new Material(assetManager,
                "Common/MatDefs/Terrain/Terrain.j3md");

        terrainMat.setTexture("Alpha", assetManager.loadTexture(
                "Textures/Terrain/splat/alphamap.png"));

        Texture grass = assetManager.loadTexture("Textures/Terrain/splat/grass.jpg");
        grass.setWrap(Texture.WrapMode.Repeat);
        terrainMat.setTexture("Tex1", grass);
        terrainMat.setFloat("Tex1Scale", 64f);

        Texture dirt = assetManager.loadTexture("Textures/Terrain/splat/dirt.jpg");
        dirt.setWrap(Texture.WrapMode.Repeat);
        terrainMat.setTexture("Tex2", dirt);
        terrainMat.setFloat("Tex2Scale", 32f);

        Texture rock = assetManager.loadTexture("Textures/Terrain/splat/road.jpg");
        rock.setWrap(Texture.WrapMode.Repeat);
        terrainMat.setTexture("Tex3", rock);
        terrainMat.setFloat("Tex3Scale", 128f);

        AbstractHeightMap heightmap = null;
        Texture heightMapImage = assetManager.loadTexture("Textures/Terrain/splat/mountains512.png");
        heightmap = new ImageBasedHeightMap(heightMapImage.getImage(), 0.5f);        
        heightmap.load();
        heightmap.smooth(0.9f, 1);
        
        

        int patchSize = 65;
        terrain = new TerrainQuad("my terrain", patchSize, 513, heightmap.getHeightMap());
        terrain.setMaterial(terrainMat);
        terrain.setLocalTranslation(0, -200, 0);
        terrain.setLocalScale(2f, 1f, 2f);
        terrain.setShadowMode(RenderQueue.ShadowMode.Receive);
        rootNode.attachChild(terrain);

        TerrainLodControl control = new TerrainLodControl(terrain, getCamera());
        terrain.addControl(control);
        terrain.addControl(new RigidBodyControl(0));
        bulletAppState.getPhysicsSpace().add(terrain);
    }

    private void initLight() {
        DirectionalLight light = new DirectionalLight();
        light.setColor(ColorRGBA.White.mult(2));
        light.setDirection(new Vector3f(-.5f, -.5f, -.5f).normalizeLocal());
        rootNode.addLight(light);
//        AmbientLight al = new AmbientLight();
//al.setColor(ColorRGBA.White.mult(1.3f));
//rootNode.addLight(al); /** Add fog to a scene */


    }

    private void createCharacter() {
        /*
         * 1. Place the "Oto" model into the assets/Models/Oto/ directory of your project.
         * 2. Create the CollisionShape and adjust the capsule radius and height to fit your character model.
         * 3. Create the CharacterControl and adjust the stepheight (here 0.05f) to the height that the 
         *    character can climb up without jumping.
         * 4. Load the visible model. Make sure its start position does not overlap with scene objects.
         * 5. Add the CharacterControl to the model and register it to the physicsSpace.
         * 6. Attach the visible model to the rootNode.
         */
        
        CapsuleCollisionShape capsule = new CapsuleCollisionShape(3f, 4f);
        character = new CharacterControl(capsule, 0.05f);
        character.setJumpSpeed(20f);
        model = (Node) assetManager.loadModel("Models/Oto/Oto.mesh.xml");
        model.addControl(character);
//        avatar = new Node();
        body = (Node) assetManager.loadModel("Models/malebuilder_male.j3o");
////        head = (Node)assetManager.loadModel("Models/malebuilder_headA.j3o");
////        avatar.attachChild(head);
////        avatar.attachChild(body);
//        avatar.addControl(character);
        bulletAppState.getPhysicsSpace().add(character);
        rootNode.attachChild(model);
    }

    private void setupAnimControl() {
        animationControl = model.getControl(AnimControl.class);
        animationControl.addListener(this);
        
    }

    private void setupAnimChannels() {
        animationChannel = animationControl.createChannel();
        attackChannel = animationControl.createChannel();
        attackChannel.addBone(animationControl.getSkeleton().getBone("uparm.right"));
        attackChannel.addBone(animationControl.getSkeleton().getBone("arm.right"));
        attackChannel.addBone(animationControl.getSkeleton().getBone("hand.right"));
    }

    private void createChaseCam() {
//        camNode = new CameraNode("Camera Node", cam);
//        camNode.setControlDir(ControlDirection.SpatialToCamera);
//        model.attachChild(camNode);
//        camNode.setLocalTranslation(new Vector3f(0, 10, -10));
//        camNode.lookAt(model.getLocalTranslation(), Vector3f.UNIT_Y);
        chaseCam = new ChaseCamera(cam, model, inputManager);
//        chaseCam.setSpatial(model);
        chaseCam.setSmoothMotion(false);
        flyCam.setEnabled(false);


    }

    private void initKeyBindings() {
        inputManager.addMapping("CharLeft", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("CharRight", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("CharForward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("CharBackward", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("CharJump", new KeyTrigger(KeyInput.KEY_RETURN));
        inputManager.addMapping("CharAttack", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(this, "CharLeft", "CharRight");
        inputManager.addListener(this, "CharForward", "CharBackward");
        inputManager.addListener(this, "CharJump", "CharAttack");

    }

    private void attack() {
        attackChannel.setAnim("Dodge", 0.1f);
        attackChannel.setLoopMode(LoopMode.DontLoop);
    }

    private void createShadows() {
        shadowRenderer = new PssmShadowRenderer(assetManager, 1024, 3);
        shadowRenderer.setDirection(new Vector3f(-.5f, -.5f, -.5f).normalizeLocal());
//        shadowRenderer.setFilterMode(PssmShadowRenderer.FilterMode.PCF4);
        viewPort.addProcessor(shadowRenderer);
//        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
//        SSAOFilter ssaoFilter = new SSAOFilter(12.94f, 43.92f, 0.33f, 0.61f);
//        fpp.addFilter(ssaoFilter);
//        viewPort.addProcessor(fpp);
        rootNode.setShadowMode(ShadowMode.Off);
        terrain.setShadowMode(ShadowMode.CastAndReceive);
        model.setShadowMode(ShadowMode.CastAndReceive);


    }
}
