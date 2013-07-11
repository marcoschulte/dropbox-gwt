package de.voot.dropboxgwt.client.overlay;

import com.google.gwt.core.client.JavaScriptObject;

public class ArrayBuffer extends JavaScriptObject {
	protected ArrayBuffer() {
	}

	public final native int size()/*-{
		return this.byteLength;
	}-*/;

	public final native int getByte(int pos)/*-{
		if (!this.view) {
			this.view = new Uint8Array(this);
		}
		return this.view[pos];
	}-*/;
}
