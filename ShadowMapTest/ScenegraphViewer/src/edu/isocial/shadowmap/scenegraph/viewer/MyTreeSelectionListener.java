/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.shadowmap.scenegraph.viewer;

import com.jme.scene.Node;
import edu.isocial.shadowmap.propertiesapi.PropertiesPresenterAPI;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import org.jdesktop.mtgame.Entity;
import org.jdesktop.mtgame.RenderComponent;
import org.openide.util.Lookup;

/**
 *
 * @author justin
 */
public class MyTreeSelectionListener implements TreeSelectionListener {

    private JTree entityTree;

    MyTreeSelectionListener(JTree entityTree) {
        this.entityTree = entityTree;
    }

    @Override
    public void valueChanged(TreeSelectionEvent tse) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) entityTree.getLastSelectedPathComponent();
        if (node == null || !node.isLeaf()) {
            return;
        } else {
            Entity entity = (Entity) node.getUserObject();
            Node sceneRoot = getSceneRootFromEntity(entity);
//            PropertiesPresenter.updatePropertiesWindow(sceneRoot.);  
//            System.out.println("SCENE ROOT: " + sceneRoot);
            if(sceneRoot != null){
                PropertiesPresenterAPI propertiesPresenter = Lookup.getDefault().lookup(PropertiesPresenterAPI.class);
                propertiesPresenter.updatePropertiesWindow(sceneRoot);
            }
            System.out.println("NODE SELECTED: " + node);
        }
    }

    private Node getSceneRootFromEntity(Entity e) {
        RenderComponent rc = e.getComponent(RenderComponent.class);

        if (rc == null) {
            return new Node();
        } else {
            return rc.getSceneRoot();
            
        }
    }
}
