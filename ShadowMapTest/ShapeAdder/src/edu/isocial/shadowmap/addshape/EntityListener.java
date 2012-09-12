/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.shadowmap.addshape;

import edu.isocial.shadowmap.sgvapi.ScenegraphViewerAPI;
import java.util.Collection;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 *
 * @author justin
 */
public class EntityListener implements LookupListener{
  
    
    @Override
    public void resultChanged(LookupEvent le) {
        System.out.println("NEW ENTITY ADDED TO SCENEGRAPH: " + le.toString());
        ScenegraphViewerAPI scenegraphViewer = Lookup.getDefault().lookup(ScenegraphViewerAPI.class);
        scenegraphViewer.refreshScenegraph();
    }
    
}
