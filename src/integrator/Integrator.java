package integrator;

import simulator.Simulation;

public abstract class Integrator {

	Simulation sim;
	double dt;
	
	public Integrator(Simulation sim, double dt){
		this.sim = sim;
		this.dt = dt;
	}
	public abstract void step();
	
}

