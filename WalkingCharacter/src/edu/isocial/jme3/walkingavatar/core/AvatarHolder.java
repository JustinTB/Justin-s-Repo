/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.isocial.jme3.walkingavatar.core;

/**
 *
 * @author justin
 */
public class AvatarHolder {
    private static Avatar avatar;
    
    
    public static Avatar getAvatar() {
        return avatar;
    }

    public static void setAvatar(Avatar avatar) {
        AvatarHolder.avatar = avatar;
    }
    
}
