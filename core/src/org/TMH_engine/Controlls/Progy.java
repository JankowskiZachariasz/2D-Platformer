package org.TMH_engine.Controlls;

/**
 * Created by Zacki on 22.08.2017.
 */


public class Progy{
    Clicker assig=null;
    boolean resetTODO=false;
    float ile=0f;
    boolean oddol=false;
    Progy next=null;
    Runnable effect=null;
    public Progy(float wartosc,Runnable outcome,boolean odDolu){
        ile=wartosc;
        effect=outcome;
        oddol=odDolu;
    }
    void add_prog(Progy nachst){next=nachst;}

}