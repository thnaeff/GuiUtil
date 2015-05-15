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

import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class ImageRotating extends ImageManipulation {


	private Image image = null;

	private int imageWidth = 0;
	private int imageHeight = 0;

	private int degreesPerStep = 0;


	/**
	 * 
	 * 
	 * @param image
	 * @param degreesPerStep
	 */
	public ImageRotating(Image image, int degreesPerStep) {
		this(null, image, degreesPerStep);
	}


	/**
	 * 
	 * 
	 * @param imageToDrawOn
	 * @param image
	 * @param degreesPerStep
	 */
	public ImageRotating(BufferedImage imageToDrawOn, Image image, int degreesPerStep) {
		this.degreesPerStep = degreesPerStep;

		setImage(image);


		if (imageToDrawOn == null) {
			setManipulatingImage(new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB));
		} else {
			setManipulatingImage(imageToDrawOn);
		}


	}

	@Override
	protected void setManipulatingImage(BufferedImage imageToDrawOn) {
		super.setManipulatingImage(imageToDrawOn);

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

	/**
	 * 
	 * 
	 * @param degreesPerStep The degreesPerStep to set
	 */
	public void setDegreesPerStep(int degreesPerStep) {
		this.degreesPerStep = degreesPerStep;
	}


	@Override
	public boolean isDone() {
		return false;
	}

	@Override
	public void reset() {
	}


	@Override
	protected BufferedImage manipulate() {
		return rotate();
	}


	public BufferedImage rotate() {
		clear();

		graphicsManipulated.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		graphicsManipulated.rotate(Math.toRadians(degreesPerStep), imageManipulatedWidth / 2, imageManipulatedHeight / 2);
		graphicsManipulated.drawImage(image, centerWidth, centerHeight, null);
		//graphicsManipulated.drawRect(0, 0, imageWidth-1, imageHeight-1);

		return imageManipulated;
	}


}
