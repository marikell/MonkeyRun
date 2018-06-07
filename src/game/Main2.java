/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
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
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import java.util.LinkedList;
import models.Element;
import models.Esfera;
import models.Game;
import models.Player;
import models.RigidBodyBox;

/**
 *
 * @author Marianne
 */
public class Main2 extends SimpleApplication implements ActionListener, PhysicsCollisionListener {

    private BulletAppState bulletAppState;
    private Game game;

    private RigidBodyBox floorBox;
    private Element element;

    private AnimChannel channel;
    private AnimControl control;
    private Node player;

    public static void main(String[] args) {
        Main2 app = new Main2();
        app.showSettings = false;
        app.start();
    }

    public void initGame() {
                initKeys();

        //Configurações da Câmera

        cam.getLocation().z +=33f;
        cam.getLocation().y+=2f;
        flyCam.setEnabled(false);

        //Inicialização do BulletAppState
        initBulletAppState();

        //Instância de um jogo
        game = new Game();
        game.createScene(assetManager, bulletAppState);

        //Instância do player
        game.createPlayer(assetManager, bulletAppState, new Vector3f(0, 10, 0), new Vector3f(0, 0, 0));        

        createLight(ColorRGBA.White);
        bulletAppState.setDebugEnabled(true);
        bulletAppState.getPhysicsSpace().addCollisionListener(this);

        drawElements();

    }

    private void drawElements() {
        LinkedList<Element> elements = (LinkedList<Element>) game.getScene().getElements();

        for (int i = 0; i < elements.size(); i++) {

            Spatial element = elements.get(i).getBox();

            rootNode.attachChild(elements.get(i).getBox());
        }
        

        //Desenhando o player        
        rootNode.attachChild(game.getPlayer().getNode());

    }


       public void initBulletAppState(){
       bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
    }

    private DirectionalLight createLight(ColorRGBA color) {
        viewPort.setBackgroundColor(color);
        DirectionalLight light = new DirectionalLight();
        light.setDirection(new Vector3f(-0.1f, -1f, -1).normalizeLocal());
        rootNode.addLight(light);
        return light;
    }

    @Override
    public void simpleInitApp() {

        initGame();
        rootNode.rotate(2.5f, 0, 0);


    }

    @Override
    public void simpleUpdate(float tpf) {

    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {

    }

    @Override
    public void collision(PhysicsCollisionEvent event) {

    }

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
                    player.move(-3f, 0, 0);
                }
            }
            if(name.equals("Right") && keyPressed){
                Node player = (Node)rootNode.getChild("monkey");
                if(player.getLocalTranslation().x < 1){
                    player.move(3f, 0, 0);
                }
            }            
        }
    };

}
