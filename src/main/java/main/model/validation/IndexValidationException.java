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

package main.model.validation;

import java.io.IOException;
import java.util.List;

/**
 * Exception that is thrown when a document index is found to be invalid.
 */
public class IndexValidationException extends IOException
{
    private final List<String> errorMsgs;

    /**
     * Constructor.
     *
     * @param errorMsgs A list of error messages.
     */
    public IndexValidationException(List<String> errorMsgs) {
        this.errorMsgs = errorMsgs;
    }

    /**
     * Retrieve a list of error messages.
     */
    public List<String> getErrorMessages()
    {
        return this.errorMsgs;
    }
}
