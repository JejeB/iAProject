package iAProject.OudinetPackage;


import java.awt.Dimension;	// Pour définir une dimension d'élément graphique
import java.awt.Graphics;	// Pour manipuler un contexte de dessin
import java.awt.image.BufferedImage;	// Pour contenir l'image lue d'un fichier
import javax.swing.*;	// Pour manipuler tous les composants Swing

// *****************************************************************************
// La classe PanneauCouleur illustre la création et la manipulation
// d'une image couleur, afin de pouvoir éventuellement présenter des résultats
// sous forme graphique

class PanneauCouleur extends JPanel
{
	private BufferedImage image;	// Pour stocker une image
	public PanneauCouleur() throws Exception
	{
		image = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);	// Crée une image couleur de dimensions voulues
		setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));	// Fixe la taille du JPanel à la taille de l'image
		for (int y = 0; y < 256; ++y)
		{
			for (int x = 0; x < 256; ++x)
			{
				image.setRGB(x, y, 0xFF000000 | (x<<16) | (y<<8) | 128);	// Opacité maximale (0xFF), rouge à x, vert à y, bleu à 128
			}
		}
	}

	// Chaque fois que le contenu du composant doit être redessiné, cette méthode
	// est appelée : il faut donc y mettre ce que l'on veut afficher, ici : image
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);	// Exécute les actions graphiques héritées
		g.drawImage(image, 0, 0, null);	// Dessine l'image
	}

	public static void main(String[] args)
	{
		JFrame fenetre = new JFrame();	// Fabrique la fenêtre
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// Quand on ferme la fenêtre, l'application s'arrête
		try
		{
			PanneauCouleur panneauImage = new PanneauCouleur();	// Fabrique l'élément image
			fenetre.getContentPane().add(panneauImage);	// Ajoute l'élément image au contenu de la fenêtre
			fenetre.pack();	// Fixe la taille de la fenêtre relativement à son contenu
			fenetre.setLocationRelativeTo(null);		// Place la fenêtre au milieu de l'écran
			fenetre.setVisible(true);					// Affiche la fenêtre
		}
		catch (Exception e)
		{
			System.out.println("Impossible d'ouvrir le panneau ("+e+")");
		}
	}
}
