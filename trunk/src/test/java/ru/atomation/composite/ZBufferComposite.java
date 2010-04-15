package ru.atomation.composite;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ru.atomation.composite.ZComposite;
import ru.atomation.composite.ZPlaneResolver;

/**
 * Shows how to use zbuffer composite
 * @author caiiiycuk
 *
 */
public class ZBufferComposite {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final JFrame frame = new JFrame("ZBuffer composite");
		
		JPanel bufferdPanel = new JPanel() {
			private static final long serialVersionUID = 1959924209249841311L;

			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				ZComposite composite = new ZComposite(640, 480);

				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setComposite(composite);
				

				final GeneralPath generalPath = new GeneralPath();
				generalPath.moveTo(0, 0);
				generalPath.lineTo(100, 50);
				generalPath.lineTo(100, 100);
				generalPath.lineTo(0, 50);
				generalPath.closePath();
				
				composite.setValueResolver(new ZPlaneResolver(0, 100, 100, 0, 50, 100, 100, 0, 0) {
					public double resolve(double x, double y) {
						if (generalPath.contains(x, y)) {
							return super.resolve(x, y);
						}
						
						return Double.MAX_VALUE;
					}
				});
				
				g2d.setColor(Color.red);
				g2d.fill(generalPath);
				g2d.setColor(Color.yellow);
				g2d.draw(generalPath);
				

				final GeneralPath generalPath2 = new GeneralPath();
				generalPath2.moveTo(0, 50);
				generalPath2.lineTo(100, 0);
				generalPath2.lineTo(100, 50);
				generalPath2.lineTo(0, 100);
				generalPath2.closePath();

				composite.setValueResolver(new ZPlaneResolver(0, 100, 100, 50, 0, 50, 0, 100, 100) {
					public double resolve(double x, double y) {
						if (generalPath2.contains(x, y)) {
							return super.resolve(x, y);
						}
						
						return Double.MAX_VALUE;
					}
				});
				g2d.setColor(Color.yellow);
				g2d.fill(generalPath2);
				g2d.setColor(Color.red);
				g2d.draw(generalPath2);
			}
		};
		
		frame.getContentPane().add(bufferdPanel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(640, 480);
		frame.setLocationRelativeTo(null);
		
		frame.getContentPane().addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				frame.setTitle(e.getX()+", " +e.getY());
			}
		});
		
		frame.setVisible(true);
	}

}
