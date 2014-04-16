/**
 * 
 */
package ch.thn.guiutil;

import javax.swing.ImageIcon;

/**
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class Loader {
	
	public static ImageIcon loadIcon(String resourcePath) {
		Object o = new Object();
		return new ImageIcon(o.getClass().getResource(resourcePath));
	}

}
