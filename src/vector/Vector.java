package vector;

public class Vector {

	private double x,y,z;
	
	public Vector(){
		this.x=0;
		this.y=0;
		this.z=0;
	}
	
	public Vector(double x, double y, double z){
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public Vector add(Vector addThis) {
		return new Vector(x+addThis.x,y+addThis.y,z+addThis.z);
	}
	
	public Vector sub(Vector subThis) {
		return new Vector(x-subThis.x,y-subThis.y,z-subThis.z);
	}
	
	public Vector mult(double multThis) {
		return new Vector(x*multThis,y*multThis,z*multThis);
	}
	
	public Vector copy(){
		return new Vector(this.x, this.y, this.z);
	}
	
	public double mag(){
		return Math.sqrt(x*x+y*y+z*z);
	}
	
	public void normalise(){
		double mag = this.mag();
		x=x/mag;
		y=y/mag;
		z=z/mag;
	}
	
	public static Vector CrossProduct(Vector u, Vector v){
		Vector result = new Vector();
		result.x=u.y*v.z-u.z*v.y;
		result.y=u.z*v.x-u.x*v.z;
		result.z=u.x*v.y-u.y*v.x;
		return result;
	}
}
