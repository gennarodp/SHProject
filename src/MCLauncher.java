import java.util.Random;

import entities.Entity;
import simulator.MCSimulation;
import vector.Vector;

public class MCLauncher {

	public static void main(String[] args){
		Random rnd = new Random(123456789);
		double cMag = new Vector(1.078391048790424E+00,  5.022211220294290E-01, -4.201852473035220E-01).mag();
		cMag = cMag*0.0001;
		for(int i=2000; i<=2000; i++){
			MCSimulation sim = new MCSimulation(i);

			double sf = 149597871.0;
			sim.setStep(1.0/365000.242);

			//14 sept 2015
			Entity e1 = new Entity(132712440018.0*3.15569e7*3.15569e7, "sun");
			e1.setVelocity(new Vector(0,0,0));
			e1.setPosition(new Vector(0,0,0));
			e1.setVelocity(e1.getVelocity().mult(365*sf));
			e1.setPosition(e1.getPosition().mult(sf));
			sim.addEntity(e1);

			Entity e2 = new Entity(398600.4418*3.15569e7*3.15569e7, "earth");
			e2.setPosition(new Vector(9.927919290557768E-01, -1.632821404745737E-01,  1.195466587026960E-06));
			e2.setVelocity(new Vector(2.512278855423096E-03,  1.691890781267503E-02, -1.240999535152608E-06));
			e2.setVelocity(e2.getVelocity().mult(365*sf));
			e2.setPosition(e2.getPosition().mult(sf));
			sim.addEntity(e2);

			Entity e3 = new Entity(4902.8*3.15569e7*3.15569e7, "moon");
			e3.setPosition(new Vector(9.900764171080150E-01, -1.632088569410187E-01, -8.824634720868396E-06));
			e3.setVelocity(new Vector(2.494837643745809E-03,  1.636044481924846E-02,  5.047614232916136E-05));
			e3.setVelocity(e3.getVelocity().mult(365*sf));
			e3.setPosition(e3.getPosition().mult(sf));
			sim.addEntity(e3);

			Entity e4 = new Entity(42828*3.15569e7*3.15569e7, "Mars");
			e4.setPosition(new Vector(-9.699716180660849E-01,  1.325755609036277E+00,  5.158581424387751E-02));
			e4.setVelocity(new Vector(-1.076396421122455E-02, -7.072097553734500E-03,  1.159972197723787E-04));
			e4.setVelocity(e4.getVelocity().mult(365*sf));
			e4.setPosition(e4.getPosition().mult(sf));
			sim.addEntity(e4);

			Entity e5 = new Entity(324859*3.15569e7*3.15569e7, "Venus");
			e5.setPosition(new Vector(7.163740463661524E-01,  1.106338030647346E-01, -3.982241393553421E-02));
			e5.setVelocity(new Vector(-3.163694218710286E-03,  1.989777543897208E-02,  4.553550162088531E-04));
			e5.setVelocity(e5.getVelocity().mult(365*sf));
			e5.setPosition(e5.getPosition().mult(sf));
			sim.addEntity(e5);

			Entity e6 = new Entity(126686534*3.15569e7*3.15569e7, "Jupiter");
			e6.setPosition(new Vector(-4.867896260721013E+00,  2.317865824455204E+00,  9.929991271318378E-02));
			e6.setVelocity(new Vector(-3.336739406408881E-03, -6.462834962047507E-03,  1.015237964128767E-04));
			e6.setVelocity(e6.getVelocity().mult(365*sf));
			e6.setPosition(e6.getPosition().mult(sf));
			sim.addEntity(e6);

			Entity e7 = new Entity(37931187*3.15569e7*3.15569e7, "Saturn");
			e7.setPosition(new Vector(-4.238814912767944E+00, -9.046669610176270E+00,  3.259993092812575E-01));
			e7.setVelocity(new Vector(4.745466015363778E-03, -2.389131936824487E-03, -1.471015524220709E-04));
			e7.setVelocity(e7.getVelocity().mult(365*sf));
			e7.setPosition(e7.getPosition().mult(sf));
			sim.addEntity(e7);

			Entity e8 = new Entity(0.0, "Cruithne");
			double x,y,z;
			x=1.078391048790424E+00;//+rnd.nextDouble()*cMag;
			y=5.022211220294290E-01;//+rnd.nextDouble()*cMag;
			z=-4.201852473035220E-01;//+rnd.nextDouble()*cMag;
			e8.setPosition(new Vector(x,  y, z));
			e8.setVelocity(new Vector(-1.029372467114329E-02,  8.056837942649730E-03,  1.274496219730674E-03));
			e8.setVelocity(e8.getVelocity().mult(365*sf));
			e8.setPosition(e8.getPosition().mult(sf));
			sim.addEntity(e8);

			Entity e9 = new Entity(0.0, "Neptune");
			e9.setPosition(new Vector(2.782837883963466E+01, -1.109728218462406E+01, -4.127455802904292E-01));
			e9.setVelocity(new Vector(1.140103830625123E-03,  2.927872667627532E-03, -8.709882625541909E-05));
			e9.setVelocity(e9.getVelocity().mult(365*sf));
			e9.setPosition(e9.getPosition().mult(sf));
			sim.addEntity(e9);

			Entity e10 = new Entity(0.0, "Uranus");
			e10.setPosition(new Vector(1.900809765245727E+01,  6.167745672129368E+00, -2.232349075839025E-01));
			e10.setVelocity(new Vector(-1.244332679218360E-03,  3.551339049362050E-03,  2.941282136606087E-05));
			e10.setVelocity(e10.getVelocity().mult(365*sf));
			e10.setPosition(e10.getPosition().mult(sf));
			sim.addEntity(e10);


			sim.run();
		}
	}

}
