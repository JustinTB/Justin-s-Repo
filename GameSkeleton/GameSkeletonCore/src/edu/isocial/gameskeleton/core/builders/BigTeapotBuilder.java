/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.gameskeleton.core.builders;

import com.jme.bounding.BoundingBox;
import com.jme.math.Triangle;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.shape.Teapot;
import com.jme.scene.state.CullState;
import com.jme.scene.state.MaterialState;
import com.jme.scene.state.RenderState;
import com.jme.scene.state.ZBufferState;
import org.jdesktop.mtgame.*;
import org.jdesktop.mtgame.processor.RotationProcessor;

/**
 *
 * @author justin
 */
public class BigTeapotBuilder {
    WorldManager worldManager;
    ShadowMapRenderBuffer shadowMapBuffer;
    
    public BigTeapotBuilder(WorldManager wm, ShadowMapRenderBuffer shadowMapBuffer){
        this.worldManager = wm;
        this.shadowMapBuffer = shadowMapBuffer;
    }

    public void createBigTeapot() {
        Node node = new Node();
        Teapot teapot = new Teapot();
        teapot.updateGeometryData();
        node.attachChild(teapot);
        node.setLocalScale(3.0f);

        Triangle[] tris = new Triangle[teapot.getTriangleCount()];

        BoundingBox bbox = new BoundingBox();
        bbox.computeFromTris(teapot.getMeshAsTriangles(tris), 0, tris.length);

        ColorRGBA color = new ColorRGBA();

        ZBufferState zBufferState = (ZBufferState) worldManager.getRenderManager().createRendererState(RenderState.StateType.ZBuffer);
        zBufferState.setEnabled(true);
        zBufferState.setFunction(ZBufferState.TestFunction.LessThanOrEqualTo);

        MaterialState matState = (MaterialState) worldManager.getRenderManager().createRendererState(RenderState.StateType.Material);
        matState.setDiffuse(color);

        CullState cullState = (CullState) worldManager.getRenderManager().createRendererState(RenderState.StateType.Cull);
        cullState.setEnabled(true);
        cullState.setCullFace(CullState.Face.Back);
        //node.setRenderState(cs);

        node.setRenderState(matState);
        node.setRenderState(zBufferState);
        node.setLocalTranslation(0.0f, 0.0f, 0.0f);
        teapot.setModelBound(bbox);
        teapot.setCullHint(Spatial.CullHint.Never);     
        
        Entity teapotEntity = new Entity("Teapot");
        RenderComponent renderComponent = worldManager.getRenderManager().createRenderComponent(node);
        teapotEntity.addComponent(RenderComponent.class, renderComponent);

        shadowMapBuffer.addRenderScene(renderComponent);
//        shadowMapBuffer.setRenderUpdater(new RenderUpdator(){});
        
        RotationProcessor rotationProcessor = new RotationProcessor("Teapot Rotator", worldManager,
                node, (float) (6.0f * Math.PI / 180.0f));
        //e.addComponent(ProcessorComponent.class, rp);
        worldManager.addEntity(teapotEntity);
    }
}
