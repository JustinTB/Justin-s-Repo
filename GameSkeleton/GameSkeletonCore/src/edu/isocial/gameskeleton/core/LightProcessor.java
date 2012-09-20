/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.gameskeleton.core;

import com.jme.light.LightNode;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import org.jdesktop.mtgame.*;

/**
 *
 * @author justin
 */
public class LightProcessor extends ProcessorComponent {

        private WorldManager worldManager = null;
        private float degrees = 0.0f;
        private float increment = 0.0f;
        private Quaternion quaternion = new Quaternion();
        private LightNode target = null;
        private ShadowMapRenderBuffer shadowMapBuffer = null;
        Vector3f position = new Vector3f(50.0f, 50.0f, 50.0f);
        Vector3f positionOut = new Vector3f(50.0f, 50.0f, 50.0f);
        Vector3f up = new Vector3f(-1.0f, 1.0f, -1.0f);
        Vector3f upOut = new Vector3f(-1.0f, 1.0f, -1.0f);

        public LightProcessor(WorldManager worldManager, LightNode ln, ShadowMapRenderBuffer sb, float increment) {
            this.worldManager = worldManager;
            this.target = ln;
            this.increment = increment;
            this.shadowMapBuffer = sb;
            setArmingCondition(new NewFrameCondition(this));
        }

    @Override
        public void initialize() {
        }

    @Override
        public void compute(ProcessorArmingCollection collection) {
            degrees += increment;
            quaternion.fromAngles(0.0f, degrees, 0.0f);
            quaternion.mult(up, upOut);
            quaternion.mult(position, positionOut);

        }

    @Override
        public void commit(ProcessorArmingCollection collection) {
            target.setLocalTranslation(positionOut);
            worldManager.addToUpdateList(target);
            shadowMapBuffer.setCameraPosition(positionOut);
            shadowMapBuffer.setCameraUp(upOut);
        }
    }
