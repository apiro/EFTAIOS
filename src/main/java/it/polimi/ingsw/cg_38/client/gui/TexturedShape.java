package it.polimi.ingsw.cg_38.client.gui;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;

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
}