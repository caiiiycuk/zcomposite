package ru.atomation.composite.misc;

import java.awt.geom.GeneralPath;

import ru.atomation.composite.ZPlaneResolver;
import ru.atomation.composite.ZValueResolver;

/**
 * Utils class, for work with planes
 * @author caiiiycuk
 *
 */
public class ZValueResolverFactory {

	/**
	 * Create a {@link ZValueResolver} for given polygon
	 * and three z coordinates 
	 * @param xpoints 
	 * @param ypoints
	 * @param z1 z-coordinate for point[0,0]
	 * @param z2 z-coordinate for point[1,1]
	 * @param z3 z-coordinate for point[2,2]
	 * @return
	 */
	public static ZValueResolver createPolygonResolver(final int[] xpoints, final int[] ypoints, double z1, double z2, double z3) {
		GeneralPath generalPath = createClippingShape(xpoints, ypoints);
		
		ZPlaneResolver resolver = new ZPlaneResolver(
				xpoints[0], xpoints[1], xpoints[2], 
				ypoints[0], ypoints[1], ypoints[2],
				z1, z2, z3);
		resolver.setClippingShape(generalPath);
		
		return resolver;
	}

	/**
	 * Create polygon clipping shape
	 * @param xpoints
	 * @param ypoints
	 * @return
	 */
	public static GeneralPath createClippingShape(final int[] xpoints,	final int[] ypoints) {
		if (xpoints.length < 3) {
			throw new IllegalArgumentException("Polygon must have >2 points");
		}
		
		GeneralPath generalPath = new GeneralPath();
		generalPath.moveTo(xpoints[0], ypoints[0]);
		for (int i=1; i<xpoints.length; i++) {
			generalPath.lineTo(xpoints[i], ypoints[i]);
		}
		generalPath.closePath();
		return generalPath;
	}

//--
	
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
	public static double[] createPlane(double x1, double x2, double x3,
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
	public static double resolveZ(double x, double y, double[] plane) {
		return - (plane[0] * x + plane[1] * y + plane[3]) / plane[2];
	}
	
}
