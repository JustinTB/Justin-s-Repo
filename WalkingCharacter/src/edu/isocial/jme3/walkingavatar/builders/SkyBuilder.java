/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.jme3.walkingavatar.builders;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.util.SkyFactory;

/**
 *
 * @author justin
 */
public class SkyBuilder {
    
    public static void buildSky(Node rootNode, AssetManager assetManager){
        rootNode.attachChild(SkyFactory.createSky(assetManager,
                "Textures/Sky/Bright/BrightSky.dds", false));
    }
}
