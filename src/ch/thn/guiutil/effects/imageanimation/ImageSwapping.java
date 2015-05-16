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

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class ImageSwapping extends ImageManipulation {


	private Image image = null;

	private int imageWidth = 0;
	private int imageHeight = 0;

	private boolean swapped = false;

	/**
	 * 
	 * 
	 * @param image
	 */
	public ImageSwapping(Image image) {
		this(null, null, image);
	}


	/**
	 * 
	 * 
	 * @param imageToDrawOn
	 * @param graphicsToDrawOn
	 * @param image
	 */
	public ImageSwapping(BufferedImage imageToDrawOn, Graphics2D graphicsToDrawOn,
			Image image) {

		setImage(image);


		if (imageToDrawOn == null) {
			setManipulatingImage(new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB), null);
		} else {
			setManipulatingImage(imageToDrawOn, graphicsToDrawOn);
		}


	}

	@Override
	protected void setManipulatingImage(BufferedImage imageToDrawOn,
			Graphics2D graphicsToDrawOn) {
		super.setManipulatingImage(imageToDrawOn, graphicsToDrawOn);

		graphicsManipulated.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		calcCenter(imageWidth, imageHeight);
	}

	/**
	 * 
	 * 
	 * @param image
	 */
	public void setImage(Image image) {
		this.image = image;

		if (image != null) {
			this.imageWidth = image.getWidth(null);
			this.imageHeight = image.getHeight(null);
		} else {
			this.imageWidth = 0;
			this.imageHeight = 0;
		}

		calcCenter(imageWidth, imageHeight);
	}


	@Override
	public boolean isDone() {
		return swapped;
	}

	@Override
	public void reset() {
		super.reset();

		swapped = false;
	}

	@Override
	protected BufferedImage manipulate() {
		return swap();
	}


	public BufferedImage swap() {
		clear();

		graphicsManipulated.drawImage(image, centerX, centerY, null);
		graphicsManipulated.drawRect(0, 0, imageManipulatedWidth-1, imageManipulatedWidth-1);

		swapped = true;

		return imageManipulated;
	}


}
