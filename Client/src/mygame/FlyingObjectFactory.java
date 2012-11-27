package mygame;

import com.jme3.math.Vector3f;

public class FlyingObjectFactory {

    public static FlyingObject createBullet(Vector3f direction, Vector3f position) {
        return new Bullet(direction, position, 350);
    }
    
    public static FlyingObject createTarget(Vector3f direction, Vector3f position) {
        return new Target(direction, position, 11);
    }
}
