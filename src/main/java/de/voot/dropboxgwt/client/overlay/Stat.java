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
