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

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.net.URI;

/**
 * Class that represents the files that are associated with the document.
 */
public class DocumentFiles
{
    private URI sourceFile = null;

    private URI notesFile = null;

    /**
     * Retrieve a URI that points to the source of the document.
     */
    @JsonGetter("sourceFile")
    public URI getSourceFile() {
        return sourceFile;
    }

    /**
     * Retrieve a URI that points to a file that contains notes that the reader made while reading the document.
     */
    @JsonGetter("notesFile")
    public URI getNotesFile() {
        return notesFile;
    }

    /**
     * Set the URI that points to the source of the document.
     */
    @JsonSetter("sourceFile")
    public void setSourceFile(URI sourceFile) {
        this.sourceFile = sourceFile;
    }

    /**
     * Set the URI that points to a file that contains notes about the document.
     */
    @JsonSetter("notesFile")
    public void setNotesFile(URI notesFile)
    {
        this.notesFile = notesFile;
    }
}
