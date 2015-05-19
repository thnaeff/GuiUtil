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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.Timer;

import ch.thn.guiutil.Loader;
import ch.thn.util.ColorUtil;
import ch.thn.util.html.HtmlUtil;


/**
 * An {@link OverlayPanel} which shows a loading/busy animation.
 * 
 * 
 * 
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class BusyOverlay extends OverlayPanel implements ActionListener {
	private static final long serialVersionUID = 1013412074114453291L;


	private static boolean isVisible = false;
	private static boolean setVisible = false;

	private JLabel lBusy = null;

	private Timer tSetVisible = null;

	private final ImageIcon icon = Loader.loadIcon("/32x32/loading.gif");

	private String busyText = null;

	/**
	 * 
	 * @param rootPane
	 * @param busyText
	 */
	public BusyOverlay(JLayeredPane rootPane, String busyText) {
		super(rootPane, false);

		setLayout(new BorderLayout());

		tSetVisible = new Timer(0, this);

		lBusy = new JLabel(icon);
		lBusy.setVerticalTextPosition(JLabel.BOTTOM);
		lBusy.setHorizontalTextPosition(JLabel.CENTER);

		add(lBusy, BorderLayout.CENTER);

		setLoadingText(busyText);
	}


	/**
	 * Sets the text shown under the busy icon. The text is formatted with some
	 * html/css, thus a linebreak can be added with &lt;br&gt;.
	 * 
	 * @param busyText
	 */
	public void setLoadingText(String busyText) {
		this.busyText = busyText;
		lBusy.setText(HtmlUtil.textHtml(textFormatted(busyText, 4, ColorUtil.colorToHex(Color.white), true, true)));
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public String getBusyText() {
		return busyText;
	}

	/**
	 * 
	 * 
	 * @param hide
	 */
	public void hideBusyIcon(boolean hide) {
		if (hide) {
			lBusy.setIcon(null);
		} else {
			lBusy.setIcon(icon);
		}
	}

	/**
	 * Puts the text between font-tags with the given options
	 * 
	 * @param text The text to put between the font-tags
	 * @param size The text size, or 0 for not text size definition
	 * @param color The text color (for example #75ba45), or null for not text color definition
	 * @param alignCenter
	 * @return The text within the font-tags and the options
	 */
	private String textFormatted(String text, int size, String color, boolean bold, boolean alignCenter) {
		String s = "<font";

		if (size != 0) {
			s = s + " size=\"" + size + "\"";
		}

		if (color != null) {
			s = s + " color=\"" + color + "\"";
		}

		if (bold) {
			s = s + " weight=\"bold\"";
		}

		s = s + ">" + text + "</font>";

		return s;
	}


	/**
	 * Shows the busy overlay. A delay can be given so that the busy animation
	 * is now shown right away in case the operation finished within a short time
	 * and the busy overlay does not need to be shown.
	 * 
	 * @param visible
	 * @param delay The delay in ms to execute the setVisible action
	 */
	public void setVisible(boolean visible, int delay) {
		setVisible = visible;

		tSetVisible.setDelay(delay);
		tSetVisible.setInitialDelay(delay);
		tSetVisible.start();

		if (!visible) {
			setVisible(false);
		}
	}

	/**
	 * 
	 * @param visible
	 */
	@Override
	public void setVisible(boolean visible) {
		if (tSetVisible != null) {
			tSetVisible.stop();
		}

		//If one loading overlay is already visible, do not show a new one
		if (isVisible && visible) {
			return;
		}

		isVisible = visible;

		super.setVisible(visible);

	}


	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == tSetVisible) {
			tSetVisible.stop();

			setVisible(setVisible);
		}

	}


}
