package Handler;

import GameObjects.CoreGameObjects.ID;
import GameObjects.GameObject;
import GameObjects.CoreGameObjects.Hpbar;

import java.awt.*;
import java.util.LinkedList;

/**
 * The class for the game object handler It helps with keeping track of all present game objects
 */
public class Handler {
    /**
     * List of all game objects currently being rendered and updated
     */
    public LinkedList<GameObject> object = new LinkedList<GameObject>();
    private GameObject hpbar;


    /**
     * Updates all the game objects without rendering them.
     */
    public void tick(){
        for(int i = 0; i < object.size(); i++){
            GameObject tempObject = object.get(i);
            tempObject.tick();
        }
    }

    /**
     * Renders all the game objects
     * @param g graphics
     */
    public void render(Graphics g){
        for(int i = 0; i < object.size(); i++){
            GameObject tempObject = object.get(i);
            if(tempObject.getId() != ID.Hpbar){
                tempObject.render(g);
            }else{
                hpbar = tempObject;
            }
        }
        hpbar.render(g);
    }

    /**
     * Adds the game object to the handlers list of game objects
     * @param object
     */
    public void addObject(GameObject object){

        this.object.add(object);
    }

    /**
     * Removes the game object from the handlers list of game objects
     * @param object
     */
    public void removeObject(GameObject object){
        this.object.remove(object);
    }
}
