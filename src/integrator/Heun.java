package integrator;

import java.util.ArrayList;

import entities.Entity;
import simulator.Simulation;
import vector.Vector;

public class Heun extends Integrator{

	public Heun(Simulation sim, double dt) {
		super(sim, dt);
	}

	@Override
	public void step() {
		ArrayList<Entity> entityList=sim.getEntityList();

		for(Entity e:entityList){		
			Vector vTwiddle = e.getVelocity().add(e.getAcceleration().mult(dt));
			Vector xTwiddle = e.getPosition().add(e.getVelocity().mult(dt));
			

		}
	}



}
