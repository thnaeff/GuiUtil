/**
 * 
 */
package ch.thn.guiutil.component.table;

import javax.swing.JTable;
import javax.swing.ToolTipManager;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

/**
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class SimpleTable extends JTable {
	private static final long serialVersionUID = 4868306360112011436L;
	
	private SimpleTableModel tableModel = null;
		
	/**
	 * 
	 * 
	 * @param columns
	 */
	public SimpleTable(SimpleTableModel model) {
		super(model);
		
		setModel(model);
		
		//TODO just set a custom table header for now. Maybe there are fun things to do with it...
		getTableHeader().setDefaultRenderer(new SimpleTableHeaderCellRenderer(model));
		
		setFillsViewportHeight(true);
		
		
		//http://www.chka.de/swing/table/faq.html
		//"Why is JTable so slow and why does it ask the renderer every time the mouse is moved?"
		ToolTipManager.sharedInstance().unregisterComponent(this);
		ToolTipManager.sharedInstance().unregisterComponent(getTableHeader());
	}
	
	@Override
	public void setModel(TableModel dataModel) {
		if (tableModel != null) {
			//Make sure the column model is not connected to the old table model any more
			tableModel.setColumnModel(null);
		}
		
		if (dataModel instanceof SimpleTableModel) {
			tableModel = (SimpleTableModel) dataModel;
			tableModel.setColumnModel(getColumnModel());
		} else {
			tableModel = null;
		}
		
		super.setModel(dataModel);
	}
	
	@Override
	public TableCellRenderer getCellRenderer(int row, int column) {
		
		if (tableModel != null) {
			if (column >= 0 && column < tableModel.getColumnCount()) {
				int modelColumn = convertColumnIndexToModel(column);
				int modelRow = convertRowIndexToModel(row);
				Object value = tableModel.getValueAt(modelRow, modelColumn);
				
				if (value != null && tableModel.getColumn(modelColumn).hasRenderer(value.getClass())) {
					//Return a column specific renderer
					return tableModel.getColumn(modelColumn).getRenderer(value.getClass());
				}
				
				TableCellRenderer tr = tableModel.getColumn(modelColumn).getDefaultRenderer();
				if (tr != null) {
					return tr;
				}
			}
		}
		
		//Returns the default cell renderer which matches the class returned with 
		//TableModel.getColumnClass
		//Row and column seem to be converted to the model view internally
		return super.getCellRenderer(row, column);
	}
	
	@Override
	public TableCellEditor getCellEditor(int row, int column) {
		
		if (tableModel != null) {
			if (column >= 0 && column < tableModel.getColumnCount()) {
				int modelColumn = convertColumnIndexToModel(column);
				int modelRow = convertRowIndexToModel(row);
				Object value = tableModel.getValueAt(modelRow, modelColumn);
				
				if (value != null && tableModel.getColumn(modelColumn).hasEditor(value.getClass())) {
					//Return a column specific renderer
					return tableModel.getColumn(modelColumn).getEditor(value.getClass());
				}
				
				TableCellEditor te = tableModel.getColumn(modelColumn).getDefaultEditor();
				if (te != null) {
					return te;
				}
			}
		}
		
		//Returns the default cell editor which matches the class returned with 
		//TableModel.getColumnClass
		//Row and column seem to be converted to the model view internally
		return super.getCellEditor(row, column);
	}
	
	
	
}
