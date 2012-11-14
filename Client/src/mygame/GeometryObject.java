/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.scene.Node;

/**
 *
 * @author Vincent Séguin
 */
public abstract class GeometryObject {
    
    protected AssetManager assetManager;
    protected Node rootNode;
    protected BulletAppState appState;
    
    public GeometryObject(AssetManager assetManager, Node rootNode, BulletAppState state) {
        this.assetManager = assetManager;
        this.rootNode = rootNode;
        this.appState = state;
    }
    
    protected abstract void instantiateObject();
    
}
