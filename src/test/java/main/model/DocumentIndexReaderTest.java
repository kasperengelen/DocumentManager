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

import main.model.document.Document;
import main.model.document.EnumDocumentType;
import main.model.document.EnumReadingStatus;
import main.model.index.DocumentIndex;
import main.model.index.DocumentIndexReader;


import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.time.Year;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Tests for {@link DocumentIndexReader}, {@link Document}, and {@link DocumentIndex}.
 */
public class DocumentIndexReaderTest
{
    /**
     * Retrieve a Path object that points to the file with the specified filename located in the resources folder.
     */
    public static Path getFile(String filename) throws URISyntaxException {
        return Path.of(DocumentIndexReaderTest.class.getClassLoader().getResource(filename).toURI());
    }

    @Test
    void testCorrectContentsIndex() throws Exception {
        DocumentIndexReader reader = new DocumentIndexReader(getFile("correctFile.json"));
        DocumentIndex index = reader.read();

        assertEquals(6, index.getDocumentList().size());

        this.testCorrectContentsIndex_0(index.getDocumentList().get(0));
        this.testCorrectContentsIndex_1(index.getDocumentList().get(1));
        this.testCorrectContentsIndex_2(index.getDocumentList().get(2));
        this.testCorrectContentsIndex_3(index.getDocumentList().get(3));
        this.testCorrectContentsIndex_4(index.getDocumentList().get(4));
        this.testCorrectContentsIndex_5(index.getDocumentList().get(5));
    }

    void testCorrectContentsIndex_0(Document doc0) {
        assertEquals("Peter", doc0.getAuthors().get(0).getFirstName(), "document[0].authors[0].firstname");
        assertEquals("Selie", doc0.getAuthors().get(0).getLastName(), "document[0].authors[0].lastName");

        assertEquals("John", doc0.getAuthors().get(1).getFirstName(), "document[0].authors[1].firstname");
        assertEquals("Doe", doc0.getAuthors().get(1).getLastName(), "document[0].authors[1].lastName");

        assertEquals("Some title", doc0.getTitle(), "document[0].title");
        assertEquals(Year.of(2009), doc0.getPublicationYear(), "document[0].publicationYear");
        assertEquals("Important conference", doc0.getPublicationVenue(), "document[0].publicationVenue");
        assertEquals(50, doc0.getPageCount(), "document[0].pageCount");
        assertEquals(EnumDocumentType.BOOK, doc0.getDocumentType(), "document[0].documentType");

        assertNull(doc0.getSourceLocation(), "document[0].sourceLocation");
        assertNull(doc0.getNotesLocation(), "document[0].notesLocation");

        assertEquals(EnumReadingStatus.FINISHED, doc0.getReadingStatus(), "document[0].readingStatus");
        assertEquals(List.of("tag1", "tag2", "third tag"), doc0.getTags(), "document[0].tags");
    }

    void testCorrectContentsIndex_1(Document doc1) {
        assertEquals("The", doc1.getAuthors().get(0).getFirstName(), "document[1].authors[0].firstname");
        assertEquals("Name", doc1.getAuthors().get(0).getLastName(), "document[1].authors[0].lastName");

        assertEquals("Another title", doc1.getTitle(), "document[1].title");
        assertEquals(Year.of(1), doc1.getPublicationYear(), "document[1].publicationYear");
        assertEquals("International conference on nonsense", doc1.getPublicationVenue(), "document[1].publicationVenue");
        assertEquals(1, doc1.getPageCount(), "document[1].pageCount");
        assertEquals(EnumDocumentType.PAPER, doc1.getDocumentType(), "document[1].documentType");

        assertEquals(doc1.getSourceLocation().getPath(), "ABCDEF", "document[1].sourceLocation");
        assertNull(doc1.getNotesLocation(), "document[1].notesLocation");

        assertEquals(EnumReadingStatus.FINISHED, doc1.getReadingStatus(), "document[1].readingStatus");
        assertEquals(List.of(), doc1.getTags(), "document[1].tags");
    }

    void testCorrectContentsIndex_2(Document doc2) {
        assertEquals("Jane", doc2.getAuthors().get(0).getFirstName(), "document[2].authors[0].firstname");
        assertEquals("Doe", doc2.getAuthors().get(0).getLastName(), "document[2].authors[0].lastName");

        assertEquals("Third title", doc2.getTitle(), "document[2].title");
        assertEquals(Year.of(0), doc2.getPublicationYear(), "document[2].publicationYear");
        assertEquals("prestigious journal", doc2.getPublicationVenue(), "document[2].publicationVenue");
        assertEquals(35, doc2.getPageCount(), "document[2].pageCount");
        assertEquals(EnumDocumentType.PRESENTATION, doc2.getDocumentType(), "document[2].documentType");

        assertNull(doc2.getSourceLocation(), "document[2].sourceLocation");
        assertEquals(doc2.getNotesLocation().getPath(), "UVWXYZ", "document[2].notesLocation");

        assertEquals(EnumReadingStatus.NOT_STARTED, doc2.getReadingStatus(), "document[2].readingStatus");
        assertEquals(List.of("too", "many", "tags"), doc2.getTags(), "document[2].tags");
    }

    void testCorrectContentsIndex_3(Document doc3) {
        assertEquals("Jonas", doc3.getAuthors().get(0).getFirstName(), "document[3].authors[0].firstname");
        assertEquals("Johnson", doc3.getAuthors().get(0).getLastName(), "document[3].authors[0].lastName");

        assertEquals("Title number Four", doc3.getTitle(), "document[3].title");
        assertEquals(Year.of(-1), doc3.getPublicationYear(), "document[3].publicationYear");
        assertEquals("Journal on Computer Science", doc3.getPublicationVenue(), "document[3].publicationVenue");
        assertEquals(33, doc3.getPageCount(), "document[3].pageCount");
        assertEquals(EnumDocumentType.COURSE_TEXT, doc3.getDocumentType(), "document[3].documentType");

        assertEquals(doc3.getSourceLocation().getPath(), "LMN123", "document[3].sourceLocation");
        assertEquals(doc3.getNotesLocation().getPath(), "IJK654", "document[3].notesLocation");

        assertEquals(EnumReadingStatus.IN_PROGRESS, doc3.getReadingStatus(), "document[3].readingStatus");
        assertEquals(List.of(), doc3.getTags(), "document[3].tags");
    }

    void testCorrectContentsIndex_4(Document doc4) {
        assertNull(doc4.getAuthors().get(0).getFirstName(), "document[4].authors[0].firstname");
        assertEquals("2nd Name", doc4.getAuthors().get(0).getLastName(), "document[4].authors[0].lastName");

        assertEquals("Final Title", doc4.getTitle(), "document[3].title");
        assertNull(doc4.getPublicationYear(), "document[3].publicationYear");
        assertEquals("Journal on Medicine", doc4.getPublicationVenue(), "document[3].publicationVenue");
        assertEquals(99999, doc4.getPageCount(), "document[3].pageCount");
        assertEquals(EnumDocumentType.POSTER, doc4.getDocumentType(), "document[3].documentType");

        assertNull(doc4.getSourceLocation(), "document[4].sourceLocation");
        assertNull(doc4.getNotesLocation(), "document[4].notesLocation");

        assertEquals(EnumReadingStatus.ON_HOLD, doc4.getReadingStatus(), "document[3].readingStatus");
        assertEquals(List.of("tag1", "2nd tag"), doc4.getTags(), "document[3].tags");
    }

    void testCorrectContentsIndex_5(Document doc5) {
        assertEquals("Some", doc5.getAuthors().get(0).getFirstName(), "document[4].authors[0].firstname");
        assertEquals("Author", doc5.getAuthors().get(0).getLastName(), "document[4].authors[0].lastName");

        assertEquals("Last title", doc5.getTitle(), "document[4].title");
        assertNull(doc5.getPublicationYear(), "document[4].publicationYear");
        assertEquals("Journal X", doc5.getPublicationVenue(), "document[4].publicationVenue");
        assertEquals(36, doc5.getPageCount(), "document[4].pageCount");
        assertEquals(EnumDocumentType.OTHER, doc5.getDocumentType(), "document[4].documentType");

        assertNull(doc5.getSourceLocation(), "document[5].sourceLocation");
        assertNull(doc5.getNotesLocation(), "document[5].notesLocation");

        assertEquals(EnumReadingStatus.FINISHED, doc5.getReadingStatus(), "document[4].readingStatus");
        assertEquals(List.of("A", "B", "C", "D"), doc5.getTags(), "document[4].tags");
    }

    /**
     * Test that an exception is thrown if there is an unknown field.
     */
    @Test
    void testUnknownField() throws Exception
    {
        IOException thrown = Assertions.assertThrows(IOException.class, () -> {
            DocumentIndexReader reader = new DocumentIndexReader(getFile("redundantField.json"));
            DocumentIndex index = reader.read();
        });
    }

    /**
     * Test that IOException is thrown in case there is a syntax error.
     */
    @Test
    void testParseError() throws IOException, URISyntaxException {
        getFile("incorrectSyntax.json");

        IOException thrown = Assertions.assertThrows(IOException.class, () -> {
            DocumentIndexReader reader = new DocumentIndexReader(getFile("incorrectSyntax.json"));
            DocumentIndex index = reader.read();
        });
    }

    /**
     * Test that IOException is thrown in case the file does not exist.
     */
    @Test()
    void testFileNotFoundError() {
        IOException thrown = Assertions.assertThrows(IOException.class, () -> {
            DocumentIndexReader reader = new DocumentIndexReader(Path.of("./file_that_does_definitely_not_exist.json"));
            DocumentIndex index = reader.read();
        });
    }
}
