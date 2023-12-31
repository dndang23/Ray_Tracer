import java.util.ArrayList;

import javax.imageio.event.IIOWriteWarningListener;

public class World {

	// The list of Traceable Objects

	ArrayList<Traceable> objects = new ArrayList<Traceable>();

	public World() {

	}

	public void add(Traceable t) {
		objects.add(t);
	}

	public void setDefault() {


		Cube s1 = new Cube();
		Material material = new Material();
		material.color = new MyColor (0.8, 1.0, 0.6);
		material.diffuse = 0.7;
		material.specular = 0.2;
		s1.material = material;
		Cube s2 = new Cube();
		System.out.println(s2);
		s2.transform = Transformations.getTranslate(0,-8,-4);
		//s2.transform = Transformations.getScale(0.5, 0.5, 0.5);	
		System.out.println(s2);

		objects.add(s1);
		objects.add(s2);


	}

	public void setDefault2() {


		Sphere s1 = new Sphere();
		Material material = new Material();
		material.color = new MyColor (0.8, 1.0, 0.6);
		material.diffuse = 0.7;
		material.specular = 0.2;
		s1.material = material;
		Sphere s2 = new Sphere();
		s2.transform = Transformations.getTranslate(0,-8,-4);
		Material material2 = new Material();
		material2.color = new MyColor (0.8, 1.0, 0.6);
		material2.diffuse = 0.7;
		material2.specular = 0.2;
		s2.material = material2;
		//s2.transform = Transformations.getScale(0.5, 0.5, 0.5);	
		//System.out.println(s2);
		Cube s3 = new Cube();
		s3.transform = Matrices.mult(Transformations.getTranslate(0,0,-4),
									 Transformations.getScale(3, 3, 3));

		objects.add(s1);
		objects.add(s2);
		objects.add(s3);


	}

	/*
	 * NOTE:  RIGHT HAND COORDINATE SYSTEM
	 * Using 0,0,2 as the center of projection, casting rays toward the z = -1
	 * plane, with the "film" covering size by size centered at the origin
	 * 
	 * generate an image hsize by vsize pixels  and store it in fileName
	 * 
	 */

	public Canvas render(String fileName, int hsize, int vsize, double size) {


		Canvas cav = new Canvas(hsize,vsize);

		for (double i = 0; i < hsize; i = i + 1) {
			for (double j = 0; j < vsize; j = j + 1) {
				double new_i = (i*2/((double) hsize)) - 1;
				double new_j = (j*(-2)/((double) vsize)) + 1;

				double f_o_v = 10; 

				Ray r = new Ray(new Point(0,0,2), new Vector(new_i*f_o_v,new_j*f_o_v,-3));

				ArrayList<Intersection> results = intersectWorld(r);
				Intersection temp = Traceable.hit(results);

				if (temp != null) {
					MyColor color = temp.object.material.color;
					cav.writeP((int) i, (int) j, color);
				}
			}
		}

		cav.toPPM(fileName);
		//Print message saying render is done
		System.out.println("Tracer world done");

		return cav;
	}

	public Canvas render2(String fileName, int hsize, int vsize, double size) {

		Canvas cav = new Canvas(hsize,vsize);

		for (double i = 0; i < hsize; i = i + 1) {
			for (double j = 0; j < vsize; j = j + 1) {

				double new_i = (i*2/((double) hsize)) - 1;
				double new_j = (j*(-2)/((double) vsize)) + 1;

				double f_o_v = 10; 

				Ray r = new Ray(new Point(0,0,2), new Vector(new_i*f_o_v,new_j*f_o_v,-3));

				ArrayList<Intersection> results = intersectWorld(r);
				
				Intersection temp = Traceable.hit(results);

				if (temp != null) {
					cav.writeP((int) i, (int) j, new MyColor(Math.abs(new_i), Math.abs(new_j), Math.abs(new_j)));
				}
			}
		}

		cav.toPPM(fileName);
		//Print message saying render is done
		System.out.println("Tracer world done");

		return cav;
	}

	public Canvas render3(String fileName, int hsize, int vsize, double size, PointLight pointLight) {

		Canvas cav = new Canvas(hsize,vsize);

		for (double i = 0; i < hsize; i = i + 1) {
			for (double j = 0; j < vsize; j = j + 1) {

				double new_i = (i*2/((double) hsize)) - 1;
				double new_j = (j*(-2)/((double) vsize)) + 1;

				double f_o_v = 10; 

				Ray r = new Ray(new Point(0,0,2), new Vector(new_i*f_o_v,new_j*f_o_v,-3));

				ArrayList<Intersection> results = intersectWorld(r);
				
				Intersection temp = Traceable.hit(results);

				if (temp != null) {
					//get object color
					MyColor c = temp.object.material.color;
					
					//get t value
					double[] c_vals = c.getT();
					
					//get the intensity of the intersection point
					double[] pointLight_vals = pointLight.intensityAt(r.position(temp.t), this).getT();

					double[] new_colors = new double[c_vals.length];
					
					for (int k = 0; k < new_colors.length; k++) {
						new_colors[k] = c_vals[k] * pointLight_vals[k];
					}

					MyColor point_color = new MyColor(new Tuple(new_colors));

					cav.writeP((int) i, (int) j, point_color);
				}
			}
		}

		cav.toPPM(fileName);
		//Print message saying render is done
		System.out.println("Tracer world done");

		return cav;
	}

	public Canvas render4(String fileName, int hsize, int vsize, double size, PointLight pointLight) {

		Canvas cav = new Canvas(hsize,vsize);

		for (double i = 0; i < hsize; i = i + 1) {
			for (double j = 0; j < vsize; j = j + 1) {

				double new_i = (i*2/((double) hsize)) - 1;
				double new_j = (j*(-2)/((double) vsize)) + 1;

				double f_o_v = 10; 

				Ray r = new Ray(new Point(0,0,2), new Vector(new_i*f_o_v,new_j*f_o_v,-3));

				ArrayList<Intersection> results = intersectWorld(r);
				
				Intersection temp = Traceable.hit(results);

				if (temp != null) {
					//get object color
					MyColor c = temp.object.material.color;
					
					//get t value
					double[] c_vals = c.getT();

					//get intersection point
					Point point = r.position(temp.t);

					//get the coordinates of intersection point
					double[] point_vals = point.getT();
					
					//get the normal of the intersection point
					double[] normal = temp.object.local_normal_at(point, temp).getT();
					
					double constant = 0.00001;
					
					//create a new point where the normal vector pushes the intersection point by a small amount
					double[] new_point = new double[point_vals.length];
					
					for (int q = 0; q < point_vals.length; q++) {
						new_point[q] = point_vals[q] + constant*normal[q];
					} 

					Point actual_point = new Point(new Tuple(new_point));
					
					double[] pointLight_vals = pointLight.intensityAt(actual_point, this).getT();

					double[] new_colors = new double[c_vals.length];
					
					for (int k = 0; k < new_colors.length; k++) {
						new_colors[k] = c_vals[k] * pointLight_vals[k];
					}

					MyColor point_color = new MyColor(new Tuple(new_colors));

					cav.writeP((int) i, (int) j, point_color);
				}
			}
		}

		cav.toPPM(fileName);
		//Print message saying render is done
		System.out.println("Tracer world done");

		return cav;
	}

	public Canvas diffuse_light(String fileName, int hsize, int vsize, double size, PointLight pointLight) {

		Canvas cav = new Canvas(hsize,vsize);

		for (double i = 0; i < hsize; i = i + 1) {
			for (double j = 0; j < vsize; j = j + 1) {

				double new_i = (i*2/((double) hsize)) - 1;
				double new_j = (j*(-2)/((double) vsize)) + 1;

				double f_o_v = 10; 

				Ray r = new Ray(new Point(0,0,2), new Vector(new_i*f_o_v,new_j*f_o_v,-3));

				ArrayList<Intersection> results = intersectWorld(r);
				
				Intersection temp = Traceable.hit(results);

				if (temp != null) {
					//get object color
					MyColor c = temp.object.material.color;
					
					//get t value
					double[] c_vals = c.getT();

					//get intersection point
					Point point = r.position(temp.t);

					//get the coordinates of intersection point
					double[] point_vals = point.getT();
					
					//get the normal of the intersection point
					double[] normal = temp.object.local_normal_at(point, temp).getT();
					
					double constant = 0.00001;
					
					//create a new point where the normal vector pushes the intersection point by a small amount
					double[] new_point = new double[point_vals.length];
					
					for (int q = 0; q < point_vals.length; q++) {
						new_point[q] = point_vals[q] + constant*normal[q];
					} 

					Point actual_point = new Point(new Tuple(new_point));
					
					double[] pointLight_vals = pointLight.intensityAt(actual_point, this).getT();

					double[] new_colors = new double[c_vals.length];

					//new_code
					double[] normalized_normal = temp.object.local_normal_at(actual_point, temp).normalize().getT();
					double[] normalized_ls = pointLight.getDirection(actual_point, this).normalize().getT();
					double dot = 0;

					for (int b = 0; b < 3; b++) {
						dot = dot + normalized_normal[b]*normalized_ls[b];
					}

					
					
					for (int k = 0; k < new_colors.length; k++) {
						//new_colors[k] = c_vals[k] * pointLight_vals[k];
						new_colors[k] = (dot*pointLight_vals[k]*temp.object.material.diffuse + 0.2)*c_vals[k];
						if (new_colors[k] < 0) {
							new_colors[k] = 0;
						} else if (new_colors[k] > 1) {
							new_colors[k] = 1;
						}
					}

					MyColor point_color = new MyColor(new Tuple(new_colors));

					cav.writeP((int) i, (int) j, point_color);
				}
			}
		}

		cav.toPPM(fileName);
		//Print message saying render is done
		System.out.println("Tracer world done");

		return cav;
	}

	public Vector reflection_vector(Vector vector, Vector normal_vector) {
		Vector normalized_normal_vector = normal_vector.normalize();
		double dot_product = Tuple.dot(vector, normalized_normal_vector);
		return new Vector(Tuple.sub(vector, normalized_normal_vector.scale(2*dot_product)));
	}


	//my attempt at distributed ray tracing
	public Canvas distributed_ray_tracing(String fileName, int hsize, int vsize, double size, Grid grid) {

		Canvas cav = new Canvas(hsize,vsize);

		for (double i = 0; i < hsize; i = i + 1) {
			for (double j = 0; j < vsize; j = j + 1) {

				double new_i = (i*2/((double) hsize)) - 1;
				double new_j = (j*(-2)/((double) vsize)) + 1;

				double f_o_v = 10; 

				Ray r = new Ray(new Point(0,0,2), new Vector(new_i*f_o_v,new_j*f_o_v,-3));


				ArrayList<Intersection> results = intersectWorld(r);
				
				Intersection temp = Traceable.hit(results);

				if (temp != null) {
					//get object color
					MyColor c = temp.object.material.color;
					
					//get t value
					double[] c_vals = c.getT();

					//get intersection point
					Point point = r.position(temp.t);

					//get the coordinates of intersection point
					double[] point_vals = point.getT();
					
					//get the normal of the intersection point
					double[] normal = temp.object.local_normal_at(point, temp).getT();
					
					double constant = 0.00001;
					
					//create a new point where the normal vector pushes the intersection point by a small amount
					double[] new_point = new double[point_vals.length];
					
					for (int q = 0; q < point_vals.length; q++) {
						new_point[q] = point_vals[q] + constant*normal[q];
					} 

					Point actual_point = new Point(new Tuple(new_point));
					
					double[] grid_vals = grid.intensityAt(actual_point, this).getT();

					double[] new_colors = new double[c_vals.length];

					//new_code
					double[] normalized_normal = temp.object.local_normal_at(actual_point, temp).normalize().getT();
					double[] normalized_ls = grid.getDirection(actual_point, this).normalize().getT();
					double dot = 0;

					for (int b = 0; b < 3; b++) {
						dot = dot + normalized_normal[b]*normalized_ls[b];
					}

					
					
					for (int k = 0; k < new_colors.length; k++) {
						//new_colors[k] = c_vals[k] * pointLight_vals[k];
						new_colors[k] = (dot*grid_vals[k]*temp.object.material.diffuse + 0.2)*c_vals[k];
						if (new_colors[k] < 0) {
							new_colors[k] = 0;
						} else if (new_colors[k] > 1) {
							new_colors[k] = 1;
						}
					}

					MyColor point_color = new MyColor(new Tuple(new_colors));

					cav.writeP((int) i, (int) j, point_color);
				}
			}
		}

		cav.toPPM(fileName);
		//Print message saying render is done
		System.out.println("Tracer world done");

		return cav;
	}

		public Canvas transparency(String fileName, int hsize, int vsize, double size, PointLight pointLight) {

		Canvas cav = new Canvas(hsize,vsize);

		for (double i = 0; i < hsize; i = i + 1) {
			for (double j = 0; j < vsize; j = j + 1) {

				double new_i = (i*2/((double) hsize)) - 1;
				double new_j = (j*(-2)/((double) vsize)) + 1;

				double f_o_v = 10; 

				Ray r = new Ray(new Point(0,0,2), new Vector(new_i*f_o_v,new_j*f_o_v,-3));

				ArrayList<Intersection> results = intersectWorld(r);

				MyColor finalMyColor = new MyColor(0, 0, 0);
				
				Intersection temp = Traceable.hit(results);

				double transparency_counter = 0;

				while (transparency_counter < 1 && temp != null) {
					//get object color
					MyColor c = temp.object.material.color;
					
					//get t value
					double[] c_vals = c.getT();

					//get intersection point
					Point point = r.position(temp.t);

					//get the coordinates of intersection point
					double[] point_vals = point.getT();
					
					//get the normal of the intersection point
					double[] normal = temp.object.local_normal_at(point, temp).getT();
					
					double constant = 0.00001;
					
					//create a new point where the normal vector pushes the intersection point by a small amount
					double[] new_point = new double[point_vals.length];
					
					for (int q = 0; q < point_vals.length; q++) {
						new_point[q] = point_vals[q] + constant*normal[q];
					} 

					Point actual_point = new Point(new Tuple(new_point));
					
					double[] pointLight_vals = pointLight.intensityAt(actual_point, this).getT();

					//new_code
					double[] normalized_normal = temp.object.local_normal_at(actual_point, temp).normalize().getT();
					double[] normalized_ls = pointLight.getDirection(actual_point, this).normalize().getT();
					double dot = 0;

					for (int b = 0; b < 3; b++) {
						dot = dot + normalized_normal[b]*normalized_ls[b];
					}

					double[] finalMyColorArray = finalMyColor.getT();

					for (int k = 0; k < finalMyColorArray.length; k++) {
						//new_colors[k] = c_vals[k] * pointLight_vals[k];
						//adding each of the indiviudal colors together
						finalMyColorArray[k] = finalMyColorArray[k] + (dot*pointLight_vals[k]*temp.object.material.diffuse + 0.2)*c_vals[k]*(1 - temp.object.material.transparency);
						if (finalMyColorArray[k] < 0) {
							finalMyColorArray[k] = 0;
						} else if (finalMyColorArray[k] > 1) {
							finalMyColorArray[k] = 1;
						}
					}
					
					/*
					if (temp.object.material.color.getT()[1] == 1) {
						new_colors[0] = new_colors[0] * (1 - temp.object.material.transparency);
						new_colors[1] = new_colors[1] * (1 - temp.object.material.transparency);
						new_colors[2] = new_colors[2] * (1 - temp.object.material.transparency);
					}
					
					//System.out.println("===============================================================");
						
					*/

					finalMyColor = new MyColor(finalMyColor);
					transparency_counter = transparency_counter + (1 - temp.object.material.transparency);

					temp = Traceable.otherHit(results, temp.t, temp);
				}

				cav.writeP((int) i, (int) j, finalMyColor);
			}
		}
	
		cav.toPPM(fileName);
		//Print message saying render is done
		System.out.println("Tracer world done");
	
		return cav;
	}

	public Canvas my_render(String fileName, int hsize, int vsize, double size, Grid grid) {

			Canvas cav = new Canvas(hsize,vsize);
	
			for (double i = 0; i < hsize; i = i + 1) {
				for (double j = 0; j < vsize; j = j + 1) {
	
					double new_i = (i*2/((double) hsize)) - 1;
					double new_j = (j*(-2)/((double) vsize)) + 1;
	
					double f_o_v = 10; 
	
					Ray r = new Ray(new Point(0,0,2), new Vector(new_i*f_o_v,new_j*f_o_v,-3));

					MyColor finalMyColor = getColor(r, grid, 0);			

					double[] finalMyColorArray = finalMyColor.getT();

					for (int l = 0; l < finalMyColorArray.length; l++) {
						if (finalMyColorArray[l] < 0) {
							finalMyColorArray[l] = 0;
						} else if (finalMyColorArray[l] > 1) {
							finalMyColorArray[l] = 1;
						}
					}

					finalMyColor = new MyColor(finalMyColorArray[0],finalMyColorArray[1],finalMyColorArray[2]);

					cav.writeP((int) i, (int) j, finalMyColor);
				}
			}
		
			cav.toPPM(fileName);
			//Print message saying render is done
			System.out.println("Tracer world done");
		
			return cav;
		}

	public MyColor getColor(Ray r, Grid grid, int counter) {
		if (counter == 3) {
			return new MyColor(0,0,0);
		} else {
			ArrayList<Intersection> results = intersectWorld(r);
	
					MyColor finalMyColor = new MyColor(0, 0, 0);
					
					Intersection temp = Traceable.hit(results);
	
					double transparency_counter = 0;
	
					while (transparency_counter < 1 && temp != null) {
						//get object color
						MyColor c = temp.object.material.color;
						
						//get t value
						double[] c_vals = c.getT();
	
						//get intersection point
						Point point = r.position(temp.t);
	
						//get the coordinates of intersection point
						double[] point_vals = point.getT();
						
						//get the normal of the intersection point
						double[] normal = temp.object.local_normal_at(point, temp).getT();
						
						double constant = 0.00001;
						
						//create a new point where the normal vector pushes the intersection point by a small amount
						double[] new_point = new double[point_vals.length];
						
						for (int q = 0; q < point_vals.length; q++) {
							new_point[q] = point_vals[q] + constant*normal[q];
						} 
	
						Point actual_point = new Point(new Tuple(new_point));
						
						double[] grid_vals = grid.intensityAt(actual_point, this).getT();

						double[] new_colors = new double[c_vals.length];

						//new_code
						double[] normalized_normal = temp.object.local_normal_at(actual_point, temp).normalize().getT();
						double[] normalized_ls = grid.getDirection(actual_point, this).normalize().getT();
						double dot = 0;

						for (int b = 0; b < 3; b++) {
							dot = dot + normalized_normal[b]*normalized_ls[b];
						}
	
						double[] finalMyColorArray = finalMyColor.getT();
	
						for (int k = 0; k < finalMyColorArray.length; k++) {
							//new_colors[k] = c_vals[k] * pointLight_vals[k];
							//adding each of the indiviudal colors together
							finalMyColorArray[k] = finalMyColorArray[k] + (dot*grid_vals[k]*temp.object.material.diffuse + 0.2)*c_vals[k]*(1 - temp.object.material.transparency)*(1 - temp.object.material.reflective);	
						}

						if (temp.object.material.reflective > 0) {
							MyColor tempColor = getColor(new Ray(actual_point, reflection_vector(r.direction, new Vector(new Tuple(normalized_normal)))), grid, counter + 1);	
							double[] tempColorArray = tempColor.getT();
							finalMyColorArray[0] = finalMyColorArray[0] + tempColorArray[0] * temp.object.material.reflective;
							finalMyColorArray[1] = finalMyColorArray[1] + tempColorArray[1] * temp.object.material.reflective;
							finalMyColorArray[2] = finalMyColorArray[2] + tempColorArray[2] * temp.object.material.reflective;
						}
	
						finalMyColor = new MyColor(finalMyColor);
						transparency_counter = transparency_counter + (1 - temp.object.material.transparency);
	
						temp = Traceable.otherHit(results, temp.t, temp);
					}
			return finalMyColor;
		}
	}

	public ArrayList<Intersection> intersectWorld(Ray r) {

		ArrayList<Intersection> result = new ArrayList<Intersection>();


		for (Traceable o : objects) {

			ArrayList<Intersection> inters = o.intersections(r);
			result = Traceable.mergeInters(inters, result);

		}

		return result;
	}

	public static void main(String[] args) {

		World w = new World();
		Grid g = new Grid(3, 0.1, new MyColor(1,1,1), new Point(-1.0, 2.0, 2));
		w.final_image();
		w.my_render("images/final_image.ppm", 1000, 1000,10, g);
	}

	public void groupTest() {
		
		Cube cube1 = new Cube();
		cube1.transform = Matrices.mult(
			Transformations.getTranslate(0, -1, 2),
			Transformations.getScale(0.1, 0.1, 0.1));
		cube1.material = new Material();
		cube1.material.color = new MyColor(0,0,1);
		cube1.material.diffuse = 0.7;
		cube1.material.specular = 0.3;
		cube1.material.ambient = 0.9;

		Sphere sphere1 = new Sphere();
		sphere1.transform = Transformations.getRotX(45);
		sphere1.material = new Material();
		sphere1.material.color = new MyColor(1,0,0);
		sphere1.material.diffuse = 0.7;
		sphere1.material.specular = 0.3;
		sphere1.material.ambient = 0.9;

		Group group1 = new Group();
		group1.transform = Matrices.mult(
			Transformations.getTranslate(-0.5, 1,0),
			Transformations.getScale(0.5,0.5,0.7));
		group1.addObject(cube1);
		group1.addObject(sphere1);

		
		Cube cube2 = new Cube();
		cube2.transform = Matrices.mult(
		Transformations.getTranslate(0, -1, 2),
		Transformations.getScale(0.1, 0.1, 0.1));
		cube2.material = new Material();
		cube2.material.color = new MyColor(0,0,1);
		cube2.material.diffuse = 0.7;
		cube2.material.specular = 0.3;
		cube2.material.ambient = 0.9;

		Sphere sphere2 = new Sphere();
		sphere2.transform = Transformations.getRotX(45);
		sphere2.material = new Material();
		sphere2.material.color = new MyColor(1,0,0);
		sphere2.material.diffuse = 0.7;
		sphere2.material.specular = 0.3;
		sphere2.material.ambient = 0.9;

		Group group2 = new Group();
		group2.transform = Matrices.mult(
		Transformations.getRotZ(20.5),
		Transformations.getTranslate(-1.0, 3.5,0),
		Transformations.getScale(0.8,1.0,0.5));
		group2.addObject(cube2);
		group2.addObject(sphere2);
		

		add(group1);
		add(group2);
	}

	public void distributedTest() {

		Cube cube = new Cube();
		cube.transform = Matrices.mult(Transformations.getTranslate(0, 0, -3.0), Transformations.getScale(3.0,3.0,3.0));
		cube.material = new Material();
		cube.material.color = new MyColor(1,0,0);
		cube.material.diffuse = 0.7;
		cube.material.specular = 0.3;
		cube.material.ambient = 0.9;

		Cube cube1 = new Cube();
		cube1.transform = Transformations.getTranslate(0, 0, -0.5);
		cube1.material = new Material();
		cube1.material.color = new MyColor(0,1,0);
		cube1.material.diffuse = 0.7;
		cube1.material.specular = 0.3;
		cube1.material.ambient = 0.9;

		Cube cube2 = new Cube();
		cube2.transform = Transformations.getScale(0.5,0.5,0.7);
		cube2.material = new Material();
		cube2.material.color = new MyColor(0,0,1);
		cube2.material.diffuse = 0.7;
		cube2.material.specular = 0.3;
		cube2.material.ambient = 0.9;

		add(cube);
		add(cube1);
		add(cube2);
	}

	public void transparencyTest() {

		/*
		Cube cube = new Cube();
		cube.transform = Matrices.mult(Transformations.getTranslate(0, 0, -5.0), Transformations.getScale(3.0,3.0,3.0));
		cube.material = new Material();
		cube.material.color = new MyColor(1,1,1);
		cube.material.diffuse = 0.7;
		cube.material.specular = 0.3;
		cube.material.ambient = 0.9;

		Cube cube1 = new Cube();
		cube1.transform = Transformations.getTranslate(0, 0, -0.5);
		cube1.material = new Material();
		cube1.material.color = new MyColor(0,1,0);
		cube1.material.diffuse = 0.7;
		cube1.material.specular = 0.3;
		cube1.material.ambient = 0.9;
		cube1.material.transparency = 0.5;

		add(cube);
		add(cube1);
		*/

		Cube cube = new Cube();
		cube.transform = Matrices.mult(Transformations.getTranslate(0, 0, -3.0), Transformations.getScale(3.0,3.0,3.0));
		cube.material = new Material();
		cube.material.color = new MyColor(1,0,0);
		cube.material.diffuse = 0.7;
		cube.material.specular = 0.3;
		cube.material.ambient = 0.9;

		Cube cube1 = new Cube();
		cube1.transform = Transformations.getTranslate(0, 0, -0.5);
		cube1.material = new Material();
		cube1.material.color = new MyColor(0,1,0);
		cube1.material.diffuse = 0.7;
		cube1.material.specular = 0.3;
		cube1.material.ambient = 0.9;
		cube1.material.transparency = 1.0;

		Cube cube2 = new Cube();
		cube2.transform = Transformations.getScale(0.5,0.5,0.7);
		cube2.material = new Material();
		cube2.material.color = new MyColor(0,0,1);
		cube2.material.diffuse = 0.7;
		cube2.material.specular = 0.3;
		cube2.material.ambient = 0.9;
		cube2.material.transparency = 1.0;

		add(cube);
		add(cube1);
		add(cube2);
	}

	public void reflectiveTest() {

		Cube cube = new Cube();
		cube.transform = Matrices.mult(Transformations.getTranslate(0, 0, -2), Transformations.getScale(1.5,1.5,1.5));
		cube.material = new Material();
		cube.material.color = new MyColor(1,1,1);
		cube.material.diffuse = 0.7;
		cube.material.specular = 0.3;
		cube.material.ambient = 0.9;
		cube.material.reflective = 0.5;

		Cube cube1 = new Cube();
		cube1.transform = Transformations.getTranslate(0, 0, 4);
		cube1.material = new Material();
		cube1.material.color = new MyColor(0,1,0);
		cube1.material.diffuse = 0.7;
		cube1.material.specular = 0.3;
		cube1.material.ambient = 0.9;

		add(cube);
		add(cube1);
	}


	public void triple() {

		Cube middle = new Cube();
		middle.transform = Matrices.mult(
				Transformations.getTranslate(-0.5, 1,0),
				Transformations.getScale(0.5,0.5,0.7));
		middle.material = new Material();
		middle.material.color = new MyColor(1,0,1);
		middle.material.diffuse = 0.7;
		middle.material.specular = 0.3;
		middle.material.ambient = 0.9;

		add(middle);

		Cube right = new Cube();
		right.transform = Matrices.mult(
				Transformations.getTranslate(1.5, 0.5,0),
				Transformations.getScale(0.5,0.5,0.5));
		right.material = new Material();
		right.material.color = new MyColor(0,1,0);
		right.material.diffuse = 0.7;
		right.material.specular = 0.0;
		right.material.ambient = 0.9;
		add(right);

		Cube left = new Cube();
		left.transform = Matrices.mult(
				Transformations.getTranslate(-2.75, 0.73,-0.75),
				Transformations.getScale(1.33,1.33,1.33));
		left.material = new Material();
		left.material.color = new MyColor(1,0,0);
		left.material.diffuse = 0.21;
		left.material.specular = 0.3;
		left.material.ambient = 0.9;
		add(left);


		Cube fourth = new Cube();
		fourth.transform = Matrices.mult(
				Transformations.getTranslate(1.5, 2.0,-0.5),
				Transformations.getScale(0.5,0.5,0.5));
		fourth.material = new Material();
		fourth.material.color = new MyColor(1,1,1);
		fourth.material.diffuse = 1.7;
		fourth.material.specular = 0.0;
		fourth.material.ambient = 0.0;

		add(fourth);
	}

	public void final_image() {
		Cube cube = new Cube();
		cube.transform = Matrices.mult(Transformations.getTranslate(0, 0, -5.0), 
									   Transformations.getScale(15.0,15.0,3.0));
		cube.material = new Material();
		cube.material.color = new MyColor(1,0,0);
		cube.material.diffuse = 0.7;
		cube.material.specular = 0.3;
		cube.material.ambient = 0.9;

		Sphere sphere = new Sphere();
		sphere.transform = Matrices.mult(Transformations.getTranslate(0.2, 0.5, -0.1), 
										Transformations.getScale(1.0,1.0,0.7));
		sphere.material = new Material();
		sphere.material.color = new MyColor(0,1,0);
		sphere.material.diffuse = 0.7;
		sphere.material.specular = 0.3;
		sphere.material.ambient = 0.9;

		Cube cube2 = new Cube();
		cube2.transform = Matrices.mult(Transformations.getTranslate(0, -0.5, -0.7), 
						                Transformations.getScale(1.0,1.0,0.7));
		cube2.material = new Material();
		cube2.material.color = new MyColor(0,0,1);
		cube2.material.diffuse = 0.7;
		cube2.material.specular = 0.3;
		cube2.material.ambient = 0.9;

		add(cube);
		add(sphere);
		add(cube2);
	}

	

}
