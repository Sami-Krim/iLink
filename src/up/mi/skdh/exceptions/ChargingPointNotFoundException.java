package up.mi.skdh.exceptions;

/**
 * Exception levée lorsqu'une borne de recharge est introuvable pour une suppression dans la communauté urbaine.
 */
public class ChargingPointNotFoundException extends Exception {
	/**
     * Construit une exception "ChargingPointNotFoundException" avec un message d'erreur par défaut.
     */
	public ChargingPointNotFoundException() {
		super("Cette ville ne dispose pas de borne de recharge à supprimer.");
	}
}
