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

/**
 *
 * @author Marianne
 */
public class Game {

    private Scene scene;
    private boolean started;
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public Scene getScene() {
        return scene;
    }

    public Game() {
        //createInitialScreen(assetManager);
    }

    public void createInitialScreen(AssetManager assetManager, Node guiNode, BitmapFont guiFont) {
        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/ShowcardGothic.fnt");        
        
        BitmapText title = new BitmapText(guiFont, false);
        title.setSize(30);        
        title.setColor(ColorRGBA.Yellow);
        title.setText("MONKEY RUN");        
        title.setLocalTranslation(225, 300, 0);
        
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

    public void createScene(AssetManager assetManager, BulletAppState bulletAppState) {

        scene = new Scene(assetManager, bulletAppState);
        //Geração de Cenário
        this.getScene().generate();
        //Criação do Chão
        this.getScene().createFloor(6f, 15f);
        //Conversão da matriz em cenário
        this.getScene().show(2f, 1.875f);
    }

    public void createPlayer(AssetManager assetManager) {
        //player = new Player(position, rotation, bulletAppState, assetManager);
        player = new Player(assetManager);
    }

}
