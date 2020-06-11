package iAProject.OudinetPackage;

// À compiler par javac PanneauSon.java
// À exécuter par java PanneauSon <nomFichierSon.wav>

import java.awt.Dimension;	// Pour définir une dimension d'élément graphique
import java.awt.Graphics;	// Pour manipuler un contexte de dessin
import java.awt.image.BufferedImage;	// Pour contenir l'image lue d'un fichier
import java.io.File;	// Pour manipuler un fichier
import javax.imageio.ImageIO;	// Pour pouvoir lire une image
import javax.swing.*;	// Pour manipuler tous les composants Swing

class PanneauSon extends JPanel
{
	private final static int HauteurImage = 800;
	
	private BufferedImage image;	// Pour stocker une image

	// Crée une image (donc 2D) de l'information du son (donc 1D), de HauteurImage
	// pixels de haut quoi qu'il arrive
	private void calculeImage(final float[] donnees)
	{
		final int NbBlocs = donnees.length/HauteurImage;
		
		// On cherche les bornes maximale et minimale englobant les valeurs de l'image
		float Maximum = Float.NEGATIVE_INFINITY;
		for (float f : donnees) {Maximum = Math.max(Maximum, f);}
		float Minimum = Float.POSITIVE_INFINITY;
		for (float f : donnees) {Minimum = Math.min(Minimum, f);}
		// Calcul d'une valeur de rééchelonnement de la dynamique de l'image
		// sur la plage de 0 à 255
		final double CorrectionDeLaDynamique = 255/(Maximum-Minimum+1);
		// Découpage en blocs du son
		final float[][] blocs = new float[NbBlocs][HauteurImage];
		for (int bloc = 0, compteur = 0; bloc < NbBlocs; ++bloc)
			for (int y = 0; y < HauteurImage; ++y)
				blocs[bloc][y] = donnees[compteur++];
		// Affichage bloc par bloc de "l'image du son"
		for (int x = 0; x < NbBlocs; ++x)
			for (int y = 0; y < HauteurImage; ++y)
			{
				final int valeur = (int)((blocs[x][y]-Minimum+1)*CorrectionDeLaDynamique);
				// On crée une valeur ARVB en fixant le rouge, le vert et le bleu (RVB)
				// à la valeur ci-dessus, et l'opacité (A) au maximum
				// (les décalages et combinaisons logiques permettent de forger
				// la valeur binaire comme désiré)
				image.setRGB(x, y, 0xFF000000 | (valeur<<16) | (valeur<<8) | valeur);
			}
	}
	
	// Le constructeur de PanneauSon
	public PanneauSon(final Son son) throws Exception
	{
		final int NbBlocs = son.donnees().length/HauteurImage;
		image = new BufferedImage(NbBlocs, HauteurImage, BufferedImage.TYPE_INT_ARGB);	// Crée une image couleur de dimensions voulue
		setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));			// Fixe la taille du JPanel à la taille de l'image
		calculeImage(son.donnees());
	}

	// Chaque fois que le contenu du composant doit être redessiné, cette méthode
	// est appelée : il faut donc y mettre ce que l'on veut afficher, ici : image
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);		// Exécute les actions graphiques héritées
		g.drawImage(image, 0, 0, null);	// Dessine l'image
	}

	public static void main(String[] args)
	{
		if (args.length == 1)
		{
			System.out.println("Lecture du fichier WAV "+args[0]);
			Son son = new Son(args[0]);	// Voir la classe Son
			System.out.println("Fichier "+args[0]+" : "+son.donnees().length+" échantillons à "+son.frequence()+"Hz");
			
			JFrame fenetre = new JFrame();	// Fabrique la fenêtre
			fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// Quand on ferme la fenêtre, l'application s'arrête
			try
			{
				PanneauSon panneauImage = new PanneauSon(son);	// Fabrique l'élément image
				fenetre.getContentPane().add(panneauImage);		// Ajoute l'élément image au contenu de la fenêtre
				fenetre.pack();	// Fixe la taille de la fenêtre relativement à son contenu
				fenetre.setLocationRelativeTo(null);			// Place la fenêtre au milieu de l'écran
				fenetre.setVisible(true);						// Affiche la fenêtre
			}
			catch (Exception e)
			{
				System.out.println("Impossible d'ouvrir le panneau ("+e+")");
			}
		}
		else
			System.out.println("Veuillez donner le nom d'un fichier WAV en paramètre SVP.");
	}
}
