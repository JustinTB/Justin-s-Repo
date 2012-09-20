/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.gameskeleton.core;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jdesktop.mtgame.FrameRateListener;
import org.jdesktop.mtgame.OnscreenRenderBuffer;
import org.jdesktop.mtgame.RenderBuffer;
import org.jdesktop.mtgame.WorldManager;

/**
 *
 * @author justin
 */
public class DefaultGameFrame extends JFrame implements FrameRateListener {

    private JPanel contentPane;
    private Canvas canvas;
    private JPanel canvasPanel = new JPanel();
    private JPanel statusPanel = new JPanel();
    private JLabel fpsLabel = new JLabel("FPS: ");

    // Construct the frame
    public DefaultGameFrame(WorldManager worldManager, 
                            RenderBuffer renderBuffer, 
                            int width, 
                            int height) {

        contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(new BorderLayout());


        renderBuffer = createRenderBuffer(worldManager, width, height);
        worldManager.getRenderManager().addRenderBuffer(renderBuffer);
        canvas = ((OnscreenRenderBuffer) renderBuffer).getCanvas();
        canvas.setVisible(true);
        canvas.setBounds(0, 0, width, height);

        worldManager.getRenderManager().setFrameRateListener(this, 100);
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

    private RenderBuffer createRenderBuffer(WorldManager worldManager, int width, int height) {
        return worldManager.getRenderManager().createRenderBuffer(RenderBuffer.Target.ONSCREEN,
                width, height);
    }
}
