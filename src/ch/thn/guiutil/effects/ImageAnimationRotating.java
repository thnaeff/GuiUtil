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

/**
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class ImageAnimationRotating extends ImageAnimation<ImageRotating> {


	/**
	 * 
	 * 
	 * @param componentToRepaint
	 * @param repaintArea
	 * @param imageOut
	 */
	public ImageAnimationRotating(Component componentToRepaint,
			Rectangle repaintArea, BufferedImage imageOut) {
		super(componentToRepaint, repaintArea, imageOut);
	}

	/**
	 * 
	 * 
	 * @param componentToRepaint
	 * @param repaintArea
	 * @param width
	 * @param height
	 */
	public ImageAnimationRotating(Component componentToRepaint,
			Rectangle repaintArea, int width, int height) {
		super(componentToRepaint, repaintArea, width, height);
	}

	/**
	 * 
	 * 
	 * @param componentToRepaint
	 * @param width
	 * @param height
	 */
	public ImageAnimationRotating(Component componentToRepaint, int width,
			int height) {
		super(componentToRepaint, width, height);
	}

	/**
	 * 
	 * 
	 * @param componentToRepaint
	 * @param imageOut
	 */
	public ImageAnimationRotating(Component componentToRepaint,
			BufferedImage imageOut) {
		super(componentToRepaint, imageOut);

	}


	/**
	 * 
	 * 
	 * @param image
	 * @param degreesPerStep
	 * @param timeout The timeout between each image rotation step (determines
	 * the speed of the rotation)
	 * @param delay The delay for the rotation to start
	 * @param repeat The number of times this animation step should be repeated.
	 * A repeat of 1 means the step will be animated twice (once plus 1 repeat).
	 * @return The step index
	 */
	public int addStep(Image image, int degreesPerStep, long timeout, long delay, int repeat) {

		return addAnimationStep(new AnimationStep(
				new ImageRotating(getOutputImage(), image, degreesPerStep),
				timeout, delay, repeat));

	}


	@Override
	protected void clear() {
		//Since all the animation steps use the same buffered image, only one
		//has to be cleared
		if (getAnimationSteps().size() > 0) {
			getAnimationSteps().get(0).getImageManipulation().clear();
			repaintComponentOrArea();
		}
	}


}
