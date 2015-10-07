package simulator;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import entities.Entity;
import integrator.Integrator;
import integrator.IntegratorFactory;
import utils.Utils;

public class Simulation {

	private Display display;
	private BufferStrategy bs;
	private int width=800, height=800;
	private Graphics g;
	public ArrayList<Entity> entityList;
	protected double dt;
	public double time;
	protected Integrator integrator;
	protected boolean displayGraphics; 
	protected String integratorType;
	protected Utils util;


	public Simulation(){
		entityList = new ArrayList<Entity>();
		time=0;
	}

	public void setStep(double dt){
		this.dt = dt;
	}

	public void addEntity(Entity e){
		entityList.add(e);
	}

	protected void tick(){
		integrator.step();
		time+=dt;
	}

	protected void init(){
		boolean graphs=false, toFile=true;
		util=new Utils(this, graphs, toFile);


		integratorType = "Symplectic4";
		//get data from file (future)
		displayGraphics = false;
		if(displayGraphics){
			display = new Display("simulation",width,height);
		}
		integrator = IntegratorFactory.getIntegrator(integratorType, this, dt);
		
		
		//Set up first step
		for(Entity e:entityList){
			e.setAcceleration(e.calcAccel(this));
		}
	}

	protected void render(){
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null){
			display.getCanvas().createBufferStrategy(4);
			return;
		}
		g = bs.getDrawGraphics();

		//Clear Screen
		g.clearRect(0, 0, width, height);

		//Draw Here!
		g.drawString(Double.toString(time), 5,10);
		g.drawString(Double.toString(util.getEnergy()), 5,20);

		g.translate(width/2, height/2);
		for(Entity e:entityList){
			e.render(g);
		}

		//End Drawing!
		bs.show();
		g.dispose();
	}

	public void run(){

		init();

		int fps = 600;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		long tickadoodle = 0;

		while(true){
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;

			if(delta >= 1){

				for (int i = 0; i < 100; i++) {
					tick();
				}
				tickadoodle+=1;
				util.utilRun();
				if(displayGraphics){
					render();
				}
				
				ticks++;
				delta--;
				if(tickadoodle==5000){
					System.out.println(time);
					tickadoodle=0;
				}
				if(time>=1000){
					util.utilClose();
					
				}
			}

		}
	}

	
	//getters and setters
	public ArrayList<Entity> getEntityList(){
		return entityList;
	}

	public double getTime() {
		return time;
	}
}
