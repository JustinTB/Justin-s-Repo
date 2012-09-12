/*
 * Copyright (c) 2009, Sun Microsystems, Inc. All rights reserved.
 *
 *    Redistribution and use in source and binary forms, with or without
 *    modification, are permitted provided that the following conditions
 *    are met:
 *
 *  . Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  . Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *  . Neither the name of Sun Microsystems, Inc., nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDER AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package edu.isocial.shadowmap;

import com.jme.scene.CameraNode;
import com.jme.scene.shape.Quad;
import edu.isocial.shadowmap.shadowmap_api.ShadowMapCoreAPI;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jdesktop.mtgame.*;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;
import org.openide.windows.TopComponent;

/**
 * A World test application
 *
 * @author Doug Twilleager
 */
@TopComponent.Description(preferredID = "ShadowMapTest",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "edu.isocial.shadowmap.fdTopComponent")
@ActionReference(path = "Menu/Window" /*
 * , position = 333
 */)
@TopComponent.OpenActionRegistration(displayName = "#CTL_fdAction",
preferredID = "fdTopComponent")
@NbBundle.Messages({
    "CTL_fdAction=Shadow Map Test",
    "CTL_fdTopComponent=fd Window",
    "HINT_fdTopComponent=This is a fd window"
})
@ServiceProvider(service = ShadowMapCoreAPI.class)
public class ShadowMapCore extends TopComponent implements ShadowMapCoreAPI, RenderUpdater{

    private WorldManager worldManager = null;
    
    private CameraNode cameraNode = null;

    private int desiredFrameRate = 60;
    
    //The width and height of our 3D window 
    private int width = 800;
    private int height = 600;
    
    private float aspectRatio = 800.0f / 600.0f;

    private int gridWidth = 250;
    
    //The entities which represent the grid and axis
    private Entity gridEntity = new Entity("Grid");
    private Entity axisEntity = new Entity("Axis");

    
    private Canvas canvas = null;
    private RenderBuffer renderBuffer;
    private ShadowMapRenderBuffer shadowMapBuffer = null;
    private RenderComponent floorRenderComponent = null;
    private Quad quadGeo = null;
    
    //Builders for the camera, grid, axis, light, original teapot, and the floor
    private CameraBuilder cameraBuilder;
    private GridBuilder gridBuilder;
    private AxisBuilder axisBuilder;
    private LightBuilder lightBuilder;
    private BigTeapotBuilder bigTeapotBuilder;
    private FloorBuilder floorBuilder;

    private static ShadowMapCore shadowMapTest;
    
    public ShadowMapCore(){
        
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                worldManager = new WorldManager("TestWorld");
                
                worldManager.getRenderManager().setDesiredFrameRate(desiredFrameRate);
                

                createUI(worldManager);

                cameraBuilder = new CameraBuilder(height, width, aspectRatio, renderBuffer, cameraNode, canvas);
                cameraBuilder.createCameraEntity(worldManager);
                
                gridBuilder = new GridBuilder(gridWidth, gridEntity);
                gridBuilder.createGrid(worldManager);
//                worldManager.addEntity(grid);
                axisBuilder = new AxisBuilder(worldManager, axisEntity);
                axisBuilder.createAxis();
                worldManager.addEntity(axisEntity);
                
                lightBuilder = new LightBuilder(worldManager, shadowMapBuffer);
                lightBuilder.createGlobalLight();
                shadowMapBuffer = lightBuilder.getShadowMapBuffer();
                
                bigTeapotBuilder = new BigTeapotBuilder(worldManager, shadowMapBuffer);
                bigTeapotBuilder.createBigTeapot();

                
                floorBuilder = new FloorBuilder(worldManager, floorRenderComponent, quadGeo, shadowMapBuffer);
                floorBuilder.createFloor();
                
            }
        }).start();
        
        shadowMapTest = this;

    }
    
    public static ShadowMapCore getShadowMapTest(){
        return shadowMapTest;
    }


    //Create the Swing window and the 3D window
    private void createUI(WorldManager wm) {
        SwingFrame frame = new SwingFrame(wm);
        add(frame.contentPane);
    }

    @Override
    public void update(Object o) {
        ShadowMapRenderBuffer sb = (ShadowMapRenderBuffer) o;
    }

    @Override
    public ShadowMapCoreAPI getInstance() {
        return this;
    }

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
    
    @Override
    public WorldManager getWorldManager() {
        return worldManager;
    }
    
    @Override
    public ShadowMapRenderBuffer getShadowMapBuffer() {
        return shadowMapBuffer;
    }

    
    @Override
    public void componentOpened(){
        
    }

}