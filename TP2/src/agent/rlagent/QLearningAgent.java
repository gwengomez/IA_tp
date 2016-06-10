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
        if (!this.getActionsLegales(e).isEmpty()) {
            if (tableQValeurs != null && tableQValeurs.get(e) != null) {
                for (Action _a : tableQValeurs.get(e).keySet()) {
                    if (getQValeur(e, _a) > maxValeur) {
                        maxValeur = getQValeur(e, _a);
                        actionsMaxValeur.clear();
                        actionsMaxValeur.add(_a);
                    } else if (getQValeur(e, _a) == maxValeur) {
                        actionsMaxValeur.add(_a);
                    }
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
        Double maxValeur = null;
        if (tableQValeurs != null && tableQValeurs.get(e) != null) {
            for (Action _a : tableQValeurs.get(e).keySet()) {
                if (maxValeur == null || getQValeur(e, _a) > maxValeur) {
                    maxValeur = getQValeur(e, _a);
                }
            }
        }
        if (maxValeur == null) {
            maxValeur = 0.0;
        }
        return maxValeur;

    }
    
    /**
     * @return la valeur minimum d'un etat
     */
    public double getValeurMax() {
        Double maxValeur = null;
        if (tableQValeurs != null) {
            for (Etat _e : tableQValeurs.keySet()) {
                if (maxValeur == null || getValeur(_e) > maxValeur) {
                    maxValeur = getValeur(_e);
                }
            }
        }
        if (maxValeur == null) {
            maxValeur = 0.0;
        }
        return maxValeur;

    }
    
    /**
     * @return la valeur minimum d'un etat
     */
    public double getValeurMin() {
        Double minValeur = null;
        if (tableQValeurs != null) {
            for (Etat _e : tableQValeurs.keySet()) {
                for (Action _a : tableQValeurs.get(_e).keySet()) {
                    if (minValeur == null || getQValeur(_e, _a) < minValeur) {
                        minValeur = getQValeur(_e, _a);
                    }
                }
            }
        }
        if (minValeur == null) {
            minValeur = 0.0;
        }
        return minValeur;

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
        vmin = getValeurMin();

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
        double valeur = (1 - alpha)*getQValeur(e, a) + alpha*(reward + gamma*getValeur(esuivant));
	setQValeur(e, a, valeur);
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
