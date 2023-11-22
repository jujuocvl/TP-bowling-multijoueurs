package bowling;

import java.util.HashMap;
import java.util.Map;

public class PartieMultiJoueurs implements IPartieMultiJoueurs{
	private Map<String, PartieMonoJoueur> lesParties;
	private String[] nomsDesJoueurs;
	private int nbJoueur;
	private int tourJoueurNo;
	
	public PartieMultiJoueurs(Map<String, PartieMonoJoueur> lesParties, String[] nomsDesJoueurs, int nbJoueur, int tourJoueurNo) {
		this.lesParties = lesParties;
		this.nomsDesJoueurs = nomsDesJoueurs;
		this.nbJoueur = nbJoueur;
		this.tourJoueurNo=tourJoueurNo;
	}
	public PartieMultiJoueurs() {
	}

	@Override
	public String demarreNouvellePartie(String[] nomsDesJoueurs) throws IllegalArgumentException {
		if (nomsDesJoueurs.length == 0) throw new IllegalArgumentException("Il faut au minimum 1 joueur");
		lesParties = new HashMap<>();
		nbJoueur = nomsDesJoueurs.length;
		tourJoueurNo = 0;
		this.nomsDesJoueurs = nomsDesJoueurs;
		for (String nom: nomsDesJoueurs) {
			lesParties.put(nom, new PartieMonoJoueur());
		}
		return tourJoueur();
	}

	@Override
	public String enregistreLancer(int nombreDeQuillesAbattues) throws IllegalStateException {
		if (estTerminer()) throw  new IllegalStateException("La partie est terminée");
		PartieMonoJoueur partieJoueur = lesParties.get(nomsDesJoueurs[tourJoueurNo]);
		partieJoueur.enregistreLancer(nombreDeQuillesAbattues);
		if (partieJoueur.numeroProchainLancer() == 1 || partieJoueur.estTerminee()) tourJoueurNo = (tourJoueurNo+1)%nbJoueur;
		if (estTerminer()) return "Partie terminée";
		return tourJoueur();
	}

	@Override
	public int scorePour(String nomDuJoueur) throws IllegalArgumentException {
		PartieMonoJoueur partieJoueur = lesParties.get(nomDuJoueur);
		if (partieJoueur == null) throw new IllegalArgumentException("Joueur inexistant");
		return partieJoueur.score();
	}
	
	private String tourJoueur(){
		return "Prochain tir: joueur " + nomsDesJoueurs[tourJoueurNo] + ", tour n° " + lesParties.get(nomsDesJoueurs[tourJoueurNo]).numeroTourCourant() + ", boule n° " + lesParties.get(nomsDesJoueurs[tourJoueurNo]).numeroProchainLancer();
	}
	
	private boolean estTerminer(){
		return lesParties.get(nomsDesJoueurs[nbJoueur-1]).estTerminee();
	}
}