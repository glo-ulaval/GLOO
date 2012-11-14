package mygame;

import com.jme3.math.Vector3f;

public class FlyingObjectFactory {

    public static FlyingObject createBullet(float speed, Vector3f direction, Vector3f position) {
        return new Bullet(direction, position, speed);
    }
}
