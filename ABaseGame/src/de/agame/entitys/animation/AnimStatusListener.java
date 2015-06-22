/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.animation;

/**
 *
 * @author Fredie
 */
public interface AnimStatusListener {
    
    public void onAnimStart();
    public void onAnimDone(boolean aborted);
    
    public void setEstimatedDuration(float duration);
}