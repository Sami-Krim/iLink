package up.mi.skdh.exceptions;

/**
 * Exception levée lorsque la contrainte d'accessibilité n'est pas vérifiée dans la communauté urbaine.
 * Cette exception est lancée lorsqu'une ville n'a pas accès direct à un point de recharge après une suppression,
 */
public class AccessibilityConstraintNotVerifiedException extends Exception {
	/**
     * Construit une exception "AccessibilityConstraintNotVerifiedException" avec un message d'erreur par défaut.
     */
	public AccessibilityConstraintNotVerifiedException() {
		super("Cas impossible après suppression car la contrainte d'accessibilité n'est pas respectée (il existe une divile qui n'a pas d'accès direct à une bore de chargement).");
	}
}
