/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.gameskeleton.core;

import com.jme.scene.CameraNode;
import com.jme.scene.Node;
import java.awt.Canvas;
import org.jdesktop.mtgame.*;
import org.jdesktop.mtgame.processor.MouseSelectionProcessor;
import org.jdesktop.mtgame.processor.OrbitCameraProcessor;

/**
 *
 * @author justin
 */
public class CameraBuilder {

//    static MyMouseSelectionProcessor getMouseProcessor() {
//        return mouseSelector;
//    }

    private int width, height;
    private float aspectRatio;
    private RenderBuffer renderBuffer;
    private CameraNode cameraNode;
    private Canvas canvas;
    private static MouseSelectionProcessor mouseSelector;


    public CameraBuilder(int height, int width, float aspect, RenderBuffer rb, CameraNode cameraNode, Canvas canvas) {
        this.height = height;
        this.width = width;
        this.aspectRatio = aspect;
        this.renderBuffer = rb;
        this.cameraNode = cameraNode;
        this.canvas = canvas;

    }


    public void createCameraEntity(WorldManager wm) {
        Node cameraSG = createCameraGraph(wm);

        // Add the camera
        Entity cameraEntity = new Entity("DefaultCamera");
        CameraComponent cameraComponent = wm.getRenderManager().createCameraComponent(cameraSG, cameraNode,
                width, height, 45.0f, aspectRatio, 1.0f, 1000.0f, true);
        //CameraComponent cc = wm.getRenderManager().createCameraComponent(cameraSG, cameraNode, 
        //        width, height, 1.0f, 1000.0f, -100, 100, 100, -100, true);
        renderBuffer.setCameraComponent(cameraComponent);
        cameraEntity.addComponent(CameraComponent.class, cameraComponent);

        // Create the input listener and process for the camera
        int eventMask = InputManager.KEY_EVENTS | InputManager.MOUSE_EVENTS;
        AWTInputComponent cameraListener = (AWTInputComponent) wm.getInputManager().createInputComponent(canvas, eventMask);
        //FPSCameraProcessor eventProcessor = new FPSCameraProcessor(eventListener, cameraNode, wm, camera);
        OrbitCameraProcessor eventProcessor = new OrbitCameraProcessor(cameraListener, cameraNode, wm, cameraEntity);
        eventProcessor.setRunInRenderer(true);

        AWTInputComponent selectionListener = (AWTInputComponent) wm.getInputManager().createInputComponent(canvas, eventMask);
        mouseSelector = new MouseSelectionProcessor(selectionListener, wm, cameraEntity, cameraEntity, width, height, eventProcessor);
        //EyeSelectionProcessor selector = new EyeSelectionProcessor(selectionListener, wm, camera, camera, width, height, eventProcessor);
        //SelectionProcessor selector = new SelectionProcessor(selectionListener, wm, camera, camera, width, height, eventProcessor);

        mouseSelector.setRunInRenderer(true);

        ProcessorCollectionComponent processorCollectionComp = new ProcessorCollectionComponent();
        processorCollectionComp.addProcessor(eventProcessor);
        processorCollectionComp.addProcessor(mouseSelector);
        cameraEntity.addComponent(ProcessorCollectionComponent.class, processorCollectionComp);

        wm.addEntity(cameraEntity);
    }

    private Node createCameraGraph(WorldManager wm) {
        Node cameraSG = new Node("MyCamera SG");
        cameraNode = new CameraNode("MyCamera", null);
        cameraSG.attachChild(cameraNode);

        return (cameraSG);
    }
}
