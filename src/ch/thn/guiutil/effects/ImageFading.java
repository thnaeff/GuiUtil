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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import ch.thn.util.valuerange.ImageAlphaGradient;

/**
 * Fading a single image or fading from one image to the other with given gradients.
 * Repeated calling the {@link #fade()} method performs each fading step.<br />
 * A {@link BufferedImage} can be passed on or one will be created internally if
 * not provided.
 * 
 * 
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class ImageFading {

	private Image image1 = null;
	private Image image2 = null;

	private ImageAlphaGradient gradient1 = null;
	private ImageAlphaGradient gradient2 = null;

	private BufferedImage imageFaded = null;

	private Graphics2D graphicsFaded = null;

	private Float nextAlpha1 = null;
	private Float nextAlpha2 = null;

	private int width = 0;
	private int height = 0;

	public static final Color cClear = new Color(0, 0, 0, 0);

	/**
	 * 
	 * 
	 * @param image1
	 * @param image2
	 * @param gradient1
	 * @param gradient2
	 */
	public ImageFading(Image image1, Image image2,
			ImageAlphaGradient gradient1, ImageAlphaGradient gradient2) {
		this(null, image1, image2, gradient1, gradient2);
	}

	/**
	 * 
	 * 
	 * @param image1
	 * @param gradient1
	 */
	public ImageFading(Image image1, ImageAlphaGradient gradient1) {
		this(null, image1, null, gradient1, null);
	}

	/**
	 * 
	 * 
	 * @param imageToDrawOn
	 * @param image1
	 * @param gradient1
	 */
	public ImageFading(BufferedImage imageToDrawOn, Image image1, ImageAlphaGradient gradient1) {
		this(imageToDrawOn, image1, null, gradient1, null);
	}

	/**
	 * 
	 * 
	 * @param imageToDrawOn
	 * @param image1
	 * @param image2
	 * @param gradient1
	 * @param gradient2
	 */
	public ImageFading(BufferedImage imageToDrawOn, Image image1, Image image2,
			ImageAlphaGradient gradient1, ImageAlphaGradient gradient2) {
		this.image1 = image1;
		this.image2 = image2;
		this.gradient1 = gradient1;
		this.gradient2 = gradient2;

		if (image1 == null) {
			width = image2.getWidth(null);
			height = image2.getHeight(null);
		} else {
			width = image1.getWidth(null);
			height = image1.getHeight(null);
		}

		if (imageToDrawOn == null) {
			imageFaded = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			graphicsFaded = imageFaded.createGraphics();
		} else {
			setFadingImage(imageToDrawOn);
		}

		reset();

	}

	/**
	 * Sets the image which should be used to draw the faded images on
	 * 
	 * @param imageToDrawOn
	 */
	public void setFadingImage(BufferedImage imageToDrawOn) {
		this.imageFaded = imageToDrawOn;
		graphicsFaded = imageToDrawOn.createGraphics();
	}

	/**
	 * Resets the gradients and continues with the first gradient value
	 */
	public void reset() {

		if (gradient1 != null) {
			gradient1.reset();
			nextAlpha1 = gradient1.getNext().floatValue();
		}

		if (gradient2 != null) {
			gradient2.reset();
			nextAlpha2 = gradient2.getNext().floatValue();
		}


	}

	/**
	 * Clears the image by filling it with the given color
	 */
	public void clear(Color c) {
		graphicsFaded.setBackground(c);
		graphicsFaded.clearRect(0, 0, width, height);
	}

	/**
	 * Checks if there are any more fading steps or if the fading is done.
	 * 
	 * @return <code>true</code> if fading is done, <code>false</code> if more steps
	 * are available.
	 */
	public boolean isDone() {
		//The fading is done if there are no alpha values left any more
		if ((gradient1 == null || nextAlpha1 == null)
				&& (gradient2 == null || nextAlpha2 == null)) {
			return true;
		}

		return false;
	}

	/**
	 * Continues with the next fading step and returns the resulting image.
	 * 
	 * @return
	 */
	public BufferedImage fade() {
		clear(cClear);

		//--- image1: draw with current alpha value
		if (image1 != null) {
			//Don't draw anything if alpha value is 0
			if (nextAlpha1 != null && nextAlpha1 != 0.0) {
				graphicsFaded.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, nextAlpha1));
				graphicsFaded.drawImage(image1, 0, 0, null);
			}
		}

		//--- image1: get next alpha value
		if (gradient1 != null) {
			if (gradient1.hasNext()) {
				nextAlpha1 = gradient1.getNext().floatValue();
			} else {
				nextAlpha1 = null;
			}
		}


		//--- image2: draw with current alpha value
		if (image2 != null) {
			//Don't draw anything if alpha value is 0
			if (nextAlpha2 != null && nextAlpha2 != 0.0) {
				graphicsFaded.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, nextAlpha2));
				graphicsFaded.drawImage(image2, 0, 0, null);
			}
		}

		//--- image2: get next alpha value
		if (gradient2 != null) {
			if (gradient2.hasNext()) {
				nextAlpha2 = gradient2.getNext().floatValue();
			} else {
				nextAlpha2 = null;
			}
		}

		return imageFaded;

	}


}
