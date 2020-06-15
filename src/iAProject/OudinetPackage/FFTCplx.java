package iAProject.OudinetPackage;

public class FFTCplx
{
	public final static int TailleFFTtest = 16;
	public final static double Periode = 1;


 	private int taille;
 
	// Indiquer la taille dans le constructeur permettra des optimisations par la suite :
	// on pourra facilement transformer les méthodes statiques en méthodes
	// d'instance, et optimiser l'objet en fonction de la taille indiquée dans
	// le constructeur
 	public FFTCplx(int taille)
 	{
 		this.taille = taille;
 	}


	// Sous-signal obligatoirement découpé par pas de deux
	// La méthode est statique car c'est plutôt une fonction dans notre cas,
	// et privée car elle n'a de réel intérêt qu'à l'intérieur de la classe
	private static Complexe[] demiSignal(Complexe[] signal, int depart)
	{
		Complexe[] sousSignal = new Complexe[signal.length/2];
		for (int i = 0; i < sousSignal.length; ++i)
			sousSignal[i] = signal[depart+2*i];
		return sousSignal;
	}
	
	// La méthode est statique car c'est plutôt une fonction dans notre cas ;
	// on pourra la rendre méthode d'instance dès qu'on voudra optimiser le code
	// et tenir compte de la taille, ce qui permettra d'éviter de refaire
	// certains calculs à chaque nouvel appel de appliqueSur()
	public static Complexe[] appliqueSur(Complexe[] signal)
	{
		// On crée la donnée renvoyée en retour
		Complexe[] trSignal = new Complexe[signal.length];
		if (signal.length == 1)	// Cas trivial de la FFT d'une valeur unique
			trSignal[0] = new ComplexeCartesien(signal[0].reel(), signal[0].imag());
		else	// Si le cas n'est pas trivial
		{
			// Découpage des deux sous-groupes de données sur lesquels on applique la FFT
			// (voir définition de cours)
			final Complexe[] P0 = appliqueSur(demiSignal(signal, 0));
			final Complexe[] P1 = appliqueSur(demiSignal(signal, 1));
			// On applique le regroupement "papillon" pour créer le résultat final
			for (int k = 0; k < signal.length/2; ++k)
			{
				final ComplexePolaire expo = new ComplexePolaire(1., -2.*Math.PI*k/signal.length);
				final Complexe temp = P0[k];
				trSignal[k] = temp.plus(expo.fois(P1[k]));
				trSignal[k+signal.length/2] = temp.moins(expo.fois(P1[k]));
			}
		}
		return trSignal;
	}

	public static void main(String[] args)
	{
		// Création d'un signal test simple
		Complexe[] signalTest = new Complexe[TailleFFTtest];
		for (int i = 0; i < TailleFFTtest; ++i)
			signalTest[i] = new ComplexeCartesien(Math.cos(2.*Math.PI*i/TailleFFTtest*Periode), 0);
		// On applique la FFT sur ce signal
		Complexe[] resultat = appliqueSur(signalTest);
		// On affiche les valeurs du résultat
		for (int i = 0; i < resultat.length; ++i) {
			System.out.print(i+" : ("+(float)resultat[i].reel()+" ; "+(float)resultat[i].imag()+"i)");
			System.out.println(", ("+(float)resultat[i].mod()+" ; "+(float)resultat[i].arg()+" rad)");
		}
		int tab[]=GetFrequencePrincipale(20,(float) 1.05,resultat);
		ConsoleFrequence(tab,1,16);
	}
	
	public static void ConsoleFrequence(int freq[],int frequeEch,int NBech) {
		for (int i = 0; i < freq.length; i++) {
			System.out.println("f="+ ((float)freq[i]/NBech)*frequeEch+" Hz");
		}
	}
	
	public static int[] GetFrequencePrincipale(int NBFrequence, float seuil,Complexe[] resultat) {
		int tab[] = new int[NBFrequence];
		int j=0;
		for (int i = 0; i < tab.length; i++) {
			tab[i]=0;
		}
		for (int i = 0; i < resultat.length; i++) {
			if(resultat[i].mod()>seuil && j<tab.length) {
				
				tab[j]=i;
				j++;
			}
		}
		triFusion(tab,0,tab.length-1);
		return tab;
	}
	
	
	public static void triFusion (int [] tab, int d�but, int fin)
	{
		int milieu;
		if(d�but < fin)
		{
			milieu = (d�but+fin) / 2;
			triFusion(tab, d�but, milieu);
			triFusion(tab, milieu + 1, fin);
			fusionner (tab, d�but, milieu, fin);
		}
	}

	public static void fusionner (int tab[], int d�but, int milieu, int fin)
	{
		int [] old_tab = (int[]) tab.clone(); 
	        // tab.clone est tres gourmand en temps d'execution surtout dans un algo recursif
	        // il faudrait passer par un tableau temporaire pour stocker les donn�es tri�es.
	        // puis recopier la partie tri�e a la fin de la m�thode.

		int i1 = d�but; //indice dans la premi�re moiti� de old_tab
		int i2 = milieu + 1; // indice dans la deuxi�me moiti� de old_tab
		int i = d�but; //indice dans le tableau tab

		while (i1 <= milieu && i2 <= fin)
		{
			//quelle est la plus petite t�te de liste?
			if(old_tab[i1] <= old_tab[i2])
			{
				tab[i] = old_tab[i1];
				i1++;
			}
			else
			{
				tab[i] = old_tab[i2];
				i2++;
			}
			i++;
		}
		if (i <= fin)
		{
			while(i1 <= milieu)  // le reste de la premi�re moiti�
			{
				tab[i] = old_tab[i1];
				i1++;
				i++;
			}
			while(i2 <= fin) // le reste de la deuxi�me moiti�
			{
				tab[i] = old_tab[i2];
				i2++;
				i++;
			}
		}
	}
	
	
}
