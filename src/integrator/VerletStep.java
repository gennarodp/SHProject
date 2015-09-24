package integrator;

import java.util.ArrayList;

import entities.Entity;
import simulator.Simulation;
import vector.Vector;

public class VerletStep {


	public static void step(Simulation sim, double dt){
		
		
		ArrayList<Entity> entityList=sim.getEntityList();
		for(Entity e:entityList){
			Vector newX = new Vector();
			Vector x = e.getPosition().copy();
			Vector v = e.getVelocity().copy();
			Vector a = e.getAcceleration().copy();

			//get new X
			newX.add(x);
			v.mult(dt);
			newX.add(v);
			a.mult(dt*dt*0.5);
			newX.add(a);
			e.setPosition(newX);
		}

		for(Entity e:entityList){
			Vector temp = e.getAcceleration().copy();
			Vector newA = sim.calcAccel(e);
			temp.add(newA);
			temp.mult(dt*0.5);
			temp.add(e.getVelocity());
			e.setVelocity(temp);
			e.setAcceleration(newA);
		}
	}
}
