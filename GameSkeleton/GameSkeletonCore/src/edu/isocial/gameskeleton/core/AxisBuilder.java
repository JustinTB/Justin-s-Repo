/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.gameskeleton.core;

import com.jme.scene.Node;
import com.jme.scene.shape.AxisRods;
import com.jme.scene.state.RenderState;
import com.jme.scene.state.ZBufferState;
import org.jdesktop.mtgame.Entity;
import org.jdesktop.mtgame.RenderComponent;
import org.jdesktop.mtgame.WorldManager;

/**
 *
 * @author justin
 */
public class AxisBuilder {
    
    private WorldManager worldManager;
    private Entity axis;
    
    
    public AxisBuilder(WorldManager wm, Entity axis){
        this.worldManager = wm;
        this.axis = axis;
    }
    
    public void createAxis() {
        ZBufferState zBufferState = (ZBufferState) worldManager.getRenderManager().createRendererState(RenderState.StateType.ZBuffer);
        zBufferState.setEnabled(true);
        zBufferState.setFunction(ZBufferState.TestFunction.LessThanOrEqualTo);

        Node axisSG = new Node("Axis");
        AxisRods axisG = new AxisRods("Axis", true, 10.0f, 0.2f);
        axisSG.attachChild(axisG);
        axisSG.setRenderState(zBufferState);

        RenderComponent renderComponent = worldManager.getRenderManager().createRenderComponent(axisSG);
        renderComponent.setLightingEnabled(false);
        axis.addComponent(RenderComponent.class, renderComponent);
    }    
}