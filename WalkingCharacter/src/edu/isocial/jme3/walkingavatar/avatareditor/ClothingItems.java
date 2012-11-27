/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.jme3.walkingavatar.avatareditor;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.scene.Node;
import edu.isocial.jme3.walkingavatar.core.WalkingAvatarTest;
import java.util.HashMap;

/**
 *
 * @author justin
 */
public enum ClothingItems {
    INSTANCE;
    
    private static final String JEANS = "Jeans",
                                KHAKIS = "Khakis",
                                BROWN_PANTS = "Brown Pants",
                                GREEN_SHIRT = "Green Shirt",
                                BLUE_SHIRT = "Blue Shirt",
                                BROWN_SHIRT = "Brown Shirt",
                                GREEN_CLOAK = "Green Cloak",
                                BLUE_CLOAK = "Blue Cloak",
                                BROWN_CLOAK = "Brown Cloak";
   
    private static final Node jeans, khakis, brownPants, 
            greenShirt, blueShirt, brownShirt, 
            greenCloak, blueCloak, brownCloak;
    
    private static final Material defaultMaterial, jeansMat, khakisMat, greenMat, blueMat;
    
    private static final HashMap<String, Node> clothingItems = new HashMap();
    
    private static final AssetManager assetManager;
    
    static{
        assetManager = WalkingAvatarTest.getInstance().getAssetManager();
        
        defaultMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        
        brownPants = (Node)assetManager.loadModel("Models/malebuilder_pants.j3o");
        
        jeans = (Node) brownPants.clone();
        jeansMat = defaultMaterial.clone();
        jeansMat.setTexture("ColorMap", assetManager.loadTexture("Textures/jeans.jpeg"));
        jeans.setMaterial(jeansMat);
        
        khakis = (Node) brownPants.clone();
        khakisMat = defaultMaterial.clone();
        khakisMat.setTexture("ColorMap", assetManager.loadTexture("Textures/khaki.jpeg"));
        khakis.setMaterial(khakisMat);
        
        brownShirt = (Node)assetManager.loadModel("Models/malebuilder_shirt.j3o");
        
        greenShirt = (Node)brownShirt.clone();
        greenMat = defaultMaterial.clone();
        greenMat.setTexture("ColorMap", assetManager.loadTexture("Textures/greenshirt.jpeg"));
        greenShirt.setMaterial(greenMat);
        
        blueShirt = (Node)brownShirt.clone();
        blueMat = defaultMaterial.clone();
        blueMat.setTexture("ColorMap", assetManager.loadTexture("Textures/blueshirt.jpeg"));
        blueShirt.setMaterial(blueMat);
        
        brownCloak = (Node)assetManager.loadModel("Models/malebuilder_cloak.j3o");
        
        greenCloak = (Node)brownCloak.clone();
        greenCloak.setMaterial(greenMat);
        
        blueCloak = (Node)brownCloak.clone();
        blueCloak.setMaterial(blueMat);
        
        clothingItems.put(JEANS, jeans);
        clothingItems.put(KHAKIS, khakis);
        clothingItems.put(BROWN_PANTS, brownPants);
        clothingItems.put(GREEN_SHIRT, greenShirt);
        clothingItems.put(BLUE_SHIRT, blueShirt);
        clothingItems.put(BROWN_SHIRT, brownShirt);
        clothingItems.put(GREEN_CLOAK, greenCloak);
        clothingItems.put(BLUE_CLOAK, blueCloak);
        clothingItems.put(BROWN_CLOAK, brownCloak);
    }

    
    public HashMap<String, Node> getClothingItems(){
        return clothingItems;
    }
}
