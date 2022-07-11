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

package main.model.document;

/**
 * Enum that lists different types of documents.
 */
public enum EnumDocumentType
{
    /**
     * Book format.
     */
    BOOK,

    /**
     * Scientific paper.
     */
    PAPER,

    /**
     * Presentation slides.
     */
    PRESENTATION,

    /**
     * Course text.
     */
    COURSE_TEXT,

    /**
     * A poster.
     */
    POSTER,

    /**
     * Unknown document type.
     */
    OTHER;
}
