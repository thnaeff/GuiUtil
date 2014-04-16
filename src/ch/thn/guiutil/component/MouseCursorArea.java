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

import java.awt.Cursor;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;


/**
 * Use this class to define an area on a certain component to show a specific 
 * mouse pointer.
 * 
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class MouseCursorArea extends ActionArea implements ActionAreaListener {
	
	private int cursor = Cursor.DEFAULT_CURSOR;
	
	private Cursor outsideCursor = null;
	
	/**
	 *
	 * @param component
	 */
	public MouseCursorArea(JComponent component) {
		super(component);
		outsideCursor = component.getCursor();
		addActionAreaListener(this);
		active(true);
	}
	
	/**
	 * Set the cursor and the area where the coursor should appear.
	 * 
	 * @param cursor A cursor constant from the {@link Cursor} class
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void setCursor(int cursor, int x, int y, int width, int height) {
		this.cursor = cursor;
		setArea(x, y, width, height);
	}
	
	/**
	 * 
	 * @param cursor
	 */
	public void setCursor(int cursor) {
		this.cursor = cursor;
	}



	@Override
	public void mouseEnteredArea(MouseEvent e) {
		outsideCursor = getComponent().getCursor();
		getComponent().setCursor(new Cursor(cursor));
	}

	@Override
	public void mouseExitedArea(MouseEvent e) {
		getComponent().setCursor(outsideCursor);
	}

}
