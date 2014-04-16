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
package ch.thn.guiutil.component.table;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class SimpleTableColumn {
	
	private HashMap<Class<?>, TableCellRenderer> renderers = null;
	private HashMap<Class<?>, TableCellEditor> editors = null;
	
	private ArrayList<SimpleTableColumnListener> listeners = null;
	
	private Object columnTitle = null;
	private Object defaultValue = null;
	
	private TableCellEditor defaultEditor = null;
	private TableCellRenderer defaultRenderer = null;
	
	private Class<?> defaultClass = null;
	
	private String columnTitleToolTip = null;
	
//	private boolean sortable = false;
	private boolean editable = false;
	private boolean forceDefaultClass = false;
	
	private int minWidth = 0;
	private int maxWidth = 0;
	
	
	/**
	 * 
	 * 
	 * @param columnTitle
	 */
	public SimpleTableColumn(Object columnTitle) {
		this(columnTitle, String.class, null, null, null, false, false, 30, 0);
	}
	
	/**
	 * 
	 * 
	 * @param columnTitle
	 * @param defaultClass
	 */
	public SimpleTableColumn(Object columnTitle, Class<?> defaultClass) {
		this(columnTitle, defaultClass, null, null, null, false, false, 30, 0);
	}
	
	
	/**
	 * Creates a new table column object which contains the preferences of a  
	 * column.<br>
	 * <br>
	 * Any changes done to a {@link SimpleTableColumn} object notifies the 
	 * {@link SimpleTableModel} of all the tables in which the column object 
	 * is in (yes, it is possible to have one {@link SimpleTableColumn} object in multiple 
	 * tables). The table model then calls <code>fireTableStructureChanged</code> 
	 * to notify the table of an update.
	 * 
	 * @param columnTitle The title of the column
	 * @param defaultClass The default class of this column content. The default class 
	 * defines which default table cell renderer is used if no specific renderer is 
	 * defined for that class. Class specific renderers can be set with 
	 * {@link #setRenderer(Class, TableCellRenderer)}.
	 * @param defaultValue The default value to be set in a cell of this column when 
	 * a new empty row or this column is added.
	 * @param defaultEditor The editor to use for all classes which do not have a 
	 * specific editor assigned. If set to <code>null</code>, JTable's default table 
	 * cell editor is loaded.
	 * @param defaultRenderer The renderer to use for all classes which do not have a 
	 * specific renderer assigned. If set to <code>null</code>, JTable's default table 
	 * cell renderer is loaded.
	 * @param forceDefaultClass If set to true, values of a class other than the 
	 * <code>defaultClass</code> can not be added. If set to false, the added 
	 * values can be of any class (and in this case, a class specific renderer 
	 * should be defined via {@link #setRenderer(Class, TableCellRenderer)} to 
	 * handle those values).
	 * @param editable If set to true, values in this column are editable 
	 * per default
	 */
	public SimpleTableColumn(Object columnTitle, Class<?> defaultClass, Object defaultValue, 
			TableCellEditor defaultEditor, TableCellRenderer defaultRenderer, 
			boolean forceDefaultClass, boolean editable, int minWidth, int maxWidth) {
		this.columnTitle = columnTitle;
		this.defaultClass = defaultClass;
		this.defaultValue = defaultValue;
		this.defaultEditor = defaultEditor;
		this.defaultRenderer = defaultRenderer;
		this.forceDefaultClass = forceDefaultClass;
		this.editable = editable;
		this.minWidth = minWidth;
		this.maxWidth = maxWidth;
		
		renderers = new HashMap<Class<?>, TableCellRenderer>();
		editors = new HashMap<Class<?>, TableCellEditor>();
		listeners = new ArrayList<>();
	}
	
	/**
	 * Used internally for {@link SimpleTableModel} to register a listener 
	 * which receives events about column changes
	 * 
	 * @param l
	 */
	protected void addSimpleTableColumnListener(SimpleTableColumnListener l) {
		listeners.add(l);
	}
	
	/**
	 * Used internally for {@link SimpleTableModel} to unregister a listener 
	 * which received events about column changes
	 * 
	 * @param l
	 */
	protected void removeSimpleTableColumnListener(SimpleTableColumnListener l) {
		listeners.remove(l);
	}
	
	/**
	 * Used internally by {@link SimpleTableModel}.<br>
	 * Notifies the registered objects about changes in the column
	 */
	private void fireColumnChanged() {
		for (SimpleTableColumnListener l : listeners) {
			l.columnChanged(this);
		}
	}
	
	/**
	 * 
	 * 
	 * @param c
	 * @param r
	 */
	public void setRenderer(Class<?> c, TableCellRenderer r) {
		renderers.put(c, r);
		fireColumnChanged();
	}
	
	/**
	 * 
	 * 
	 * @param c
	 * @return
	 */
	public TableCellRenderer getRenderer(Class<?> c) {
		return renderers.get(c);
	}
	
	/**
	 * 
	 * 
	 * @param c
	 * @return
	 */
	public boolean hasRenderer(Class<?> c) {
		return renderers.containsKey(c);
	}
	
	/**
	 * 
	 * 
	 * @param c
	 * @param e
	 */
	public void setEditor(Class<?> c, TableCellEditor e) {
		editors.put(c, e);
		fireColumnChanged();
	}
	
	/**
	 * 
	 * 
	 * @param c
	 * @return
	 */
	public boolean hasEditor(Class<?> c) {
		return editors.containsKey(c);
	}
	
	/**
	 * 
	 * 
	 * @param c
	 * @return
	 */
	public TableCellEditor getEditor(Class<?> c) {
		return editors.get(c);
	}
	
	/**
	 * 
	 * 
	 * @param editable
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
		fireColumnChanged();
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public boolean isEditable() {
		return editable;
	}
	
	/**
	 * @return the columnTitle
	 */
	public Object getColumnTitle() {
		return columnTitle;
	}
	
	/**
	 * @param columnTitle the columnTitle to set
	 */
	public void setColumnTitle(Object columnTitle) {
		this.columnTitle = columnTitle;
		fireColumnChanged();
	}
	
	/**
	 * @return the columnTitleToolTip
	 */
	public String getColumnTitleToolTip() {
		return columnTitleToolTip;
	}
	
	/**
	 * @param columnTitleToolTip the columnTitleToolTip to set
	 */
	public void setColumnTitleToolTip(String columnTitleToolTip) {
		this.columnTitleToolTip = columnTitleToolTip;
		fireColumnChanged();
	}
	
	/**
	 * @return the defaultClass
	 */
	public Class<?> getDefaultClass() {
		return defaultClass;
	}
	
	/**
	 * @param defaultClass the defaultClass to set
	 */
	public void setDefaultClass(Class<?> defaultClass) {
		this.defaultClass = defaultClass;
		fireColumnChanged();
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public boolean forceDefaultClass() {
		return forceDefaultClass;
	}
	
	/**
	 * 
	 * 
	 * @param force
	 */
	public void forceDefaultClass(boolean force) {
		this.forceDefaultClass = force;
	}
	
	/**
	 * @return the defaultValue
	 */
	public Object getDefaultValue() {
		return defaultValue;
	}
	
	/**
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the defaultEditor
	 */
	public TableCellEditor getDefaultEditor() {
		return defaultEditor;
	}
	
	/**
	 * @param defaultEditor the defaultEditor to set
	 */
	public void setDefaultEditor(TableCellEditor defaultEditor) {
		this.defaultEditor = defaultEditor;
	}
	
	/**
	 * @return the defaultRenderer
	 */
	public TableCellRenderer getDefaultRenderer() {
		return defaultRenderer;
	}
	
	/**
	 * @param defaultRenderer the defaultRenderer to set
	 */
	public void setDefaultRenderer(TableCellRenderer defaultRenderer) {
		this.defaultRenderer = defaultRenderer;
	}
	
	/**
	 * @param minWidth the minWidth to set
	 */
	public void setMinWidth(int minWidth) {
		this.minWidth = minWidth;
		fireColumnChanged();
	}
	
	/**
	 * @return the minWidth
	 */
	public int getMinWidth() {
		return minWidth;
	}
	
	/**
	 * @param maxWidth the maxWidth to set
	 */
	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
		fireColumnChanged();
	}
	
	/**
	 * @return the maxWidth
	 */
	public int getMaxWidth() {
		return maxWidth;
	}
	
}
