/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;

/**
 *
 * @author Marianne
 */
public class Game {
    private Scene scene;
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public Scene getScene() {
        return scene;
    }
    
    public Game()
    {
    }
    
   public void createScene(AssetManager assetManager, BulletAppState bulletAppState){
       scene = new Scene(assetManager,bulletAppState);
        //Geração de Cenário
        this.getScene().generate();
        //Criação do Chão
        this.getScene().createFloor(6f, 15f);   
        //Conversão da matriz em cenário
        this.getScene().show(2f, 1.875f);
   }
   
   public void createPlayer(AssetManager assetManager, BulletAppState bulletAppState, Vector3f position, Vector3f rotation){
       player = new Player(position, rotation, bulletAppState, assetManager);
   }
   
}
