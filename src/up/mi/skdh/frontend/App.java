	package up.mi.skdh.frontend;
	
	/**
	 * Classe principale de l'application, responsable du démarrage de l'interface utilisateur.
	 * Cette classe contient la méthode principale (main) qui lance l'application pour manipuler la communauté urbaine.
	 * 
	 * 
	 * @author Sami KRIM
	 * @author Daniel HUANG
	 */
	
	public class App {
		/**
	     * Entrée de l'application.
	     * Crée une instance de MenuControl et lance l'application en appelant sa méthode startApp().
	     * 
	     * @param args Les arguments en ligne de commande.
	     */
		public static void main(String[] args) {
			MenuControl app = new MenuControl();
			if(args.length > 0) {
				app.startApp(args[0]);
			}
		}
	
	}
