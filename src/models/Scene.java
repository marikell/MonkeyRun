package models;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Marianne
 * Se for 2, não foi inicializado ainda.
 * Se for 0, na matriz, o cenário não possui obstáculo.
 * Se for 1, possui obstáculo único
 */

public class Scene {
    
    private Queue<Element> elements;
    private int[][] scene;

    public Queue<Element> getElements() {
        return elements;
    }

    public Element getFloor() {
        return floor;
    }
    private Element floor;
            
    public Scene(){
        
       elements = new LinkedList<Element>();
       scene = new int[8][3];
    }  
    
            
    public void createFloor(float width, float height, AssetManager assetManager, BulletAppState bulletAppState)
    {
        floor = new Element("floor",bulletAppState,assetManager, width, height, 0.1f, "Common/MatDefs/Light/Lighting.j3md", ColorRGBA.Orange, new Vector3f(0,0,0), new Vector3f(0,0,0));
        floor.getBox().rotate(100f, 0, 0);
        elements.add(floor);
    }

    public void create(){
        
        
        
    }
    
    public void generate(){
        
        scene = new int[8][3];
                
        for(int i = 0; i<8;i++){
           scene[i] = rowGenerator();
        }        
    }
    
    private boolean verify(int[] numbers, int factor, int occurrences){
        
        int cont = 0;
        
        for (int i = 0; i < numbers.length; i++) {
            
            if(factor == numbers[i])
                cont++;           
        }
        
        return (cont <= occurrences);
        
    }
    
    private void initializeRow(int[] row){
         for (int i = 0; i < row.length; i++) {
            row[i] = 2;
        }
    }
    
    //Esse método serve para verificar se pelo menos tem um espaço livre para o personagem andar
    private int[] rowGenerator(){
        
        int[] row = new int[3];  
       
        Random random = new Random();        
        
       do{
           initializeRow(row);
            for (int i = 0; i < row.length; i++) {
            row[i] = random.nextInt(2);
        }
       }while(!verify(row, 1, 2));       

        return row;
    }   
    
}
