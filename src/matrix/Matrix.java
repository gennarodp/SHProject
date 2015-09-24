package matrix;

import vector.Vector;

public class Matrix {
	
	private int row, column;
	public Vector[] matrix;
	
	public Matrix(int m, int n){
		if (m < 0) m = 0;
		row = m;
		if (n < 0) n = 0;
		column = n;
		matrix = new Vector [row*column];
	}


	public void setElement(int i, int j, Vector value){
		if (i >= 0 && j >= 0){
			matrix[i*column+j] = value;
		}
	}
	public Vector getElement(int i, int j){
		return matrix[i*column+j].copy();
	}
}
