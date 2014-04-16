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
package ch.thn.guiutil.component;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 * Just a {@link JButton} which is formatted so that no borders or background 
 * is visible. Only the icons are visible.
 * 
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class IconButton extends JButton implements FocusListener {
	private static final long serialVersionUID = -5957190073582338443L;
	
	private Icon buttonIcon = null;
	private Icon buttonIconRollover = null;
	
	/**
	 * Creates a {@link JButton} with only an icon
	 * 
	 * @param buttonIcon
	 * @param buttonIconRollover
	 */
	public IconButton(Icon buttonIcon, Icon buttonIconRollover) {
		super();
		init(buttonIcon, buttonIconRollover);
	}
	
	/**
	 * Creates a {@link JButton} with only an icon
	 * 
	 * @param a
	 * @param buttonIcon
	 * @param buttonIconRollover
	 */
	public IconButton(Action a, Icon buttonIcon, Icon buttonIconRollover) {
		super(a);
		init(buttonIcon, buttonIconRollover);
	}
	
	/**
	 * Creates a {@link JButton} with only an icon
	 * 
	 * @param buttonIcon
	 */
	public IconButton(Icon buttonIcon) {
		super(buttonIcon);
		init(buttonIcon, null);
	}
	
	/**
	 * Sets the button properties so that only the icon is shown
	 * 
	 * @param buttonIcon
	 * @param buttonIconRollover
	 */
	private void init(Icon buttonIcon, Icon buttonIconRollover) {
		this.buttonIcon = buttonIcon;
		this.buttonIconRollover = buttonIconRollover;
		
		setBorderPainted(false);
		setFocusPainted(true);
		setIcon(buttonIcon);
		setContentAreaFilled(false);
		
		if (buttonIconRollover != null) {
			setRolloverIcon(buttonIconRollover);
			setPressedIcon(buttonIcon);
		}
		
		Dimension dButton = new Dimension(buttonIcon.getIconWidth(), buttonIcon.getIconWidth());
		setMinimumSize(dButton);
		setPreferredSize(dButton);
		setMaximumSize(dButton);
		
		addFocusListener(this);
	}

	@Override
	public void focusGained(FocusEvent e) {
		if (buttonIconRollover != null) {
			setIcon(buttonIconRollover);
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		setIcon(buttonIcon);
	}
	

}
