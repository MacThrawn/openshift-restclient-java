package com.openshift.internal.restclient.model;

import com.openshift.restclient.model.IObjectReference;

public class ObjectReference implements IObjectReference {

	private final String kind;
	private final String name;
	private final String namespace;

	public ObjectReference(String kind, String name, String namespace) {
		this.kind = kind;
		this.name = name;
		this.namespace = namespace;
	}

	public ObjectReference(String kind, String name) {
		this(kind, name, null);
	}

	/* (non-Javadoc)
	 * @see com.openshift.internal.restclient.model.IObjectReference#getKind()
	 */
	@Override
	public String getKind() {
		return kind;
	}

	/* (non-Javadoc)
	 * @see com.openshift.internal.restclient.model.IObjectReference#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see com.openshift.internal.restclient.model.IObjectReference#getNamespace()
	 */
	@Override
	public String getNamespace() {
		return namespace;
	}

}
