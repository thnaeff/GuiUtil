/**
 * 
 */
package ch.thn.guiutil.effects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import ch.thn.util.valuerange.ImageAlphaGradient;

/**
 * Fading from one image to the other, using the given gradients
 * 
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class ImageFading {
	
	private Image image1 = null;
	private Image image2 = null;
	
	private ImageAlphaGradient gradient1 = null;
	private ImageAlphaGradient gradient2 = null;
	
	private BufferedImage imageFaded = null;
	
	private Graphics2D graphicsFaded = null;
	
	private Float nextAlpha1 = null;
	private Float nextAlpha2 = null;
	
	private int width = 0;
	private int height = 0;
	
	/**
	 * 
	 * 
	 * @param imageToDrawOn
	 * @param image1
	 * @param image2
	 * @param gradient1
	 * @param gradient2
	 */
	public ImageFading(BufferedImage imageToDrawOn, Image image1, Image image2, ImageAlphaGradient gradient1, 
			ImageAlphaGradient gradient2) {
		this.image1 = image1;
		this.image2 = image2;
		this.gradient1 = gradient1;
		this.gradient2 = gradient2;
		
		if (image1 == null) {
			width = image2.getWidth(null);
			height = image2.getHeight(null);
		} else {
			width = image1.getWidth(null);
			height = image1.getHeight(null);
		}
		
		if (imageToDrawOn == null) {
			imageFaded = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			graphicsFaded = imageFaded.createGraphics();
		} else {
			setFadingImage(imageToDrawOn);
		}
		
		reset();
		
	}
	
	/**
	 * Sets the image which should be used to draw the faded images
	 * 
	 * @param imageToDrawOn
	 */
	public void setFadingImage(BufferedImage imageToDrawOn) {
		this.imageFaded = imageToDrawOn;
		graphicsFaded = imageToDrawOn.createGraphics();
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public BufferedImage getFadingImage() {
		return imageFaded;
	}
	
	/**
	 * Resets the gradients and continues with the first gradient value
	 */
	public void reset() {
  		
  		if (gradient1 != null) {
	  		gradient1.reset();
	  		nextAlpha1 = gradient1.getNext().floatValue();
  		}
  		
	  	if (gradient2 != null) {
	  		gradient2.reset();
	  		nextAlpha2 = gradient2.getNext().floatValue();
  		}
	  	
	  	
	}
	
	/**
	 * 
	 */
	private void clearImage() {
		graphicsFaded.setBackground(new Color(0, 0, 0, 0));
	  	graphicsFaded.clearRect(0, 0, width, height);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean fade() {
	  	clearImage();
	  		  	
	  	//--- image1
	  	if (image1 != null) {
	  		if (nextAlpha1 != null && nextAlpha1 != 0.0) {
	  			//Don't draw anything if alpha value is 0
			  	graphicsFaded.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, nextAlpha1));
			  	graphicsFaded.drawImage(image1, 0, 0, null);
	  		}
	  	}
	  	
	  	if (gradient1 != null) {
	  		//Keep the old alpha value if there is no next one
		  	if (gradient1.hasNext()) {
		  		nextAlpha1 = gradient1.getNext().floatValue();
		  	}
	  	}
	  	
	  	
	  	//--- image2
	  	if (image2 != null) {
	  		if (nextAlpha2 != null && nextAlpha2 != 0.0) {
	  			//Don't draw anything if alpha value is 0
	  			graphicsFaded.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, nextAlpha2));
	  			graphicsFaded.drawImage(image2, 0, 0, null);
	  		}
	  	}
	  	
	  	if (gradient2 != null) {
	  		//Keep the old alpha value if there is no next one
		  	if (gradient2.hasNext()) {
		  		nextAlpha2 = gradient2.getNext().floatValue();
		  	}
	  	}
	  	
	  	
	  	
	  	//The fading is done if there are no alpha values left any more
	  	if ((gradient1 == null || !gradient1.hasNext()) 
	  			&& (gradient2 == null || !gradient2.hasNext())) {
	  		reset();
	  		return false;
	  	} else {
	  		return true;
	  	}
	  	
	  	
	}
	

}
