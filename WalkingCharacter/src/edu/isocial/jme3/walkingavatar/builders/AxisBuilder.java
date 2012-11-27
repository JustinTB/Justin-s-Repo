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
import com.jme3.scene.debug.Arrow;

/**
 *
 * @author justin
 */
public class AxisBuilder {
    
    public static void createArrows(Node parentNode, AssetManager assetManager) {
        Material xMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Material yMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Material zMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        
        Arrow xArrow = new Arrow(Vector3f.UNIT_X);
        xArrow.setArrowExtent(new Vector3f(2f, 0f, 0f));
        Arrow yArrow = new Arrow(Vector3f.UNIT_Y);
        yArrow.setArrowExtent(new Vector3f(0f, 2f, 0f));
        Arrow zArrow = new Arrow(Vector3f.UNIT_Z);
        zArrow.setArrowExtent(new Vector3f(0f, 0f, 2f));

        Geometry xGeo = new Geometry("X Arrow", xArrow);
        Geometry yGeo = new Geometry("Y Arrow", yArrow);
        Geometry zGeo = new Geometry("Z Arrow", zArrow);
        
        xArrow.setLineWidth(6);
        yArrow.setLineWidth(4);
        zArrow.setLineWidth(4);
        
        parentNode.attachChild(xGeo);
        parentNode.attachChild(yGeo);
        parentNode.attachChild(zGeo);
        
        xMat.setColor("Color", ColorRGBA.Red);
        yMat.setColor("Color", ColorRGBA.Green);
        zMat.setColor("Color", ColorRGBA.Blue);
        
        xGeo.setMaterial(xMat);
        yGeo.setMaterial(yMat);
        zGeo.setMaterial(zMat);
        xGeo.setShadowMode(ShadowMode.Cast);
        yGeo.setShadowMode(ShadowMode.Cast);
        zGeo.setShadowMode(ShadowMode.Cast);

    }
}
