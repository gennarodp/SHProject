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
		for (int i = 0; i < c.length; i++) {
			ArrayList<Entity> entityList=sim.getEntityList();
			for(Entity e:entityList){
				Vector newV = new Vector();
				Vector x = e.getPosition();
				Vector v = e.getVelocity();
				Vector a = e.getAcceleration();

				a.mult(c[i]*dt);
				v.add(a);
				newV = v.copy();
				newV.mult(dt*d[i]);
				x.add(newV);
			}
			for(Entity e:entityList){
				e.setAcceleration(sim.calcAccel(e));
			}
		}
	}
}