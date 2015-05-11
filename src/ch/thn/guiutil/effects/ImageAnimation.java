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
 * Automated image fading which uses the {@link ImageFading} class and automatically
 * performs the fading steps. Multiple images, gradients and animation speeds can
 * be added after each other. It can be defined for each step how many times the
 * step should be repeated.
 * 
 * 
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class ImageAnimation extends ControlledRunnable {

	private LinkedList<AnimationStep> animationSteps = null;

	private BufferedImage imageOut = null;

	private Component componentToRepaint = null;

	private Rectangle repaintArea = null;

	private volatile int loops = 0;

	private boolean stopWhenDone = false;
	private volatile boolean isAnimating = false;


	/**
	 * 
	 * 
	 * @param componentToRepaint The component on which the image is being drawn
	 * @param width The width of the animated image(s)
	 * @param height The height of the animated image(s)
	 */
	public ImageAnimation(Component componentToRepaint, int width, int height) {
		super(true, true);
		init(componentToRepaint, null, null, width, height);
	}

	/**
	 * 
	 * @param componentToRepaint The component on which the image is being drawn
	 * @param imageOut The image where the animations should be drawn on
	 */
	public ImageAnimation(Component componentToRepaint, BufferedImage imageOut) {
		super(true, true);
		init(componentToRepaint, null, imageOut, 0, 0);
	}

	/**
	 * 
	 * 
	 * @param componentToRepaint The component on which the image is being drawn
	 * @param repaintArea The area of on the image component which should be
	 * updated after painting a new image
	 * @param width The width of the animated image(s)
	 * @param height The height of the animated image(s)
	 */
	public ImageAnimation(Component componentToRepaint, Rectangle repaintArea,
			int width, int height) {
		super(true, true);
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
		super(true, true);
		init(componentToRepaint, repaintArea, imageOut, 0, 0);
	}

	/**
	 * 
	 * @param componentToRepaint The component on which the image is being drawn
	 * @param repaintArea The area which should be updated after painting a new image
	 * @param imageOut The image where the animations should be drawn on
	 * @param width The size of the image. Only needed if imageOut=<code>null</code>
	 * @param height The size of the image. Only needed if imageOut=<code>null</code>
	 */
	private void init(Component componentToRepaint, Rectangle repaintArea,
			BufferedImage imageOut, int width, int height) {
		this.componentToRepaint = componentToRepaint;
		this.repaintArea = repaintArea;

		animationSteps = new LinkedList<>();

		if (imageOut == null) {
			//If no width and height are given, the buffered image has to be set
			//manually wit the setAnimatedImage method
			if (width != 0 || height != 0) {
				this.imageOut = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			}
		} else {
			this.imageOut = imageOut;
		}

		pause(true);
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
	 * Sets the given image as image to draw on
	 * 
	 * @param image
	 */
	public void setAnimatedImage(BufferedImage image) {
		this.imageOut = image;

		//Set the new image also for all the already defined steps
		for (AnimationStep animationStep : animationSteps) {
			animationStep.imageFading.setFadingImage(image);
		}

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
	 * Adds a new step to the animation. This step fades from one image to the other
	 * using the given gradients.
	 * 
	 * @param image1 The fist image (fading "from" this one)
	 * @param image2 The second image (fading "to" this one)
	 * @param gradient1 The gradient for the first image
	 * @param gradient2 The gradient for the second image
	 * @param timeout The timeout between each image gradient step (determines
	 * the speed of the animation)
	 * @param delay The delay for the animation to start
	 * @param repeat The number of times this animation step should be repeated.
	 * A repeat of 1 means the step will be animated twice (once plus 1 repeat).
	 * @return The step index
	 */
	public int addStep(Image image1, Image image2, ImageAlphaGradient gradient1,
			ImageAlphaGradient gradient2, long timeout, long delay, int repeat) {

		animationSteps.add(new AnimationStep(new ImageFading(imageOut, image1, image2, gradient1, gradient2),
				timeout, delay, repeat));

		return animationSteps.size() - 1;
	}

	/**
	 * Adds a new step to the animation. This step fades only the one image using
	 * the given gradient.
	 * 
	 * @param image
	 * @param gradient The gradient for the image
	 * @param timeout The timeout between each image gradient step (determines
	 * the speed of the animation)
	 * @param delay The delay for the animation to start
	 * @param repeat The number of times this animation step should be repeated.
	 * A repeat of 1 means the step will be animated twice (once plus 1 repeat).
	 * @return The step index
	 */
	public int addStep(Image image, ImageAlphaGradient gradient, long timeout, long delay, int repeat) {
		animationSteps.add(new AnimationStep(new ImageFading(imageOut, image, null, gradient, null),
				timeout, delay, repeat));

		return animationSteps.size() - 1;
	}

	/**
	 * Clears all animation steps
	 * 
	 */
	public void clearSteps() {
		animationSteps.clear();
	}

	/**
	 * The number of animation steps
	 * 
	 */
	public int stepCount() {
		return animationSteps.size();
	}

	/**
	 * Resets the fading and starts at the beginning with a cleared image.
	 */
	@Override
	public void reset() {
		super.reset();
		clear();
	}

	/**
	 * Clears the image
	 */
	private void clear() {
		//Since all the animation steps use the same buffered image, only one
		//has to be cleared
		if (animationSteps.size() > 0) {
			animationSteps.get(0).imageFading.clear();
			repaintComponentOrArea();
		}
	}

	/**
	 * Starts the animation and repeats it with the given number of loops. If
	 * stopWhenDone=true, this animation thread will be ended when the animation
	 * is done.
	 * 
	 * @param loops The number of repeats of all the animation steps
	 * @param stopWhenDone Stops the thread when animation is done. After the
	 * animation is done, the thread can not be used any more.
	 */
	public void animate(int loops, boolean stopWhenDone) {
		this.loops = loops;
		this.stopWhenDone = stopWhenDone;

		clear();
		pause(false);
	}

	/**
	 * Starts the animation and repeats it with the given number of loops. After the
	 * animation is done, the animation thread is paused and can be started again
	 * by calling one of the fade methods.<br>
	 * Set loops=0 for infinite number of loops.
	 * 
	 * @param loops The number of repeats of all the animation steps
	 */
	public void animate(int loops) {
		animate(loops, false);
	}

	/**
	 * Starts the animation and runs all animation steps one single time. After the
	 * animation is done, the animation thread is paused and can be started again
	 * by calling one of the fade methods.
	 */
	public void animate() {
		animate(1, false);
	}

	/**
	 * Returns <code>true</code> if the animation is currently running, <code>false</code>
	 * if the animation is paused, stopped or not yet started
	 * 
	 * @return
	 */
	public boolean isAnimating() {
		return isAnimating;
	}

	@Override
	public void run() {
		runStart();

		//Main loop. Keeping the thread running
		while (!isStopRequested()) {
			runReset();
			runPause(false);

			if (isStopRequested()) {
				break;
			}

			int currentLoop = 0;

			//Animation repeats. Zero loops means infinite repeats.
			while ((currentLoop < loops || loops == 0)
					&& !isResetRequested() && !isStopRequested()) {

				isAnimating = true;

				//Each animation
				for (int animationIndex = 0;
						animationIndex < animationSteps.size()
						&& !isResetRequested() && !isStopRequested();
						animationIndex++) {


					AnimationStep animationStep = animationSteps.get(animationIndex);
					ImageFading imageFading = animationStep.imageFading;

					//Step repeats
					for (int stepRepeat = 0;
							stepRepeat <= animationStep.repeat
									&& !isResetRequested() && !isStopRequested();
							stepRepeat++) {

						imageFading.reset();

						if (animationStep.delay > 0) {
							controlledPause(animationStep.delay);
						}

						//Fading
						while (!imageFading.isDone()
								&& !isResetRequested()
								&& !isStopRequested()) {

							if (isPauseRequested()) {
								isAnimating = false;
								runPause(true);
								isAnimating = true;
							}

							imageFading.fade();

							repaintComponentOrArea();

							controlledPause(animationStep.timeout);

						}	//End fading

					}	//End step repeats

				}	//End each animation


				currentLoop++;

			}	// End animation repeats


			isAnimating = false;


			if (stopWhenDone && !isResetRequested()) {
				break;
			}

			pause(true);

		}	// End thread main loop

		runEnd();
	}


	/**
	 * Repaints the component which was set for this {@link ImageAnimation} or
	 * the given area on that component. This method should be called after each
	 * fading step or whenever the output image changes.
	 * 
	 */
	protected void repaintComponentOrArea() {
		if (componentToRepaint != null) {
			if (repaintArea != null) {
				componentToRepaint.repaint((int)repaintArea.getX(), (int)repaintArea.getY(),
						(int)repaintArea.getWidth(), (int)repaintArea.getHeight());
			} else {
				componentToRepaint.repaint();
			}
		}
	}


	/**************************************************************************
	 * 
	 * 
	 * 
	 *
	 * @author Thomas Naeff (github.com/thnaeff)
	 *
	 */
	private class AnimationStep {

		protected ImageFading imageFading = null;
		protected long timeout = 0;
		protected long delay = 0;
		protected long repeat = 0;


		/**
		 * 
		 * 
		 * @param imageFading
		 * @param timeout
		 * @param delay
		 */
		public AnimationStep(ImageFading imageFading, long timeout, long delay, int repeat) {
			this.imageFading = imageFading;
			this.timeout = timeout;
			this.delay = delay;
			this.repeat = repeat;

		}



	}


}
