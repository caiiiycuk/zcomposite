package ca.atomation.composite;

/**
 * Converts given x, y coordinate to z coordinate
 * @author caiiiycuk
 *
 */
public interface ZValueResolver {

	/**
	 * @param x given x coordinate
	 * @param y given y coordinate
	 * @return z coordinate of x, y
	 */
	double resolve(double x, double y);
	
}
