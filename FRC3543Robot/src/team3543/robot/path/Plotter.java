package team3543.robot.path;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Plotter {
	int w = 800;
	int h = 600;
	int conversion = 800 / 20;	// 800 is 10 meters
	JFrame frame;
	JPanel panel;
	
	public Plotter() {
		frame = new JFrame("Plot");
		frame.setSize(w,h);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.doLayout();
	}
	
	public void plot(Path path, double wheelBase) {
		if (panel != null) {
			frame.remove(panel);
		}
		panel = new PlotCanvas(path, wheelBase);
		panel.setSize(w,h);
		frame.add(panel);
		frame.doLayout();
	}
		
	class PlotCanvas extends JPanel {
		Path path;
		double wheelBase;
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public PlotCanvas(Path path, double wheelBase) {
			this.path = path;
			this.wheelBase = wheelBase;
		}
		
		@Override
		public void paintComponent( Graphics g ) {
			g.setColor(java.awt.Color.RED);
			g.drawOval(100, 100, 50, 50);
			for (PositionAndVelocity pv : path) {
				plotXYThetaWheelbase(g, pv.position.getX(), pv.position.getY(), pv.position.getTheta(), wheelBase);
			}
			
		}
		
		void plotXYThetaWheelbase(Graphics g, double x, double y, double theta, double wheelBase) {
			System.out.println(String.format("%f, %f, %f", x,y,theta));
			double xp = convert(x);
			double yp = convert(y);
			double w2 = convert(wheelBase) / 2;
			double arrow = 10;
			double lx = xp - w2 * Math.cos(theta);
			double ly = yp - w2 * Math.sin(theta);
			double rx = xp + w2 * Math.cos(theta);
			double ry = yp + w2 * Math.sin(theta);
			
			g.setPaintMode();
			g.setColor(java.awt.Color.BLACK);
			System.out.println(String.format("%d,%d", (int)lx, (int)ly));
	        g.drawLine((int)lx, (int) (h - ly), (int)rx, (int)(h - ry));
	        // plot the theta
	        rx  = xp + arrow * Math.sin(Math.PI - theta);
	        ry = yp - arrow * Math.cos(Math.PI - theta);        
	        g.drawLine((int)xp, (int)(h - yp), (int)rx, (int)(h - ry));  
	        frame.doLayout();
		}
		
		int convert(double v) {
			return (int)(v * this.getWidth() / 10);
		}
	}
	
}
