/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.shadowmap.shadowmap_api;

import org.jdesktop.mtgame.ShadowMapRenderBuffer;
import org.jdesktop.mtgame.WorldManager;

/**
 *
 * @author justin
 */
public interface ShadowMapCoreAPI {
    public WorldManager getWorldManager();
    public ShadowMapRenderBuffer getShadowMapBuffer();
    public ShadowMapCoreAPI getInstance();
}
