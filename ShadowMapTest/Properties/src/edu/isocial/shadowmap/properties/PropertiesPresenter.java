/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.shadowmap.properties;

import com.jme.image.Texture;
import com.jme.image.Texture.Type;
import com.jme.image.Texture.WrapAxis;
import com.jme.image.Texture.WrapMode;
import com.jme.intersection.CollisionResults;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Geometry;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.state.RenderState;
import edu.isocial.shadowmap.propertiesapi.PropertiesPresenterAPI;
import org.jdesktop.mtgame.JMEPickDetails;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author justin
 */
@ServiceProvider(service=PropertiesPresenterAPI.class)
public class PropertiesPresenter implements PropertiesPresenterAPI{

    private PropertiesTopComponent propertiesView;
    private Properties properties;
    private static Node shapeNode;
    
    public PropertiesPresenter(){
        
    }
    
    public PropertiesPresenter(PropertiesTopComponent propertiesView) {
        this.propertiesView = propertiesView;
        properties = new Properties();
    }
    
    void setPropertyValuesFromMesh() {
        properties.setxTranslation(shapeNode.getWorldTranslation().x);
        properties.setyTranslation(shapeNode.getWorldTranslation().y);
        properties.setzTranslation(shapeNode.getWorldTranslation().z);
        
        float[] angles = new float[] {0,0,0};
        shapeNode.getWorldRotation().toAngles(angles);
        
        properties.setxRotation(convertToDegrees(angles[0]));
        properties.setyRotation(convertToDegrees(angles[1]));
        properties.setzRotation(convertToDegrees(angles[2]));
        properties.setScale(shapeNode.getWorldScale().x);        
    }

    
    public void setPropertyValuesFromWindow(){
        properties.setxTranslation(propertiesView.getXTranslation());
        properties.setyTranslation(propertiesView.getYTranslation());
        properties.setzTranslation(propertiesView.getZTranslation());
        properties.setxRotation(propertiesView.getXRotation());
        properties.setyRotation(propertiesView.getYRotation());
        properties.setzRotation(propertiesView.getZRotation());
        properties.setScale(propertiesView.getScale());
    }

    public void applyPropertyValuesToMesh(){
        float xTranslation = properties.getxTranslation();//meshPropertiesMap.get(pickData.getTriMesh()).getxTranslation();
        float yTranslation = properties.getyTranslation();//meshPropertiesMap.get(pickData.getTriMesh()).getyTranslation();
        float zTranslation = properties.getzTranslation();//meshPropertiesMap.get(pickData.getTriMesh()).getzTranslation();
        float scale = properties.getScale();//meshPropertiesMap.get(pickData.getTriMesh()).getScale();
        float xRotation = properties.getxRotation();
        float yRotation = properties.getyRotation();
        float zRotation = properties.getzRotation();

        Vector3f newLocalTranslationVector = new Vector3f(xTranslation, yTranslation, zTranslation);
        Vector3f newLocalScaleVector = new Vector3f(scale, scale, scale);
        
        shapeNode.setLocalTranslation(newLocalTranslationVector);
        shapeNode.setLocalScale(newLocalScaleVector);
        shapeNode.getLocalRotation().x = convertToRadians(xRotation);
        shapeNode.getLocalRotation().y = convertToRadians(yRotation);
        shapeNode.getLocalRotation().z = convertToRadians(zRotation);
    }

    private void populatePropertyWindow() {
        propertiesView.setXTranslationSpinner(properties.getxTranslation());//setXTranslationSpinner(meshPropertiesMap.get(pickData.getTriMesh()).getxTranslation());
        propertiesView.setYTranslationSpinner(properties.getyTranslation());//setYTranslationSpinner(meshPropertiesMap.get(pickData.getTriMesh()).getyTranslation());
        propertiesView.setZTranslationSpinner(properties.getzTranslation());//setZTranslationSpinner(meshPropertiesMap.get(pickData.getTriMesh()).getzTranslation());
        propertiesView.setXRotationSpinner(properties.getxRotation());//setXRotationSpinner(meshPropertiesMap.get(pickData.getTriMesh()).getxRotation());
        propertiesView.setYRotationSpinner(properties.getyRotation());//setYRotationSpinner(meshPropertiesMap.get(pickData.getTriMesh()).getxRotation());
        propertiesView.setZRotationSpinner(properties.getzRotation());//setZRotationSpinner(meshPropertiesMap.get(pickData.getTriMesh()).getxRotation());
        propertiesView.setScaleTextField(properties.getScale());//setScaleTextField(meshPropertiesMap.get(pickData.getTriMesh()).getScale());
    }


    public void updatePropertiesWindow(Node shapeNode) {
            PropertiesPresenter.shapeNode = shapeNode;
            propertiesView.setShapeLabel("(" + shapeNode.getName() + ")");
            setPropertyValuesFromMesh();
            populatePropertyWindow();       
    }
      
    private static float convertToDegrees(float angle) {
        return (float)(angle * (180/Math.PI));
    }

    private float convertToRadians(float angle) {
        return (float)(angle * (Math.PI/180));
    }
}
