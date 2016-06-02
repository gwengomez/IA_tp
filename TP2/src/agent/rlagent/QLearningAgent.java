package agent.rlagent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import environnement.Action;
import environnement.Environnement;
import environnement.Etat;
import java.util.Map;

/**
 *
 * @author laetitiamatignon
 *
 */
public class QLearningAgent extends RLAgent {

    private Map<Etat, Map<Action, Double>> tableQValeurs;
    
    /**
     *
     * @param alpha
     * @param gamma
     * @param Environnement
     */
    public QLearningAgent(double alpha, double gamma,
            Environnement _env) {
        super(alpha, gamma, _env);
	tableQValeurs = new HashMap<Etat, Map<Action, Double>>();

    }

    /**
     * renvoi la (les) action(s) de plus forte(s) valeur(s) dans l'etat e
     *
     * renvoi liste vide si aucunes actions possibles dans l'etat
     */
    @Override
    public List<Action> getPolitique(Etat e) {
        List<Action> actionsMaxValeur = new ArrayList<>();
        double maxValeur = 0;
        // Gerer etat absorbant
        for (Etat _e : tableQValeurs.keySet()) {
            for (Action _a : tableQValeurs.get(_e).keySet()) {
                if (tableQValeurs.get(_e).get(_a) > maxValeur) {
                    maxValeur = tableQValeurs.get(_e).get(_a);
                    actionsMaxValeur.clear();
                    actionsMaxValeur.add(_a);
                } else if (tableQValeurs.get(_e).get(_a) == maxValeur) {
                    actionsMaxValeur.add(_a);
                }
            }
        }
        return actionsMaxValeur;

    }

    /**
     * @return la valeur d'un etat
     */
    @Override
    public double getValeur(Etat e) {
        double maxValeur = 0;
        for (Etat _e : tableQValeurs.keySet()) {
            for (Action _a : tableQValeurs.get(_e).keySet()) {
                if (tableQValeurs.get(_e).get(_a) > maxValeur) {
                    maxValeur = tableQValeurs.get(_e).get(_a);
                }
            }
        }
        return maxValeur;

    }

    /**
     *
     * @param e
     * @param a
     * @return Q valeur du couple (e,a)
     */
    @Override
    public double getQValeur(Etat e, Action a) {
        if (tableQValeurs.get(e) != null && tableQValeurs.get(e).get(a) != null) {
            return tableQValeurs.get(e).get(a);
        }
        return 0;
    }

    /**
     * setter sur Q-valeur
     */
    @Override
    public void setQValeur(Etat e, Action a, double d) {
        
        if (tableQValeurs.get(e) == null) {
            tableQValeurs.put(e, new HashMap<Action, Double>());
        }
        tableQValeurs.get(e).put(a, d);
        
        vmax = getValeur(e);
        // vmin à faire 

        //mise a jour vmin et vmax pour affichage gradient de couleur
        //...
        this.notifyObs();
    }

    /**
     *
     * mise a jour de la Q-valeur du couple (e,a) apres chaque interaction
     * <etat e,action a, etatsuivant esuivant, recompense reward>
     * la mise a jour s'effectue lorsque l'agent est notifie par l'environnement
     * apres avoir realise une action.
     *
     * @param e
     * @param a
     * @param esuivant
     * @param reward
     */
    @Override
    public void endStep(Etat e, Action a, Etat esuivant, double reward) {
		//TODO

    }

    @Override
    public Action getAction(Etat e) {
        this.actionChoisie = this.stratExplorationCourante.getAction(e);
        return this.actionChoisie;
    }

    /**
     * reinitialise les Q valeurs
     */
    @Override
    public void reset() {
        super.reset();
        this.episodeNb = 0;
	tableQValeurs.clear();

        this.notifyObs();
    }

}
