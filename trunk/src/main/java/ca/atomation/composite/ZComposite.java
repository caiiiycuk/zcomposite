package ca.atomation.composite;

import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;
import java.util.Arrays;

/**
 * ZComposite emulates ZBuffer 
 * @author caiiiycuk
 */
public class ZComposite implements Composite {

	protected float[][] buffer;
	protected int width;
	protected int height;
	
	protected ZValueResolver valueResolver;
	
	public ZComposite(int width, int height) {
		buffer = new float[height][width];
		
		this.width = width;
		this.height = height;
		
		clearBufferBit();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public CompositeContext createContext(ColorModel srcColorModel,
			ColorModel dstColorModel, RenderingHints hints) {
		return new ZCompositeContext(this);
	}

	/**
	 * Clear ZBuffer (fill buffer with Float.MAX_VALUE)
	 */
	public void clearBufferBit() {
		for (int y=0; y<height; y++) {
			Arrays.fill(buffer[y], Float.MAX_VALUE);
		}
	}
	
	/**
	 * Set z-value in buffer for given point
	 * @param x coordinate
	 * @param y coordinate
	 * @param value z-value
	 */
	public void setZOf(int x, int y, float value) {
		if (x >= width || x < 0 ||
			y >= height || y < 0) {
			throw new IllegalArgumentException("Point [" + x + ", " + y + "] is outside of the Z Buffer array");
		}
		
		buffer[y][x] = value;
	}
	
	/**
	 * Get Z Buffer values in array
	 * @return values in array
	 */
	public float[][] getBuffer() {
		return buffer;
	}
	
	/**
	 * Set z-value resolver 
	 * @param valueResolver {@link ZValueResolver}
	 */
	public void setValueResolver(ZValueResolver valueResolver) {
		this.valueResolver = valueResolver;
	}
	
	/**
	 * Get z-value resolver
	 * @return {@link ZValueResolver}
	 */
	public ZValueResolver getValueResolver() {
		return valueResolver;
	}

	public float getZOf(int realX, int realY) {
		return buffer[realY][realX];
	}
	
}
