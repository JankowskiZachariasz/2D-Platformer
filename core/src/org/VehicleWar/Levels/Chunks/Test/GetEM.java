package org.VehicleWar.Levels.Chunks.Test;


import com.badlogic.gdx.physics.box2d.World;
import org.TMH_engine.Objects.JSONreader;
import org.VehicleWar.Levels.Chunks.Chunk;
import org.VehicleWar.Objects.ObjectB2;
import org.VehicleWar.Objects.Terrain;

public class GetEM {

public static Chunk[] bricks(World world){


    Chunk[] out = new Chunk[3];
    int dx=0;

    for(int i=0;i<3;i++) {
        Chunk x=null;
        switch(i)
        {
            case(0):{x=new Test1_1(world, dx);break;}
            case(1):{x=new Test1_2(world, dx);break;}
            case(2):{x=new Test1_3(world, dx);break;}
            case(3):{x=new Test1_1(world, dx);break;}


        }

        dx+=28;
        out[i]=x;
    }

    return out;
}
}

class Test1_1 extends Chunk {
public Test1_1(World world,int dx){
    objs = new ObjectB2[1];
    JSONreader Space_terrein = new JSONreader("testXD/test1XD.json", world);
    objs[0] = new Terrain(Space_terrein.getBody(dx-25, -10),"testXD/test1XD.png");


//    CircleShape ballShape = new CircleShape();
//    ballShape.setRadius(1.5f);
//    FixtureDef def = new FixtureDef();
//    def.restitution = 0.9f;
//    def.friction = 0.7f;
//    def.shape = ballShape;
//    def.density = 1f;
//    BodyDef boxBodyDef = new BodyDef();
//    boxBodyDef.type = BodyDef.BodyType.DynamicBody;
//    boxBodyDef.position.x = 0;
//    boxBodyDef.position.y = 0;
//    Body boxBody = world.createBody(boxBodyDef);
//    boxBody.createFixture(def);
//    objs[2] = new Terrain(Space_Car.getBody(dx, 0), "Car/Rover.png");


}
}
class Test1_2 extends Chunk {
    public Test1_2(World world, int dx){
        objs = new ObjectB2[1];
        JSONreader Space_terrein = new JSONreader("testXD/test2XD.json", world);
        objs[0] = new Terrain(Space_terrein.getBody(dx-25, -14),"testXD/test2XD.png");
    }
}
class Test1_3 extends Chunk {
    public Test1_3(World world, int dx){
        objs = new ObjectB2[1];
        JSONreader Space_terrein = new JSONreader("testXD/test3XD.json", world);
        objs[0] = new Terrain(Space_terrein.getBody(dx-25, -14),"testXD/test3XD.png");
    }
}
class Test1_4 extends Chunk {
    public Test1_4(World world, int dx){
        objs = new ObjectB2[1];
        JSONreader Space_terrein = new JSONreader("testXD/test1XD.json", world);
        objs[0] = new Terrain(Space_terrein.getBody(dx-25, -14),"testXD/test1XD.png");
    }
}