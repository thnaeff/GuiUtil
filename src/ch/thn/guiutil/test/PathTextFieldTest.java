/**
 * 
 */
package ch.thn.guiutil.test;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import ch.thn.guiutil.input.PathTextField;

/**
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class PathTextFieldTest extends JPanel implements ActionListener {
	private static final long serialVersionUID = 8646762548881946278L;

	
	private JFileChooser fileChooser = null;
	
	private PathTextField ptf = null;
	
	
	public PathTextFieldTest(JLayeredPane lp) {
		
		setLayout(new BorderLayout());
		
		
		ptf = new PathTextField("/home", 20);
		ptf.addButtonActionListener(this);
		ptf.activatePathWatcher(true);
		fileChooser = new JFileChooser(ptf.getText());
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		add(ptf, BorderLayout.NORTH);
		
//		JComponentDebugger debugger = new JComponentDebugger(ptf);
//		debugger.setLayeredPane(lp);
		
		ptf.getBorder();
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		fileChooser.setCurrentDirectory(new File(ptf.getText()));
//		fileChooser.setSelectedFile(new File(ptf.getText()));
		
		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			ptf.setText(fileChooser.getSelectedFile().getAbsolutePath());
		}
	}

}
