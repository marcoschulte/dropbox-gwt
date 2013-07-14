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
package de.voot.dropboxgwt.client;

import java.util.LinkedList;
import java.util.List;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

import de.voot.dropboxgwt.client.overlay.ApiError;
import de.voot.dropboxgwt.client.overlay.ArrayBuffer;
import de.voot.dropboxgwt.client.overlay.Stat;

public class DropboxWrapper {

	private JavaScriptObject dropboxClient;
	private List<ProgressCallback> progressCallbacks;

	/**
	 * See
	 * https://dl-web.dropbox.com/spa/pjlfdak1tmznswp/api_keys.js/public/index
	 * .html to generate encoded api key
	 * 
	 * @param encodedAPIKey
	 */
	public DropboxWrapper(String encodedAPIKey) {
		dropboxClient = instantiateClientObject(encodedAPIKey);
		progressCallbacks = new LinkedList<ProgressCallback>();
		installProgressListener();
	}

	public void addProgressListener(ProgressCallback progressCallback) {
		progressCallbacks.add(progressCallback);
	}

	private void onProgress(String path, float progress) {
		for (ProgressCallback callback : progressCallbacks) {
			callback.onProgress(path, progress);
		}
	}

	private native void installProgressListener() /*-{
		var client = this.@de.voot.dropboxgwt.client.DropboxWrapper::dropboxClient;
		var wrapper = this;

		client.onXhr
				.addListener(function(Xhr) {
					var path = client.dropboxgwt ? client.dropboxgwt.path : undefined;
					Xhr.xhr
							.addEventListener(
									"progress",
									function(path) {
										return function(oEvent) {
											if (oEvent.lengthComputable) {
												var progress = oEvent.loaded
														/ oEvent.total;
												$entry(wrapper.@de.voot.dropboxgwt.client.DropboxWrapper::onProgress(Ljava/lang/String;F)(path, progress));
											} else {
												// Unable to compute progress information since the total size is unknown
											}
										}
									}(path), false);
				});
	}-*/;

	private native JavaScriptObject instantiateClientObject(String encodedAPIKey) /*-{
		var Dropbox = $wnd.Dropbox;
		var client = new $wnd.Dropbox.Client({
			key : encodedAPIKey,
			sandbox : true
		});

		return client;
	}-*/;

	public native void signOut()/*-{
		var client = this.@de.voot.dropboxgwt.client.DropboxWrapper::dropboxClient;
		client.signOut();
	}-*/;

	/**
	 * Tests if already connected or tries to connect via url params or local
	 * storage/cached data
	 * 
	 * @param callback
	 */
	public native void isAuthenticated(Callback<Boolean, ApiError> callback) /*-{
		var client = this.@de.voot.dropboxgwt.client.DropboxWrapper::dropboxClient;

		client.authDriver(new $wnd.Dropbox.Drivers.Redirect({
			rememberUser : true,
			useQuery : true
		}));

		client
				.authenticate(
						{
							interactive : false
						},
						function(error, client) {
							if (error) {
								$entry(callback.@com.google.gwt.core.client.Callback::onFailure(Ljava/lang/Object;)(error));
								return;
							}
							if (client.isAuthenticated()) {
								var result = @java.lang.Boolean::TRUE;
							} else {
								var result = @java.lang.Boolean::FALSE;
							}
							$entry(callback.@com.google.gwt.core.client.Callback::onSuccess(Ljava/lang/Object;)(result));
						});
	}-*/;

	/**
	 * Tries to connect. If storage params are cached callback will be called
	 * directly, else a redirect happens and callback is never called. In this
	 * case use isAuthenticated when returning to this app to finish
	 * authentication process.
	 * 
	 * @param callback
	 */
	public native void authenticate(boolean rememberUser, Callback<Void, ApiError> callback) /*-{
		var client = this.@de.voot.dropboxgwt.client.DropboxWrapper::dropboxClient;

		client.authDriver(new $wnd.Dropbox.Drivers.Redirect({
			rememberUser : rememberUser,
			useQuery : true
		}));

		client
				.authenticate(
						{
							interactive : false
						},
						function(error, client) {
							if (error) {
								$entry(callback.@com.google.gwt.core.client.Callback::onFailure(Ljava/lang/Object;)(error));
								return;
							}
							if (!client.isAuthenticated()) {
								client
										.authenticate(function(error, client) {
											if (error) {
												$entry(callback.@com.google.gwt.core.client.Callback::onFailure(Ljava/lang/Object;)(error));
												return;
											}
											$entry(callback.@com.google.gwt.core.client.Callback::onSuccess(Ljava/lang/Object;)(null));
										});
							} else {
								$entry(callback.@com.google.gwt.core.client.Callback::onSuccess(Ljava/lang/Object;)(null));
							}
						});
	}-*/;

	public native void metadata(String path, Callback<Stat, ApiError> callback) /*-{
		var client = this.@de.voot.dropboxgwt.client.DropboxWrapper::dropboxClient;

		client
				.metadata(
						path,
						function(error, stat) {
							if (error) {
								$entry(callback.@com.google.gwt.core.client.Callback::onFailure(Ljava/lang/Object;)(error));
								return;
							}
							$entry(callback.@com.google.gwt.core.client.Callback::onSuccess(Ljava/lang/Object;)(stat));
						});
	}-*/;

	public native void readdir(String path, Callback<JsArray<Stat>, ApiError> callback) /*-{
		var client = this.@de.voot.dropboxgwt.client.DropboxWrapper::dropboxClient;

		client
				.readdir(
						path,
						function(error, entries, stat, stats) {
							if (error) {
								$entry(callback.@com.google.gwt.core.client.Callback::onFailure(Ljava/lang/Object;)(error));
								return;
							}
							$entry(callback.@com.google.gwt.core.client.Callback::onSuccess(Ljava/lang/Object;)(stats));
						});
	}-*/;

	public native void readFile(String path, Callback<ArrayBuffer, ApiError> callback) /*-{
		var client = this.@de.voot.dropboxgwt.client.DropboxWrapper::dropboxClient;

		// Used to inject the path into progress listener. Not thread-safe, but there is not really a better way.
		client.dropboxgwt = {path: path};

		client
				.readFile(
						path,
						{
							arrayBuffer : true
						},
						function(error, data, stat, rangeInfo) {
							if (error) {
								$entry(callback.@com.google.gwt.core.client.Callback::onFailure(Ljava/lang/Object;)(error));
								return;
							}
							$entry(callback.@com.google.gwt.core.client.Callback::onSuccess(Ljava/lang/Object;)(data));
						});
	}-*/;
}
