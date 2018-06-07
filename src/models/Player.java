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
public class Player {
    
    private boolean IsAlive;   
    
    private Jamie player;
    
    public Player(Vector3f position, Vector3f rotation, BulletAppState bulletAppState, AssetManager assetManager){
    
    player = new Jamie("player",position,rotation, bulletAppState,assetManager);
        
    }    
     public boolean isIsAlive() {
        return IsAlive;
    }

    public void setIsAlive(boolean IsAlive) {
        this.IsAlive = IsAlive;
    }

    public Jamie getNode() {
        return player;
    }
    
    
}
