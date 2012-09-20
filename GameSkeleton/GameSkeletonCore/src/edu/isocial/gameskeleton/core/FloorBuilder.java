/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.gameskeleton.core;

import com.jme.bounding.BoundingBox;
import com.jme.math.Quaternion;
import com.jme.math.Triangle;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.shape.Quad;
import com.jme.scene.state.*;
import org.jdesktop.mtgame.*;

/**
 *
 * @author justin
 */
public class FloorBuilder {
    
    private ShadowMapRenderBuffer shadowMapBuffer;
    private RenderComponent orthoRC = null;
    private Quad quadGeo = null;
    private WorldManager worldManager;
    
    public FloorBuilder(WorldManager worldManager, RenderComponent orthoRC, Quad quadGeo, ShadowMapRenderBuffer shadowMapBuffer){
        this.worldManager = worldManager;
        this.quadGeo = quadGeo;
        this.orthoRC = orthoRC;
        this.shadowMapBuffer = shadowMapBuffer;
    }
    
     public void createFloor() {
        Node orthoQuad = new Node();
        quadGeo = new Quad("Ortho", 100, 100);
        Entity floorEntity = new Entity("Ortho ");
        
        orthoQuad.attachChild(quadGeo);
        Quaternion quat = new Quaternion();
        quat.fromAngleAxis((float)(Math.PI/2.0), new Vector3f(1.0f, 0.0f, 0.0f));
        orthoQuad.setLocalRotation(quat);

        ZBufferState zBufferState = (ZBufferState) worldManager.getRenderManager().createRendererState(RenderState.StateType.ZBuffer);
        zBufferState.setEnabled(true);
        zBufferState.setFunction(ZBufferState.TestFunction.LessThanOrEqualTo);
        orthoQuad.setRenderState(zBufferState);
                
        TextureState textureState = (TextureState) worldManager.getRenderManager().createRendererState(RenderState.StateType.Texture);
        textureState.setEnabled(true);
        textureState.setTexture(shadowMapBuffer.getTexture(), 0);
        quadGeo.setRenderState(textureState);
    
        BlendState discardShadowFragments = (BlendState)worldManager.getRenderManager().createRendererState(RenderState.StateType.Blend);
        discardShadowFragments.setEnabled(true);
        discardShadowFragments.setBlendEnabled(true);
        discardShadowFragments
                .setSourceFunction(BlendState.SourceFunction.SourceAlpha);
        discardShadowFragments
                .setDestinationFunction(BlendState.DestinationFunction.OneMinusSourceAlpha);
        quadGeo.setRenderState(discardShadowFragments);
        
        MaterialState darkMaterial = (MaterialState)worldManager.getRenderManager().createRendererState(RenderState.StateType.Material);
        darkMaterial.setEnabled(true);
        darkMaterial.setDiffuse(new ColorRGBA(0, 0, 0, 0.3f));
        darkMaterial.setAmbient(new ColorRGBA(0, 0, 0, 0f));
        darkMaterial.setShininess(0);
        darkMaterial.setSpecular(new ColorRGBA(0, 0, 0, 0));
        darkMaterial.setEmissive(new ColorRGBA(0, 0, 0, 0));
        darkMaterial.setMaterialFace(MaterialState.MaterialFace.Front);
        quadGeo.setRenderState(darkMaterial);
        
        orthoRC = worldManager.getRenderManager().createRenderComponent(orthoQuad);
        floorEntity.addComponent(RenderComponent.class, orthoRC);
        
//        RenderComponent sc = worldManager.getRenderManager().createRenderComponent(orthoQuad);
        JMECollisionSystem collisionSystem =
                (JMECollisionSystem)WorldManager
                    .getDefaultWorldManager()
                    .getCollisionManager()
                    .loadCollisionSystem(JMECollisionSystem.class);
        Triangle[] tris = new Triangle[quadGeo.getTriangleCount()];
        BoundingBox bbox = new BoundingBox();
        bbox.computeFromTris(quadGeo.getMeshAsTriangles(tris), 0, tris.length);
        orthoQuad.setModelBound(bbox);
        CollisionComponent cc = collisionSystem.createCollisionComponent(orthoQuad);
        floorEntity.addComponent(CollisionComponent.class, cc);
        
        
        worldManager.addEntity(floorEntity);
    }
}

