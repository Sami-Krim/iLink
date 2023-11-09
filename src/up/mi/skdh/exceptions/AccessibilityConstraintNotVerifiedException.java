package up.mi.skdh.exceptions;

public class AccessibilityConstraintNotVerifiedException extends Exception {
	public AccessibilityConstraintNotVerifiedException() {
		super("Cas impossible après suppression car la contrainte d'accessibilité n'est pas respectée (il existe une divile qui n'a pas d'accès direct à une bore de chargement).");
	}
}
