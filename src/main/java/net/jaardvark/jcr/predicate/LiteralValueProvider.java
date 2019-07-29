package net.jaardvark.jcr.predicate;

import javax.jcr.Item;
import javax.jcr.Value;

public class LiteralValueProvider implements ValueProvider {

	protected Value jcrValue = null;
	protected Value[] jcrValues = null;
	protected boolean multiple = false;

	public LiteralValueProvider(Value jcrValue) {
		this.jcrValue = jcrValue;
	}
	public LiteralValueProvider(Value[] jcrValues) {
		this.jcrValues = jcrValues;
		this.multiple = true;
	}

	@Override
	public Value getValue(Item i) {
		return jcrValue;
	}

	@Override
	public boolean isMultiple(Item item) {
		return multiple;
	}

	@Override
	public Value[] getValues(Item item) {
		return jcrValues;
	}

}
