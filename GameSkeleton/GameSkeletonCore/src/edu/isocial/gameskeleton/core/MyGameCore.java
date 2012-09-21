/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.gameskeleton.core;

import java.awt.Canvas;
import javax.swing.JOptionPane;
import org.jdesktop.mtgame.RenderBuffer;
import org.jdesktop.mtgame.WorldManager;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

/**
 *
 * @author justin
 */
@ConvertAsProperties(dtd = "-//edu.isocial.gameskeleton.core//sample//EN",
autostore = false)
@TopComponent.Description(preferredID = "sampleTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "edu.isocial.gameskeleton.core.sampleTopComponent")
@ActionReference(path = "Menu/Window" /*
 * , position = 333
 */)
@TopComponent.OpenActionRegistration(displayName = "#CTL_sampleAction",
preferredID = "sampleTopComponent")
@NbBundle.Messages({
    "CTL_sampleAction=Core Window",
    "CTL_sampleTopComponent=sample Window",
    "HINT_sampleTopComponent=This is a sample window"
})
public class MyGameCore extends GameSkeletonCORE{
    
    public MyGameCore(){
        super();
        
    }
    
    @Override
    protected void createUI(WorldManager worldManager, RenderBuffer renderBuffer, Canvas canvas, int width, int height) {
        DefaultGameFrame frame = new DefaultGameFrame(worldManager, renderBuffer, canvas, width, height);
        add(frame.getContentPane());
    }
    
    @Override
    protected void createScene(WorldManager worldManager) {
        System.out.println("CREATING SCENE!");
    }
    
    // <editor-fold defaultstate="collapsed" desc="Required Top Component Methods">
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
    //</editor-fold>
    
}
