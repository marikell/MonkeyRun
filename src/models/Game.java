/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import java.util.ArrayList;

/**
 *
 * @author Marianne
 */
public class Game {
    private ArrayList<Scene> scenes;
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Scene> getScenes() {
        return scenes;
    }
    
    public Game()
    {
        scenes = new ArrayList<>();
    }
    
   public void createScene(AssetManager assetManager, BulletAppState bulletAppState, int scenarioControl){
        scenes.add(new Scene(assetManager,bulletAppState));
        //Geração de Cenário
        this.getScenes().get(scenarioControl).generate();
        //Criação do Chão
        this.getScenes().get(scenarioControl).createFloor(6f, 15f, new Vector3f(0,scenarioControl*(-20),0));   
        //Conversão da matriz em cenário
        this.getScenes().get(scenarioControl).show(2f, 1.875f, this.getScenes().get(scenarioControl).getFloor().getBox().getLocalTranslation());
   }
   
   public void createPlayer(AssetManager assetManager, BulletAppState bulletAppState, Vector3f position, Vector3f rotation, Camera cam){
       //player = new Player(position, rotation, bulletAppState, assetManager);
       player = new Player(assetManager, cam);
   }
   
}
