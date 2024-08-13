package model.enums;

public enum Role {

	ADMIN, SUPERVISOR, USER;

	public static Role getRole(String roleString) {

		return switch (roleString) {

		case "admin" -> Role.ADMIN;
		case "supervisor" -> Role.SUPERVISOR;
		case "user" -> Role.USER;
		default -> throw new IllegalArgumentException("There is no such role");

		};
	}

	@Override
	public String toString() {

		return switch (this) {

		case Role.ADMIN -> "admin";
		case Role.SUPERVISOR -> "supervisor";
		case Role.USER -> "user";

		};

	}
}
