/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys.movement;

import com.jme3.scene.Spatial;
import de.agame.entitys.AnimLink;
import de.agame.entitys.EntityLivingAnimated;
import java.util.HashMap;

/**
 *
 * @author Fredie
 */
public interface MovementEvent {
    
    public void setAnims(AnimLink... anims);

    public String getName();
    
    /**
     * Puts all the params into the given HashMap to make sure they are available at runtime
     * @param params 
     */
    public void ensureParams(HashMap<String, Boolean> params);

    /**
     * checks if the movementevent should be executed for a given set of params
     * @param params
     * @return true if the event should be executed
     */
    public boolean executeEvent(HashMap<String, Boolean> params);
    
    /**
     * checks if the movement event is still executed for a given set of params
     * @param params
     * @return true if the movement event should be continued
     */
    public boolean onMovementChanged(HashMap<String, Boolean> params);
    
    /**
     * updates the entity according to the movement event
     * @param entity
     * @param spatial
     */
    public void onUpdate(float dt, EntityLivingAnimated entity, Spatial spatial);
    
    /**
     * Called when movementevent is started
     */
    public void onStartEvent(HashMap<String, Boolean> params, Spatial spatial, EntityLivingAnimated living);
    
    /**
     * Called when movement event is concluded
     * @return the following event (e.g. stumbling if roll was not successful)
     */
    public MovementEvent onEndEvent(HashMap<String, Boolean> params, Spatial spatial, EntityLivingAnimated living);
}