/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.shadowmap;


import com.jme.math.Ray;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.scene.Geometry;
import com.jme.scene.Node;
import edu.isocial.shadowmap.propertiesapi.PropertiesPresenterAPI;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import org.jdesktop.mtgame.*;
import org.jdesktop.mtgame.processor.MouseSelectionProcessor;
import org.jdesktop.mtgame.processor.OrbitCameraProcessor;
import org.openide.util.Lookup;


/**
 *
 * @author justin
 */
public class MyMouseSelectionProcessor extends MouseSelectionProcessor
{
    
    
        /**
     * Some objects used for selection
     */
    private Ray eyeRay = new Ray();
    private Vector3f origin = new Vector3f(0.0f, 0.0f, 0.0f);
    private Vector3f direction = new Vector3f(0.0f, 0.0f, -1.0f);
    private int halfWidth = 0;
    private int halfHeight = 0;
    private Camera jmeCamera = null;
    private OrbitCameraProcessor cameraProcessor = null;
    private Vector3f thru = new Vector3f();

    
    /**
     * An array list of entities for the currently visible bounds
     */
    private ArrayList visibleBounds = new ArrayList();
    private ArrayList glowList = new ArrayList();
       
    /**
     * The current camera entity
     */
    private Entity cameraEntity = null;
    private CameraComponent cameraComponent = null;
    int height = 0;
    
    /**
     * The WorldManager
     */
    private WorldManager worldManager = null;
    
    /**
     * The collision component for this processor
     */
    private JMECollisionSystem collisionSystem = null;
    
    private String shape;
    
     public MyMouseSelectionProcessor(AWTInputComponent listener, WorldManager wm, 
            Entity myEntity, Entity cameraEntity, int windowWidth, int windowHeight,
            OrbitCameraProcessor ocp) {
        super(listener, wm, myEntity, cameraEntity, windowWidth, windowHeight, ocp);
        worldManager = wm;
        collisionSystem = (JMECollisionSystem) 
                worldManager.getCollisionManager().loadCollisionSystem(JMECollisionSystem.class);
        cameraComponent = (CameraComponent) cameraEntity.getComponent(CameraComponent.class);
        
        this.cameraEntity = cameraEntity;     
        cameraComponent = (CameraComponent) cameraEntity.getComponent(CameraComponent.class);
        eyeRay.origin.set(origin);
        eyeRay.direction.set(direction);
        height = windowHeight;
        halfWidth = windowWidth/2;
        halfHeight = windowHeight/2;
        
        cameraProcessor = ocp;

    }
     
    @Override
    public void compute(ProcessorArmingCollection collection) {
        Object[] events = getEvents();
        
        for (int i=0; i<events.length; i++) {
            if (events[i] instanceof MouseEvent) {
                MouseEvent me = (MouseEvent) events[i];
                
                if (me.getID() == MouseEvent.MOUSE_MOVED || me.getID() == MouseEvent.MOUSE_CLICKED) {
                    int x = me.getX();
                    int y = me.getY();
                    cameraProcessor.getPosition(eyeRay.origin);
                    jmeCamera = cameraComponent.getCamera();
                    Vector2f screenCoord = new Vector2f(x, (halfHeight*2) - y);
                    jmeCamera.getWorldCoordinates(screenCoord, 0.0f, thru);
                    
                    if (jmeCamera.isParallelProjection()) {
                        eyeRay.direction.x = 0.0f;
                        eyeRay.direction.y = 0.0f;
                        eyeRay.direction.z = thru.z - eyeRay.origin.z;
                        eyeRay.origin.x = thru.x;
                        eyeRay.origin.y = thru.y;
                        eyeRay.origin.z = thru.z;
                        
                    } else {
                        eyeRay.direction.x = thru.x - eyeRay.origin.x;
                        eyeRay.direction.y = thru.y - eyeRay.origin.y;
                        eyeRay.direction.z = thru.z - eyeRay.origin.z;  
                    }
                    eyeRay.direction.normalizeLocal();
                    //clearVisibleBounds();
                    clearGlowList();
                    JMEPickInfo pickInfo = (JMEPickInfo) collisionSystem.pickAllWorldRay(eyeRay, true, false, true, cameraComponent);
//                    if(me.getID() == MouseEvent.MOUSE_CLICKED){
//                    shape = AddShapeTopComponent.getShape();
//                            shapeBuilder = ShadowMapTest.getShapeBuilder();
////                            Vector3f click = ;
//                            Vector3f pos = pickInfo.get(0).getPosition();
//                            shapeBuilder.createShape(pos.x, pos.y += 3, pos.z, false, "Box");
//                    }
//                    System.out.println(pickInfo.size() + " Geometries were picked");
                    for (int j = 0; j < pickInfo.size(); j++) {
                        JMEPickDetails pickData = (JMEPickDetails) pickInfo.get(j);
                        
                           
                        if (pickData.getPickData().getTargetMesh().getRenderQueueMode() !=
                                com.jme.renderer.Renderer.QUEUE_ORTHO) {
                            //addToVisibleBounds(pd.getPickData().getTargetMesh());
                            addToGlow(pickData.getPickData().getTargetMesh());
                        }
                        if(me.getID() == MouseEvent.MOUSE_CLICKED){
                            

//                            JOptionPane.showMessageDialog(null,"You picked " + pickData.getPickData().getTargetMesh());
                            RenderComponent renderComponent = pickData.getEntity().getComponent(RenderComponent.class);
                            Node sceneRoot = renderComponent.getSceneRoot();
                            sendPickDataToPropertiesWindow(sceneRoot);
                            
                        }
                    }
                }    
            }
        }

    }
    

    
    private void addToGlow(Geometry g) {
        g.setGlowEnabled(true);
        glowList.add(g);
    }

    private void clearGlowList() {
        for (int i=0; i<glowList.size(); i++) {
            Geometry g = (Geometry) glowList.get(i);
            g.setGlowEnabled(false);
        }
        glowList.clear();
    }

    private void sendPickDataToPropertiesWindow(Node sceneRoot) {
        PropertiesPresenterAPI propertiesPresenter = Lookup.getDefault().lookup(PropertiesPresenterAPI.class);
        propertiesPresenter.updatePropertiesWindow(sceneRoot);
    }
}
