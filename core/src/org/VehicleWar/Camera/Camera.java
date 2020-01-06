package org.VehicleWar.Camera;


import com.badlogic.gdx.graphics.OrthographicCamera;

import org.VehicleWar.Cars.Car;

public class Camera {
    OrthographicCamera cam;
    Car car;
    float zoom=1;
     public float goalzoom=1;

    public Camera(OrthographicCamera cam, Car car){
        this.cam=cam;
        this.car=car;
    }
    public void handle(){
        float x = car.marker.getPosition().x;
        float y = car.marker.getPosition().y;
        cam.zoom=zoom;
        cam.position.set(x+10*cam.zoom,y+2*cam.zoom,0);
        cam.update();
        zoomIteration();
        if(car.marker.getLinearVelocity().x>0)
        goalzoom=1+(float)Math.sqrt(Math.pow(car.marker.getLinearVelocity().x,2) + Math.pow(car.marker.getLinearVelocity().y,2))*0.6f;
    }
    private void zoomIteration(){
        if(zoom!=goalzoom){
            float delta=goalzoom-zoom;
            if(goalzoom>zoom){
                zoom+=delta/100;
            }
            else{
                zoom+=delta/200;
            }
        }
    }
}
