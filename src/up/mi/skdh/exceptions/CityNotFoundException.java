package up.mi.skdh.exceptions;

/**
 * Exception levée lorsqu'une ville est introuvable dans la communauté urbaine.
 */
public class CityNotFoundException extends Exception {
	/**
     * Construit une exception "CityNotFoundException" avec un message d'erreur spécifique.
     *
     * @param message Le message spécifiant l'erreur.
     */
	public CityNotFoundException(String message) {
		super(message);
	}
}
