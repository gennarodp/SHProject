package integrator;

import java.util.ArrayList;

import entities.Entity;
import simulator.Simulation;
import vector.Vector;

public class Order4Symplectic extends Integrator{

	private static final double[] d=new double[]{1.35120719195966,-1.70241438391932,1.35120719195966,0};
	private static final double[] c=new double[]{0.67560359597983,-0.17560359597983,-0.17560359597983,0.67560359597983};

	public Order4Symplectic(Simulation sim, double dt) {
		super(sim, dt);
	}
	
	
	public  void step(){
		ArrayList<Entity> entityList=sim.getEntityList();

		for (int i = 0; i < c.length; i++) {
			for(Entity e:entityList){		
				
				Vector a = e.getAcceleration().mult(c[i]*dt);

				Vector v=e.getVelocity().add(a);
				e.setVelocity(v);

				e.setPosition(e.getPosition().add(v.mult(dt*d[i])));
			}
			for(Entity e:entityList){
				e.setAcceleration(e.calcAccel(sim));
			}
		}
	}
}