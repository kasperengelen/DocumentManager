/**
 *   Copyright (C) 2022  Kasper Engelen
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.

 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.

 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */


package main.model.index;

import com.fasterxml.jackson.annotation.JsonSetter;
import main.model.document.Document;
import main.model.validation.IValidatable;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains a list of documents and a directory in which document files are stored.
 */
public class DocumentIndex implements IValidatable {

    private List<Document> documents = new ArrayList<>();

    /**
     * Retrieve the documents that are stored in the index.
     */
    @JsonSetter("documents")
    public List<Document> getDocumentList()
    {
        return documents;
    }

    /**
     * Set the documents that are stored in the index.
     */
    @JsonSetter("documents")
    public void setDocumentList(List<Document> documents) {
        this.documents = documents;
    }

    /**
     * Validate the document index. The following things will be checked:
     *  - all the documents must be valid.
     */
    @Override
    public List<String> validate() {
        List<String> retval = new ArrayList<>();

        for (int i = 0; i < this.documents.size(); i++) {
            Document doc = this.documents.get(i);
            List<String> errorMsgs = doc.validate();

            for (String errorMsg : errorMsgs) {
                retval.add("Invalid document at index #%d: %s".formatted(i, errorMsg));
            }
        }

        return retval;
    }
}
