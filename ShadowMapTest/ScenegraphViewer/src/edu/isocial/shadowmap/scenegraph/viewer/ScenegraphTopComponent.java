/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.shadowmap.scenegraph.viewer;

import com.jme.scene.Node;
import edu.isocial.shadowmap.sgvapi.ScenegraphViewerAPI;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
//import javax.media.j3d.Node;
import javax.swing.text.Position;
import javax.swing.tree.*;
import org.jdesktop.mtgame.Entity;
import org.jdesktop.mtgame.RenderComponent;
import org.jdesktop.mtgame.WorldManager;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Lookup;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.ServiceProvider;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//edu.isocial.shadowmap.scenegraph.viewer//Viewer//EN",
autostore = false)
@TopComponent.Description(preferredID = "ViewerTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "explorer", openAtStartup = false)
@ActionID(category = "Window", id = "edu.isocial.shadowmap.scenegraph.viewer.ViewerTopComponent")
@ActionReference(path = "Menu/Window" /*
 * , position = 333
 */)
@TopComponent.OpenActionRegistration(displayName = "#CTL_ViewerAction",
preferredID = "ViewerTopComponent")
@Messages({
    "CTL_ViewerAction=Viewer",
    "CTL_ViewerTopComponent=Viewer Window",
    "HINT_ViewerTopComponent=This is a Viewer window"
})
@ServiceProvider(service=ScenegraphViewerAPI.class)
public final class ScenegraphTopComponent extends TopComponent implements ScenegraphViewerAPI{

//    private ScenegraphPresenter scenegraphPresenter;
    private DefaultMutableTreeNode top = new DefaultMutableTreeNode("Scenegraph");
     private MyTreeSelectionListener treeSelectionListener;   
    
    public ScenegraphTopComponent() {
        initComponents();
        setName(Bundle.CTL_ViewerTopComponent());
        setToolTipText(Bundle.HINT_ViewerTopComponent());
        refreshScenegraph();
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        entityTree = new javax.swing.JTree(top);
        jButton1 = new javax.swing.JButton();

        jScrollPane1.setViewportView(entityTree);

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(ScenegraphTopComponent.class, "ScenegraphTopComponent.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jButton1)
                        .addGap(0, 52, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(61, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        refreshScenegraph(); 
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree entityTree;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        entityTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        treeSelectionListener = new MyTreeSelectionListener(entityTree);
        entityTree.addTreeSelectionListener(treeSelectionListener);
//        scenegraphPresenter = new ScenegraphPresenter(this);
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
    
    
    public JTree getEntityTree() {
        return entityTree;
    }

    DefaultMutableTreeNode getTop() {
        return top;
    }
    
    private WorldManager wm() {
        return WorldManager.getDefaultWorldManager();
    }

    
    
    
    private void processGraph(Entity e, DefaultMutableTreeNode ent) {
        int numberOfChildren = e.numEntities();
        System.out.println("IN PROCESS GRAPH");
        for(int i = 0; i < numberOfChildren; i++) {
            System.out.println("CHILD");
            DefaultMutableTreeNode newEntity = new DefaultMutableTreeNode(e.getEntity(i));
            ent.add(newEntity);
            processGraph(e.getEntity(i), newEntity);
        }
   
        
    }

    @Override
    public void refreshScenegraph() {
        System.out.println("REFRESHED");
        top.removeAllChildren();
        top = new DefaultMutableTreeNode("Scenegraph");
        int numberOfEntities = wm().numEntities();
        System.out.println("NUMBER OF ENTITIES: " + numberOfEntities);
        List<Entity> entities = new ArrayList<Entity>();
        for(int i = 0; i < numberOfEntities; i++) {
            DefaultMutableTreeNode newEntity = new DefaultMutableTreeNode(wm().getEntity(i));
            top.add(newEntity);
            processGraph(wm().getEntity(i), newEntity);
        }
        entityTree = new JTree(top);
        jScrollPane1.setViewportView(entityTree);
        entityTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        treeSelectionListener = new MyTreeSelectionListener(entityTree);
        entityTree.addTreeSelectionListener(treeSelectionListener);
    }


}
