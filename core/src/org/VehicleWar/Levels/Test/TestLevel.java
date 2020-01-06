package org.VehicleWar.Levels.Test;

import com.badlogic.gdx.physics.box2d.World;

import org.VehicleWar.Levels.Chunks.Chunk;
import org.VehicleWar.Levels.Chunks.Test.GetEM;
import org.VehicleWar.Levels.Level;

public class TestLevel extends Level {
    public TestLevel(){}

    @Override
    public void initialize(World world) {

        chunks = GetEM.bricks(world);
    }
}
