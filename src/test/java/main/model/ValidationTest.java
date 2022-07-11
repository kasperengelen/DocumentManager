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

import main.model.document.*;
import main.model.index.DocumentIndex;
import main.model.validation.IValidatable;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.time.Year;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests of the classes that implement {@link IValidatable#validate()}.
 */
public class ValidationTest
{
    /**
     * Retrieve a Path object that points to the file with the specified filename located in the resources folder.
     */
    public static Path getFile(String filename) throws URISyntaxException {
        return Path.of(DocumentIndexReaderTest.class.getClassLoader().getResource(filename).toURI());
    }

    private Document getDoc0() throws URISyntaxException {
        Document retval = new Document();

        retval.setAuthors(List.of(
                new Author("Peter", ""),
                new Author("John", null)
        ));

        retval.setTitle("");
        retval.setPublicationYear(Year.of(2009));
        retval.setPublicationVenue("Important conference");
        retval.setPageCount(-1);
        retval.setDocumentType(EnumDocumentType.BOOK);
        retval.setReadingStatus(null);
        retval.setTags(List.of("tag1", "tag2", "third tag"));

        retval.setSourceLocation(new URI("LMN123"));
        retval.setNotesLocation(new URI("IJK654"));

        return retval;
    }

    public Document getDoc1()
    {
        Document retval = new Document();

        retval.setAuthors(List.of());
        retval.setTitle(null);
        retval.setPublicationYear(Year.of(2009));
        retval.setPublicationVenue(null);
        retval.setPageCount(0);
        retval.setDocumentType(null);
        retval.setReadingStatus(EnumReadingStatus.FINISHED);
        retval.setTags(List.of("tag1", "tag2", "third tag"));

        return retval;
    }

    public DocumentIndex getDocumentIndex() throws URISyntaxException {
        DocumentIndex retval = new DocumentIndex();
        retval.setDocumentList(List.of(getDoc0(), getDoc1()));
        return retval;
    }

    @Test
    public void testDocumentValidation() throws URISyntaxException {
        DocumentIndex index = getDocumentIndex();

        List<String> errorMessages = index.validate();

        List<String> expectedMessages = List.of(
                "Invalid document at index #0: Invalid author at index #0: Lastname must not be empty.",
                "Invalid document at index #0: Invalid author at index #1: Lastname must not be 'null'.",
                "Invalid document at index #0: Title must not be empty or null.",
                "Invalid document at index #0: Page count must be greater than zero.",
                "Invalid document at index #0: Reading status must not be null.",
                "Invalid document at index #1: List of authors must not be empty.",
                "Invalid document at index #1: Title must not be empty or null.",
                "Invalid document at index #1: Page count must be greater than zero.",
                "Invalid document at index #1: Document type must not be null."
        );

        assertEquals(expectedMessages.size(), errorMessages.size(), "number of error messages");

        for (int i = 0; i < errorMessages.size(); i++) {
            assertEquals(expectedMessages.get(i), errorMessages.get(i), "message " + (i+1));
        }
    }
}
