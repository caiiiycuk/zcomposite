package ca.atomation.composite;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

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
		JFrame frame = new JFrame("ZBuffer composite");
		
		JPanel bufferdPanel = new JPanel() {
			private static final long serialVersionUID = 1959924209249841311L;

			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				ZComposite composite = new ZComposite(640, 480);
				g2d.setComposite(composite);
				
				g2d.setColor(Color.red);
				composite.setValueResolver(new ZValueResolver() {
					public float resolve(int x, int y) {
						return (float)Math.random();
					}
				});
				g2d.fillOval(10, 10, 100, 100);
				
				g2d.setColor(Color.yellow);
				composite.setValueResolver(new ZValueResolver() {
					public float resolve(int x, int y) {
						return (float)Math.random();
					}
				});
				g2d.fillOval(50, 50, 150, 150);
			}
		};
		
		frame.getContentPane().add(bufferdPanel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(640, 480);
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
	}

}
