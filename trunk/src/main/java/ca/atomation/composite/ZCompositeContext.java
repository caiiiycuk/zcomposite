package ca.atomation.composite;

import java.awt.CompositeContext;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

/**
 * Composite emulates Z buffer
 * @author caiiiycuk
 */
public class ZCompositeContext implements CompositeContext {

	protected final static byte R_BAND = 0;
	protected final static byte G_BAND = 1;
	protected final static byte B_BAND = 2;
	
	protected ZComposite zComposite;
	
	ZCompositeContext(ZComposite zComposite) {
		this.zComposite = zComposite;
	}

	/**
	 * {@inheritDoc}
	 */
	public void compose(Raster src, Raster dstIn, WritableRaster dstOut) {
		if (zComposite.getValueResolver() == null) {
			throw new IllegalArgumentException("You must set a ZValueResolver before draw any polygon with this composite");
		}
		
		int maxX = dstOut.getMinX() + dstOut.getWidth();
		int maxY = dstOut.getMinY() + dstOut.getHeight();

		for (int y = dstOut.getMinY(); y < maxY; y++) {
			for (int x = dstOut.getMinX(); x < maxX; x++) {
				int realX = -dstOut.getSampleModelTranslateX() + x;
				int realY = -dstOut.getSampleModelTranslateY() + y;
				
				float dstZ = zComposite.getZOf(realX, realY);
				float srcZ = zComposite.getValueResolver().resolve(realX, realY);
				
				if (srcZ <= dstZ) {
					zComposite.setZOf(realX, realY, srcZ);
					
					dstOut.setSample(x, y, R_BAND, src.getSample(x, y, R_BAND)); //R
					dstOut.setSample(x, y, G_BAND, src.getSample(x, y, G_BAND)); //G
					dstOut.setSample(x, y, B_BAND, src.getSample(x, y, B_BAND)); //B
				} else {
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void dispose() {
	}

}
