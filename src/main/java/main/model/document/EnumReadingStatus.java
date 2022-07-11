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
 * Enum that lists the different stages of the reading process.
 */
public enum EnumReadingStatus
{
    /**
     * The has not yet started reading the document.
     */
    NOT_STARTED,

    /**
     * The reader is in the process of reading the document.
     */
    IN_PROGRESS,

    /**
     * The reader has finished reading the document.
     */
    FINISHED,

    /**
     * The reader is no longer reading the document.
     */
    ON_HOLD;
}
