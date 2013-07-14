/*
  	Copyright (C) 2013 Marco Schulte

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.voot.dropboxgwt.client.overlay;

import com.google.gwt.core.client.JavaScriptObject;

public class Stat extends JavaScriptObject {
	protected Stat() {
	}

	public final native boolean isFolder() /*-{
		return this.isFolder;
	}-*/;

	public final native String getMimeType() /*-{
		return this.mimeType;
	}-*/;

	public final native String getModified() /*-{
		return this.modifiedAt;
	}-*/;

	public final native String getPath() /*-{
		return this._json.path;
	}-*/;

	public final native int getSize() /*-{
		return this.size;
	}-*/;
}
