package com.deepoove.swagger.diff.compare;

import java.util.ArrayList;
import java.util.List;

import com.deepoove.swagger.diff.model.ElProperty;

import io.swagger.models.properties.Property;

public class AbstractPropertyDiff {

	private List<ElProperty> increased;
	private List<ElProperty> missing;
	private List<ElProperty> changed;

	Property oldDedinitions;
	Property newDedinitions;

	public AbstractPropertyDiff(Property left, Property right) {
		increased = new ArrayList<>();
		missing = new ArrayList<>();
		changed = new ArrayList<>();
		this.oldDedinitions = left;
		this.newDedinitions = right;
	}

	public AbstractPropertyDiff diff() {
		if ((oldDedinitions == null && newDedinitions == null) ) {
			return this;
		}
		if (newDedinitions == null) {
			ElProperty elProperty = new ElProperty();
			elProperty.setEl(String.format("response.%s", oldDedinitions.getType()));
			elProperty.setProperty(oldDedinitions);
			this.missing.add(elProperty);
			return this;
		}
		if (oldDedinitions == null) {
			ElProperty elProperty = new ElProperty();
			elProperty.setEl(String.format("response.%s", newDedinitions.getType()));
			elProperty.setProperty(newDedinitions);
			this.increased.add(elProperty);
			return this;
		}
		if(oldDedinitions.equals(newDedinitions)){
            return this;
        }
		ElProperty elProperty = new ElProperty();
		elProperty
				.setEl(String.format("response.%s => response.%s", oldDedinitions.getType(), newDedinitions.getType()));
		elProperty.setProperty(newDedinitions);
		this.increased.add(elProperty);
		return this;
	}

	private String buildElString(String parentEl, String propName) {
		return null == parentEl ? propName : (parentEl + "." + propName);
	}

	public List<ElProperty> getMissing() {
		return missing;
	}

	public void setMissing(List<ElProperty> missing) {
		this.missing = missing;
	}

	public List<ElProperty> getChanged() {
		return changed;
	}

	public void setChanged(List<ElProperty> changed) {
		this.changed = changed;
	}

	public List<ElProperty> getIncreased() {
		return increased;
	}

	public void setIncreased(List<ElProperty> increased) {
		this.increased = increased;
	}
}
