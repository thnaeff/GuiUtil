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
package ch.thn.guiutil.component.imageanimation;

import javax.swing.Icon;

import ch.thn.guiutil.effects.imageanimation.ImageAnimationFading;
import ch.thn.util.valuerange.ImageAlphaGradient;

/**
 * A label with an image which fades in and out.
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class ImageAnimationLabelFading extends ImageAnimationLabel<ImageAnimationFading> {
	private static final long serialVersionUID = 3864075241985202132L;

	/**
	 * 
	 * 
	 */
	public ImageAnimationLabelFading() {
		super();
	}

	/**
	 * 
	 * 
	 * @param icon
	 */
	public ImageAnimationLabelFading(Icon icon) {
		super(icon);
	}

	/**
	 * 
	 * 
	 * @param icon
	 * @param horizontalAlignment
	 * @param imageAnimation
	 */
	public ImageAnimationLabelFading(Icon icon, int horizontalAlignment) {
		super(icon, horizontalAlignment);
	}

	/**
	 * 
	 * 
	 * @param text
	 * @param icon
	 * @param horizontalAlignment
	 */
	public ImageAnimationLabelFading(String text, Icon icon, int horizontalAlignment) {
		super(text, icon, horizontalAlignment);
	}

	/**
	 * 
	 * 
	 * @param text
	 * @param imageAnimation
	 */
	public ImageAnimationLabelFading(String text) {
		super(text);
	}

	/**
	 * 
	 * 
	 * @param text
	 * @param horizontalAlignment
	 */
	public ImageAnimationLabelFading(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
	}


	/**
	 * 
	 * 
	 */
	@Override
	protected void init() {
		setImageAnimation(new ImageAnimationFading(this, null));

		super.init();
	}


	/**
	 * 
	 * 
	 * @param gradient
	 * @param timeout
	 * @param delay
	 * @param repeat
	 */
	public void addStep(ImageAlphaGradient gradient, long timeout, long delay, int repeat) {

		getImageAnimation().addStep(getImageIcon().getImage(), gradient, timeout, delay, repeat);

	}


	@Override
	public void animate(int loops) {

		if (hasIconChanged()) {
			//Reset the image for all steps if the image has changed
			for (int i = 0; i < getImageAnimation().stepCount(); i++) {
				getImageAnimation().getAnimationStep(i).getImageManipulation().setImage1(getImageIcon().getImage());
			}

		}

		super.animate(loops);
	}


}
