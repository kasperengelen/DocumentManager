/**
 *   Copyright (C) 2022  Kasper Engelen
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation under version 3 of the License.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 *   This file is based on another project by TERAI Atsuhiro. The source can be found
 *   at <https://github.com/aterai/java-swing-tips/blob/123800bc03d5cae9297a21c7c63ca8e7e99e98ba/PointInsidePrefSize/src/java/example/MainPanel.java>.
 *   This project is, at the time of writing, published under the MIT license:
 *
 *   >  Copyright (C) 2015 TERAI Atsuhiro
 *   >
 *   >  Permission is hereby granted, free of charge, to any person obtaining a copy
 *   >  of this software and associated documentation files (the "Software"), to deal
 *   >  in the Software without restriction, including without limitation the rights
 *   >  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   >  copies of the Software, and to permit persons to whom the Software is
 *   >  furnished to do so, subject to the following conditions:
 *
 *   >  The above copyright notice and this permission notice shall be included in all
 *   >  copies or substantial portions of the Software.
 *
 *   >  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   >  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   >  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   >  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   >  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   >  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   >  SOFTWARE.
 */

package main.view;

import org.oxbow.swingbits.dialog.task.TaskDialogs;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.net.URI;
import java.net.URL;

/**
 * Class that handles a {@link URI} datatype in a {@link JTable}.
 */
class URIRenderer extends DefaultTableCellRenderer implements MouseListener, MouseMotionListener {
    private static final Rectangle CELL_RECT = new Rectangle();
    private static final Rectangle ICON_RECT = new Rectangle();
    private static final Rectangle TEXT_RECT = new Rectangle();
    private int viewRowIndex = -1;
    private int viewColumnIndex = -1;
    private boolean isRollover;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIdx, int columnIdx) {
        super.getTableCellRendererComponent(table, value, isSelected, false, rowIdx, columnIdx);

        TableColumnModel colModel = table.getColumnModel();
        TableColumn col = colModel.getColumn(columnIdx);

        Insets i = this.getInsets();
        CELL_RECT.x = i.left;
        CELL_RECT.y = i.top;
        CELL_RECT.width = col.getWidth() - colModel.getColumnMargin() - i.right - CELL_RECT.x;
        CELL_RECT.height = table.getRowHeight(rowIdx) - table.getRowMargin() - i.bottom - CELL_RECT.y;
        ICON_RECT.setBounds(0, 0, 0, 0);
        TEXT_RECT.setBounds(0, 0, 0, 0);

        // The text value that the URI will have in the table.
        String label = (value == null) ? "N/A" : "LINK";

        // this will determine the exact style and layout of the text in the cell
        String str = SwingUtilities.layoutCompoundLabel(
                this,
                this.getFontMetrics(this.getFont()),
                label,
                this.getIcon(),
                this.getVerticalAlignment(),
                this.getHorizontalAlignment(),
                this.getVerticalTextPosition(),
                this.getHorizontalTextPosition(),
                CELL_RECT,
                ICON_RECT, // icon
                TEXT_RECT, // text
                this.getIconTextGap());

        // determine whether the text should be underlined, blue colored or not
        // also sets the mouse cursor depending on whether the link is hovered or not
        if(value == null) {
            this.setText(str);
        } else if (isRolloverCell(table, rowIdx, columnIdx)) {
            setText("<html><u><font color='blue'>" + str);
            table.setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else {
            setText("<html><font color='blue'>" + str);
            table.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }

        return this;
    }

    /**
     * Determine if the cursor currently hovers over the cell.
     */
    protected boolean isRolloverCell(JTable table, int row, int column) {
        return !table.isEditing() && viewRowIndex == row && viewColumnIndex == column && isRollover;
    }

    // @see SwingUtilities2.pointOutsidePrefSize(...)
    private static boolean pointInsidePrefSize(JTable table, Point p) {
        int row = table.rowAtPoint(p);
        int col = table.columnAtPoint(p);

        if(isRowColOutOfBounds(table, row, col)) {
            return false;
        }

        TableCellRenderer tcr = table.getCellRenderer(row, col);
        Object value = table.getValueAt(row, col);
        Component cell = tcr.getTableCellRendererComponent(table, value, false, false, row, col);
        Dimension itemSize = cell.getPreferredSize();
        Insets i = ((JComponent) cell).getInsets();
        Rectangle cellBounds = table.getCellRect(row, col, false);
        cellBounds.width = itemSize.width - i.right - i.left;
        cellBounds.translate(i.left, i.top);
        return cellBounds.contains(p);
    }

    /**
     * Check if the specified column contains values of the {@link URI} datatype.
     */
    private static boolean isURIColumn(JTable table, int column) {
        return column >= 0 && table.getColumnClass(column).equals(URI.class);
    }

    /**
     * Check for out of bounds, this can happen if the mouse if moving over an empty part of the table.
     */
    private static boolean isRowColOutOfBounds(JTable table, int row, int col)
    {
        return row >= table.getRowCount() || row < 0 || col >= table.getColumnCount() || col < 0;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        JTable table = (JTable) e.getComponent();
        Point pt = e.getPoint();

        final int prevRow = viewRowIndex;
        final int prevCol = viewColumnIndex;

        final boolean prevRollover = isRollover;

        viewRowIndex = table.rowAtPoint(pt);
        viewColumnIndex = table.columnAtPoint(pt);

        if(isRowColOutOfBounds(table, viewRowIndex, viewColumnIndex)) {
            return;
        }

        isRollover = isURIColumn(table, viewColumnIndex) && pointInsidePrefSize(table, pt);
        if (viewRowIndex == prevRow && viewColumnIndex == prevCol && isRollover == prevRollover) {
            return;
        }
        if (!isRollover && !prevRollover) {
            return;
        }
        // >>>> HyperlinkCellRenderer.java
        // @see http://java.net/projects/swingset3/sources/svn/content/trunk/SwingSet3/src/com/sun/swingset3/demos/table/HyperlinkCellRenderer.java
        Rectangle repaintRect;
        if (isRollover) {
            Rectangle r = table.getCellRect(viewRowIndex, viewColumnIndex, false);
            repaintRect = prevRollover ? r.union(table.getCellRect(prevRow, prevCol, false)) : r;
        } else {
            repaintRect = table.getCellRect(prevRow, prevCol, false);
        }
        table.repaint(repaintRect);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JTable table = (JTable) e.getComponent();
        if (isURIColumn(table, viewColumnIndex)) {
            table.repaint(table.getCellRect(viewRowIndex, viewColumnIndex, false));
            viewRowIndex = -1;
            viewColumnIndex = -1;
            isRollover = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JTable table = (JTable) e.getComponent();
        Point pt = e.getPoint();
        int col = table.columnAtPoint(pt);

        if(col >= table.getColumnCount() || col < 0) {
            return;
        }

        // check if the left mouse button clicked on a link
        if (e.getButton() == MouseEvent.BUTTON1 && isURIColumn(table, col) && pointInsidePrefSize(table, pt)) {
            int crow = table.rowAtPoint(pt);
            URI uri = (URI) table.getValueAt(crow, col);
            if(uri == null) {
                return;
            }

            if (Desktop.isDesktopSupported()) {
                // first, try if the URI points to a file
                try {
                    File file = new File(uri);
                    Desktop.getDesktop().open(file);

                    return;

                } catch (Exception ignored) {
                    System.out.println("Not a valid file: '" + uri + "'");
                }

                // second, check if the URI is a syntactically correct URL
                try {
                    URL url = uri.toURL(); // NOTE: this is to check that this is a valid URL, this one throws useful exceptions otherwise
                    Desktop.getDesktop().browse(url.toURI());
                    return;
                } catch(Exception ignored) {
                    System.out.println("Not a valid URL: '" + uri + "'");
                } // ignore, just try to open it as a file

                // otherwise, report an error
                UIManager.getLookAndFeel().provideErrorFeedback(e.getComponent());
                TaskDialogs.error(null, "Cannot open link!", String.format("\"%s\"", uri));
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        /* not needed */
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        /* not needed */
    }

    @Override
    public void mousePressed(MouseEvent e) {
        /* not needed */
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        /* not needed */
    }
}