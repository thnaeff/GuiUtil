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
package ch.thn.guiutil.test;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class GuiUtilTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		JFrame f = new JFrame("GuiUtilTest");

		JPanel p = new JPanel(new BorderLayout());


		//		p.add(new BorderContentTextFieldTest(), BorderLayout.EAST);
		//		p.add(new PathTextFieldTest(f.getLayeredPane()), BorderLayout.NORTH);

		p.add(new EffectsTest(), BorderLayout.SOUTH);

		//		p.add(new SimpleTableTest(), BorderLayout.CENTER);

		//		p.add(new LabelledComponentPaneTest(), BorderLayout.CENTER);

		//		p.add(new ClippingLabel("asdf löaksfdl aösdlf köals jfö sfas dasfdas fdasdfasdf asdfasdfaslaks fölaks fla jdsölasd fasd fsd"));

		//		p.add(new DisplayTreeTest());

		//		p.add(new CollapsiblePaneTest());

		//		p.add(new MessageDetailsPanelTest());

		//		JComponentDebugger debugger = new JComponentDebugger(p);
		//		debugger.setLayeredPane(f.getLayeredPane());
		//		debugger.showInfoFrame(true);


		f.getContentPane().add(p);

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setMinimumSize(new Dimension(1000, 400));
		f.setLocationRelativeTo(null);
		f.setVisible(true);

	}

}
