package de.voot.dropboxgwt.client.overlay;

import com.google.gwt.core.client.JavaScriptObject;

public class ApiError extends JavaScriptObject {
	protected ApiError() {
	}

	public final native String getMethod() /*-{
		return this.method;
	}-*/;

	public final native String getResponseText() /*-{
		return this.responseText;
	}-*/;

	public final native int getStatus() /*-{
		return this.status;
	}-*/;

	public final native String getUrl() /*-{
		return this.url;
	}-*/;

}
