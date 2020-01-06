package org.TMH_engine.Collisions;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;


/**
 * Created by Zacki on 27.08.2017.
 */

public class CollisionBOX implements ContactListener {
    Collision_checker first=null;
    Collision_checker last=null;
    public CollisionBOX(){

    }
    public void add(Collision_checker neu){
        if(first==null){first=neu;}
        else if(last==null){last=neu;first.next=neu;}
        else if(last.next==null){last.next=neu;last=neu;}
    }
    void start(Contact col){
        for(Collision_checker v=first;v!=null;v=v.next){
            boolean ff=v.result;
            v.Touch_begin(col);
            if(v.result!=ff&&v.endONdifRESULT){break;}
        }
    }
    void stop(Contact col){
        for(Collision_checker v=first;v!=null;v=v.next){
            boolean ff=v.result;
            v.Touch_end(col);
            if(ff!=v.result&&v.endONdifRESULT){break;}
        }
    }

    @Override
    public void beginContact(Contact contact) {
        this.start(contact);
    }

    @Override
    public void endContact(Contact contact) {this.stop(contact);}

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }


}