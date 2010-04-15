package ru.atomation.composite;

import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.image.ColorModel;
import java.util.Arrays;

/**
 * ZComposite emulates ZBuffer 
 * @author caiiiycuk
 */
public class ZComposite implements Composite {

	protected double[] clearBuffer;
	protected double[] buffer;
	protected int width;
	protected int height;
	
	protected ZValueResolver valueResolver;
	protected boolean antialiasingEnabled;
	
	public ZComposite(int width, int height) {
		buffer = new double[height * width];
		
		clearBuffer = new double[height * width];
		Arrays.fill(clearBuffer, Double.MAX_VALUE);

		this.antialiasingEnabled = false;
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
		System.arraycopy(clearBuffer, 0, buffer, 0, buffer.length);
	}
	
	/**
	 * Set z-value in buffer for given point
	 * @param x coordinate
	 * @param y coordinate
	 * @param value z-value
	 */
	public void setZOf(int x, int y, double value) {
		if (x >= width || x < 0 ||
			y >= height || y < 0) {
			throw new IllegalArgumentException("Point [" + x + ", " + y + "] is outside of the Z Buffer array");
		}
		
		buffer[y*width + x] = value;
	}
	
	/**
	 * Get Z Buffer values in array
	 * @return values in array
	 */
	public double[] getBuffer() {
		return buffer;
	}
	
	/**
	 * Set z-value resolver 
	 * @param valueResolver {@link ZValueResolver}
	 */
	public void setValueResolver(ZValueResolver valueResolver) {
		this.valueResolver = valueResolver;
		this.valueResolver.setAntialiasingEnabled(antialiasingEnabled);
	}
	
	/**
	 * Get z-value resolver
	 * @return {@link ZValueResolver}
	 */
	public ZValueResolver getValueResolver() {
		return valueResolver;
	}

	public double getZOf(int realX, int realY) {
		return buffer[realY*width + realX];
	}

	/**
	 * @return is Antialiasing Enabled 
	 */
	public boolean isAntialiasingEnabled() {
		return antialiasingEnabled;
	}
	
	/**
	 * {@link RenderingHints}
	 * @param key
	 * @param value
	 */
	public void setRenderingHint(Key key, Object value) {
		if (RenderingHints.KEY_ANTIALIASING.equals(key)) {
			this.antialiasingEnabled = RenderingHints.VALUE_ANTIALIAS_ON.equals(value);
		}
	}
	
}
