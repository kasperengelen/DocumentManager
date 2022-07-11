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

package main.model;

import main.model.index.DocumentIndex;
import main.model.index.DocumentIndexReader;
import main.model.index.DocumentIndexWriter;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * Unit tests for {@link DocumentIndexWriter}.
 */
public class DocumentIndexWriterTest
{
    /**
     * Retrieve a Path object that points to the file with the specified filename located in the resources folder.
     */
    public static Path getFile(String filename) throws URISyntaxException {
        return Path.of(DocumentIndexReaderTest.class.getClassLoader().getResource(filename).toURI());
    }

    /**
     * In this test we will take an index, which has some contents, and write it to a JSON. We then compare the
     * newly written JSON and the original JSON.
     */
    @Test
    public void testWritingIndexWithContents() throws Exception {
        // retrieve some content
        DocumentIndexReader reader = new DocumentIndexReader(getFile("correctFile.json"));
        DocumentIndex index = reader.read();

        // create temporary file
        File file = File.createTempFile("IndexWriteWithContents", "");
        file.deleteOnExit();

        // write index contents
        DocumentIndexWriter writer = new DocumentIndexWriter(index);
        writer.write(file.toPath());

        // retrieve JSON data in string format
        String expectedJson = FileUtils.readFileToString(getFile("correctFile.json").toFile(), Charset.defaultCharset());
        String actualJson = FileUtils.readFileToString(file, Charset.defaultCharset());

        JSONAssert.assertEquals("Compare index with contents", expectedJson, actualJson, false);
    }

    /**
     * In this test we will take an empty index and write it to a JSON. We then compare the
     * newly written JSON and the original JSON.
     */
    @Test
    public void testWritingEmptyIndex() throws Exception {
        // create temporary file
        File file = File.createTempFile("IndexWriteWithoutContents", "");
        file.deleteOnExit();

        // create empty index and write to file
        DocumentIndex empty_index = new DocumentIndex();
        DocumentIndexWriter writer = new DocumentIndexWriter(empty_index);
        writer.write(file.toPath());

        // retrieve JSON data in string format
        String expectedJson = FileUtils.readFileToString(getFile("emptyIndex.json").toFile(), Charset.defaultCharset());
        String actualJson = FileUtils.readFileToString(file, Charset.defaultCharset());

        JSONAssert.assertEquals("Compare index without contents", expectedJson, actualJson, false);
    }
}
