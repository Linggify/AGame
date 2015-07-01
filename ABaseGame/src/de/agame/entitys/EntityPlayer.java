/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.agame.entitys;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import de.agame.entitys.animation.AnimationProvider;
import de.agame.entitys.sets.EnviromentObservationSet;
import de.agame.entitys.sets.SpatialControlSet;
import de.agame.entitys.sets.UserInterfaceSet;

/**
 *
 * @author Fredie
 */
public class EntityPlayer extends EntityCharacter implements ActionListener{
    
    private boolean m_left = false;
    private boolean m_right = false;
    private boolean m_forward = false;
    private boolean m_backward = false;
    
    private Vector3f m_walkdirection = new Vector3f();
    
    public EntityPlayer(AnimationProvider provider, Spatial spatial, SpatialControlSet spatialcontrolset, EnviromentObservationSet enviromentobservationset, UserInterfaceSet userinterfaceset) {
        super(provider, spatial, spatialcontrolset, enviromentobservationset, userinterfaceset);
        
    }
    
    @Override
    public boolean mayDespawn() {
        return false;
    }
    
    @Override
    public void onAttach() {
        super.onAttach();
        
        InputManager inputmanager = m_userinterfaceset.getInputManager();
        
        inputmanager.addMapping("forward", new KeyTrigger(KeyInput.KEY_W));
        inputmanager.addMapping("backward", new KeyTrigger(KeyInput.KEY_S));
        inputmanager.addMapping("left", new KeyTrigger(KeyInput.KEY_A));
        inputmanager.addMapping("right", new KeyTrigger(KeyInput.KEY_D));
        inputmanager.addMapping("jump", new KeyTrigger(KeyInput.KEY_SPACE));
        inputmanager.addMapping("sprint", new KeyTrigger(KeyInput.KEY_LSHIFT));
        inputmanager.addMapping("crouch", new KeyTrigger(KeyInput.KEY_LCONTROL));
        inputmanager.addMapping("attack", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputmanager.addMapping("block", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        
        inputmanager.addListener(this, "forward", "backward", "left", "right", "jump", "sprint", "crouch", "attack", "block");
        
    }
    
    @Override
    public void onDetach() {
        super.onDetach();
        
        InputManager inputmanager = m_userinterfaceset.getInputManager();
        
        inputmanager.deleteMapping("forward");
        inputmanager.deleteMapping("backward");
        inputmanager.deleteMapping("left");
        inputmanager.deleteMapping("right");
        inputmanager.deleteMapping("jump");
        inputmanager.deleteMapping("sprint");
        inputmanager.deleteMapping("crouch");
        inputmanager.deleteMapping("attack");
        inputmanager.deleteMapping("block");
        
        inputmanager.removeListener(this);
        
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if(name.equals("forward")) m_forward = isPressed;
        else if(name.equals("backward")) m_backward = isPressed;
        else if(name.equals("left")) m_left = isPressed;
        else if(name.equals("right")) m_right = isPressed;
        
        else if(name.equals("sprint")) {
            if(isPressed) getMovementManager().sprint();
            else getMovementManager().walk();
        }
        else if(name.equals("crouch")) {
            if(isPressed) getMovementManager().crouch();
            else getMovementManager().unCrouch();
        }
        
        else if(name.equals("jump") && isPressed) getMovementManager().jump();
        
        else if(name.equals("attack") && isPressed) attack();
        
        else if(name.equals("block") && isPressed) block();
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        super.simpleUpdate(tpf);
        
        Vector3f camDir = m_userinterfaceset.getCam().getDirection();
        Vector3f camLeft = m_userinterfaceset.getCam().getLeft();
        camDir.setY(0);
        camLeft.setY(0);
        camDir.normalizeLocal();
        camLeft.normalizeLocal();
        
        m_walkdirection.set(0, 0, 0);
        
        if(m_left) m_walkdirection.addLocal(camLeft);
        if(m_right) m_walkdirection.addLocal(camLeft.negate());
        if(m_forward) m_walkdirection.addLocal(camDir);
        if(m_backward) m_walkdirection.addLocal(camDir.negate());
        
        setWalkDirection(m_walkdirection);
    }
}
