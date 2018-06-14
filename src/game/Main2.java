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
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import java.util.LinkedList;
import models.Element;
import models.Game;
import models.Jamie;
import models.RigidBodyBox;
import models.Scene;

/**
 *
 * @author Marianne
 */
public class Main2 extends SimpleApplication implements ActionListener, PhysicsCollisionListener {

    private BulletAppState bulletAppState;
    private Game game;
    private int scenarioControl = 0;
    private Jamie player;
    private AnimChannel channel;

    public static void main(String[] args) {
        Main2 app = new Main2();
        app.showSettings = false;
        app.start();
    }

    public void initGame() {    
        setDisplayFps(false);
        setDisplayStatView(false);
        createBackground();
        initKeys();        
        //Configurações da Câmera
        cam.getLocation().z += 33f;
        cam.getLocation().y += 2f;
        flyCam.setEnabled(false);
        //Inicialização do BulletAppState
        initBulletAppState();
        createLight(ColorRGBA.Black);
        bulletAppState.setDebugEnabled(true);
        bulletAppState.getPhysicsSpace().addCollisionListener(this);

        //Instância de um jogo
        game = new Game();
        game.createInitialScreen(assetManager, guiNode, guiFont);        
    }

    public void createBackground() {
        Box backMesh = new Box(35f, 35f, 1f);
        Vector2f scale = new Vector2f(5, 5);
        backMesh.scaleTextureCoordinates(scale);
        Geometry backGeo = new Geometry("Background", backMesh);
        Material backMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture backTex = assetManager.loadTexture("Textures/background.jpg");
        backTex.setWrap(WrapMode.Repeat);
        backMat.setTexture("ColorMap", backTex);
        backGeo.setMaterial(backMat);
        backGeo.rotate(1f, 0.65f, 1.5f);
        backGeo.move(0, 0, 0);
        rootNode.attachChild(backGeo);
    }

    private void drawElements() {

        for (int j = 0; j < game.getScenes().size(); j++) {

            Scene scene = game.getScenes().get(j);

            LinkedList<Element> elements = (LinkedList<Element>) scene.getElements();

            for (int i = 0; i < elements.size(); i++) {

                Spatial element = elements.get(i).getBox();
                rootNode.attachChild(elements.get(i).getBox());
                RigidBodyControl rigidBodyControl = new RigidBodyControl(0);
                elements.get(i).getBox().addControl(rigidBodyControl);
                bulletAppState.getPhysicsSpace().add(rigidBodyControl);
            }
        }     
        //Desenhando o player        
        rootNode.attachChild(game.getPlayer());
      
    }

    public void initBulletAppState() {
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
        rootNode.rotate(1.8f, 1.55f, 0);     
    }

    @Override
    public void simpleUpdate(float tpf) {
        if (game.getStatus()) {
            game.updateScore(guiNode);
            Spatial player = game.getPlayer();
//            Spatial player = game.getPlayer().getNode();
            Spatial floor = game.getScenes().get(game.getScenes().size() - 1).getFloor().getBox();

            if (game.getPlayer().getChild("player").getLocalTranslation().x > floor.getLocalTranslation().y + 6.7f) {
            } else {
                scenarioControl++;
                game.createScene(assetManager, bulletAppState, scenarioControl);
                drawElements();
            }


//            rootNode.getChild("monkey").move((-1) * game.getSpeed() * tpf, 0, 0);
//            game.getPlayer().getChild("player").move((-1) * game.getSpeed() * tpf, 0, 0);
        }

    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {

    }

    @Override
    public void collision(PhysicsCollisionEvent event) {
         if(event.getNodeA().getName().equals("monkey") || 
           event.getNodeB().getName().equals("monkey")){

            if(event.getNodeA().getName().contains("wall")){
                game.setStatus(false);
                System.out.println("colisao");                  
            }
            else
            if(event.getNodeB().getName().contains("wall")){
                game.setStatus(false);
            }
            
        }
    }

    private void initKeys() {
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping("Space", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(actionListener, "Space", "Left", "Right");
    }

    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("Space") && !game.getStatus()) {
                game.initGame(4);
                game.setStatus(true);
                game.createScene(assetManager, bulletAppState, scenarioControl);
                guiNode.detachAllChildren();
                game.createScore(assetManager, guiNode, guiFont);

                //Instância do player
                game.createPlayer(assetManager, cam, bulletAppState);
                rootNode.detachChildNamed("Background");
            }

            if (name.equals("Left") && keyPressed) {
                System.out.println("left");
                Node player = (Node) rootNode.getChild("monkey");                                
                
                if (game.getPlayer().getChild("player").getLocalTranslation().z > -2) {
//                    game.getPlayer().move(5, 5, 0); 
                    game.getPlayer().getChild("player").move(0, 0, 1);
                }                

            }

            if (name.equals("Right") && keyPressed) {
                Node player = (Node) rootNode.getChild("monkey");
                if (game.getPlayer().getChild("player").getLocalTranslation().z < 2) {
//                    player.move(3.7f, 0, 0);
                    game.getPlayer().getChild("player").move(0, 0, -1);
                }
            }
        }
    };

}
