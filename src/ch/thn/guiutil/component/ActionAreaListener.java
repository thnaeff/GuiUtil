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

/**
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public interface ActionAreaListener {
	
	/**
	 * The mouse pointer entered the defined area
	 * 
	 * @param e The mouse event which caused the listeners to be notified
	 */
	public void mouseEnteredArea(MouseEvent e);
	
	/**
	 * The mouse pointer exited the defined area
	 * 
	 * @param e The mouse event which caused the listeners to be notified
	 */
	public void mouseExitedArea(MouseEvent e);

}
