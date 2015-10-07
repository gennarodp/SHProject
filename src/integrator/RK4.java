package integrator;

import java.util.ArrayList;

import entities.Entity;
import simulator.Simulation;
import vector.Vector;

public class RK4 extends Integrator{
	ArrayList<Entity> entityList;
	int n=6;

	public RK4(Simulation sim, double dt) {
		super(sim, dt);
		entityList=sim.getEntityList();
	}

	@Override
	public void step() {
			for(Entity e:entityList){
				doStep(e);
		}
	}
	
	private void doStep(Entity e) {
		double[] y = new double[n];
		double[] dydx = new double[n];
		double[] yOut = new double[n];
		
		y[0]=e.getPosition().getX();
		y[1]=e.getPosition().getY();
		y[2]=e.getPosition().getZ();
		y[3]=e.getVelocity().getX();
		y[4]=e.getVelocity().getY();
		y[5]=e.getVelocity().getZ();
		
		dydx[0]=e.getVelocity().getX();
		dydx[1]=e.getVelocity().getY();
		dydx[2]=e.getVelocity().getZ();
		dydx[3]=e.getAcceleration().getX();
		dydx[4]=e.getAcceleration().getY();
		dydx[5]=e.getAcceleration().getZ();

		rk4(y,dydx,n,dt,yOut,e);
		
		e.setPosition(new Vector(yOut[0],yOut[1],yOut[2]));
		e.setVelocity(new Vector(yOut[3],yOut[4],yOut[5]));
		e.setAcceleration(e.calcAccel(sim));
	}

	private void rk4(double[] y, double[] dydx, int n, double h, double[] yOut,Entity e){
		double hh,h6;
		double[] dym = new double[n];
		double[] dyt = new double[n];
		double[] yt = new double[n];

		hh=h*0.5;
		h6=h/6.0;
		for(int i=0; i<n;i++){
			yt[i] = y[i]+hh*dydx[i];
		}
		derivs(yt,dyt,e);
		for(int i=0;i<n;i++){
			yt[i] = y[i]+hh*dyt[i];
		}
		derivs(yt,dym,e);
		for(int i=0;i<n;i++){
			yt[i]=y[i]+h*dym[i];
			dym[i]+=dyt[i];
		}
		derivs(yt,dyt,e);
		for(int i=0;i<n;i++){
			yOut[i]=y[i]+h6*(dydx[i]+dyt[i]+2.0*dym[i]);
		}
	}

	private void derivs(double[] yt, double[] out, Entity e) {
		e.setPosition(new Vector(yt[0],yt[1],yt[2]));
		Vector a = e.calcAccel(sim);
		out[0]=yt[3];
		out[1]=yt[4];
		out[2]=yt[5];
		out[3]=a.getX();
		out[4]=a.getY();
		out[5]=a.getZ();
	}

}
