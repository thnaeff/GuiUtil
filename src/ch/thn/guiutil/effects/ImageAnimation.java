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
import java.util.LinkedList;

import javax.swing.JLabel;

import ch.thn.util.thread.ControlledRunnable;
import ch.thn.util.valuerange.ImageAlphaGradient;


/**
 * A class to create a simple image animation, using multiple images, gradients 
 * and animation speeds.
 * 
 * 
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class ImageAnimation extends ControlledRunnable {
	
	private LinkedList<ImageFading> images = null;
	private LinkedList<Double> timeouts = null;
	
	private BufferedImage imageOut = null;
	
	private Component componentToRepainting = null;
	
	private Rectangle repaintArea = null;
	
	private int index = 0;
	private int loop = 0;
	private int loops = 0;
	
	private boolean stopWhenDone = false;
	
	
	/**
	 * 
	 * 
	 * @param componentToRepaint The component on which the image is being drawn
	 * @param width The width of the animated image(s)
	 * @param height The height of the animated image(s)
	 */
	public ImageAnimation(Component componentToRepaint, int width, int height) {
		super(true);
		init(componentToRepaint, null, null, width, height);
	}
	
	/**
	 * 
	 * @param componentToRepaint The component on which the image is being drawn
	 * @param imageOut The image where the animations should be drawn on
	 */
	public ImageAnimation(Component componentToRepaint, BufferedImage imageOut) {
		super(true);
		init(componentToRepaint, null, imageOut, 0, 0);
	}
	
	/**
	 * 
	 * 
	 * @param componentToRepaint The component on which the image is being drawn
	 * @param repaintArea The area which should be updated after painting a new image
	 * @param width The width of the animated image(s)
	 * @param height The height of the animated image(s)
	 */
	public ImageAnimation(Component componentToRepaint, Rectangle repaintArea, 
			int width, int height) {
		super(true);
		init(componentToRepaint, repaintArea, null, width, height);
	}
	
	/**
	 * 
	 * @param componentToRepaint The component on which the image is being drawn
	 * @param repaintArea The area which should be updated after painting a new image
	 * @param imageOut The image where the animations should be drawn on
	 */
	public ImageAnimation(Component componentToRepaint, Rectangle repaintArea, 
			BufferedImage imageOut) {
		super(true);
		init(componentToRepaint, repaintArea, imageOut, 0, 0);
	}
	
	/**
	 * 
	 * @param componentToRepaint
	 * @param repaintArea
	 * @param imageOut The image where the animations should be drawn on
	 * @param width
	 * @param height
	 */
	private void init(Component componentToRepaint, Rectangle repaintArea, 
			BufferedImage imageOut, int width, int height) {
		this.componentToRepainting = componentToRepaint;
		this.repaintArea = repaintArea;
		
		images = new LinkedList<ImageFading>();
		timeouts = new LinkedList<Double>();
		
		if (imageOut == null) {
			this.imageOut = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		} else {
			this.imageOut = imageOut;
		}
		
		pause(true);
	}
	
	/**
	 * Set the image where the animations should be drawn on
	 * 
	 * @param imageOut
	 */
	public void setAnimatedImage(BufferedImage imageOut) {
		this.imageOut = imageOut;
		
		//Update all existing images
		for (ImageFading imageFading : images) {
			imageFading.setFadingImage(imageOut);
		}
	}
	
	/**
	 * Returns the image which is used to draw the modified (faded) images.<br>
	 * Draw this image on a component (or add it to a {@link JLabel} with 
	 * new JLabel(new ImageIcon(animatedImage)) for example) to use the animation.
	 * 
	 * @return
	 */
	public BufferedImage getAnimatedImage() {
		return imageOut;
	}
	
	/**
	 * Set a specific area (the area within which the image is being drawn) which 
	 * needs to be updated after drawing a new image. If set to null, the whole 
	 * component is updated.
	 * 
	 * @param rect
	 */
	public void setRepaintArea(Rectangle rect) {
		repaintArea = rect;
	}
	
	/**
	 * Removes all the images which have been added to this animation
	 */
	public void removeAll() {
		images.clear();
		timeouts.clear();
	}
	
	/**
	 * Adds a new step to the animation. This step fades from one image to the other 
	 * using the given gradients.
	 * 
	 * @param image1
	 * @param image2
	 * @param gradient1
	 * @param gradient2
	 * @param timeout
	 */
	public void addStep(Image image1, Image image2, ImageAlphaGradient gradient1, 
			ImageAlphaGradient gradient2, double timeout) {
		
		images.add(new ImageFading(imageOut, image1, image2, gradient1, gradient2));
		timeouts.add(timeout);		
	}
	
	/**
	 * Adds a new step to the animation. This step fades only the one image using 
	 * the given gradient.
	 * 
	 * @param image
	 * @param gradient
	 * @param timeout
	 */
	public void addStep(Image image, ImageAlphaGradient gradient, double timeout) {
		images.add(new ImageFading(imageOut, image, null, gradient, null));
		timeouts.add(timeout);
	}
	
	/**
	 * Resets the fading and starts at the beginning
	 */
	public void reset() {
		super.reset();
		
		for (ImageFading imageFading : images) {
			imageFading.reset();
		}
		
	}
	
	/**
	 * Starts the fading and repeats it with the given number of loops. If 
	 * stopWhenDone=true, this animation thread will be ended when the animation 
	 * is done. After the animation is done, this thread can not be used any more.
	 * 
	 * @param loops
	 * @param stopWhenDone
	 */
	public void fade(int loops, boolean stopWhenDone) {
		this.loops = loops;
		this.stopWhenDone = stopWhenDone;
		
		reset();
		pause(false);
	}
	
	/**
	 * Starts the fading and repeats it with the given number of loops. After the 
	 * animation is done, the animation thread is paused and can be started again 
	 * by calling one of the fade methods.<br>
	 * Set loops=0 for infinite number of loops.
	 * 
	 * @param loops
	 */
	public void fade(int loops) {
		fade(loops, false);
	}
	
	/**
	 * Starts the fading and runs it one single time. After the 
	 * animation is done, the animation thread is paused and can be started again 
	 * by calling one of the fade methods.
	 */
	public void fade() {
		fade(1, false);
	}
	
	
	@Override
	public void run() {
		running();
		
		//Main loop. Keeping the thread running
		while (!isStopRequested()) {
			//Only end this pause when pause(false) is called
			clearReset();
			doPause(true, false);
			
			if (isStopRequested()) {
				break;
			}
			
			loop = 0;
			
			//Number of animation repeats
			while (loop < loops || loops == 0) {
				index = 0;
				
				if (images.size() == 0) {
					pause(true);
					break;
				}
				
				if (isReset()) {
					break;
				}

				//A single animation of all the pictures
				while (true) {
					//Allow pause during the animation
					doPause(false, false);
					
					if (isStopRequested()) {
						stopped();
						return;
					}
					
					if (isReset()) {
						break;
					}
					
					if (!images.get(index).fade()) {
						//Fading of the current image is done
						index++;
						
						//All images are done
						if (index >= images.size()) {
							loop++;
							break;
						}
					}
				
					if (repaintArea != null) {
						componentToRepainting.repaint((int)repaintArea.getX(), (int)repaintArea.getY(), 
								(int)repaintArea.getWidth(), (int)repaintArea.getHeight());
					} else {
						componentToRepainting.repaint();
					}
					
					
					synchronized (this) {
						try {
							wait(timeouts.get(index).longValue());
						} catch (InterruptedException e) {}
					}
				}
				
				
			}
			
			if (!isReset()) {
				if (stopWhenDone) {
					break;
				} else {
					pause(true);
				}
			}
			
		}
		
		
		stopped();
	}
	

}
