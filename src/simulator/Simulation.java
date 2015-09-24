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
import integrator.BetterIntegrator;
import integrator.VerletStep;
import matrix.Matrix;
import vector.Vector;

public class Simulation {

	private Display display;
	private BufferStrategy bs;
	private int width=800, height=800;
	private Graphics g;
	private ArrayList<Entity> entityList;
	private double dt, time;
	private double energy;
	private Matrix adjacencyMatrix;
	private final double G = 6.67384e-11/1000000000*3.15569e7*3.15569e7;
	private LineGraph energyGraph, angularMomentumGraph;
	private double initEnergy, initAngularMomentum;
	
	private FileWriter fw;

	public Simulation(){
		display = new Display("simulation",width,height);
		entityList = new ArrayList<Entity>();
		time=0;
		energyGraph = new LineGraph("Energy difference");
		angularMomentumGraph = new LineGraph("Angular Momentum difference");
		try {
			fw = new FileWriter("VerletData.csv");
			fw.write(Double.toString(dt));
			fw.write(System.lineSeparator());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setStep(double dt){
		this.dt = dt;
	}

	public void addEntity(Entity e){
		entityList.add(e);
	}

	private void tick(){
		VerletStep.step(this, dt);
		time+=dt;
		try {
			fw.write(Double.toString(entityList.get(7).getPosition().getX()/149597871.0));
			fw.write(",");
			fw.write(Double.toString(entityList.get(7).getPosition().getY()/149597871.0));
			fw.write(",");
			fw.write(Double.toString(entityList.get(7).getPosition().getZ()/149597871.0));
			fw.write(System.lineSeparator());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		energyGraph.addPoint(time, (initEnergy-calcEnergy())/initEnergy);
//		angularMomentumGraph.addPoint(time, (initAngularMomentum-calcAngularMomentum().mag())
//				/initAngularMomentum);
	}

	
	private void init(){
		for(Entity e:entityList){
			e.setAcceleration(calcAccel(e));
		}
		initEnergy = calcEnergy();
		initAngularMomentum = calcAngularMomentum().mag();
	}

	public void calcAdjacencyMatrix(){

		for(int i=0; i<entityList.size(); i++){
			for(int j=0; j<i; j++){
				Vector diff = entityList.get(i).getPosition().copy();
				diff.sub(entityList.get(j).getPosition());
				adjacencyMatrix.setElement(i, j, diff);
			}
		}
		System.out.println(Arrays.toString(adjacencyMatrix.matrix));
	}
	
	public Vector calcAngularMomentum(){
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
		/*
		int i=0;
		for(Entity entity:entityList){
			Vector a = new Vector();
			Loop:
			for(int j=0; j<entityList.size(); j++){
				if(i==j){
					break Loop;
				}
				Vector diff = adjacencyMatrix.getElement(i, j);

				double mag = diff.mag();
				diff.normalise();
				diff.mult(-entity.getMu()/(mag*mag));
				a.add(diff);
			}
			i++;
			entity.setAcceleration(a);
		}*/
	}
	
	public double calcEnergy(){
		double energy = 0;
		energy+=calcKinetic();
		energy+=calcPotential();
		return energy;
	}
	
	public double calcKinetic(){
		double kinetic = 0;
		for(Entity e:entityList){
			double vel = e.getVelocity().mag();
			kinetic+=vel*vel*e.getMu()*0.5;
		}
		return kinetic/G;
	}
	
	public double calcPotential(){
		double potential = 0;
		for(Entity e:entityList){
			potential+=calcIndividualPotential(e);
		}
		return potential;
	}
	
	public double calcIndividualPotential(Entity e){
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

		int fps = 6000;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
/*		for (int i = 0; i < 1; i++) {
			tick();
		}
		
		for (int i = 0; i < 1000; i++) {
			tick();
			render();
		}
		try {
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(System.nanoTime()-lastTime);
		System.out.println("done");*/
		while(true){
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;

			if(delta >= 1){
				//	tick();
				//	tick();
				//	tick();
				//	tick();
			
				tick();
				render();
			
				ticks++;
				delta--;
			}

			if(timer >= 1000000000){
				System.out.println("Ticks and Frames: " + ticks);
				ticks = 0;
				timer = 0;
			}
			if(time>=5){
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public ArrayList<Entity> getEntityList(){
		return entityList;
	}
}
