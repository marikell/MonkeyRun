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
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.ChaseCamera;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;

/**
 *
 * @author Marianne
 */
public class Player implements AnimEventListener {

    private boolean IsAlive;
    private RigidBodyControl rigidbody;
    private BetterCharacterControl physicsCharacter;
    private Node player;
    private AnimChannel channel;
    private AnimControl control;
    private ChaseCamera chaseCamera;
    
    public Player(AssetManager assetManager,Camera cam, BulletAppState bulletAppState){
        player = (Node) assetManager.loadModel("Models/Jaime/Jaime.j3o");
        player.setLocalScale(4f);
        player.setLocalTranslation(0, 12, -0.5f);
        player.setName("monkey");
        player.rotate(2, FastMath.PI, 0);

        control = player.getControl(AnimControl.class);
        control.addListener(this);

        channel = control.createChannel();
        channel.setAnim("Run", 0.05f);
        
        ChaseCamera camChase = new ChaseCamera(cam, player);
        camChase.setDownRotateOnCloseViewOnly(true);     
        
//        physicsCharacter = new BetterCharacterControl(1, 2.5f, 16f);
//        player.addControl(physicsCharacter);
//        bulletAppState.getPhysicsSpace().add(physicsCharacter);
    }

    public RigidBodyControl getRigidbody() {
        return rigidbody;
    }

    public void setRigidbody(RigidBodyControl rigidbody) {
        this.rigidbody = rigidbody;
    }

    public BetterCharacterControl getPhysicsCharacter() {
        return physicsCharacter;
    }

    public void setPhysicsCharacter(BetterCharacterControl physicsCharacter) {
        this.physicsCharacter = physicsCharacter;
    }

    public boolean isIsAlive() {
        return IsAlive;
    }

    public void setIsAlive(boolean IsAlive) {
        this.IsAlive = IsAlive;
    }

    public Node getNode() {
        return player;
    }

    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
    }

    @Override
    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
    }

}
