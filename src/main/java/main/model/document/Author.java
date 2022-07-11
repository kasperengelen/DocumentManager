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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import main.model.validation.IValidatable;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents an author.
 */
public class Author implements IValidatable
{
    @JsonProperty("firstName")
    private final String firstName;

    @JsonProperty("lastName")
    private final String lastName;

    /**
     * Constructor. Whitespace will be stripped.
     *
     * @param firstName The first name of the author.
     * @param lastName The last name of the author.
     */
    @JsonCreator
    public Author(
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName
    ) {
        this.firstName = (firstName != null) ? firstName.strip() : null;
        this.lastName = (lastName != null) ? lastName.strip() : null;
    }

    /**
     * Retrieve the first name of the author.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Retrieve the lastname of the author.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Validate the author. The following things will be checked:
     *  - the last name must not be null,
     *  - the last name must not be an empty string.
     */
    @Override
    public List<String> validate()
    {
        List<String> retval = new ArrayList<>();

        if(lastName == null) {
            retval.add("Lastname must not be 'null'.");
        } else if (lastName.isEmpty()) {
            retval.add("Lastname must not be empty.");
        }

        return retval;
    }
}
