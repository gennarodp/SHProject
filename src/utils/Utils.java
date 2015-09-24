package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utils {
	
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

}
