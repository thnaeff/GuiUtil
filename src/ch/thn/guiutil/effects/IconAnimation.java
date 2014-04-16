/**
 * 
 */
package ch.thn.guiutil.effects;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import ch.thn.util.thread.ControlledRunnable;


/**
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class IconAnimation extends ControlledRunnable {
	
	private JLabel lIcon = null;
	
	private int iconIndex = 0;
	private int timeout = 300;
	private int looped = 0;
	private int loops = 0;
	
	private boolean stopWhenDone = true;
	
	private Object o = new Object();
	
	
	private ImageIcon[] icons = null;
	
	
	/**
	 * Animates an icon with the given icons
	 * 
	 * @param lIcon
	 * @param icons
	 * @param timeout 
	 */
	public IconAnimation(JLabel lIcon, ImageIcon[] icons, int timeout) {
		super(true);
		
		this.lIcon = lIcon;
		this.icons = icons;
		this.timeout = timeout;
		
		if (icons != null) {
			lIcon.setIcon(icons[0]);
		}
				
		pause(true);
	}
	
	
	/**
	 * 
	 * 
	 * @param icons 
	 */
	public void setIcons(ImageIcon[] icons) {
		this.icons = icons;
		lIcon.setIcon(icons[0]);
	}
	
	/**
	 * 
	 * @param loops
	 * @param stopWhenDone
	 */
	public void animate(int loops, boolean stopWhenDone) {
		this.loops = loops;
		this.stopWhenDone = stopWhenDone;
		looped = 0;
		iconIndex = 0;
		pause(false);
	}
	
	/**
	 * 
	 * @param loops
	 */
	public void animate(int loops) {
		animate(loops, false);
	}
	
	
	
	@Override
	public void run() {
		running();
		
		//Main loop
		while (!isStopRequested()) {
			doPause(true, false);
			
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
		
		stopped();
	}
	
	
}
