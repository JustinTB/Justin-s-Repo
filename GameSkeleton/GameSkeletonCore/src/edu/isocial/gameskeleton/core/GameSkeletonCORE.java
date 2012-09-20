/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.gameskeleton.core;

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
    private int desiredFrameRate = 60;
    
    //The width and height of our 3D window 
    private int width = 800;
    private int height = 600;
    private float aspectRatio = 800.0f / 600.0f;
    
    //The entities which represent the grid and axis
    private Entity axisEntity = new Entity("Axis");
    private Canvas canvas = null;
    
    private RenderBuffer renderBuffer;
    private ShadowMapRenderBuffer shadowMapBuffer = null;
    private RenderComponent floorRenderComponent = null;
    private Quad quadGeo = null;
    
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


                createUI(worldManager, renderBuffer, width, height);

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

        floorBuilder = new FloorBuilder(worldManager, floorRenderComponent, quadGeo, shadowMapBuffer);
        floorBuilder.createFloor();
    }

    protected abstract void createUI(WorldManager worldManager, RenderBuffer renderBuffer, 
                                        int width, int height);

    protected abstract void createScene(WorldManager worldManager);
    
    class SwingFrame extends JFrame implements FrameRateListener{

        JPanel contentPane;
        JPanel canvasPanel = new JPanel();
        JPanel statusPanel = new JPanel();
        JLabel fpsLabel = new JLabel("FPS: ");

        // Construct the frame
        public SwingFrame(WorldManager wm) {

            contentPane = (JPanel) this.getContentPane();
            contentPane.setLayout(new BorderLayout());


            renderBuffer = wm.getRenderManager().createRenderBuffer(RenderBuffer.Target.ONSCREEN, width, height);
            wm.getRenderManager().addRenderBuffer(renderBuffer);
            canvas = ((OnscreenRenderBuffer) renderBuffer).getCanvas();
            canvas.setVisible(true);
            canvas.setBounds(0, 0, width, height);

            wm.getRenderManager().setFrameRateListener(this, 100);
            canvasPanel.setLayout(new GridBagLayout());

            canvasPanel.add(canvas);
            contentPane.add(canvasPanel, BorderLayout.CENTER);


            // The status panel
            statusPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            statusPanel.add(fpsLabel);
            contentPane.add(statusPanel, BorderLayout.SOUTH);

            pack();
        }

        
        //Listen for frame rate updates
        @Override
        public void currentFramerate(float framerate) {
            fpsLabel.setText("FPS: " + framerate);
        }

    }

   
}
