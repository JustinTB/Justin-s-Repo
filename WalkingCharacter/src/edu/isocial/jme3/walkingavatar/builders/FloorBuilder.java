/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.jme3.walkingavatar.builders;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 *
 * @author justin
 */
public class FloorBuilder {
    
    public static void createFloor(Node parentNode, AssetManager assetManager){
        System.out.println("INITIALIZING FLOOR!");
        Box floorBox = new Box(Vector3f.ZERO, 5f, .01f, 5f);
        
        Geometry floor = new Geometry("Floor", floorBox);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");       
        mat.setColor("Color", ColorRGBA.Gray);
        floor.setMaterial(mat);
        floor.setLocalTranslation(0,-0.01f, 0);
        floor.setShadowMode(ShadowMode.Receive);
        parentNode.attachChild(floor);              
    }
}
