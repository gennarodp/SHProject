package simulator;

import entities.Entity;
import integrator.IntegratorFactory;
import utils.Utils;

public class MCSimulation extends Simulation {

	private int simId;

	public MCSimulation(int simId) {
		super();
		this.simId = simId;
	}

	@Override
	protected void init(){
		boolean graphs=false, toFile=true;
		util=new Utils(this, graphs, toFile);
		util.setFileName("RunNumber_"+simId);
		integratorType = "Symplectic4";
		//get data from file (future)
		displayGraphics = false;
		integrator = IntegratorFactory.getIntegrator(integratorType, this, dt);


		//Set up first step
		for(Entity e:entityList){
			e.setAcceleration(e.calcAccel(this));
		}
	}

	@Override
	public void run(){

		init();
		System.out.println("Starting run "+simId+"...");
		long t = System.currentTimeMillis();
		while(true){
			for (int i = 0; i < 10; i++) {
				tick();
			}
			util.utilRun();
			if(time>=10){
				util.utilClose();
				System.out.println("Ending run "+simId+".");
				System.out.println("Total Runtime : "+(System.currentTimeMillis()-t));
				return;
			}
		}
	}
}



