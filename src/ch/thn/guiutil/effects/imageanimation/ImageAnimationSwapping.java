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
package ch.thn.guiutil.effects.imageanimation;

import java.awt.Component;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class ImageAnimationSwapping extends ImageAnimation<ImageSwapping> {


	/**
	 * 
	 * 
	 * @param componentToRepaint
	 * @param repaintArea
	 * @param imageOut
	 */
	public ImageAnimationSwapping(Component componentToRepaint,
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
	public ImageAnimationSwapping(Component componentToRepaint,
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
	public ImageAnimationSwapping(Component componentToRepaint, int width,
			int height) {
		super(componentToRepaint, width, height);
	}

	/**
	 * 
	 * 
	 * @param componentToRepaint
	 * @param imageOut
	 */
	public ImageAnimationSwapping(Component componentToRepaint,
			BufferedImage imageOut) {
		super(componentToRepaint, imageOut);

	}


	/**
	 * 
	 * 
	 * @param image
	 * @param delay The delay for the swap to start
	 * @return The step index
	 */
	public int addStep(Image image, long delay) {

		return addAnimationStep(new AnimationStep(
				new ImageSwapping(getOutputImage(), getOutputGraphics(), image),
				0, delay, 0));

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
