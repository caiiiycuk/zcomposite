package ca.atomation.composite;

import ca.atomation.composite.math.PlaneUtils;

/**
 * Resolve z-value based on plane {@link ZValueResolver}
 * @author caiiiycuk
 *
 */
public class ZPlaneResolver implements ZValueResolver {

	protected double[] plane;
	
	/**
	 * Create {@link ZValueResolver} based on plane (3 points defenitaion)
	 * @param x1 coordinate
	 * @param x2 coordinate
	 * @param x3 coordinate
	 * @param y1 coordinate
	 * @param y2 coordinate
	 * @param y3 coordinate
	 * @param z1 coordinate
	 * @param z2 coordinate
	 * @param z3 coordinate
	 */
	public ZPlaneResolver(double x1, double x2, double x3, double y1, double y2, double y3, double z1, double z2, double z3) {
		this(PlaneUtils.plane(x1, x2, x3, y1, y2, y3, z1, z2, z3));
	}
	
	/**
	 * Create {@link ZValueResolver} based on plane
	 * @param plane Ax + By + Cz + D = 0 => A = plane[0], B = plane[1], C = plane[2], D = plane[3]
	 */
	public ZPlaneResolver(double[] plane) {
		setPlane(plane);
	}
	
	
	/**
	 * Set new plane to work with
	 * @param plane Ax + By + Cz + D = 0 => A = plane[0], B = plane[1], C = plane[2], D = plane[3]
	 */
	public void setPlane(double[] plane) {
		if (plane.length != 4) {
			throw new IllegalArgumentException("");
		}
		
		this.plane = plane;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public double resolve(double x, double y) {
		return PlaneUtils.z(x, y, plane);
	}

}
