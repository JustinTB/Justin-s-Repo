/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.shadowmap.addshape;

import com.jme.scene.Node;

/**
 *
 * @author justin
 */
public interface ShapeNode {
    public Node createNode(float x, float y, float z, boolean transparent, String shape);
}
