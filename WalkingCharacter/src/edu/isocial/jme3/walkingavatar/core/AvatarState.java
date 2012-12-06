/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.jme3.walkingavatar.core;

import com.jme3.scene.Node;

/**
 *
 * @author justin
 */
public enum AvatarState{
    INSTANCE;
    
    
    private Node pants, shirt;
    
    private AvatarState(){
        pants = (Node) WalkingAvatarTest.getInstance().getAssetManager().loadModel("Models/malebuilder_pants.j3o");
        shirt = (Node) WalkingAvatarTest.getInstance().getAssetManager().loadModel("Models/malebuilder_shirt.j3o");
    }

    public Node getPants() {
        return pants;
    }

    public void setPants(Node pants) {
        this.pants = pants;
    }

    public Node getShirt() {
        return shirt;
    }

    public void setShirt(Node shirt) {
        this.shirt = shirt;
    }
}
