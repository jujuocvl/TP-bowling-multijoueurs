
package bowling;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MultiJoueurGameTest {
	
	private PartieMultiJoueurs partie;
	private String[] lesJoueurs;

	@BeforeEach
	public void setUp(){
		partie = new PartieMultiJoueurs();
		lesJoueurs = new String[]{"Sophie Marceau", "Alice Isaaz"};
	}

	@Test
	void testDemarrerPartie(){
		assertEquals(partie.demarreNouvellePartie(lesJoueurs), "Prochain tir: joueur Sophie Marceau, tour n° 1, boule n° 1", "Mauvais tour/joueur/lancer");
	}
	
	@Test
	void testDemarrerPartieSansJoueur(){
		assertThrows(IllegalArgumentException.class, () -> {
			partie.demarreNouvellePartie(new String[]{});
			}, "Une partie ne peux pas commencer sans joueur");
	}
	
	@Test
	void testEnregistrementLancer(){
		partie.demarreNouvellePartie(lesJoueurs);
		assertEquals(partie.enregistreLancer(9), "Prochain tir: joueur Sophie Marceau, tour n° 1, boule n° 2", "Mauvais tour/joueur/lancer");
		assertEquals(partie.enregistreLancer(1), "Prochain tir: joueur Alice Isaaz, tour n° 1, boule n° 1", "Mauvais tour/joueur/lancer");
		assertEquals(partie.enregistreLancer(10), "Prochain tir: joueur Sophie Marceau, tour n° 2, boule n° 1", "Mauvais tour/joueur/lancer");
		for (int i = 0; i < 21; i++) {
			partie.enregistreLancer(10);
		}
		assertEquals(partie.enregistreLancer(10), "Partie terminée", "La partie doit être terminée");
		assertThrows(IllegalStateException.class, () -> {partie.enregistreLancer(1);}, "Pas de lancer après la fin de la partie");
	}
	
	@Test
	void testScore(){
		partie.demarreNouvellePartie(lesJoueurs);
		partie.enregistreLancer(3);
		assertEquals(partie.scorePour("Sophie Marceau"), 3, "Le score n'est pas bon pour ce joueur");
		assertThrows(IllegalArgumentException.class, () -> {partie.scorePour("Alice Izaaz");}, "Ce joueur n'existe pas");
	}
    
}
