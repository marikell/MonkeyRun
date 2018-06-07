/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author Marianne
 */
public class Esfera extends Geometry{
    
        private RigidBodyControl rigidBodyControl;
        private BulletAppState bulletAppState;

    public Esfera(String name, AssetManager asset, BulletAppState bulletAppState){
      super(name);
      
      Material mat = new Material(asset, "Common/MatDefs/Misc/Unshaded.j3md");
      mat.setColor("Color", ColorRGBA.Magenta);
      super.setMaterial(mat);
      
      Sphere b = new Sphere(3, 200, 1f);
      super.setMesh(b);
      this.bulletAppState = bulletAppState;
    
    }
    
     public void initRigidBody(float mass){
        rigidBodyControl = new RigidBodyControl(mass);
        this.addControl(rigidBodyControl);
        bulletAppState.getPhysicsSpace().add(rigidBodyControl);
    }
}
