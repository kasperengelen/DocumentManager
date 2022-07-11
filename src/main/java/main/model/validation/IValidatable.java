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

import java.util.List;

/**
 * Interface for objects that can be validated.
 */
public interface IValidatable
{
    /**
     * Validate the object.
     *
     * @return A list of error messages. Each error message contains a reason why the object is invalid.
     */
    List<String> validate();
}
