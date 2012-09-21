/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.gameskeleton.core;

import edu.isocial.gameskeleton.core.builders.LightBuilder;
import edu.isocial.gameskeleton.core.builders.FloorBuilder;
import edu.isocial.gameskeleton.core.builders.CameraBuilder;
import edu.isocial.gameskeleton.core.builders.AxisBuilder;
import com.jme.scene.CameraNode;
import com.jme.scene.shape.Quad;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.util.concurrent.ExecutorService;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jdesktop.mtgame.*;
import org.openide.windows.TopComponent;

/**
 *
 * @author justin
 */
public abstract class GameSkeletonCORE extends TopComponent {

    private WorldManager worldManager = null;
    private CameraNode cameraNode = null;
    private Canvas canvas = null;
    
    private int desiredFrameRate = 60;
    
    //The width and height of our 3D window 
    private int width = 800;
    private int height = 600;
    private float aspectRatio = 800.0f / 600.0f;
    
    //Entity which represents the axis
    private Entity axisEntity = new Entity("Axis");
    
    private RenderBuffer renderBuffer;
    private ShadowMapRenderBuffer shadowMapBuffer = null;
    
    //Builders for the camera, axis, light, and the floor
    private CameraBuilder cameraBuilder;
    private AxisBuilder axisBuilder;
    private LightBuilder lightBuilder;
    private FloorBuilder floorBuilder;

    public GameSkeletonCORE() {
        ExecutorService executor = java.util.concurrent.Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        executor.submit(new Runnable() {
            public void run() {
                
                worldManager = new WorldManager("TestWorld");

                worldManager.getRenderManager().setDesiredFrameRate(desiredFrameRate);

                setupRenderBuffer();
                
                canvas = ((OnscreenRenderBuffer) renderBuffer).getCanvas(); 
                
                createUI(worldManager, renderBuffer, canvas, width, height);

                createDefaultSceneComponents();

                createScene(worldManager);
                
            }
        });
    }

    public void createDefaultSceneComponents() {
        cameraBuilder = new CameraBuilder(height, width, aspectRatio, renderBuffer, cameraNode, canvas);
        cameraBuilder.createCameraEntity(worldManager);

        axisBuilder = new AxisBuilder(worldManager, axisEntity);
        axisBuilder.createAxis();
        worldManager.addEntity(axisEntity);

        lightBuilder = new LightBuilder(worldManager, shadowMapBuffer);
        lightBuilder.createGlobalLight();
        shadowMapBuffer = lightBuilder.getShadowMapBuffer();

        floorBuilder = new FloorBuilder(worldManager, shadowMapBuffer);
        floorBuilder.createFloor();
    }

    protected abstract void createUI(WorldManager worldManager, RenderBuffer renderBuffer,
                                                Canvas canvas, int width, int height);

    protected abstract void createScene(WorldManager worldManager);
    
    private void setupRenderBuffer() {
        renderBuffer = worldManager.getRenderManager().createRenderBuffer(RenderBuffer.Target.ONSCREEN,
                width, height);
        worldManager.getRenderManager().addRenderBuffer(renderBuffer);      
    } 
}
