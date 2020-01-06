package org.VehicleWar.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

public class Terrain extends ObjectB2 {

    public Terrain(Body b, String photo){
        Texture img = new Texture(photo);
        sprite = new Sprite(img);
        sprite.setSize(28, 28);
        category=1;
        body=b;
    }

}
