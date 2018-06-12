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
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

/**
 *
 * @author Marianne
 */
public class Element extends Box {

    private RigidBodyControl rigidBodyControl;
    private float width;
    private float height;
    private float depth;
    private Material material;
    private Texture texture;
    private String name;
    private Vector3f position;
    private Vector3f rotation;
    private Geometry boxGeometry;
    private BulletAppState bulletAppState;

    public Geometry getBox() {
        return boxGeometry;
    }

    public Element(String name, BulletAppState bulletAppState, AssetManager assetManager, float width, float height, float depth, String defName, ColorRGBA color, Vector3f position, Vector3f rotation, String tex) {

        this.width = width;
        this.height = height;
        this.depth = depth;

        this.name = name;
        this.position = position;
        this.rotation = rotation;

        this.bulletAppState = bulletAppState;

        //material = new Material(assetManager, defName);
        material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //material.setBoolean("UseMaterialColors", true);
        //material.setColor("Ambient", color);
        //material.setColor("Diffuse", color);

        texture = assetManager.loadTexture(tex);
        texture.setWrap(Texture.WrapMode.Repeat);

        material.setTexture("ColorMap", texture);

        boxGeometry = new Geometry(name, this);
        boxGeometry.setMaterial(material);
        boxGeometry.setLocalTranslation(position);
        boxGeometry.rotate(rotation.x, rotation.y, rotation.z);

        updateGeometry(Vector3f.ZERO, width, height, depth);

    }

    public void initRigidBody(float mass) {
        rigidBodyControl = new RigidBodyControl(mass);
        boxGeometry.addControl(rigidBodyControl);
        bulletAppState.getPhysicsSpace().add(rigidBodyControl);
    }

}
