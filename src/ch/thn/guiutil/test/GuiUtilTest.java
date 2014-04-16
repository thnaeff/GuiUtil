/**
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
		p.add(new PathTextFieldTest(f.getLayeredPane()), BorderLayout.NORTH);
		
		p.add(new EffectsTest(), BorderLayout.SOUTH);
		
		p.add(new SimpleTableTest(), BorderLayout.CENTER);
		
		
//		JComponentDebugger debugger = new JComponentDebugger(p);
//		debugger.setLayeredPane(f.getLayeredPane());
//		debugger.showInfoFrame(true);
		
		
		f.getContentPane().add(p);
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setMinimumSize(new Dimension(800, 500));
		f.setLocationRelativeTo(null);
		f.setVisible(true);

	}

}
