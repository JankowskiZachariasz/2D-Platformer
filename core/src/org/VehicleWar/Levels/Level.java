package org.VehicleWar.Levels;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.World;
import org.VehicleWar.Levels.Chunks.Chunk;



public abstract class Level {

    public Chunk[] chunks;

    public void display(ShapeRenderer shape,Batch batch, Camera camera){
        landscape();
        solids(shape,batch, camera);
        Mobs();
    }
    private void landscape(){}
    private void solids(ShapeRenderer shape, Batch batch, Camera camera){



        for(int i=0;i<chunks.length;i++){
            chunks[i].display(shape,batch,camera);
        }

    }
    private void Mobs(){}
    public abstract void initialize(World world);
}
