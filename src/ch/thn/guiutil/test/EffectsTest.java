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
package ch.thn.guiutil.test;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


import ch.thn.guiutil.Loader;
import ch.thn.guiutil.component.FadingLabel;
import ch.thn.guiutil.effects.FadingImage;
import ch.thn.guiutil.effects.IconAnimation;
import ch.thn.guiutil.effects.ImageAnimation;
import ch.thn.util.thread.ControlledRunnable;
import ch.thn.util.thread.ControlledRunnableEvent;
import ch.thn.util.thread.ControlledRunnableListener;
import ch.thn.util.valuerange.ImageAlphaGradient;

/**
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class EffectsTest extends JPanel implements ControlledRunnableListener {
	private static final long serialVersionUID = 8762214384151119341L;

	private JLabel lIconAnimation = null;
	private JLabel lImageAnimation = null;
	private JLabel lFadingImage = null;
	
	private BufferedImage image2 = null;
	
	
	private static final ImageIcon[] icons = {
			Loader.loadIcon("/16x16/close_hover.png"),
			Loader.loadIcon("/16x16/close_pressed.png"), 
			Loader.loadIcon("/16x16/close.png"), 
			Loader.loadIcon("/16x16/folder_explore.png"), 
			Loader.loadIcon("/16x16/folder.png"), 
			Loader.loadIcon("/16x16/question.png"), 
			};
	
	/**
	 * 
	 */
	public EffectsTest() {
		
		setLayout(new FlowLayout());
		
		lIconAnimation = new JLabel("Icon Animation: ");
		lIconAnimation.setHorizontalTextPosition(SwingConstants.LEFT);
		
		lImageAnimation = new JLabel("Image Animation:");
		lImageAnimation.setHorizontalTextPosition(SwingConstants.LEFT);
		
		lFadingImage = new JLabel("Fading Image:");
		lFadingImage.setHorizontalTextPosition(SwingConstants.LEFT);
		
		
		// ================================
		
		IconAnimation iconAnim = new IconAnimation(lIconAnimation, icons, 300);
		iconAnim.addRunnableControlListener(this);
		
		Thread tIconAnim = new Thread(iconAnim);
		tIconAnim.start();
		iconAnim.animate(0, true);
		
		
		// ================================
		
		ImageAlphaGradient simpleGradientOut = new ImageAlphaGradient(ImageAlphaGradient.FADE_OUT, 20, 0, 0);
		ImageAlphaGradient simpleGradientIn = new ImageAlphaGradient(ImageAlphaGradient.FADE_IN, 20, 0, 0);
		
		ImageAnimation imageAnimation = new ImageAnimation(this, 20, 20);
		imageAnimation.addRunnableControlListener(this);
		imageAnimation.addStep(icons[0].getImage(), icons[1].getImage(), 
				simpleGradientOut, simpleGradientIn, 100);
		imageAnimation.addStep(icons[1].getImage(), icons[2].getImage(), 
				simpleGradientOut, simpleGradientIn, 100);
		imageAnimation.addStep(icons[2].getImage(), icons[0].getImage(), 
				simpleGradientOut, simpleGradientIn, 100);
		
		
		image2 = imageAnimation.getAnimatedImage();
		lImageAnimation.setIcon(new ImageIcon(image2));
		
		
		Thread tFading = new Thread(imageAnimation);
		tFading.start();
		
		imageAnimation.fade(5, false);
		
		
		// ================================

		FadingImage fadingImage = new FadingImage(lFadingImage, 20, 20, 20, 100);
		
//		fadingImage.setImage(icons[0].getImage(), 0);
		
		fadingImage.addImage(icons[0].getImage(), 0);
		fadingImage.addImage(icons[4].getImage(), fadingImage.DELAY_ALL);
		fadingImage.addImage(icons[5].getImage(), 40);
		fadingImage.fade(2);
		
		BufferedImage image3 = fadingImage.getAnimatedImage();
		lFadingImage.setIcon(new ImageIcon(image3));
		
		
		// ================================
		
		FadingLabel fadingLabel = new FadingLabel("Fading label: ", 20, 100, FadingLabel.DELAY_NONE);
		fadingLabel.setHorizontalTextPosition(SwingConstants.LEFT);
		
		fadingLabel.setIcon(icons[0]);
		fadingLabel.setIcon(icons[1]);
		
		
		
		// ================================
		
		add(lIconAnimation);
		add(lImageAnimation);
		add(lFadingImage);
		add(fadingLabel);
	}
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(image2, 80, 80, null);
	}

	@Override
	public void runnableStateChanged(ControlledRunnableEvent e) {
		
		System.out.print(e.getSource().getClass().getCanonicalName() + " state changed: ");
		
		ControlledRunnable cr = (ControlledRunnable)e.getSource();
		
		switch (e.getStateType()) {
		case ControlledRunnableEvent.STATETYPE_RUNNING:
			System.out.println("running=" + cr.isRunning() + ", stopped=" + cr.isStopped());
			break;
		case ControlledRunnableEvent.STATETYPE_PAUSED:
			System.out.println("paused=" + cr.isPaused());
			break;	
		case ControlledRunnableEvent.STATETYPE_RESET:
			System.out.println("reset=" + cr.isReset());
			break;	
		default:
			break;
		}
		
	}
	
	

}
