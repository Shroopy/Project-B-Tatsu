package enums;

/*
 * Used for determining whether a character may perform certain actions, such as crouching, blocking, or attacking
 */
public enum ControlState {
	CONTROLLABLE, HITSTUN, RECOVERY, ATTACKING, NOT;
}
