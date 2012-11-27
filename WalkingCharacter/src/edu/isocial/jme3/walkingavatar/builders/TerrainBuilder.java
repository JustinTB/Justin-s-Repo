/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.jme3.walkingavatar.builders;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;

/**
 *
 * @author justin
 */
public class TerrainBuilder {
    
    private static TerrainQuad terrain;
    private static Material terrainMat;
    
    public static void buildTerrain(Node rootNode, AssetManager assetManager, 
            BulletAppState bulletAppState, Camera camera){
        
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
        terrain.setLocalTranslation(0, -50, 0);
        terrain.setLocalScale(2f, 1f, 2f);
        terrain.setShadowMode(RenderQueue.ShadowMode.Receive);
        rootNode.attachChild(terrain);

        TerrainLodControl control = new TerrainLodControl(terrain, camera);
        terrain.addControl(control);
        terrain.addControl(new RigidBodyControl(0));
        bulletAppState.getPhysicsSpace().add(terrain);
        
        terrain.setShadowMode(RenderQueue.ShadowMode.Receive);

    }
}
