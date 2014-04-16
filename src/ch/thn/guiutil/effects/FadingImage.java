/**
 *    Copyright 2014 Thomas Naeff (github.com/thnaeff)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package ch.thn.guiutil.effects;

import java.awt.Component;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

import ch.thn.util.valuerange.ImageAlphaGradient;

/**
 * 
 * 
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class FadingImage {
	
	public final int DELAY_NONE;
	public final int DELAY_ALL;
	public final int DELAY_HALF;
	
	private int steps = 0;
	private int timeout = 0;
	
	private BufferedImage imageOut = null;
	
	private ImageAnimation imageAnimation = null;
	
	private Image previousImage = null;
			
	/**
	 * 
	 * 
	 * @param componentToRepaint
	 * @param width
	 * @param height
	 * @param steps
	 * @param timeout
	 */
	public FadingImage(Component componentToRepaint,  
			int width, int height, int steps, int timeout) {
		DELAY_NONE = 0;
		DELAY_ALL = steps;
		DELAY_HALF = steps/2;
		
		init(componentToRepaint, null, width, height, steps, timeout);
	}
	
	/**
	 * 
	 * 
	 * @param componentToRepaint
	 * @param repaintArea
	 * @param width
	 * @param height
	 * @param steps
	 * @param timeout
	 */
	public FadingImage(Component componentToRepaint, Rectangle repaintArea, 
			int width, int height, int steps, int timeout) {
		DELAY_NONE = 0;
		DELAY_ALL = steps;
		DELAY_HALF = steps/2;
		
		init(componentToRepaint, null, width, height, steps, timeout);
	}
	
	/**
	 * 
	 * 
	 * @param componentToRepaint
	 * @param repaintArea
	 * @param width
	 * @param height
	 * @param steps
	 * @param timeout
	 */
	private void init(Component componentToRepaint, Rectangle repaintArea, 
			int width, int height, int steps, int timeout) {
		this.steps = steps;
		this.timeout = timeout;
		
		imageOut = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		imageAnimation = new ImageAnimation(componentToRepaint, repaintArea, imageOut);
		
		Thread t = new Thread(imageAnimation);
		t.setName(this.toString());
		t.start();
	}
	
	/**
	 * Set the image where the animations should be drawn on
	 * 
	 * @param imageOut
	 */
	public void setAnimatedImage(BufferedImage imageOut) {
		this.imageOut = imageOut;
		imageAnimation.setAnimatedImage(imageOut);
	}
	
	/**
	 * Returns the image which is used to draw the images.<br>
	 * Draw this image on a component (or add it to a {@link JLabel} with 
	 * new JLabel(new ImageIcon(animatedImage)) for example) to use the animation.
	 * 
	 * @return
	 */
	public BufferedImage getAnimatedImage() {
		return imageOut;
	}
	
	/**
	 * Adds a new image which starts fading in after the given delay.
	 * 
	 * @param image
	 * @param delay The delay in steps
	 */
	public void addImage(Image image, int delay) {
		//Fade the new image in with the given delay
		ImageAlphaGradient newGradient = new ImageAlphaGradient(ImageAlphaGradient.FADE_IN, steps, delay, 0);
		
		//Fade the previous image out
		ImageAlphaGradient previousGradient = new ImageAlphaGradient(ImageAlphaGradient.FADE_OUT, steps, 0, 0);
		
		//Add a new fading step, from the last image to the new one
		imageAnimation.addStep(previousImage, image, previousGradient, newGradient, timeout);
		
		previousImage = image;
		
	}
	
	/**
	 * Sets the given image and starts fading out the current image (if there is 
	 * one) right away. The new image starts fading in after the given delay.
	 * 
	 * @param image
	 * @param delay
	 */
	public void setImage(Image image, int delay) {
		addImage(image, delay);
		imageAnimation.fade();
	}
	
	/**
	 * Starts the fading and repeats with the given number of loops.<br>
	 * Set loops=0 for infinite number of loops.
	 * 
	 * @param loops
	 */
	public void fade(int loops) {
		imageAnimation.fade(loops);
	}
	
	/**
	 * Starts the fading once
	 */
	public void fade() {
		imageAnimation.fade();
	}
	
	

}
