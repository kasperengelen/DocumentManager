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

package main.model.index;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Class for writing a {@link DocumentIndex} object to the filesystem.
 */
public class DocumentIndexWriter
{
    private final DocumentIndex index;

    /**
     * Constructor.
     *
     * @param index The index that will be written to a file.
     */
    public DocumentIndexWriter(DocumentIndex index) {
        this.index = index;
    }

    /**
     * Write the file.
     *
     * @param filename The {@link Path} that points to the target file.
     *
     * @throws IOException If something went wrong while writing the file.
     */
    public void write(Path filename) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(filename.toFile(), this.index);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }
}
