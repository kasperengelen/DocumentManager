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

import main.model.document.Author;
import main.model.document.Document;

import java.net.URI;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

/**
 * Class that provides formatting for the fields of a {@link Document} object.
 */
public class DocumentView
{
    private final Document document;

    /**
     * Constructor.
     *
     * @param document The document that will be viewed.
     */
    public DocumentView(Document document) {
        this.document = document;
    }

    /**
     * Method to format an {@link Author} in string format.
     */
    private String formatAuthor(Author author) {
        if(author.getFirstName() == null) {
            return author.getLastName();
        } else {
            return author.getFirstName() + " " + author.getLastName();
        }
    }

    /**
     * Retrieve a string that contains the names of the authors.
     */
    public String getAuthors()
    {
        return this.document.getAuthors().stream().map(this::formatAuthor).collect(Collectors.joining("; "));
    }

    /**
     * Retrieve the title.
     */
    public String getTitle()
    {
        return this.document.getTitle();
    }

    /**
     * Retrieve a string that represents the year of publication. The year will be represented as an integer, suffixed
     * by "BC" in case the year is before the year 0.
     */
    public String getPublicationYear()
    {
        Year publicationYear = this.document.getPublicationYear();

        if(publicationYear == null) {
            return "N/A";
        } else {
            String pattern = "yyyy";

            // if this is before the year one, add "BC" after it.
            if(publicationYear.isBefore(Year.of(1))) {
                pattern += " G";
            }

            return publicationYear.format(DateTimeFormatter.ofPattern(pattern)).replaceFirst("^0*", "");
        }
    }

    /**
     * Retrieve a string that contains the name of the publication venue.
     */
    public String getPublicationVenue()
    {
        return this.document.getPublicationVenue();
    }

    /**
     * Retrieve a string that contains the number of pages of the document.
     */
    public String getPageCount()
    {
        return Integer.toString(this.document.getPageCount());
    }

    /**
     * Retrieve the name of the document type.
     */
    public String getDocumentType() {
        return switch(this.document.getDocumentType()) {
            case BOOK -> "Book";
            case PAPER -> "Paper";
            case PRESENTATION -> "Presentation";
            case COURSE_TEXT -> "Course text";
            case POSTER -> "Poster";
            case OTHER -> "Other";
        };
    }

    /**
     * Retrieve the reading status.
     */
    public String getReadingStatus()
    {
        return switch(this.document.getReadingStatus()) {

            case NOT_STARTED -> "Not yet started";
            case IN_PROGRESS -> "In progress";
            case FINISHED -> "Finished";
            case ON_HOLD -> "On hold";
        };
    }

    /**
     * Retrieve a URI that points to the location of the contents of the document.
     */
    public URI getSourceFileLink() {
        return this.document.getSourceLocation();
    }

    /**
     * Retrieve a URI that points to the location with notes about the document.
     */
    public URI getNotesFileLink() {
        return this.document.getNotesLocation();
    }

}
