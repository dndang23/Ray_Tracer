import java.util.ArrayList;

public class Cube extends Traceable {

	/* A Cube is cube centered at the origin, extending from -1 to +1 on
	 * each axis
	 * 
	 * Note:  there is a bug in local_intersect so it sometimes does not work
	 * correctly, but this should not give you a problem.
	 */

	public String toString() {
		return "Cube \n"+this.transform;
	}


	
	@Override
	public ArrayList<Intersection> local_intersect(Ray gray) {
			
		
		var ray = gray.transform(transform.invert());

		double[] rets = 
				check_axis(ray.origin.t[0], ray.direction.t[0]);
		double xtmin = rets[0];
		double xtmax = rets[1];

		rets = check_axis(ray.origin.t[1], ray.direction.t[1]);
		if (rets[0] > xtmin)
			xtmin = rets[0];
		if (rets[1] < xtmax)
			xtmax = rets[1];

		rets = check_axis(ray.origin.t[2], ray.direction.t[2]);
		if (rets[0] > xtmin)
			xtmin = rets[0];
		if (rets[1] < xtmax)
			xtmax = rets[1];

		ArrayList<Intersection> ans = new ArrayList<Intersection>();



		if (xtmin >= xtmax || xtmax == Double.POSITIVE_INFINITY) 
			return ans;

		ans.add(new Intersection(this, xtmin));
		ans.add(new	Intersection(this, xtmax));	

		return ans;
	}



	private double[] check_axis(double origin, double direction) {
		double tmin_numerator = (-1 - origin);
		double tmax_numerator = (1 - origin);
		double tmin;
		double tmax;

		//Had an error where Aux could not be resolved to a variable, so I simply replaced Aux.EPSILON with its value 0.0001
		if (Math.abs(direction) >= 0.0001) {
			tmin = tmin_numerator / direction;
			tmax = tmax_numerator / direction;
		}
		else {
				if (tmin_numerator >= 0)
				tmin =  Double.POSITIVE_INFINITY;
				else if (tmin_numerator <=0)
					tmin = Double.NEGATIVE_INFINITY;
				else tmin = 0;
				
				if (tmax_numerator >= 0)
					tmax =  Double.POSITIVE_INFINITY;
					else if (tmax_numerator <=0)
						tmax = Double.NEGATIVE_INFINITY;
					else tmax = 0;

		}

		if (tmin > tmax) {
			double temp = tmin;
			tmin = tmax;
			tmax = temp;
		}

		return new double[] {tmin, tmax};

	}



	@Override
	public Vector local_normal_at(Point point, Intersection dontUse) {
		double[] point_vals = world_to_object(point).getT();
		int pos = 0;
		double max = -1;
		for (int i = 0; i < point_vals.length - 1; i++) {
			if (Math.abs(point_vals[i]) > max) {
				max = Math.abs(point_vals[i]);
				pos = i;
			}
		}

		if (pos == 0) {
			return new Vector(point_vals[0],0,0);
		} else if (pos == 1) {
			return new Vector(0, point_vals[1], 0);
		} else {
			return new Vector(0, 0, point_vals[2]);
		}
	}

	public static void main(String[] args) {


	}



	@Override
	public boolean includes(Traceable object) {

		return this == object;
	}

}
