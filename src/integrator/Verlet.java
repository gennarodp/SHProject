package integrator;

import java.util.ArrayList;

import entities.Entity;
import simulator.Simulation;
import vector.Vector;

public class Verlet extends Integrator {


	public Verlet(Simulation sim, double dt) {
		super(sim, dt);
	}

	public  void step(){
		
		
		ArrayList<Entity> entityList=sim.getEntityList();
		for(Entity e:entityList){	
			Vector temp = e.getPosition();
			temp = temp.add(e.getVelocity().mult(dt));
			temp = temp.add(e.getAcceleration().mult(dt*dt*0.5));
			e.setPosition(temp);
		}

		for(Entity e:entityList){
			Vector newA = e.calcAccel(sim);
			e.setVelocity(e.getVelocity().add((e.getAcceleration().add(newA)).mult(0.5*dt)));
			e.setAcceleration(newA);
		}
	}
}
