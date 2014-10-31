package com.synergy.model;

public abstract class AbstractPersistent implements Persistent {

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			return true;
		}

		if (this.getClass().isAssignableFrom(obj.getClass())) {
			if (getId().equals(((AbstractPersistent) obj).getId())) {
				return true;
			}
		}
		return false;
	}

}
