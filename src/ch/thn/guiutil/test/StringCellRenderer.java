/**
 * 
 */
package ch.thn.guiutil.test;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class StringCellRenderer implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
//		System.out.println(row + ", " + column + " - string renderer: " + value);
		
		JLabel l = new JLabel();
		l.setOpaque(true);
		
		if (isSelected && !hasFocus) {
			l.setBackground(Color.blue.brighter());
		} else if (hasFocus) {
			l.setBackground(Color.green.brighter());
		}
		
		
		if (value != null) {
			l.setText(value.toString());
		}
		
		return l;
		
	}

}
