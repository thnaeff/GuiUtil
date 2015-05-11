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

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import ch.thn.util.thread.ControlledRunnable;


/**
 * Animates a group of icons by
 * 
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class IconAnimationLabel extends JLabel {
	private static final long serialVersionUID = 9186740032377325722L;

	private int iconIndex = 0;
	private int timeout = 300;
	private int looped = 0;
	private int loops = 0;

	private boolean stopWhenDone = true;

	private final Object o = new Object();


	private ImageIcon[] icons = null;


	/**
	 * Animates an icon with the given icons
	 * 
	 * @param lIcon
	 * @param icons
	 * @param timeout
	 */
	public IconAnimationLabel(ImageIcon[] icons, int timeout) {
		this.icons = icons;
		this.timeout = timeout;

		if (icons != null) {
			setIcon(icons[0]);
		}

		//pause(true);
	}


	/**
	 * 
	 * 
	 * @param icons
	 */
	public void setIcons(ImageIcon[] icons) {
		this.icons = icons;
		setIcon(icons[0]);
	}

	/**
	 * 
	 * @param loops
	 * @param stopWhenDone If set to <code>true</code>, the animation thread stops
	 * when the animation is done which means the tread can not be used any more. If
	 * set to <code>false</code>, the thread pauses when the animation is done and
	 * it can be restarted again.
	 */
	public void animate(int loops, boolean stopWhenDone) {
		this.loops = loops;
		this.stopWhenDone = stopWhenDone;
		looped = 0;
		iconIndex = 0;
		//pause(false);
	}

	/**
	 * Runs the animation for the given number of loops and pauses when the loops
	 * are done.
	 * 
	 * @param loops
	 */
	public void animate(int loops) {
		animate(loops, false);
	}







	/**
	 * 
	 * 
	 *
	 * @author Thomas Naeff (github.com/thnaeff)
	 *
	 */
	private class IconAnimation extends ControlledRunnable {

		private JLabel lIcon = null;

		/**
		 * @param pausingImplemented
		 * @param resetImplemented
		 */
		protected IconAnimation(JLabel lIcon) {
			super(true, false);
			this.lIcon = lIcon;

		}


		@Override
		public void run() {
			runStart();

			//Main loop
			while (!isStopRequested()) {
				runPause(false);

				if (isStopRequested()) {
					break;
				}

				lIcon.setIcon(icons[iconIndex]);


				if (iconIndex >= icons.length - 1) {
					//All icons are through

					iconIndex = 0;

					looped++;

					//Repeat or stop
					if (loops != 0 && looped >= loops) {
						if (stopWhenDone) {
							break;
						} else {
							pause(true);
						}
					}
				} else {
					//Pick next icon
					iconIndex++;
				}


				try {
					synchronized (o) {
						o.wait(timeout);
					}
				} catch (InterruptedException e) {}

			}

			runEnd();
		}



	}

}
