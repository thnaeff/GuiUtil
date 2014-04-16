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

import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.ToolTipManager;


/**
 * Use this class to show a specific tool tip message in a certain area within 
 * the given component
 * 
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class ToolTipArea extends ActionArea implements ActionAreaListener {
	
	private String toolTip = null;
	
	/**
	 * 
	 * @param component
	 */
	public ToolTipArea(JComponent component) {
		super(component);	
		addActionAreaListener(this);
		active(true);
	}
	
	/**
	 * Set the tool tip message and the area on which the message should appear
	 * 
	 * @param toolTip
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void setMessage(String toolTip, int x, int y, int width, int height) {
		this.toolTip = toolTip;
		setArea(x, y, width, height);
	}
	
	/**
	 * 
	 * @param toolTip
	 */
	public void setMessage(String toolTip) {
		this.toolTip = toolTip;
	}

	@Override
	public void mouseEnteredArea(MouseEvent e) {
		getComponent().setToolTipText(toolTip);
		ToolTipManager.sharedInstance().mouseMoved(e);
	}

	@Override
	public void mouseExitedArea(MouseEvent e) {
		getComponent().setToolTipText(null);
		ToolTipManager.sharedInstance().mouseMoved(e);
	}
	

}
