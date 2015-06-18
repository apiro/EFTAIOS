package it.polimi.ingsw.cg_38.gui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

class TexturedShape {

    public static BufferedImage getTexturedImage(
            BufferedImage src, Shape shp) {
        Rectangle r = shp.getBounds();
        // create a transparent image with 1 px padding.
        BufferedImage tmp = new BufferedImage(
                r.width+2,r.height+2,BufferedImage.TYPE_INT_ARGB);
        // get the graphics object
        Graphics2D g = tmp.createGraphics();
        // set some nice rendering hints
        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(
                RenderingHints.KEY_COLOR_RENDERING, 
                RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        // create a transform to center the shape in the image
        AffineTransform centerTransform = AffineTransform.
                getTranslateInstance(-r.x+1, -r.y+1);
        // set the transform to the graphics object
        g.setTransform(centerTransform);
        // set the shape as the clip
        g.setClip(shp);
        // draw the image
        g.drawImage(src, 0, 14, null);
        // clear the clip
        g.setClip(null);
        // draw the shape as an outline
        Color myCol = new Color(110,110,110);
        g.setColor(myCol);
        g.setStroke(new BasicStroke(1f));
        g.draw(shp);
        // dispose of any graphics object we explicitly create
        g.dispose();

        return tmp;
    }

    public static Shape fromPolygonToShape(Polygon poly) {
    	
    	GeneralPath p = new GeneralPath();
    	
    	p.moveTo(poly.xpoints[0], poly.ypoints[0]);
    	for(int i = 1; i<poly.npoints; i++) {
    		
           p.lineTo(poly.xpoints[i], poly.ypoints[i]);
    	}
    	p.closePath();
    	return p;
    	
    }
    
    public static Shape getPointedShape(int points, int radius) {
        double angle = Math.PI * 2 / points;

        GeneralPath p = new GeneralPath();
        for (int ii = 0; ii < points; ii++) {
            double a = angle * ii;

            double x = (Math.cos(a) * radius) + radius;
            double y = (Math.sin(a) * radius) + radius;
            if (ii == 0) {
                p.moveTo(x, y);
            } else {
                p.lineTo(x, y);
            }
        }
        p.closePath();
        return p;
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("https://www.willowandshea.com/images/resized/media/uploads/products/IMG_0102_200x174.jpg");
        BufferedImage bi = ImageIO.read(url);
        Shape hxgn = getPointedShape(6, 100);
        final BufferedImage txtr = getTexturedImage(bi, hxgn);
        
        HexagonHandler.setXYasVertex(false); //RECOMMENDED: leave this as FALSE.
 	    HexagonHandler.setHeight(45); //Either setHeight or setSize must be run to initialize the hex
 	    HexagonHandler.setBorders(15);
        Polygon poly = HexagonHandler.hex(0,0);
        Shape myShape = fromPolygonToShape(poly);
        final BufferedImage fromImageToShape = getTexturedImage(bi, myShape);
        
        
        Runnable r = new Runnable() {
            @Override
            public void run() {

            	JFrame mainFrame;
                mainFrame = new JFrame("test");
                mainFrame.setSize(300, 300);
                /*mainFrame.setSize( (int)(SCRSIZE/1.23), SCRSIZE);*/
                
                mainFrame.addWindowListener(new WindowAdapter() {
                   public void windowClosing(WindowEvent windowEvent){
          	        System.exit(0);
                   }        
                });
                
                mainFrame.setVisible(true); 
                
                /*JPanel controlPanel = new JPanel();
                mainFrame.add(controlPanel);
                mainFrame.setVisible(true); 
                
                controlPanel.setLayout(new GridLayout(2,1,0,0));
                
                JLabel lbl1 = new JLabel(new ImageIcon(fromImageToShape));
                JLabel lbl2 = new JLabel(new ImageIcon(fromImageToShape));
               
                controlPanel.add(lbl1);
                controlPanel.add(lbl2);*/
            	
            }
        };
        // Swing GUIs should be created and updated on the EDT
        // http://docs.oracle.com/javase/tutorial/uiswing/concurrency
        SwingUtilities.invokeLater(r);
    }
}