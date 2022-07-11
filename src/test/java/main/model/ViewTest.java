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
import main.view.DocumentTableModel;
import main.view.DocumentView;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the {@link main.view.DocumentTableModel} and {@link main.view.DocumentView} classes.
 */
public class ViewTest
{
    Document createDocument0() throws URISyntaxException {
        Document doc = new Document();

        doc.setAuthors(List.of(
                new Author("Peter", "Selie"),
                new Author("John", "Doe")
        ));
        doc.setTitle("Some title");
        doc.setPublicationYear(Year.of(2009));
        doc.setPublicationVenue("Important conference");
        doc.setPageCount(50);
        doc.setDocumentType(EnumDocumentType.BOOK);
        doc.setReadingStatus(EnumReadingStatus.FINISHED);
        doc.setTags(List.of("tag1", "tag2", "third tag"));

        return doc;
    }

    Document createDocument1() throws URISyntaxException {
        Document doc = new Document();

        doc.setAuthors(List.of(
                new Author("The", "Name")
        ));
        doc.setTitle("Another title");
        doc.setPublicationYear(Year.of(1));
        doc.setPublicationVenue("International conference on nonsense");
        doc.setPageCount(1);
        doc.setDocumentType(EnumDocumentType.PAPER);
        doc.setReadingStatus(EnumReadingStatus.FINISHED);
        doc.setTags(List.of());

        doc.setSourceLocation(new URI("ABCDEF"));

        return doc;
    }

    Document createDocument2() throws URISyntaxException {
        Document doc = new Document();

        doc.setAuthors(List.of(
                new Author("Jane", "Doe")
        ));
        doc.setTitle("Third title");
        doc.setPublicationYear(Year.of(0));
        doc.setPublicationVenue("prestigious journal");
        doc.setPageCount(35);
        doc.setDocumentType(EnumDocumentType.PRESENTATION);
        doc.setReadingStatus(EnumReadingStatus.NOT_STARTED);
        doc.setTags(List.of("too", "many", "tags"));

        doc.setNotesLocation(new URI("UVWXYZ"));

        return doc;
    }

    Document createDocument3() throws URISyntaxException {
        Document doc = new Document();

        doc.setAuthors(List.of(
                new Author("Jonas", "Johnson")
        ));
        doc.setTitle("Title number Four");
        doc.setPublicationYear(Year.of(-1));
        doc.setPublicationVenue("Journal on Computer Science");
        doc.setPageCount(33);
        doc.setDocumentType(EnumDocumentType.COURSE_TEXT);
        doc.setReadingStatus(EnumReadingStatus.IN_PROGRESS);
        doc.setTags(List.of());

        doc.setSourceLocation(new URI("LMN123"));
        doc.setNotesLocation(new URI("IJK654"));

        return doc;
    }

    Document createDocument4()
    {
        Document doc = new Document();

        doc.setAuthors(List.of(
                new Author(null, "2nd Name")
        ));
        doc.setTitle("Final Title");
        doc.setPublicationYear(null);
        doc.setPublicationVenue("Journal on Medicine");
        doc.setPageCount(99999);
        doc.setDocumentType(EnumDocumentType.POSTER);
        doc.setReadingStatus(EnumReadingStatus.ON_HOLD);
        doc.setTags(List.of("tag1", "2nd tag"));

        return doc;
    }

    Document createDocument5()
    {
        Document doc = new Document();

        doc.setAuthors(List.of(
                new Author("Some", "Author")
        ));
        doc.setTitle("Last title");
        doc.setPublicationYear(null);
        doc.setPublicationVenue("Journal X");
        doc.setPageCount(36);
        doc.setDocumentType(EnumDocumentType.OTHER);
        doc.setReadingStatus(EnumReadingStatus.FINISHED);
        doc.setTags(List.of("A", "B", "C", "D"));

        return doc;
    }

    @Test
    void testTableModelAndDocumentView() throws Exception {
        List<Document> docList = List.of(
                createDocument0(), createDocument1(),
                createDocument2(), createDocument3(),
                createDocument4(), createDocument5()
        );

        List<DocumentView> docViews = docList.stream().map(DocumentView::new).collect(Collectors.toList());

        DocumentTableModel tableModel = new DocumentTableModel(docViews);

        assertEquals(9, tableModel.getColumnCount(), "tableModel.getColumnCount");

        assertEquals("Authors", tableModel.getColumnName(0), "tableModel.columnNames[0]");
        assertEquals("Year", tableModel.getColumnName(1), "tableModel.columnNames[1]");
        assertEquals("Title", tableModel.getColumnName(2), "tableModel.columnNames[2]");
        assertEquals("Publication Venue", tableModel.getColumnName(3), "tableModel.columnNames[3]");
        assertEquals("Page count", tableModel.getColumnName(4), "tableModel.columnNames[4]");
        assertEquals("Document type", tableModel.getColumnName(5), "tableModel.columnNames[5]");
        assertEquals("Reading status", tableModel.getColumnName(6), "tableModel.columnNames[6]");
        assertEquals("Source", tableModel.getColumnName(7), "tableModel.columnNames[7]");
        assertEquals("Notes", tableModel.getColumnName(8), "tableModel.columnNames[8]");

        assertEquals(6, tableModel.getRowCount(), "tableModel.getRowCount");


        List<List<Object>> expectedData = List.of(
                Arrays.asList(new Object[] {"Peter Selie; John Doe", "2009", "Some title", "Important conference", "50", "Book", "Finished", null, null}),
                Arrays.asList(new Object[] {"The Name", "1", "Another title", "International conference on nonsense", "1", "Paper", "Finished", new URI("ABCDEF"), null}),
                Arrays.asList(new Object[] {"Jane Doe", "1 BC", "Third title", "prestigious journal", "35", "Presentation", "Not yet started", null, new URI("UVWXYZ")}),
                Arrays.asList(new Object[] {"Jonas Johnson", "2 BC", "Title number Four", "Journal on Computer Science", "33", "Course text", "In progress", new URI("LMN123"), new URI("IJK654")}),
                Arrays.asList(new Object[] {"2nd Name", "N/A", "Final Title", "Journal on Medicine", "99999", "Poster", "On hold", null, null}),
                Arrays.asList(new Object[] {"Some Author", "N/A", "Last title", "Journal X", "36", "Other", "Finished", null, null})
        );

        for (int col = 0; col < tableModel.getColumnCount(); col++) {
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                assertEquals(expectedData.get(row).get(col), tableModel.getValueAt(row, col), "rows[%d].columns[%d]".formatted(row, col));
            }
        }
    }
}
