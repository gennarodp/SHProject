package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import entities.Entity;
import graph.LineGraph;
import simulator.Simulation;
import vector.Vector;

public class Utils {

	Simulation sim;
	private double time, initEnergy, l0;
	private LineGraph energyGraph, angularMomentumGraph;
	private String fileName;
	private FileWriter posFw, velFw,energyFw;
	private boolean graphs, toFile;
	private ArrayList<Entity> entityList;
	private EnergyUtil eUtil;
	private double energy=0;

	
	public Utils(Simulation sim,boolean graphs, boolean toFile){
		this.sim=sim;
		this.graphs=graphs;
		this.toFile=toFile;
		this.entityList=sim.getEntityList();
		this.eUtil = new EnergyUtil(sim);
		if(toFile){
			fileInit();
		}
		if(graphs){
			energyGraph = new LineGraph("Energy");
			angularMomentumGraph = new LineGraph("Angular Momentum");
		}
		initEnergy=eUtil.calcEnergy();
		l0=eUtil.calcAngularMomentum().mag();
	}

	private void fileInit(){
		if(toFile){
			try {
				posFw = new FileWriter(fileName + "_posData.csv");
				velFw = new FileWriter(fileName + "_velData.csv");
				energyFw = new FileWriter("energy.csv");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void utilRun(){
		this.time=sim.getTime();
		energy = eUtil.calcEnergy();
		if(graphs){
			energyGraph.addPoint(time, (energy-initEnergy)/initEnergy);
			double l=eUtil.calcAngularMomentum().mag();
			angularMomentumGraph.addPoint(time, (l0-l)
					/l0);
		}
		if(toFile){
			writePosToFile();
	//		writeVelToFile();		
	//		writeEnergyToFile(energy);

		}
	}

	public void utilClose(){
		try {
			posFw.close();
		//	velFw.close();
		//	energyFw.close();
		} catch (IOException e) {
		}
	}

	final double scale = 1/149597871.0;
	private void writePosToFile(){
		Entity e1 = entityList.get(0), e2=entityList.get(1), e3=entityList.get(7);

		try {
	/*		posFw.write(Double.toString(e1.getPosition().getX()*scale));
			posFw.write(",");
			posFw.write(Double.toString(e1.getPosition().getY()*scale));
			posFw.write(",");
			posFw.write(Double.toString(e1.getPosition().getZ()*scale));
			posFw.write(",");
			posFw.write(Double.toString(e2.getPosition().getX()*scale));
			posFw.write(",");
			posFw.write(Double.toString(e2.getPosition().getY()*scale));
			posFw.write(",");
			posFw.write(Double.toString(e2.getPosition().getZ()*scale));
			posFw.write(",");*/
			posFw.write(Double.toString(e3.getPosition().getX()*scale));
			posFw.write(",");
			posFw.write(Double.toString(e3.getPosition().getY()*scale));
			posFw.write(",");
			posFw.write(Double.toString(e3.getPosition().getZ()*scale));
			posFw.write(",");

			posFw.write(System.lineSeparator());
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	private void writeVelToFile(){
		Entity e1 = entityList.get(0), e2=entityList.get(1), e3=entityList.get(7);

		try {
			velFw.write(Double.toString(e1.getVelocity().getX()*scale));
			velFw.write(",");
			velFw.write(Double.toString(e1.getVelocity().getY()*scale));
			velFw.write(",");
			velFw.write(Double.toString(e1.getVelocity().getZ()*scale));
			velFw.write(",");
			velFw.write(Double.toString(e2.getVelocity().getX()*scale));
			velFw.write(",");
			velFw.write(Double.toString(e2.getVelocity().getY()*scale));
			velFw.write(",");
			velFw.write(Double.toString(e2.getVelocity().getZ()*scale));
			velFw.write(",");
			velFw.write(Double.toString(e3.getVelocity().getX()*scale));
			velFw.write(",");
			velFw.write(Double.toString(e3.getVelocity().getY()*scale));
			velFw.write(",");
			velFw.write(Double.toString(e3.getVelocity().getZ()*scale));
			velFw.write(",");
			velFw.write(System.lineSeparator());
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	private void writeEnergyToFile(double energy){
		try {
			energyFw.write(Double.toString(energy));
			energyFw.write(System.lineSeparator());

		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	public static String loadFileAsString(String path){
		StringBuilder builder = new StringBuilder();

		try{
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			while((line = br.readLine()) != null)
				builder.append(line + "\n");

			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}

		return builder.toString();
	}

	public static int parseInt(String number){
		try{
			return Integer.parseInt(number);
		}catch(NumberFormatException e){
			e.printStackTrace();
			return 0;
		}
	}

	public static double parseDouble(String number){
		try{
			return Double.parseDouble(number);
		}catch(NumberFormatException e){
			e.printStackTrace();
			return 0;
		}
	}

	public double[] temp(String path){
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		double[] returns = new double[tokens.length];

		for (int i = 0; i < tokens.length; i++) {
			returns[i] = parseDouble(tokens[i]);
		}
		return returns;
	}

	public double getEnergy() {
		return energy;
	}

	public void setFileName(String string) {
		try {
			posFw = new FileWriter(string+".csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
