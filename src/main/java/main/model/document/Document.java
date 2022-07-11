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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.YearDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.YearSerializer;
import main.model.validation.IValidatable;

import java.net.URI;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Contains metadata about a document as well as paths to a source file and notes file.
 */
public class Document implements IValidatable
{
    private List<Author> authors = new ArrayList<>();

    private String title;

    private Year publicationYear = null;

    private String publicationVenue = null;

    private int pageCount = 0;

    private EnumDocumentType documentType = null;

    private URI sourceLocation = null;

    private URI notesLocation = null;

    private EnumReadingStatus readingStatus = null;

    private List<String> tags = new ArrayList<>();

    /**
     * Retrieve a list of authors that are associated with the document.
     */
    @JsonGetter("authors")
    public List<Author> getAuthors() {
        return this.authors;
    }

    /**
     * Setter for the authors.
     */
    @JsonSetter("authors")
    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    /**
     * The title of the document.
     */
    @JsonGetter("title")
    public String getTitle()
    {
        return this.title;
    }

    /**
     * Setter for the title. This will strip any whitespace from the title.
     */
    @JsonSetter("title")
    public void setTitle(String title) {
        if(title != null) {
            this.title = title.strip();
        }
    }

    /**
     * Retrieve the year that the document was published.
     */
    @JsonSerialize(using = YearSerializer.class)
    @JsonGetter("publicationYear")
    public Year getPublicationYear()
    {
        return this.publicationYear;
    }

    /**
     * Set the publication year.
     */
    @JsonDeserialize(using = YearDeserializer.class)
    @JsonSetter("publicationYear")
    public void setPublicationYear(Year publicationYear)
    {
        this.publicationYear = publicationYear;
    }

    /**
     * Retrieve the publication venue. Examples are: publisher, conference, journal, etc.
     */
    @JsonGetter("publicationVenue")
    public String getPublicationVenue()
    {
        return this.publicationVenue;
    }

    /**
     * Set the publication venue.
     */
    @JsonSetter("publicationVenue")
    public void setPublicationVenue(String publicationVenue) {
        if(publicationVenue != null) {
            this.publicationVenue = publicationVenue.strip();
        }
    }

    /**
     * Retrieve the number of pages of the document.
     */
    @JsonGetter("pageCount")
    public int getPageCount()
    {
        return this.pageCount;
    }

    /**
     * Set the number of pages of the document.
     */
    @JsonSetter("pageCount")
    public void setPageCount(int pageCount)
    {
        this.pageCount = pageCount;
    }

    @JsonGetter("documentType")
    public EnumDocumentType getDocumentType()
    {
        return this.documentType;
    }

    @JsonSetter("documentType")
    public void setDocumentType(EnumDocumentType documentType)
    {
        this.documentType = documentType;
    }

    /**
     * Retrieve the location of the source of the document (i.e. the filename or URL of a PDF file, description page, etc.)
     */
    @JsonGetter("sourceLocation")
    public URI getSourceLocation()
    {
        return this.sourceLocation;
    }

    /**
     * Set the location of the source of the document (i.e. the filename or URL of a PDF file, description page, etc.)
     */
    @JsonSetter("sourceLocation")
    public void setSourceLocation(URI sourceLocation) {
        this.sourceLocation = sourceLocation;
    }

    /**
     * Retrieve the location of the notes that the reader made about the document (i.e. a text file, note-making application, etc.)
     */
    @JsonGetter("notesLocation")
    public URI getNotesLocation() {
        return notesLocation;
    }

    /**
     * Set the location of the notes.
     */
    @JsonSetter("notesLocation")
    public void setNotesLocation(URI notesLocation) {
        this.notesLocation = notesLocation;
    }

    @JsonGetter("readingStatus")
    public EnumReadingStatus getReadingStatus()
    {
        return this.readingStatus;
    }

    @JsonSetter("readingStatus")
    public void setReadingStatus(EnumReadingStatus readingStatus)
    {
        if(readingStatus != null) {
            this.readingStatus = readingStatus;
        }
    }

    /**
     * Retrieve the tags that are associated with the document.
     */
    @JsonGetter("tags")
    public List<String> getTags()
    {
        return this.tags;
    }

    /**
     * Set the tags. Null values will be filtered out. All tags will be stripped of whitespace. Empty tags will be
     * filtered out.
     */
    @JsonSetter("tags")
    public void setTags(List<String> tags) {
        if(tags != null) {
            this.tags = tags.stream()
                    .filter(Objects::nonNull) // remove all null values
                    .map(String::strip) // strip whitespace
                    .filter(Predicate.not(String::isEmpty)) // remove all empty strings
                    .collect(Collectors.toList());
        }
    }

    /**
     * Validate the document. The following things will be checked:
     *  - the list of authors must not be empty,
     *  - the authors must all be valid,
     *  - the title must not be null or an empty string,
     *  - the page count must be bigger than zero,
     *  - the document type must not be null,
     *  - the reading status must not be null.
     */
    @Override
    public List<String> validate() {
        List<String> retval = new ArrayList<>();

        for (int i = 0; i < this.authors.size(); i++) {
            Author author = this.authors.get(i);

            for (String errorMsg : author.validate()) {
                retval.add("Invalid author at index #%d: %s".formatted(i, errorMsg));
            }
        }

        if(this.authors.isEmpty()) {
            retval.add("List of authors must not be empty.");
        }

        if(this.title == null || this.title.isEmpty()) {
            retval.add("Title must not be empty or null.");
        }

        if(this.pageCount <= 0) {
            retval.add("Page count must be greater than zero.");
        }

        if(this.documentType == null) {
            retval.add("Document type must not be null.");
        }

        if(this.readingStatus == null) {
            retval.add("Reading status must not be null.");
        }

        return retval;
    }
}
