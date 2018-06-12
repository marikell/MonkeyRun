/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.jme3.asset.AssetManager;

import com.jme3.bullet.BulletAppState;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.plugins.BitmapFontLoader;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

import com.jme3.renderer.Camera;
import java.util.ArrayList;

/**
 *
 * @author Marianne
 */
public class Game {

    private ArrayList<Scene> scenes;
    private Player player;
    private float speed;
    private int score = 0;
    private int level;
    private boolean started;

    public int getLevel() {
        return level;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Scene> getScenes() {
        return scenes;
    }

    public Game() {
        //createInitialScreen(assetManager);
    }

    public Game(float initialSpeed) {
        scenes = new ArrayList<>();
        speed = initialSpeed;
        level = 1;
    }

    public void initGame(float initialSpeed) {
        scenes = new ArrayList<>();
        speed = initialSpeed;
        level = 1;
    }

    public void createInitialScreen(AssetManager assetManager, Node guiNode, BitmapFont guiFont) {
        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/ComicSansMS.fnt");

        BitmapText title = new BitmapText(guiFont, false);
        title.setSize(40);
        title.setColor(ColorRGBA.Yellow);
        title.setText("MONKEY RUN");
        title.setLocalTranslation(190, 300, 0);

        BitmapText description = new BitmapText(guiFont, false);
        description.setSize(30);

        description.setColor(ColorRGBA.White);
        description.setText("Press 'Space' to Start!");
        description.setLocalTranslation(160, 250, 0);

        guiNode.attachChild(title);
        guiNode.attachChild(description);

    }

    public boolean getStatus() {
        return started;
    }

    public void setStatus(boolean status) {
        this.started = status;
    }

    public void createScene(AssetManager assetManager, BulletAppState bulletAppState, int scenarioControl) {
        scenes.add(new Scene(assetManager, bulletAppState));
        Scene currScene = this.getScenes().get(scenarioControl);
        //Geração de Cenário
        currScene.generate();
        //Deixando o espaço inicial(do meio) para o personagem.          
        if (scenarioControl == 0) {
            if (currScene.getScene()[0][1] == 1) {
                currScene.getScene()[0][1] = 0;
            }
        }
        currScene.createFloor(6f, 15f, new Vector3f(0, scenarioControl * (-20), 0));
        //Conversão da matriz em cenário
        currScene.show(2f, 1.875f, this.getScenes().get(scenarioControl).getFloor().getBox().getLocalTranslation());

        //Alteração na velocidade conforme novos cenários são instanciados.
        //if (scenarioControl % 6 == 0) {
            speed = speed + 0.5f;
            //Quando altera a velocidade, o nível também.
            level = level + 1;
            this.score++;
        //}

        /*//Criação do Chão
        this.getScene().createFloor(6f, 15f);
        //Conversão da matriz em cenário
        this.getScene().show(2f, 1.875f);*/
    }

    public void createPlayer(AssetManager assetManager, Camera cam, BulletAppState bulletAppState) {
        player = new Player(assetManager, cam, bulletAppState);
    }

    public float getSpeed() {
        return speed;
    }

    public void createScore(AssetManager assetManager, Node guiNode, BitmapFont guiFont) {
        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/ComicSansMS.fnt");
        
        BitmapText scoreText = new BitmapText(guiFont, false);
        scoreText.setSize(25);
        scoreText.setColor(ColorRGBA.White);
        scoreText.setText("Score: ");
        scoreText.setLocalTranslation(10, 460, 0);
        scoreText.setName("scoreText");
        guiNode.attachChild(scoreText);

        BitmapText scoreValue = new BitmapText(guiFont, false);
        scoreValue.setSize(25);
        scoreValue.setColor(ColorRGBA.White);
        scoreValue.setText("" + this.score);
        scoreValue.setLocalTranslation(75, 460, 0);
        scoreValue.setName("scoreValue");
        guiNode.attachChild(scoreValue);
    }

    public void updateScore(Node guiNode) {        
        BitmapText score = (BitmapText) guiNode.getChild("scoreValue");
        score.setText(" " + this.score);
    }
}
