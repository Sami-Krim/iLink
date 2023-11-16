package up.mi.skdh.exceptions;

public class ChargingPointNotFoundException extends Exception {
	public ChargingPointNotFoundException() {
		super("Cette ville ne dispose pas de borne de recharge à supprimer.");
	}

}
