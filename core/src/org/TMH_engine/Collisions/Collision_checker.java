package org.TMH_engine.Collisions;


import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;


/**
 * Created by Zacki on 22.05.2017.
 */


public class Collision_checker{
    Boolean endONdifRESULT=true;
    Collision_checker next=null;
    public Boolean result=false;
    Boolean multiBody=false;
    int counter=0;
    Body A=null;
    Body B=null;
    Exclude_collision not = null;
    public Collision_checker(Body AA,Body BB){
        if(BB==null){multiBody=false;A=AA;}
        else{multiBody=true;A=AA;B=BB;}
        not=new Exclude_collision();
    }
    public Collision_checker(Body AA,Body BB,boolean end){
        if(BB==null){multiBody=false;A=AA;}
        else{multiBody=true;A=AA;B=BB;}
        not=new Exclude_collision();
        endONdifRESULT=end;
    }
    public void Touch_begin(Contact contact){
        if(!not.onList(contact)) {
            if (multiBody) {

                if ((contact.getFixtureA().getBody() == A && contact.getFixtureB().getBody() == B) || (contact.getFixtureB().getBody() == A && contact.getFixtureA().getBody() == B)) {
                    counter++;
                }
            } else if (!multiBody) {
                if (contact.getFixtureA().getBody()== A || contact.getFixtureB().getBody() == A) {
                    counter++;
                }
            }
            result = counter > 0;
        }
    }
    public void Touch_end(Contact contact){
        if(!not.onList(contact)) {
            if (multiBody) {
                if ((contact.getFixtureA().getBody() == A && contact.getFixtureB().getBody() == B) || (contact.getFixtureB().getBody() == A && contact.getFixtureA().getBody() == B)) {
                    counter--;
                }
            } else if (!multiBody) {
                if (contact.getFixtureA().getBody() == A || contact.getFixtureB().getBody() == A) {
                    counter--;
                }
            }
            result = counter > 0;
        }
    }
    public void exclude(Body out){
        not.outspell(out);

    }
    public void excludeSensors(boolean g){
        not.sensorOUT=g;
    }
}
class Exclude_collision{
    Bodyholder first=null;
    Bodyholder last=null;
    boolean sensorOUT=false;
    public Exclude_collision(){}
    public void outspell(Body x){
        if(first==null){first=new Bodyholder(x);}
        else if(last==null){last=new Bodyholder(x);first.next=last; }
        else if(last.next==null){Bodyholder w = new Bodyholder(x); last.next=w; last=w;}
    }
    public Boolean onList(Contact n){
        boolean out=false;
        Body a =n.getFixtureA().getBody();
        Body b =n.getFixtureB().getBody();
        for(Bodyholder x=first; x != null;x=x.next){
            if(x.here==a||x.here==b){out=true;break;}

        }
        if(sensorOUT&&(a.getFixtureList().first().isSensor()||b.getFixtureList().first().isSensor())){
            out=true;
        }
        return out;
    }


}
class Bodyholder{
    Body here=null;
    Bodyholder next=null;
    public Bodyholder(Body z){here=z;}
}
