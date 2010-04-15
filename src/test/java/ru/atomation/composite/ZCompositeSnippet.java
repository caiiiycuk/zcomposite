package ru.atomation.composite;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ru.atomation.composite.misc.ZCompositeUtils;

/**
 * Shows how to use zbuffer composite
 * @author caiiiycuk
 *
 */
public class ZCompositeSnippet {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final JFrame frame = new JFrame("ZBuffer composite");
		
		JPanel bufferdPanel = new JPanel() {
			private static final long serialVersionUID = 1959924209249841311L;

			protected void paintComponent(Graphics g) {
				int[] xpoints;
				int[] ypoints;
				
				Graphics2D g2d = (Graphics2D) g;
				ZComposite composite = new ZComposite(640, 480);
				composite.setAntialiasingEnabled(true);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setComposite(composite);
				
				xpoints = new int[] {
					0, 100, 100, 0
				};
				
				ypoints = new int[] {
					0, 50, 100, 50
				};
				
				composite.setValueResolver(ZCompositeUtils.zForPolygonResolver(xpoints, ypoints, 100, 0, 0));
				
				g2d.setColor(Color.red);
				g2d.fillPolygon(xpoints, ypoints, xpoints.length);
				g2d.setColor(Color.yellow);
				g2d.drawPolygon(xpoints, ypoints, xpoints.length);
				
				
				xpoints = new int[] {
					0, 100, 100, 0
				};
					
				ypoints = new int[] {
					50, 0, 50, 100
				};
				
				composite.setValueResolver(ZCompositeUtils.zForPolygonResolver(xpoints, ypoints, 0, 100, 100));

				g2d.setColor(Color.yellow);
				g2d.fillPolygon(xpoints, ypoints, xpoints.length);
				g2d.setColor(Color.red);
				g2d.drawPolygon(xpoints, ypoints, xpoints.length);
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
