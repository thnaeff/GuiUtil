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
package ch.thn.guiutil.component;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import ch.thn.guiutil.ImageUtil;
import ch.thn.guiutil.effects.ImageAnimation;
import ch.thn.util.thread.ControlledRunnable;
import ch.thn.util.valuerange.ImageAlphaGradient;

/**
 * A {@link JLabel} whose icon can be animated.<br />
 * The icon has to be set with the
 * standard {@link #setIcon(Icon)} method and the animation can be defined with
 * {@link #addAnimationStep(ImageAlphaGradient, long)} and
 * {@link #addAnimationStep(ImageAlphaGradient, long, long, int)}. The animation
 * can be started and paused with {@link #animate(int)} and {@link #pause(boolean)}.
 *
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class ImageAnimationLabel extends JLabel {
	private static final long serialVersionUID = -728553274740471716L;

	private LinkedList<AnimationStep> animationSteps = null;

	private ImageAnimation imageAnimation = null;

	private BufferedImage bufferedImage = null;
	private Icon originalIcon = null;
	private ImageIcon imageIcon = null;

	private boolean iconChanged = true;


	/**
	 * 
	 * 
	 * @param icon
	 * @see JLabel
	 */
	public ImageAnimationLabel(Icon icon) {
		super(icon);
		init();
	}

	/**
	 * 
	 * 
	 * @param text
	 * @see JLabel
	 */
	public ImageAnimationLabel(String text) {
		super(text);
		init();
	}

	/**
	 * 
	 * 
	 * @param text
	 * @param horizontalAlignment
	 * @see JLabel
	 */
	public ImageAnimationLabel(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
		init();
	}

	/**
	 * 
	 * 
	 * @param icon
	 * @param horizontalAlignment
	 * @see JLabel
	 */
	public ImageAnimationLabel(Icon icon, int horizontalAlignment) {
		super(icon, horizontalAlignment);
		init();
	}

	//TODO
	public ControlledRunnable getRunnable() {
		return imageAnimation;
	}

	/**
	 * 
	 * 
	 */
	private void init() {

		animationSteps = new LinkedList<>();

		imageAnimation = new ImageAnimation(this, null);

		Thread t = new Thread(imageAnimation);
		t.start();
		t.setName(this.getClass().getSimpleName());

	}

	/**
	 * 
	 * 
	 * @param text
	 * @param icon
	 * @param horizontalAlignment
	 * @see JLabel
	 */
	public ImageAnimationLabel(String text, Icon icon, int horizontalAlignment) {
		super(text, icon, horizontalAlignment);
	}

	/**
	 * 
	 * 
	 * @param loops
	 */
	public void animate(int loops) {
		if (imageIcon == null) {
			return;
		}

		//Create a new image only if necessary
		if (bufferedImage == null
				|| bufferedImage.getWidth() != imageIcon.getIconWidth()
				|| bufferedImage.getHeight() != imageIcon.getIconHeight()) {
			bufferedImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);

			imageAnimation.setAnimatedImage(bufferedImage);
		}

		if (iconChanged) {
			//(Re)build the animation steps here if the icon has changed
			imageAnimation.clearSteps();
			for (AnimationStep animationStep : animationSteps) {
				imageAnimation.addStep(imageIcon.getImage(), animationStep.gradient,
						animationStep.timeout, animationStep.delay, animationStep.repeat);
			}

			iconChanged = false;

			super.setIcon(new ImageIcon(bufferedImage));
		}

		imageAnimation.animate(loops);

	}

	/**
	 * Pauses the animation
	 * 
	 * @param pause
	 */
	public void pause(boolean pause) {
		imageAnimation.pause(pause);
	}

	/**
	 * 
	 * 
	 * @param gradient The gradient for the image
	 * @param timeout The timeout between each image gradient step (determines
	 * the speed of the animation)
	 * @param delay The delay for the animation to start
	 * @return The step index
	 */
	public int addAnimationStep(ImageAlphaGradient gradient, long timeout) {
		return addAnimationStep(gradient, timeout, 0, 0);
	}

	/**
	 * 
	 * 
	 * @param gradient The gradient for the image
	 * @param timeout The timeout between each image gradient step (determines
	 * the speed of the animation)
	 * @param delay The delay for the animation to start
	 * @param repeat The number of times this animation step should be repeated.
	 * A repeat of 1 means the step will be animated twice (once plus 1 repeat).
	 * @return The step index
	 */
	public int addAnimationStep(ImageAlphaGradient gradient, long timeout, long delay, int repeat) {
		animationSteps.add(new AnimationStep(gradient, timeout, delay, repeat));
		return animationSteps.size() - 1;
	}


	/**
	 * Defines the icon this component will display. Setting a icon will stop the
	 * animation.
	 */
	@Override
	public void setIcon(Icon icon) {

		//setIcon is also called when constructing JLabel -> imageAnimation might not
		//be initialized
		if (imageAnimation != null) {
			//Pause the animation wherever it is
			imageAnimation.pause(true);
		}

		//Save icon to be able to return it with getIcon
		this.originalIcon = icon;

		//This ImageIcon is used for drawing
		this.imageIcon = ImageUtil.iconToImageIcon(icon);

		iconChanged = true;

		super.setIcon(icon);
	}

	/**
	 * Returns the original icon which has been set with {@link #setIcon(Icon)}
	 * 
	 * @return
	 */
	public Icon getOriginalIcon() {
		return originalIcon;
	}

	/**
	 * Returns the icon that the label displays.<br />
	 * <br />
	 * Note: This icon is not the same object as the one set with {@link #setIcon(Icon)}.
	 * This {@link ImageAnimationLabel} uses an internally created buffered image
	 * to draw the animations on. User {@link #getOriginalIcon()} to get the icon
	 * which has been set with {@link #setIcon(Icon)}
	 */
	@Override
	public Icon getIcon() {
		return super.getIcon();
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

		protected ImageAlphaGradient gradient = null;
		protected long timeout = 0;
		protected long delay = 0;
		protected int repeat = 0;

		/**
		 * 
		 * 
		 * @param gradient
		 * @param timeout
		 * @param delay
		 */
		public AnimationStep(ImageAlphaGradient gradient, long timeout, long delay, int repeat) {
			this.gradient = gradient;
			this.timeout = timeout;
			this.delay = delay;
			this.repeat = repeat;

		}



	}

}
