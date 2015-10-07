package utils;

import java.util.ArrayList;

import entities.Entity;
import simulator.Simulation;
import vector.Vector;

public class EnergyUtil {

	private ArrayList<Entity> entityList;
	private final double G = 6.67384e-11/1000000000*3.15569e7*3.15569e7;

	public EnergyUtil(Simulation sim){
		this.entityList=sim.entityList;
	}
	
	public double calcEnergy(){
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
			potential-=calcIndividualPotential(e);
		}
		//calc every potential twice so need half
		return 0.5*potential;
	}

	private double calcIndividualPotential(Entity e){
		double potential = 0;
		for(Entity entity:entityList){
			if(e.name!=entity.name){
				double mag = e.getPosition().sub(entity.getPosition()).mag();
				potential+=e.getMu()*entity.getMu()/mag;
			}
		}
		return potential/G;
	}
	
	public Vector calcAngularMomentum(){
		Vector total = new Vector();
		for(Entity e:entityList){
			Vector p = e.getVelocity().mult(e.getMu()/G);
			total=total.add(Vector.CrossProduct(e.getPosition(), p));
		}
		return total;
	}
}
