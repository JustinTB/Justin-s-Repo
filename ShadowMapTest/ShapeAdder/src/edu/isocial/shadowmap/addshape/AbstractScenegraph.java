/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.shadowmap.addshape;

import com.jme.bounding.BoundingBox;
import com.jme.math.Triangle;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.TriMesh;
import com.jme.scene.state.*;
import edu.isocial.shadowmap.ShadowMapCore;
import edu.isocial.shadowmap.shadowmap_api.ShadowMapCoreAPI;
import org.jdesktop.mtgame.*;
import org.openide.util.Lookup;

/**
 *
 * @author justin
 */
public abstract class AbstractScenegraph implements ShapeNode {
    
    private ShadowMapCoreAPI shadowMapCore;

    public AbstractScenegraph() {
//        shadowMapCore = Lookup.getDefault().lookup(ShadowMapCoreAPI.class);
    }
    
    public abstract TriMesh createMesh();

    @Override
    public Node createNode(float x, float y, float z, boolean transparent, String shape) {
        Lookup.getDefault().lookup(ShadowMapCoreAPI.class);//ShadowMapCore.getShadowMapTest();
        WorldManager worldManager = shadowMapCore.getWorldManager();
        ShadowMapRenderBuffer shadowMapBuffer = shadowMapCore.getShadowMapBuffer();
        Node node = new Node();
        TriMesh mesh = createMesh();
        mesh.setGlowEnabled(true);
        mesh.setGlowScale(new Vector3f(1.2f, 1.2f, 1.2f));
        node.attachChild(mesh);
        
        Triangle[] tris = new Triangle[mesh.getTriangleCount()];

        BoundingBox bbox = new BoundingBox();
        bbox.computeFromTris(mesh.getMeshAsTriangles(tris), 0, tris.length);
        ColorRGBA color = new ColorRGBA();

        ZBufferState buf = (ZBufferState) worldManager.getRenderManager().createRendererState(RenderState.StateType.ZBuffer);
        buf.setEnabled(true);
        buf.setFunction(ZBufferState.TestFunction.LessThanOrEqualTo);

        if (transparent) {
            BlendState as = (BlendState) worldManager.getRenderManager().createRendererState(RenderState.StateType.Blend);
            as.setEnabled(true);
            as.setBlendEnabled(true);
            as.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
            as.setDestinationFunction(BlendState.DestinationFunction.OneMinusSourceAlpha);
            node.setRenderState(as);

            CullState cs = (CullState) worldManager.getRenderManager().createRendererState(RenderState.StateType.Cull);
            cs.setEnabled(true);
            cs.setCullFace(CullState.Face.Back);
            node.setRenderState(cs);

            color.set(0.0f, 1.0f, 1.0f, 0.75f);
        }

        MaterialState matState = (MaterialState) worldManager.getRenderManager().createRendererState(RenderState.StateType.Material);
        matState.setDiffuse(color);

        node.setRenderState(matState);
        node.setRenderState(buf);
        node.setLocalTranslation(x, y, z);
        node.setModelBound(bbox);
        node.setName(shape);

        Entity shapeEntity = new Entity("Shape Entity");
        RenderComponent sc = worldManager.getRenderManager().createRenderComponent(node);
        shapeEntity.addComponent(RenderComponent.class, sc);

        shadowMapBuffer.addRenderScene(sc);
        shadowMapBuffer.setRenderUpdater(ShadowMapCore.getShadowMapTest());


        return (node);
    }
}
