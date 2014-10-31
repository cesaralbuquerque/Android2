package com.synergy.model;

import java.io.Serializable;

public interface Persistent extends Serializable {

	Long getId();

	void setId(Long id);
}
