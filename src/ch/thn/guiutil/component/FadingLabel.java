/**
 * 
 */
package ch.thn.guiutil.component;

import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import ch.thn.guiutil.effects.FadingImage;

/**
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class FadingLabel extends JLabel {
	private static final long serialVersionUID = 3440973634805075719L;
	
	public static final int DELAY_NONE = 0;
	public static final int DELAY_ALL = 1;
	public static final int DELAY_HALF = 2;
	
	private FadingImage fadingImage = null;
	
	private int fadingMode = DELAY_ALL;
	private int width = 10;
	private int height = 10;

	/**
	 * 
	 * @param steps The number of steps to fade an image in or out
	 * @param timeout The timeout between the fading steps
	 * @param fadingMode 
	 * @see JLabel#JLabel()
	 */
	public FadingLabel(int steps, int timeout, int fadingMode) {
		super();
		init(null, steps, timeout, fadingMode);
	}
	
	/**
	 * 
	 * @param icon
	 * @param steps The number of steps to fade an image in or out
	 * @param timeout The timeout between the fading steps
	 * @param fadingMode 
	 * @see JLabel#JLabel(Icon)
	 */
	public FadingLabel(Icon icon, int steps, int timeout, int fadingMode) {
		super();
		init(icon, steps, timeout, fadingMode);
	}
	
	/**
	 * 
	 * @param string
	 * @param steps The number of steps to fade an image in or out
	 * @param timeout The timeout between the fading steps
	 * @param fadingMode 
	 * @see JLabel#JLabel(String)
	 */
	public FadingLabel(String string, int steps, int timeout, int fadingMode) {
		super(string);
		init(null, steps, timeout, fadingMode);
	}
	
	/**
	 * 
	 * @param icon
	 * @param hotizontalAlignment
	 * @param steps The number of steps to fade an image in or out
	 * @param timeout The timeout between the fading steps
	 * @param fadingMode 
	 * @see JLabel#JLabel(Icon, int)
	 */
	public FadingLabel(Icon icon, int hotizontalAlignment, int steps, int timeout, int fadingMode) {
		//The icon will be set later with init(), but it has to be set here to 
		//avoid ambiguity with the super constructor
		super(icon, hotizontalAlignment);
		init(icon, steps, timeout, fadingMode);
	}
	
	/**
	 * 
	 * @param string
	 * @param hotizontalAlignment
	 * @param steps The number of steps to fade an image in or out
	 * @param timeout The timeout between the fading steps
	 * @param fadingMode 
	 * @see JLabel#JLabel(String, int)
	 */
	public FadingLabel(String string, int hotizontalAlignment, int steps, int timeout, int fadingMode) {
		super(string, hotizontalAlignment);
		init(null, steps, timeout, fadingMode);
	}
	
	/**
	 * 
	 * @param string
	 * @param icon
	 * @param hotizontalAlignment
	 * @param steps The number of steps to fade an image in or out
	 * @param timeout The timeout between the fading steps
	 * @param fadingMode 
	 * @see JLabel#JLabel(String, Icon, int)
	 */
	public FadingLabel(String string, Icon icon, int hotizontalAlignment, int steps, int timeout, int fadingMode) {
		super(string, null, hotizontalAlignment);
		init(icon, steps, timeout, fadingMode);
	}
	
	
	/**
	 * 
	 * 
	 * @param icon
	 * @param steps
	 * @param timeout
	 * @param fadingMode
	 */
	private void init(Icon icon, int steps, int timeout, int fadingMode) {
		this.fadingMode = fadingMode;
		
		if (icon != null) {
			width = icon.getIconWidth();
			height = icon.getIconHeight();
		}
		
		fadingImage = new FadingImage(this, width, height, steps, timeout);
		
		super.setIcon(new ImageIcon(fadingImage.getAnimatedImage()));
	}
	
	/**
	 * Sets the icon for this label by fading the old image out (if there was one) 
	 * and fading the new image in.
	 */
	@Override
	public void setIcon(Icon icon) {
		if (fadingImage != null) {
			boolean newSize = false;
			
			if (icon.getIconWidth() != width) {
				width = icon.getIconWidth();
				newSize = true;
			}
			
			if (icon.getIconHeight() != height) {
				height = icon.getIconHeight();
				newSize = true;
			}
			
			if (newSize) {
				BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				fadingImage.setAnimatedImage(bi);
				super.setIcon(new ImageIcon(bi));
			}
			
			
			fadingImage.setImage(((ImageIcon)icon).getImage(), fadingMode);
		} else {
			//Just set the icon if the fading is not ready yet
			super.setIcon(icon);
		}
	}
	
}
