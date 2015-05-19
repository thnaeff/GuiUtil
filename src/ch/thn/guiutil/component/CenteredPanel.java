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

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * A {@link JPanel} with one function: to display horizontally and vertically
 * centered content. One method is needed: {@link #setCenteredContent(JComponent)}.
 * This method clears any components from the panel and adds the given component
 * only.
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class CenteredPanel extends JPanel {
	private static final long serialVersionUID = 4745412439809719776L;

	/**
	 * Removes all components from the panel and adds the given content
	 * in the center of the panel. The content should have at least a maximum
	 * size set, otherwise it will expand.
	 * 
	 * @param content
	 */
	public void setCenteredContent(JComponent content) {
		removeAll();

		setLayout(new BorderLayout());

		Box box = new Box(BoxLayout.Y_AXIS);

		box.add(Box.createVerticalGlue());
		box.add(content);
		box.add(Box.createVerticalGlue());

		add(box, BorderLayout.CENTER);
	}

}
