/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.shadowmap.properties;

/**
 *
 * @author justin
 */
public class Properties {
    private float xTranslation;
    private float yTranslation;
    private float zTranslation;
    private float xRotation;
    private float yRotation;
    private float zRotation;
    private float wRotation;

    public float getyRotation() {
        return yRotation;
    }

    public void setyRotation(float yRotation) {
        this.yRotation = yRotation;
    }

    public Properties() {
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setwRotation(float wRotation) {
        this.wRotation = wRotation;
    }

    public void setxRotation(float xRotation) {
        this.xRotation = xRotation;
    }

    public void setxTranslation(float xTranslation) {
        this.xTranslation = xTranslation;
    }

    public void setyTranslation(float yTranslation) {
        this.yTranslation = yTranslation;
    }

    public void setzRotation(float zRotation) {
        this.zRotation = zRotation;
    }

    public void setzTranslation(float zTranslation) {
        this.zTranslation = zTranslation;
    }

    public float getScale() {
        return scale;
    }

    public float getwRotation() {
        return wRotation;
    }

    public float getxRotation() {
        return xRotation;
    }

    public float getxTranslation() {
        return xTranslation;
    }

    public float getyTranslation() {
        return yTranslation;
    }

    public float getzRotation() {
        return zRotation;
    }

    public float getzTranslation() {
        return zTranslation;
    }
    private float scale;
}
