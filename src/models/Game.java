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
    private float speed;
    private int level;

    public int getLevel() {
        return level;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Scene> getScenes() {
        return scenes;
    }
    
    public Game(float initialSpeed)
    {
        scenes = new ArrayList<>();
        speed = initialSpeed;
        level = 1;
    }
      
   public void createScene(AssetManager assetManager, BulletAppState bulletAppState, int scenarioControl){
        scenes.add(new Scene(assetManager,bulletAppState));
        Scene currScene =  this.getScenes().get(scenarioControl);
        //Geração de Cenário
        currScene.generate();
        //Deixando o espaço inicial(do meio) para o personagem.          
        if(scenarioControl == 0){
            if(currScene.getScene()[0][1] == 1){
                currScene.getScene()[0][1] = 0;
            }
        }
        //Criação do Chão
        currScene.createFloor(6f, 15f, new Vector3f(0,scenarioControl*(-20),0));   
        //Conversão da matriz em cenário
        currScene.show(2f, 1.875f, this.getScenes().get(scenarioControl).getFloor().getBox().getLocalTranslation());
        
        //Alteração na velocidade conforme novos cenários são instanciados.
        if(scenarioControl%6 == 0){
            speed = speed+1;
        //Quando altera a velocidade, o nível também.
        level = level +1;
        }
        
      
   }

    public float getSpeed() {
        return speed;
    }
   
   public void createPlayer(AssetManager assetManager, BulletAppState bulletAppState, Vector3f position, Vector3f rotation, Camera cam){
       //player = new Player(position, rotation, bulletAppState, assetManager);
       player = new Player(assetManager, cam);
   }
   
}
