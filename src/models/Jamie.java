/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.ChaseCamera;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.control.CameraControl;

/**
 *
 * @author Marianne
 */
public class Jamie extends Node implements AnimEventListener{

   
    private final BetterCharacterControl physicsCharacter;
    private final AnimControl animationControl;
    private final AnimChannel animationChannel;
    private Vector3f walkDirection = new Vector3f(0, 0, 0);
    private Vector3f viewDirection = new Vector3f(0, 0, 0);
    private float airTime;

    public Jamie(String name,AssetManager assetManager, BulletAppState bulletAppState, Camera cam) {
        super(name);
    
        Node oto = (Node) assetManager.loadModel("Models/Jaime/Jaime.j3o");
        oto.rotate(0f,(float) -Math.PI/2, 0);
        oto.setLocalTranslation(0, 0, 0);
        scale(3f);
        setLocalTranslation(0, 2, 0);
        attachChild(oto);
        
        
        physicsCharacter = new BetterCharacterControl(1, 3f, 16f);
        addControl(physicsCharacter);
        
        bulletAppState.getPhysicsSpace().add(physicsCharacter);
        
        animationControl = oto.getControl(AnimControl.class);
        animationChannel = animationControl.createChannel();

       
        
        ChaseCamera camChase = new ChaseCamera(cam,this);
        camChase.setDownRotateOnCloseViewOnly(true);
        //camChase.setDefaultDistance(40);
    }
       
   
    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
    }

    @Override
    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
    }
    
}
