/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.gameskeleton.core;


import com.jme.light.DirectionalLight;
import com.jme.light.LightNode;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.shape.Quad;
import com.jme.scene.state.RenderState;
import com.jme.scene.state.TextureState;
import com.jme.scene.state.ZBufferState;
import org.jdesktop.mtgame.*;

/**
 *
 * @author justin
 */
public class LightBuilder {
    
    private ShadowMapRenderBuffer shadowMapBuffer;
    private WorldManager worldManager;
    
    public LightBuilder(WorldManager wm, ShadowMapRenderBuffer shadowMapBuffer){
        this.worldManager = wm;
        this.shadowMapBuffer = shadowMapBuffer;
    }
    
      public void createGlobalLight() {
        Vector3f direction = new Vector3f(-1.0f, -1.0f, -1.0f);
        Vector3f position = new Vector3f(50.0f, 50.0f, 50.0f);
        
        //PointLight light = new PointLight();
        DirectionalLight light = new DirectionalLight();
        light.setDiffuse(new ColorRGBA(1.0f, 1.0f, 1.0f, 1.0f));
        //light.setAmbient(new ColorRGBA(0.5f, 0.5f, 0.5f, 1.0f));
        light.setEnabled(true);
        LightNode lightNode = new LightNode();
        lightNode.setLight(light);
        lightNode.setLocalTranslation(position);
        worldManager.getRenderManager().addLight(lightNode); 
        
        createShadowBuffer(direction, position);
        LightProcessor lp = new LightProcessor(worldManager, lightNode, shadowMapBuffer, (float)(1.0f * Math.PI / 180.0f));
        Entity lightEntity = new Entity("Light Rotator");
        lightEntity.addComponent(ProcessorComponent.class, lp);
        worldManager.addEntity(lightEntity);
    }

    private void createShadowBuffer(Vector3f dir, Vector3f pos) {
        int shadowWidth = 2048;
        int shadowHeight = 2048;
        Node shadowDebug = new Node("Shadow Debug");
        Quad shadowImage = new Quad("Shadow Quad", shadowWidth, shadowHeight);
        Entity shadowEntity = new Entity("Shadow Debug ");
        
        shadowDebug.attachChild(shadowImage);
        shadowDebug.setLocalTranslation(new Vector3f(0.0f, 0.0f, -350.0f));
        
        shadowMapBuffer = (ShadowMapRenderBuffer) worldManager.getRenderManager().createRenderBuffer(RenderBuffer.Target.SHADOWMAP, shadowWidth, shadowHeight);
        shadowMapBuffer.setCameraLookAt(new Vector3f());
        shadowMapBuffer.setCameraUp(new Vector3f(-1.0f, 1.0f, -1.0f));
        shadowMapBuffer.setCameraPosition(pos);
        shadowMapBuffer.setManageRenderScenes(true);

        worldManager.getRenderManager().addRenderBuffer(shadowMapBuffer);

        ZBufferState zBufferState = (ZBufferState) worldManager.getRenderManager().createRendererState(RenderState.StateType.ZBuffer);
        zBufferState.setEnabled(true);
        zBufferState.setFunction(ZBufferState.TestFunction.LessThanOrEqualTo);
        shadowDebug.setRenderState(zBufferState);
                
        TextureState textureState = (TextureState) worldManager.getRenderManager().createRendererState(RenderState.StateType.Texture);
        textureState.setEnabled(true);
        textureState.setTexture(shadowMapBuffer.getTexture(), 0);
        shadowDebug.setRenderState(textureState);
        
        RenderComponent renderComponent = worldManager.getRenderManager().createRenderComponent(shadowDebug);
        renderComponent.setLightingEnabled(false);
        shadowEntity.addComponent(RenderComponent.class, renderComponent);
//        worldManager.addEntity(e);      
    }
    
    ShadowMapRenderBuffer getShadowMapBuffer() {
        return shadowMapBuffer;
    }
    
    

}
