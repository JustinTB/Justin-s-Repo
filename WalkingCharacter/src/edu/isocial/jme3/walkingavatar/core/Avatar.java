/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.jme3.walkingavatar.core;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.material.Material;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author justin
 */

/*
 * 
 * WORK IN PROGRESS
 * 
 */
public class Avatar extends Node {

    private Node body, head, eyes;
    private Node shirt, pants;
    private Node parentNode;
    private CapsuleCollisionShape capsule;
    private CharacterControl control;
    private AssetManager assetManager;
    private AnimControl headControl, bodyControl, pantsControl, shirtControl, eyesControl;
    private AnimChannel headChannel, bodyChannel, pantsChannel, shirtChannel, eyesChannel;
    private List<AnimChannel> animationChannels;
    private List<AnimControl> animationControls;
    private AnimEventListener animListener;

    public Avatar(Node parentNode, AssetManager assetManager) {
        this.parentNode = parentNode;
        this.assetManager = assetManager;
        //Default AnimEventListener
        initAnimListener();
        createDefaultAvatar();
        initAnimationControls();
        initAnimationChannels();
    }
    
    private void createDefaultAvatar() {
        /*TODO: if using collision shape, make sure it fits the size of the avatar
         *NOTE: can use bulletAppState.getPhysicsSpace().enableDebug(assetManager);
         * to see the capsule when using physics
         */
        capsule = new CapsuleCollisionShape(2f, 4f);

        control = new CharacterControl(capsule, 0.05f);
        control.setJumpSpeed(20f);

        body = (Node) assetManager.loadModel("Models/malebuilder_male.j3o");
        head = (Node) assetManager.loadModel("Models/malebuilder_headB.j3o");
        pants = (Node) assetManager.loadModel("Models/malebuilder_pants.j3o");
        shirt = (Node) assetManager.loadModel("Models/malebuilder_shirt.j3o");
        eyes = (Node) assetManager.loadModel("Models/eyes.j3o");

        body.attachChild(head);
        body.attachChild(pants);
        body.attachChild(shirt);
        body.attachChild(eyes);

        this.attachChild(body);
//        body.move(0, -4.5f, 0);
        this.addControl(control);

        parentNode.attachChild(this);
    }

    private void initAnimationControls() {
        animationControls = new ArrayList();
        bodyControl = body.getControl(AnimControl.class);
        bodyControl.addListener(animListener);
        headControl = head.getControl(AnimControl.class);
        headControl.addListener(animListener);
        pantsControl = pants.getControl(AnimControl.class);
        pantsControl.addListener(animListener);
        shirtControl = shirt.getControl(AnimControl.class);
        shirtControl.addListener(animListener);
        eyesControl = eyes.getControl(AnimControl.class);
        eyesControl.addListener(animListener);
        animationControls.add(bodyControl);
        animationControls.add(headControl);
        animationControls.add(pantsControl);
        animationControls.add(shirtControl);
        animationControls.add(eyesControl);
    }

    private void initAnimationChannels() {
        animationChannels = new ArrayList();
        bodyChannel = bodyControl.createChannel();
        headChannel = headControl.createChannel();
        pantsChannel = pantsControl.createChannel();
        shirtChannel = shirtControl.createChannel();
        eyesChannel = eyesControl.createChannel();
        animationChannels.add(bodyChannel);
        animationChannels.add(headChannel);
        animationChannels.add(pantsChannel);
        animationChannels.add(shirtChannel);
        animationChannels.add(eyesChannel);
    }
    public void setAnimation(String animation){
        for(AnimChannel chan: animationChannels){
            chan.setAnim(animation);
        }
    }
    
    public void setAnimationSpeed(float speed){
        for(AnimChannel chan: animationChannels){
            chan.setSpeed(speed);
        }
    }
    public void setAnimation(String animation, float blendTime) {
        for (AnimChannel chan : animationChannels) {
            chan.setAnim(animation, blendTime);
        }
    }

    public Node getBody() {
        return body;
    }

    public void setBody(Node newBody) {
        this.detachChild(body);
        body = newBody;
        body.attachChild(head);
        body.attachChild(eyes);
        body.attachChild(pants);
        body.attachChild(shirt);
        this.attachChild(body);
        animationChannels.remove(bodyChannel);
        bodyControl = body.getControl(AnimControl.class);
        bodyControl.addListener(animListener);
        bodyChannel = bodyControl.createChannel();
        animationChannels.add(bodyChannel);
        this.setAnimation("idle", 1f);
    }

    public Node getHead() {
        return head;
    }

    public void setHead(Node newHead) {
        body.detachChild(head);
        head = newHead;
        body.attachChild(head);
        animationChannels.remove(headChannel);
        headControl = head.getControl(AnimControl.class);
        headControl.addListener(animListener);
        headChannel = headControl.createChannel();
        animationChannels.add(headChannel);
        this.setAnimation("idle", 1f);
    }

    public Node getEyes() {
        return eyes;
    }

    public void setEyes(Node newEyes) {
        body.detachChild(eyes);
        eyes = newEyes;
        body.attachChild(eyes);
        animationChannels.remove(eyesChannel);
        eyesControl = eyes.getControl(AnimControl.class);
        eyesControl.addListener(animListener);
        eyesChannel = eyesControl.createChannel();
        animationChannels.add(eyesChannel);
        this.setAnimation("idle", 1f);
    }

    public Node getShirt() {
        return shirt;
    }

    public void setShirt(Node newShirt) {
        body.detachChild(shirt);
        shirt = (Node) newShirt.clone();
        body.attachChild(shirt);
        animationChannels.remove(shirtChannel);
        shirtControl = shirt.getControl(AnimControl.class);
        shirtControl.addListener(animListener);
        shirtChannel = shirtControl.createChannel();
        animationChannels.add(shirtChannel);
        this.setAnimation("idle", 1f);
    }

    public Node getPants() {
        return pants;
    }

    public void setPants(Node newPants) {
        body.detachChild(pants);
        pants = (Node) newPants.clone();
        body.attachChild(pants);
        animationChannels.remove(pantsChannel);
        pantsControl = pants.getControl(AnimControl.class);
        pantsControl.addListener(animListener);
        pantsChannel = pantsControl.createChannel();
        animationChannels.add(pantsChannel);
        this.setAnimation("idle", 1f);
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        parentNode.detachChild(this);
        this.parentNode = parentNode;
        parentNode.attachChild(this);
    }

    public CapsuleCollisionShape getCapsule() {
        return capsule;
    }

    public void setCapsule(CapsuleCollisionShape capsule) {
        this.capsule = capsule;
        control.setCollisionShape(this.capsule);
    }

    public CharacterControl getControl() {
        return control;
    }

    public void setControl(CharacterControl control) {
        this.removeControl(this.control);
        this.control = control;
        this.addControl(this.control);
    }

    public List<AnimChannel> getAnimationChannels() {
        return animationChannels;
    }
    
    public List<AnimControl> getAnimationControls() {
        return animationControls;
    }

    public AnimEventListener getAnimListener() {
        return animListener;
    }

    public void setAnimListener(AnimEventListener newAnimListener) {
        for(AnimControl control: animationControls){
            control.removeListener(getAnimListener());
        }
        animListener = newAnimListener;
        for(AnimControl control: animationControls){
            control.addListener(animListener);
        }
        
    }

    public AnimChannel getHeadChannel() {
        return headChannel;
    }

    public AnimChannel getBodyChannel() {
        return bodyChannel;
    }

    public AnimChannel getPantsChannel() {
        return pantsChannel;
    }

    public AnimChannel getCloakChannel() {
        return shirtChannel;
    }

    public AnimChannel getEyesChannel() {
        return eyesChannel;
    }

    private void initAnimListener() {
        animListener = new AnimEventListener() {
            public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
            }

            public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
            }
        };
    }
}
