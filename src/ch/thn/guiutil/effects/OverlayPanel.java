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

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 * A panel which lays over all the other components with a semi transparent background.<br />
 * <br />
 * The layered pane can be obtained from a JFrame for example with getLayeredPane().<br>
 *
 * @author Thomas Naeff (github.com/thnaeff) Naeff
 * @since
 *
 */
public class OverlayPanel extends JPanel {
	private static final long serialVersionUID = 1623359440962114967L;


	private JLayeredPane layeredPane = null;

	private final boolean showTransparentBackground = true;

	private boolean firstPaint = true;

	private Color cBackground = new Color(0, 0, 0, 110);


	/**
	 * A panel over all the other panels with a semi transparent gray background.
	 * The default background color is Color(0, 0, 0, 110).
	 * 
	 * 
	 * @param layeredPane
	 * @param visible
	 * @since
	 */
	public OverlayPanel(JLayeredPane layeredPane, boolean visible) {
		this.layeredPane = layeredPane;

		//Just define a certain size to make the paint-method work
		setBounds(0, 0, 1, 1);

		//Some style
		//setBorder(BorderFactory.createLineBorder(Color.darkGray, 5));

		//Not opaque to see background components shine through
		setOpaque(false);

		//Block against mouse and key events
		enableEvents(AWTEvent.MOUSE_EVENT_MASK);
		setFocusable(true);
		setFocusCycleRoot(true);

		setVisible(visible);

		layeredPane.add(this, new Integer(JLayeredPane.POPUP_LAYER - 1));

	}

	/**
	 * Sets the color of the overlay panel.<br />
	 * To create a translucent color, use an alpha value, e.g. Color(0, 0, 0, 110)
	 * 
	 * @param color
	 */
	public void setColor(Color color) {
		cBackground = color;
	}


	@Override
	public void setVisible(boolean visible) {


		super.setVisible(visible);

		layeredPane.moveToFront(this);

		//Make sure the overlay panel gets the focus, otherwise an element below the
		//overlay panel might still have the focus and can still be controlled
		//with the keys
		if (visible) {
			requestFocusInWindow();
		}

		//Validate again
		layeredPane.validate();

	}



	@Override
	public void paint(Graphics g) {

		//Stretch the panel to cover the whole layered pane
		setBounds(0, 0, layeredPane.getWidth(), layeredPane.getHeight());

		if (showTransparentBackground) {
			//Show a translucent gray background
			g.setColor(cBackground);
			g.fillRect(0, 0, layeredPane.getWidth(), layeredPane.getHeight());
		}

		super.paint(g);

		if (firstPaint) {
			validate();
			firstPaint = false;
		}

	}



}
