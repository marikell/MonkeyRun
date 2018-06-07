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
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author Marianne
 */
public class Jamie extends Node implements AnimEventListener{

    private float posX;
    private float posY;
    private float posZ;
    private float rotX;
    private float rotY;
    private float rotZ;
    
    private Boolean isColliding;
    
    private Node nodeToLoadMesh;
    private RigidBodyControl rigidBodyControl;
    
    private AnimControl animationControl;
    private AnimChannel animationChannel;
    private BulletAppState bulletAppState;
    
    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getPosZ() {
        return posZ;
    }

    public void setPosZ(float posZ) {
        this.posZ = posZ;
    }
    
    public Jamie(String name,Vector3f position,Vector3f rotation, BulletAppState bulletAppState,AssetManager assetManager){
        super(name);
        this.name = name;
        this.posX = position.x;
        this.posY = position.y;
        this.posZ = position.z;
        this.rotX = rotation.x;
        this.rotY = rotation.y;
        this.rotZ = rotation.z;
        this.isColliding = false;
        this.nodeToLoadMesh = (Node) assetManager.loadModel("Models/Jaime/Jaime.j3o");
        this.bulletAppState = bulletAppState;
        
        this.animationControl = this.nodeToLoadMesh.getControl(AnimControl.class);
        this.animationChannel = animationControl.createChannel();
    }
    
    public void initEngine() {
        setLocalTranslation(new Vector3f(this.posX, this.posY, this.posZ));
        rotate(this.rotX, this.rotY, this.rotZ);
     
        attachChild(this.nodeToLoadMesh);
    }
    
    public void moveModelWithRigidBody(Vector3f value) throws Exception{
        super.move(value);
        if (rigidBodyControl == null) {
            throw new Exception("Colisão não iniciada");
        }
        refreshColliderPosition(value);
    }
    
    private void refreshColliderPosition(Vector3f value){
            if(rigidBodyControl == null){
                return;
            }
          rigidBodyControl.setPhysicsLocation(this.getLocalTranslation().add(value));

    }
    
    public void startAnimation(String animation, float time){
        animationControl.addListener(this);
        animationChannel.setAnim(animation, time);
    }
    
    public void startCollider(int mass, float radiusCollision, float heightCollision) {

        CapsuleCollisionShape shape = new CapsuleCollisionShape(radiusCollision,heightCollision);
        RigidBodyControl rigidBodyControl = new RigidBodyControl(shape,1);
        //rigidBodyControl.setFriction(0);
        this.rigidBodyControl = rigidBodyControl;
        this.addControl(rigidBodyControl);
        bulletAppState.getPhysicsSpace().add(rigidBodyControl);  

}
    
    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
    }

    @Override
    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
    }
    
}
