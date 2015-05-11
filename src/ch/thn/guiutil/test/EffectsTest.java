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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ch.thn.guiutil.Loader;
import ch.thn.guiutil.component.ImageAnimationLabel;
import ch.thn.guiutil.effects.ImageAnimation;
import ch.thn.util.thread.ControlledRunnable;
import ch.thn.util.thread.ControlledRunnable.ControlledRunnableEvent;
import ch.thn.util.thread.ControlledRunnable.ControlledRunnableListener;
import ch.thn.util.valuerange.ImageAlphaGradient;

/**
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class EffectsTest extends JPanel implements ControlledRunnableListener, ActionListener {
	private static final long serialVersionUID = 8762214384151119341L;

	private ImageAnimationLabel lImageAnimationLabel = null;
	private JLabel lImageAnimation = null;

	private JButton bPauseRun = null;
	private JButton bStop = null;
	private JButton bReset = null;

	private final BufferedImage image2 = null;

	private final ImageAnimation imageAnimation = null;


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

		bPauseRun = new JButton("Pause");
		bPauseRun.addActionListener(this);

		bStop = new JButton("Stop");
		bStop.addActionListener(this);

		bReset = new JButton("Reset");
		bReset.addActionListener(this);

		add(bStop);
		add(bReset);
		add(bPauseRun);

		lImageAnimationLabel = new ImageAnimationLabel("ImageAnimationLabel: ");
		lImageAnimationLabel.setHorizontalTextPosition(SwingConstants.LEFT);
		lImageAnimationLabel.getRunnable().addControlledRunnableListener(this);

		lImageAnimation = new JLabel("ImageAnimation:");
		lImageAnimation.setHorizontalTextPosition(SwingConstants.LEFT);


		// ================================

		//		ImageAlphaGradient simpleGradientIn = new ImageAlphaGradient(ImageAlphaGradient.FADE_IN, 20, 0, 0);
		//		ImageAlphaGradient simpleGradientOut = new ImageAlphaGradient(ImageAlphaGradient.FADE_OUT, 20, 0, 0);
		//
		//		System.out.println(simpleGradientIn);
		//		System.out.println(simpleGradientOut);
		//
		//		imageAnimation = new ImageAnimation(this, 20, 20);
		//		imageAnimation.addControlledRunnableListener(this);
		//
		//		imageAnimation.addStep(icons[0].getImage(),
		//				simpleGradientIn, 80, 2000);
		//		imageAnimation.addStep(icons[0].getImage(),
		//				simpleGradientOut, 80, 1000);
		//
		//
		//		image2 = imageAnimation.getAnimatedImage();
		//		lImageAnimation.setIcon(new ImageIcon(image2));
		//
		//
		//		Thread tFading = new Thread(imageAnimation);
		//		tFading.start();
		//
		//		imageAnimation.animate(2, false);


		// ================================


		lImageAnimationLabel.setIcon(icons[5]);
		lImageAnimationLabel.addAnimationStep(new ImageAlphaGradient(ImageAlphaGradient.FADE_OUT, 20), 100);
		lImageAnimationLabel.addAnimationStep(new ImageAlphaGradient(ImageAlphaGradient.FADE_IN, 5), 500);
		lImageAnimationLabel.addAnimationStep(new ImageAlphaGradient(ImageAlphaGradient.FADE_OUT, 2), 1000, 0, 5);


		lImageAnimationLabel.animate(5);

		lImageAnimationLabel.setIcon(icons[4]);
		lImageAnimationLabel.animate(5);


		// ================================


		add(lImageAnimation);
		add(lImageAnimationLabel);

	}


	//	@Override
	//	public void paint(Graphics g) {
	//		super.paint(g);
	//		g.drawImage(image2, 80, 80, null);
	//	}

	@Override
	public void runnableStateChanged(ControlledRunnableEvent e) {
		ControlledRunnable cr = (ControlledRunnable)e.getSource();


		if (cr == imageAnimation) {
			switch (e.getStateType()) {
			case RUNNING:
				System.out.print(e.getSource().getClass().getSimpleName() + ": ");
				System.out.println("running=" + cr.isRunning() + ", stopped=" + cr.isStopped() + ", will stop=" + cr.willStop());
				break;
			case PAUSED:
				if (imageAnimation.isAnimating()) {
					bPauseRun.setText("Pause");
				} else {
					bPauseRun.setText("Run");
				}
				//			System.out.print(e.getSource().getClass().getSimpleName() + ": ");
				//			System.out.println("paused=" + cr.isPaused());
				break;
			case RESET:
				System.out.print(e.getSource().getClass().getSimpleName() + ": ");
				System.out.println("will reset=" + cr.willReset());
				break;
			default:
				System.out.print(e.getSource().getClass().getSimpleName() + ": ");
				System.out.println("unknown");
				break;
			}
		} else if (cr == lImageAnimationLabel.getRunnable()) {
			switch (e.getStateType()) {
			case RUNNING:
				System.out.print(e.getSource().getClass().getSimpleName() + ": ");
				System.out.println("running=" + cr.isRunning() + ", stopped=" + cr.isStopped() + ", will stop=" + cr.willStop());
				break;
			case PAUSED:
				//				System.out.print(e.getSource().getClass().getSimpleName() + ": ");
				//				System.out.println("paused=" + cr.isPaused());
				break;
			case RESET:
				System.out.print(e.getSource().getClass().getSimpleName() + ": ");
				System.out.println("will reset=" + cr.willReset());
				break;
			default:
				System.out.print(e.getSource().getClass().getSimpleName() + ": ");
				System.out.println("unknown");
				break;
			}
		}

	}


	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == bPauseRun) {
			if (bPauseRun.getText() == "Pause") {
				imageAnimation.pause(true);
			} else if (bPauseRun.getText() == "Run") {
				imageAnimation.pause(false);
			}
		} else if (e.getSource() == bStop) {
			imageAnimation.stop();
		}
		else if (e.getSource() == bReset) {
			imageAnimation.reset();
		}

	}



}
