import entities.Entity;
import simulator.Simulation;
import vector.Vector;

public class Launcher {

	public static void main(String[] args){
		Simulation sim = new Simulation();
				
		double sf = 149597871.0;
		sim.setStep(1.0/3650.242);
		
		Entity e1 = new Entity(132712440018.0*3.15569e7*3.15569e7, "sun");
		e1.setVelocity(new Vector(1.816512817381262E-06 , 6.441888830012236E-06 ,-5.249011175312896E-08));
		e1.setPosition(new Vector(3.588606238396403E-03 , 6.812286319800660E-04 ,-1.567607283165716E-04));
		e1.getVelocity().mult(365*sf);
		e1.getPosition().mult(sf);
		sim.addEntity(e1);
		
		Entity e2 = new Entity(398600.4418*3.15569e7*3.15569e7, "earth");
		e2.setPosition(new Vector(9.987476956370784E-01 ,-1.456591278585990E-01 ,-1.568046306294577E-04));
		e2.setVelocity(new Vector(2.223709005059121E-03 , 1.697023677289311E-02 ,-1.285475339563333E-06));
		e2.getVelocity().mult(365*sf);
		e2.getPosition().mult(sf);
		sim.addEntity(e2);
		
		Entity e3 = new Entity(4902.8*3.15569e7*3.15569e7, "moon");
	//	Entity e3 = new Entity(398600.4418*3.15569e7*3.15569e7, "moon");
		e3.setPosition(new Vector(9.960748073752260E-01 ,-1.461415803646026E-01, -1.152705321398534E-04));
		e3.setVelocity(new Vector(2.326094862861458E-03 , 1.642140526442924E-02 , 4.971740087796408E-05));
		e3.getVelocity().mult(365*sf);
		e3.getPosition().mult(sf);
		sim.addEntity(e3);
		
		Entity e4 = new Entity(42828*3.15569e7*3.15569e7, "Mars");
		e4.setPosition(new Vector(-9.663848319123155E-01, 1.326430397590705E+00, 5.142910635358969E-02));
		e4.setVelocity(new Vector( -1.076214096446081E-02, -7.065658973732571E-03, 1.159446286134878E-04));
		e4.getVelocity().mult(365*sf);
		e4.getPosition().mult(sf);
		sim.addEntity(e4);
		
		Entity e5 = new Entity(324859*3.15569e7*3.15569e7, "Venus");
		e5.setPosition(new Vector(7.199608327256685E-01, 1.113085914661420E-01, -3.997912212307525E-02));
		e5.setVelocity(new Vector(-3.161870972561679E-03, 1.990421401859328E-02, 4.553024247598800E-04));
		e5.getVelocity().mult(365*sf);
		e5.getPosition().mult(sf);
		sim.addEntity(e5);
		
		Entity e6 = new Entity(126686534*3.15569e7*3.15569e7, "Jupiter");
		e6.setPosition(new Vector(-4.864309474361497E+00, 2.318540612856611E+00, 9.914320452564265E-02));
		e6.setVelocity(new Vector(-3.334916160260275E-03, -6.456396382426306E-03, 1.014712049639028E-04));
		e6.getVelocity().mult(365*sf);
		e6.getPosition().mult(sf);
		sim.addEntity(e6);
		
		Entity e7 = new Entity(37931187*3.15569e7*3.15569e7, "Saturn");
		e7.setPosition(new Vector(-4.235228126408428E+00, -9.045994821774862E+00, 3.258426010937164E-01));
		e7.setVelocity(new Vector(4.747289261512385E-03, -2.382693357203287E-03, -1.471541438710454E-04));
		e7.getVelocity().mult(365*sf);
		e7.getPosition().mult(sf);
		sim.addEntity(e7);
		
		Entity e8 = new Entity(0.0, "Cruithne");
		e8.setPosition(new Vector(1.081977851889917E+00, 5.028958479351832E-01, -4.203419106384089E-01));
		e8.setVelocity(new Vector(-1.029190089780631E-02, 8.063277916951762E-03, 1.274442811134016E-03));
		e8.getVelocity().mult(365*sf);
		e8.getPosition().mult(sf);
		sim.addEntity(e8);
		sim.run();
		
	}
	
}
