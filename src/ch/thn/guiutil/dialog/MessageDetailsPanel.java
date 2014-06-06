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
package ch.thn.guiutil.dialog;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ch.thn.guiutil.component.CollapsiblePane;

/**
 * A {@link JPanel} with two components: a text area on top to show the message, 
 * and a {@link CollapsiblePane} below to show the message details
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class MessageDetailsPanel extends JPanel {
	private static final long serialVersionUID = -4566929387975388883L;
	
	
	private CollapsiblePane collapsiblePane = null;
	
	
	/**
	 * 
	 * 
	 * @param message
	 * @param details
	 * @param detailsShown
	 */
	public MessageDetailsPanel(String message, String details, boolean detailsShown) {
		
		setLayout(new BorderLayout());
		
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		
		JTextArea taMessage = new JTextArea(message);
		taMessage.setEditable(false);
		taMessage.setBackground(getBackground());
		
		JTextArea taDetails = new JTextArea(details);
		taDetails.setEditable(false);
		taDetails.setBackground(getBackground());
		
		JScrollPane spDetails = new JScrollPane(taDetails);
		spDetails.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		spDetails.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		
		collapsiblePane = new CollapsiblePane("Details:", spDetails, !detailsShown);
		
		p.add(taMessage);
		p.add(Box.createVerticalStrut(5));
		p.add(collapsiblePane);
		
		add(p, BorderLayout.NORTH);
	}
	
	

}
