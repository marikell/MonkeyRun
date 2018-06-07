package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

public class Main extends SimpleApplication implements AnimEventListener, PhysicsCollisionListener {

    private AnimChannel channel;
    private AnimControl control;
    private Node player;   

    public static void main(String[] args) {
        Main app = new Main();
        app.setShowSettings(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Vector3f camera = new Vector3f();
        camera.x = 0;
        camera.y = 0;
        camera.z = 5;
        Quaternion cameraRot = new Quaternion();
        cameraRot.set(0, 1, 0, 0);
        viewPort.setBackgroundColor(ColorRGBA.LightGray);

        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(new Vector3f(-0.1f, -1f, -1).normalizeLocal());
        rootNode.addLight(dl);
        player = (Node) assetManager.loadModel("Models/Jaime/Jaime.j3o");
        player.setLocalScale(2f);
        player.setName("monkey");
        player.rotate(0, FastMath.PI, 0);
        rootNode.attachChild(player);

        control = player.getControl(AnimControl.class);
        control.addListener(this);

        channel = control.createChannel();
        channel.setAnim("Run", 0.05f);

        initKeys();
        
        cam.setLocation(camera);
        //cam.setRotation(cameraRot);
        flyCam.setDragToRotate(true);//nao movimenta a camera
    }

    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {

    }

    @Override
    public void simpleUpdate(float ftp) {

    }

    @Override
    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
        
    }

    /**
     * Custom Keybinding: Map named actions to inputs.
     */
    private void initKeys() {
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping("Space", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(actionListener, "Space", "Left", "Right");
    }
    
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {
            if(name.equals("Space")){
                if (!channel.getAnimationName().equals("JumpStart")) {
                    channel.setAnim("JumpStart", 3f);
                    channel.setLoopMode(LoopMode.Loop);
                }
            }
            if (name.equals("Space") && !keyPressed) {
                if (!channel.getAnimationName().equals("Run")) {
                    channel.setAnim("Run", 1f);
                    channel.setLoopMode(LoopMode.Loop);
                }
            }
            if(name.equals("Left") && keyPressed){
                Node player = (Node)rootNode.getChild("monkey");
                if(player.getLocalTranslation().x > -1){
                    player.move(-1, 0, 0);
                }
            }
            if(name.equals("Right") && keyPressed){
                Node player = (Node)rootNode.getChild("monkey");
                if(player.getLocalTranslation().x < 1){
                    player.move(1, 0, 0);
                }
            }            
        }
    };

    @Override
    public void collision(PhysicsCollisionEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

