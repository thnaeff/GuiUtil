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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * A base class for image manipulations. Contains some common variables and methods.
 * 
 * 
 * 
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public abstract class ImageManipulation {

	protected BufferedImage imageManipulated = null;

	protected Graphics2D graphicsManipulated = null;

	protected int imageManipulatedWidth = 0;
	protected int imageManipulatedHeight = 0;

	protected int centerWidth = 0;
	protected int centerHeight = 0;

	public static final Color cClear = new Color(0, 0, 0, 0);


	/**
	 * 
	 */
	public ImageManipulation() {


	}



	/**
	 * 
	 * 
	 * @param imageToDrawOn
	 */
	public ImageManipulation(BufferedImage imageToDrawOn) {

		setManipulatingImage(imageToDrawOn);

	}

	/**
	 * 
	 * 
	 * @param width
	 * @param height
	 */
	public ImageManipulation(int width, int height) {

		setManipulatingImage(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));

	}



	/**
	 * Sets the image which should be used to draw the manipulated images on
	 * 
	 * @param imageToDrawOn
	 */
	protected void setManipulatingImage(BufferedImage imageToDrawOn) {
		this.imageManipulated = imageToDrawOn;
		graphicsManipulated = imageToDrawOn.createGraphics();

		imageManipulatedWidth = imageManipulated.getWidth();
		imageManipulatedHeight = imageManipulated.getHeight();
	}

	/**
	 * Calculates centerWidth and centerHeight to center an image with the given
	 * dimensions.
	 * 
	 * @param imageWidth
	 * @param imageHeight
	 */
	protected void calcCenter(int imageWidth, int imageHeight) {
		this.centerWidth = imageManipulatedWidth / 2 - imageWidth / 2;
		this.centerHeight = imageManipulatedHeight / 2 - imageHeight / 2;
	}

	/**
	 * Clears the image by filling it with the given color
	 */
	public void clear(Color c) {
		graphicsManipulated.setBackground(c);
		graphicsManipulated.clearRect(0, 0, imageManipulatedWidth, imageManipulatedHeight);
	}

	/**
	 * Clears the image by filling it with a default color with alpha=0
	 */
	public void clear() {
		clear(cClear);
	}

	/**
	 * Resets the image
	 */
	public abstract void reset();


	/**
	 * Checks if there are any more steps or if it is done.
	 * 
	 * @return <code>true</code> if all steps are performed, <code>false</code> if more steps
	 * are available.
	 */
	public abstract boolean isDone();


	/**
	 * 
	 * 
	 * @return
	 */
	protected abstract BufferedImage manipulate();

}
