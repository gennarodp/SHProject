package simulator;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import entities.Entity;
import graph.LineGraph;
import integrator.Integrator;
import integrator.IntegratorFactory;
import matrix.Matrix;
import vector.Vector;

public class Simulation {

	private Display display;
	private BufferStrategy bs;
	private int width=800, height=800;
	private Graphics g;
	private ArrayList<Entity> entityList;
	private double dt, time, initEnergy, initAngularMomentum;
	private final double G = 6.67384e-11/1000000000*3.15569e7*3.15569e7;
	private LineGraph energyGraph, angularMomentumGraph;
	private Integrator integrator;
	private FileWriter posFw, velFw;
	private Entity writeEntity;
	private boolean displayGraphics, graphs, toFile; 
	private String integratorType, entityName, fileName;

	public Simulation(){
		display = new Display("simulation",width,height);
		entityList = new ArrayList<Entity>();
		time=0;


	}

	public void setStep(double dt){
		this.dt = dt;
	}

	public void addEntity(Entity e){
		entityList.add(e);
	}

	private void tick(){
		integrator.step();
		time+=dt;
	}

	private void writePosToFile(Entity e){
		try {
			posFw.write(Double.toString(e.getPosition().getX()/149597871.0));
			posFw.write(",");
			posFw.write(Double.toString(e.getPosition().getY()/149597871.0));
			posFw.write(",");
			posFw.write(Double.toString(e.getPosition().getZ()/149597871.0));
			posFw.write(System.lineSeparator());
		} catch (IOException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
	}

	private void writeVelToFile(Entity e){
		try {
			velFw.write(Double.toString(e.getVelocity().getX()/149597871.0));
			velFw.write(",");
			velFw.write(Double.toString(e.getVelocity().getY()/149597871.0));
			velFw.write(",");
			velFw.write(Double.toString(e.getVelocity().getZ()/149597871.0));
			velFw.write(System.lineSeparator());
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}



	private void init(){
		fileName = "test 1";
		entityName = "Cruithne";
		toFile = true;
		integratorType = "Symplectic4";
		writeEntity = entityList.get(1);
		//get data from file (future)
		displayGraphics = true;
		graphs = false;
		integrator = IntegratorFactory.getIntegrator(integratorType, this, dt);
		if(toFile){
			toFile();
		}
		if (graphs) {
			energyGraph = new LineGraph("Energy difference");
			angularMomentumGraph = new LineGraph("Angular Momentum difference");
		}
		//Set up first step
		for(Entity e:entityList){
			e.setAcceleration(calcAccel(e));
		}
		initEnergy = calcEnergy();
		initAngularMomentum = calcAngularMomentum().mag();
	}
	
	private void toFile(){
		try {
			posFw = new FileWriter(entityName +" "+fileName + "posData.csv");
			posFw.write(Double.toString(dt));
			posFw.write(",");

			posFw.write(System.lineSeparator());

			velFw = new FileWriter(entityName +" "+fileName + "velData.csv");
			velFw.write(Double.toString(dt));
			velFw.write(",");

			velFw.write(System.lineSeparator());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Vector calcAngularMomentum(){
		Vector total = new Vector();
		for(Entity e:entityList){
			Vector p = e.getVelocity().copy();
			p.mult(e.getMu()/G);
			total.add(Vector.CrossProduct(e.getPosition(), p));
		}
		return total;
	}



	public Vector calcAccel(Entity e){
		Vector a = new Vector();
		for(Entity entity:entityList){
			if(e.name!=entity.name){
				Vector diff = e.getPosition().copy();
				diff.sub(entity.getPosition());
				double mag = diff.mag();
				diff.normalise();
				diff.mult(-entity.getMu()/(mag*mag));
				a.add(diff);
			}
		}
		return a;
	}

	private double calcEnergy(){
		double energy = 0;
		energy+=calcKinetic();
		energy+=calcPotential();
		return energy;
	}

	private double calcKinetic(){
		double kinetic = 0;
		for(Entity e:entityList){
			double vel = e.getVelocity().mag();
			kinetic+=vel*vel*e.getMu()*0.5;
		}
		return kinetic/G;
	}

	private double calcPotential(){
		double potential = 0;
		for(Entity e:entityList){
			potential+=calcIndividualPotential(e);
		}
		return potential;
	}

	private double calcIndividualPotential(Entity e){
		double potential = 0;
		for(Entity entity:entityList){
			if(e.name!=entity.name){
				Vector diff = e.getPosition().copy();
				diff.sub(entity.getPosition());
				double mag = diff.mag();
				potential+=entity.getMu()/mag;
			}
		}
		return potential;
	}

	private void render(){
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
		g.drawString(Double.toString(calcEnergy()), 5,20);

		g.translate(width/2, height/2);
		/*	Graphics2D g2d= (Graphics2D)g;
		g2d.scale(100, 100);
		g = (Graphics)g2d;*/
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

		while(true){
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;

			if(delta >= 1){

				for (int i = 0; i < 10; i++) {
					tick();
				}
				if(displayGraphics){
					render();
				}
				if(graphs){
					energyGraph.addPoint(time, (initEnergy-calcEnergy())/initEnergy);
					angularMomentumGraph.addPoint(time, (initAngularMomentum-calcAngularMomentum().mag())
							/initAngularMomentum);
				}
				if(toFile){
					writePosToFile(writeEntity);
					writeVelToFile(writeEntity);
				}
				ticks++;
				delta--;
			}

			if(time>=10){
				try {
					posFw.close();
					velFw.close();
					System.exit(0);
				} catch (IOException e) {
				}
			}
		}
	}

	public ArrayList<Entity> getEntityList(){
		return entityList;
	}
}
