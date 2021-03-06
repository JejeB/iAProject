package iAProject.OudinetPackage;

public class neurone
{
	
	private int testentree;
	// Coefficient générique de mise à jour des poids, commun à tous les neurones
	public static float eta = 0.1f;
	
	// Tolérance générique permettant d'accepter la sortie d'un neurone comme valable
	public static float ToleranceSortie = 1.e-2f;
	
	// Tableau des points synaptiques d'un neurone
	private float[] synapses;
	
	// Valeur de sortie d'un neurone (à "Not A Number" par défaut)
	private float sortie = Float.NaN;
	
	private boolean ConsoleLog;
	
	// Fonction d'activation d'un neurone (peut facilement être modifiée par héritage)
	public float activation(final float valeur) {return valeur >= 0 ? 1.f : -1.f;}
	
	// Constructeur d'un neurone
	public neurone(final int nbEntrees)
	{
		// Le tableau des poids synaptiques est une case plus grand que le nombre
		// de synapses, car la case en plus joue alors le rôle de "biais"
		synapses = new float[nbEntrees+1];
		this.testentree=0;
		
		// On initialise tous les poids de manière alétoire, biais compris
		for (int i = 0; i < nbEntrees+1; ++i)
			synapses[i] = (float)(Math.random()*2.-1.);
	}
	
	// Accesseur pour la valeur de sortie
	public float sortie() {return sortie;}
	
	// Donne accès aux valeurs des poids synaptiques et au biais
	public float[] synapses() {return synapses;}
	
	//Mise a jour des synapse et du biais en fct des entrees
	public void miseAJourSyn(final float[] entree,float resultatA,float resultatO) {
		for (int i = 0; i < entree.length; i++) {
			synapses[i]=synapses[i]+eta*entree[i]*(resultatA-resultatO);
		}
		//Mise a jour du biais
		synapses[synapses.length-1]=synapses[synapses.length-1]+eta*(resultatA-resultatO);
		
	}
	
public void AfficheOperation(final float[] entree,float resultatA,float resultatO) {
	StringBuilder Sentree = new StringBuilder();
	
	for (int i = 0; i < entree.length; i++) {
			Sentree.append(entree[i]+" : ");
		}
		String r=resultatA==resultatO?"VRAI":"FAUX";
		System.out.println(Sentree.toString() + " = "+resultatO +"     "+ r);
	}
	
	// Calcule la valeur de sortie en fonction des entrées, des poids synaptiques,
	// du biais et de la fonction d'activation
	public void metAJour(final float[] entrees)
	{
		// On démarre en extrayant le biais
		float somme = synapses[synapses.length-1];
		
		// Puis on ajoute les produits entrée-poids synaptique
		for (int i = 0; i < synapses.length-1; ++i)
			somme += entrees[i]*synapses[i];
		
		// On fixe la sortie du neurone relativement à la fonction d'activation
		sortie = activation(somme);
	}
	
	// Fonction d'apprentissage permettant de mettre à jour les valeurs des 
	// poids synaptiques ainsi que du biais en fonction de données supervisées
	public void apprentissage(final float[][] entrees, final float[] resultats)
	{
		// Un "drapeau" indiquant si toutes les entrées ont permis de trouver
		// les résultats attendus (=> l'apprentissage est alors fini), ou s'il
		// y a au moins un cas qui ne correspond pas (=> apprentissage pas fini)
		boolean apprentissageFini = false;
		
		// On boucle tant que l'apprentissage n'est pas fini
			// On part du principe que tout va bien se passer => drapeau à vrai
			// Pour chacune des entrées fournies
				// On calcule la sortie du neurone en fonction de ces entrées
				// On regarde la différence avec le résultat attendu
				// Si l'erreur dépasse la tolérance autorisée 
					// On met à jour les poids synaptiques
					// On met à jour le biais 
					// On mémorise que l'apprentissage n'est pas finalisé
		while(!apprentissageFini) {
			for (int i = 0; i < entrees.length; ++i)
			{
				// Pour une entrée donnée
				final float[] entree = entrees[i];
				this.metAJour(entree);
				if(ConsoleLog)
					AfficheOperation(entree, resultats[i],sortie);
				if(sortie==resultats[i]) {
					this.testentree=this.testentree+1;
				}else {
						
					this.miseAJourSyn(entree, resultats[i],sortie);
				}
				
				
				
			}
			if(this.testentree>=resultats.length) {
				apprentissageFini=true;
			}else {
				this.testentree=0;
			}
			//System.out.println(this.testentree);
		}
	}
	
	
	
	public static void main(String[] args)
	{
		// Tableau des entrées de la fonction ET (-1 = faux, 1 = vrai)
		final float[][] entrees = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
		
		// Tableau des sorties de la fonction ET
		final float[] resultats = {-1, -1, -1, 1};
		
		// On crée un neurone taillé pour apprendre la fonction ET
		final neurone n = new neurone(entrees[0].length);
		
		n.setConsoleLog(false);
		// On lance l'apprentissage de la fonction ET sur ce neurone
		n.apprentissage(entrees, resultats);
		
		// On affiche les valeurs des synapses et du biais
		for (float f : n.synapses())
			System.out.print(f+" ");
		System.out.println("");
		
		// On affiche chaque cas appris
		for (int i = 0; i < entrees.length; ++i)
		{
			// Pour une entrée donnée
			final float[] entree = entrees[i];
			
			// On met à jour la sortie du neurone
			n.metAJour(entree);
			
			// On affiche cette sortie
			System.out.println( entree[0]+" ET "+entree[1] +" : "+n.sortie());
		}
	}

	public boolean isConsoleLog() {
		return ConsoleLog;
	}

	public void setConsoleLog(boolean consoleLog) {
		ConsoleLog = consoleLog;
	}
}
