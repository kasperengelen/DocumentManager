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

import java.util.function.Function;

/**
 * Contains the properties of a column in a table.
 */
public class ColumnInfo<T>
{
    private final String columnName;
    private final int columnWidth;
    private final Function<DocumentView, T> valueGetter;
    private final Class<?> valueClass;

    /**
     * Constructor.
     * @param columnName The name of the column.
     * @param columnWidth The width of the column. Set to 0 for variable width.
     * @param valueGetter A function that extracts the column value from a {@link DocumentView}.
     * @param valueClass A {@link Class} object that represents the type of the column value.
     */
    public ColumnInfo(String columnName, int columnWidth, Function<DocumentView, T> valueGetter, Class<T> valueClass) {
        this.columnName = columnName;
        this.valueGetter = valueGetter;
        this.columnWidth = columnWidth;
        this.valueClass = valueClass;
    }

    /**
     * Retrieve the name of the column.
     */
    public String getColumnName()
    {
        return this.columnName;
    }

    /**
     * Retrieve the function that extracts the column value from a {@link DocumentView}.
     */
    public Function<DocumentView, T> getValueGetter()
    {
        return this.valueGetter;
    }

    /**
     * Retrieve the width of the column.
     */
    public int getColumnWidth()
    {
        return this.columnWidth;
    }

    /**
     * Retrieve a {@link Class} object that represents the type of the column value.
     */
    public Class<?> getValueClass()
    {
        return this.valueClass;
    }
}
