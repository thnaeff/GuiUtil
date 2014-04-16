/**
 * 
 */
package ch.thn.guiutil.test;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class BooleanCellRenderer implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
//		System.out.println(row + ", " + column + " - rendering boolean: " + value);
		
		if (value == null) {
			value = new Boolean(false);
		}
		
		JCheckBox jb = new JCheckBox();
		
		if (isSelected && !hasFocus) {
			jb.setBackground(Color.blue);
		} else if (hasFocus) {
			jb.setBackground(Color.green);
		}
		
		jb.setSelected(((Boolean)value).booleanValue());
		
		return jb;
	}
	
}
