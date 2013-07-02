/**
 * File ./src/main/java/de/lemo/dms/db/miningDBclass/abstractions/IRatedObject.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2013
 * Leonard Kappe, Andreas Pursian, Sebastian Schwarzrock, Boris Wenzlaff
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/abstractions/IRatedObject.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.miningDBclass.abstractions;

/**
 * Interface for rated objects
 * @author Boris Wenzlaff
 * @author Leonard Kappe
 * @author Sebastian Schwarzrock
 */
public interface IRatedObject extends ILearningObject {

	Double getMaxGrade();

	Long getPrefix();

}
