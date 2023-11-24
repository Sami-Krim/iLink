package up.mi.skdh.exceptions;

/**
 * Exception levée lorsqu'une borne de recharge est trouvée lors de la suppression dans la communauté urbaine.
 */

public class ChargingPointFoundException extends Exception {
	/**
     * Construit une exception "ChargingPointFoundException" avec un message d'erreur par défaut.
     */
	public ChargingPointFoundException() {
		super("Cette ville possède déjà une borne de recharge.");
	}
}
