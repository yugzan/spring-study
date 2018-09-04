package org.yugzan.mongo.core;

import java.util.Collection;
import java.util.Objects;
import com.google.common.collect.Lists;

public class ResourceCollection<T> {

	private Collection<T> resources = Lists.newArrayList();

	public ResourceCollection() {
	}

	public ResourceCollection(Collection<T> resources) {
		this.resources = Objects.requireNonNull(resources, "rsources is null");
	}

	public Collection<T> getResources() {
		return resources;
	}

	public void setResources(Collection<T> resources) {
		this.resources = resources;
	}

	@Override
	public String toString() {
		return "ResourceCollection [resources=" + resources + "]";
	}

}
