package com.vidyo.rest.gateway;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Prefix implements Serializable {

	private static final long serialVersionUID = 5159058702368460962L;

	public static enum Direction {
		FROM_LEGACY("fromLegacy", 0),
		TO_LEGACY("toLegacy", 1);

		private final String value;
		private final int intValue;

		Direction(String value, int num) {
			this.value = value;
			this.intValue = num;
		}

		@JsonCreator
		public Direction forValue(String value) {
			if ("toLegacy".equals(value)) {
				return Direction.TO_LEGACY;
			} else if ("fromLegacy".equals(value)) {
				return Direction.FROM_LEGACY;
			} else {
				throw new IllegalArgumentException(("Invalid direction: " + value));
			}
		}

		@JsonValue
		public String toValue() {
			return this.value;
		}

		public int getIntValue() {
			return this.intValue;
		}
	}

	private String name;
	private Direction direction;

	public Prefix() {
		// default empty constructor is required
	}

	public Prefix(String name, Direction direction) {
		setName(name);
		setDirection(direction);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null) {
			throw new IllegalArgumentException(("Invalid prefix name: cannot be null"));
		}
		this.name = name;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		if (direction == null) {
			throw new IllegalArgumentException(("Invalid prefix direction: cannot be null"));
		}
		this.direction = direction;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Prefix prefix = (Prefix) o;

		if (direction != prefix.direction) return false;
		if (name != null ? !name.equals(prefix.name) : prefix.name != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (direction != null ? direction.hashCode() : 0);
		return result;
	}
}
