package entities;

import java.awt.Graphics;

import simulator.Simulation;
import vector.Vector;

public class Entity {

	

	private double mu;
	private Vector position, velocity, acceleration;
	public String name;
	private double sf = 0.0000002;
	

	public Entity(double mass, String name){
		this.mu=mass;
		this.name=name;
	}
	
	public Entity(double mass){
		this.mu=mass;
		this.name = "";
	}
	
	public void render(Graphics g){
		g.fillOval((int)(sf*(position.getX()-5)), (int)(sf*(position.getY()-5)), 5, 5);
		g.drawString(name, (int)(sf*(position.getX()-5)-5), (int)(sf*(position.getY()-5)-5));
	}
	
	
	//getters and setters
	public Vector getPosition() {
		return position;
	}

	public void setPosition(Vector position) {
		this.position = position;
	}

	public Vector getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Vector acceleration) {
		this.acceleration = acceleration;
	}
	
	public Vector getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

	public double getMu() {
		return mu;
	}

	public Vector calcAccel(Simulation simulation){
		Vector a = new Vector();
		for(Entity entity:simulation.getEntityList()){
			if(!name.equalsIgnoreCase(entity.name)){
				Vector diff = getPosition().sub(entity.getPosition());
				double mag = diff.mag();
				diff.normalise();
				diff = diff.mult(-entity.getMu()/(mag*mag));
				a = a.add(diff);
			}
		}
		return a;
	}

}
