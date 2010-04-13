package ca.atomation.composite.math;

/**
 * Utils class, for work with planes
 * @author caiiiycuk
 *
 */
public class PlaneUtils {

	/**
	 * Получить уравнение плоскости по трем точкам, уравнение для рассчета
	 * A = y1 (z2 - z3) + y2 (z3 - z1) + y3 (z1 - z2) 
	 * B = z1 (x2 - x3) + z2 (x3 - x1) + z3 (x1 - x2) 
	 * C = x1 (y2 - y3) + x2 (y3 - y1) + x3 (y1 - y2) 
	 * D = -(x1 (y2 z3 - y3 z2) + x2 (y3 z1 - y1 z3) + x3 (y1 z2 - y2 z1) )
	 * @param x1
	 * @param x2
	 * @param x3
	 * @param y1
	 * @param y2
	 * @param y3
	 * @param z1
	 * @param z2
	 * @param z3
	 * @return уравнение: Ax + By + Cz + D = 0
	 */
	public static double[] plane(double x1, double x2, double x3,
			double y1, double y2, double y3,
			double z1, double z2, double z3) {
		double[] plane = new double[4];
		
		//A
		plane[0] = y1*(z2 -z3) + y2*(z3 -z1) + y3*(z1 -z2);
		
		//B
		plane[1] = z1*(x2 -x3) + z2*(x3 -x1) + z3*(x1 -x2);
		
		//C
		plane[2] = x1*(y2 -y3) + x2*(y3 -y1) + x3*(y1 -y2);
		
		//D
		plane[3] = -( x1*(y2*z3 -y3*z2) + x2*(y3*z1 -y1*z3) + x3*(y1*z2 -y2*z1));
		
		return plane;
	}
	
	/**
	 * Получить z координату
	 * @param x координата
	 * @param y координата
	 * @param plane уравнение: Ax + By + Cz + D = 0
	 * @return z-координата (z = -(Ax + By + D) / C
	 */
	public static double z(double x, double y, double[] plane) {
		return - (plane[0] * x + plane[1] * y + plane[3]) / plane[2];
	}
	
}
