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
				int dstOutX = -dstOut.getSampleModelTranslateX() + x;
				int dstOutY = -dstOut.getSampleModelTranslateY() + y;
				int dstInX = -dstIn.getSampleModelTranslateX() + x;
				int dstInY = -dstIn.getSampleModelTranslateY() + y;
//				int srcInX = -src.getSampleModelTranslateX() + x;
//				int srcInY = -src.getSampleModelTranslateY() + y;
				
				double dstZ = zComposite.getZOf(dstInX, dstInY);
				double srcZ = zComposite.getValueResolver().resolve(dstOutX, dstOutY);
				
				if (srcZ < dstZ) {
					zComposite.setZOf(dstOutX, dstOutY, srcZ);
					dstOut.setSample(x, y, R_BAND, src.getSample(x, y, R_BAND)); //R
					dstOut.setSample(x, y, G_BAND, src.getSample(x, y, G_BAND)); //G
					dstOut.setSample(x, y, B_BAND, src.getSample(x, y, B_BAND)); //B
				} else if (srcZ == dstZ) {
					dstOut.setSample(x, y, R_BAND, src.getSample(x, y, R_BAND)); //R
					dstOut.setSample(x, y, G_BAND, src.getSample(x, y, G_BAND)); //G
					dstOut.setSample(x, y, B_BAND, src.getSample(x, y, B_BAND)); //B
				} else {
					dstOut.setSample(x, y, R_BAND, dstIn.getSample(x, y, R_BAND)); //R
					dstOut.setSample(x, y, G_BAND, dstIn.getSample(x, y, G_BAND)); //G
					dstOut.setSample(x, y, B_BAND, dstIn.getSample(x, y, B_BAND)); //B
//					dstOut.setSample(x, y, R_BAND, 255); //R
//					dstOut.setSample(x, y, G_BAND, 0); //G
//					dstOut.setSample(x, y, B_BAND, 0); //B
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
