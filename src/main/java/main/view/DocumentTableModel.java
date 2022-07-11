/**
 *   Copyright (C) 2022  Kasper Engelen
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package main.view;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.net.URI;
import java.util.List;

/**
 * Class that provides the contents of a table.
 */
public class DocumentTableModel implements TableModel
{
    private final List<DocumentView> documentViews;
    private final static List<ColumnInfo<?>> COLUMNS = List.of(
            new ColumnInfo<>("Authors", 0, DocumentView::getAuthors, String.class),
            new ColumnInfo<>("Year", 60, DocumentView::getPublicationYear, String.class),
            new ColumnInfo<>("Title", 0, DocumentView::getTitle, String.class),
            new ColumnInfo<>("Publication Venue", 0, DocumentView::getPublicationVenue, String.class),
            new ColumnInfo<>("Page count", 100, DocumentView::getPageCount, String.class),
            new ColumnInfo<>("Document type", 130, DocumentView::getDocumentType, String.class),
            new ColumnInfo<>("Reading status", 120, DocumentView::getReadingStatus, String.class),
            new ColumnInfo<>("Source", 80, DocumentView::getSourceFileLink, URI.class),
            new ColumnInfo<>("Notes", 80, DocumentView::getNotesFileLink, URI.class)
    );

    /**
     * Constructor.
     *
     * @param documents A list of document views, each view will provide a single row to the table.
     */
    public DocumentTableModel(List<DocumentView> documents) {
        this.documentViews = documents;
    }

    @Override
    public int getRowCount() {
        return this.documentViews.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.size();
    }

    @Override
    public String getColumnName(int columnIndex) {

        if (columnIndex >= this.getColumnCount()) {
            throw new IllegalArgumentException("Column index needs to be between 0 and %d, actual value: %d".formatted(this.getColumnCount()-1, columnIndex));
        }

        return COLUMNS.get(columnIndex).getColumnName();
    }

    /**
     * Retrieve the width of the specified column.
     */
    public Integer getColumnWidth(int columnIndex) {
        if (columnIndex >= this.getColumnCount()) {
            throw new IllegalArgumentException("Column index needs to be between 0 and %d, actual value: %d".formatted(this.getColumnCount()-1, columnIndex));
        }

        return COLUMNS.get(columnIndex).getColumnWidth();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex >= this.getColumnCount()) {
            throw new IllegalArgumentException("Column index needs to be between 0 and %d, actual value: %d".formatted(this.getColumnCount()-1, columnIndex));
        }

        return COLUMNS.get(columnIndex).getValueClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(rowIndex >= this.getRowCount()) {
            throw new IllegalArgumentException("Row index needs to be between 0 and %d, actual value: %d".formatted(this.getRowCount()-1, rowIndex));
        }

        if(columnIndex >= this.getColumnCount()) {
            throw new IllegalArgumentException("Column index needs to be between 0 and %d, actual value: %d".formatted(this.getColumnCount()-1, columnIndex));
        }

        // apply value getter to document view
        return COLUMNS.get(columnIndex).getValueGetter().apply(this.documentViews.get(rowIndex));
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        // JTable is the listener, do "l.tableChanged(event);"
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }
}
