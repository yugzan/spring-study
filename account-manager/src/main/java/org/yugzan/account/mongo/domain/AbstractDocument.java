package org.yugzan.account.mongo.domain;

import java.math.BigInteger;

import org.springframework.data.annotation.Id;

/**
 * @author Oliver Gierke
 * 
 */
public class AbstractDocument {

	@Id
	private BigInteger id;

	/**
	 * Returns the identifier of the document.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id.toString();
	}

	/* 
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (this.id == null || obj == null || !(this.getClass().equals(obj.getClass()))) {
			return false;
		}

		AbstractDocument that = (AbstractDocument) obj;

		return this.id.equals(new BigInteger(that.getId()));
	}

	/* 
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}
}

