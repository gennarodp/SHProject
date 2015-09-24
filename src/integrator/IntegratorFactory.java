package integrator;

import simulator.Simulation;

public class IntegratorFactory {

	public static Integrator getIntegrator(String type, Simulation sim, double dt){
		if(type == null){
			return null;
		}
		if(type.equalsIgnoreCase("VERLET")){
			return new Verlet(sim, dt);
		}else if (type.equalsIgnoreCase("SYMPLECTIC4")){
			return new Order4Symplectic(sim, dt);
		}
		return null;
	}
}
