package agent.planningagent;

import java.util.ArrayList;
import java.util.List;

import environnement.Action;
import environnement.Etat;
import environnement.MDP;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Cet agent met a jour sa fonction de valeur avec value iteration et choisit
 * ses actions selon la politique calculee.
 *
 * @author laetitiamatignon
 *
 */
public class ValueIterationAgent extends PlanningValueAgent {

    /**
     * discount facteur
     */
    protected double gamma; //pondéré plus ou moins l'importance donnée aux récompenses proches (gamma faible) ou aux récompenses faibles (gamma fort)
    protected HashMap<Etat, Double> values = new HashMap<>();

    /**
     *
     * @param gamma
     * @param mdp
     */
    public ValueIterationAgent(double gamma, MDP mdp) {
        super(mdp);
        this.gamma = gamma;
        this.delta = 0.0;
        //initialisation de tous les états à zéro
        initValues();
    }

    public ValueIterationAgent(MDP mdp) {
        this(0.9, mdp);
    }

    public void initValues() {
        for (Etat e : this.mdp.getEtatsAccessibles()) {
            this.values.put(e, null);
        }
    }

    /**
     *
     * Mise a jour de V: effectue UNE iteration de value iteration
     */
    @Override
    public void updateV() {
        try {
            HashMap<Etat, Double> cloneValues = new HashMap<>();/*(HashMap<Etat, Double>) this.values.clone();*/

            List<Etat> etats = this.mdp.getEtatsAccessibles();
            for (Etat e : etats) {
                List<Action> actions = this.mdp.getActionsPossibles(e);
                Double maxA = null;
                for (Action a : actions) {
                    double somme = this.getSomme(e, a);
                    if (maxA == null || somme > maxA) {
                        maxA = somme;
                    }
                }
                if (maxA != null) {
                    cloneValues.put(e, maxA);
                }
            }
            Double maxDelta = null;
            for (Etat _e : this.values.keySet()) {
                double diff = Math.abs(this.getValeur(_e) - (cloneValues.get(_e) == null ? 0 : cloneValues.get(_e)));
                if ((maxDelta == null || diff > maxDelta)) {
                    maxDelta = diff;
                }
            }
            if (maxDelta == null) {
                this.delta = 0.0;
            } else {
                this.delta = maxDelta;
            }
            this.values.putAll(cloneValues);
            this.notifyObs();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * renvoi l'action executee par l'agent dans l'etat e Si aucune actions
     * possibles, renvoi NONE ou null.
     */
    @Override
    public Action getAction(Etat e) {
        List<Action> aPossibles = this.getPolitique(e);
        if (aPossibles.size() == 0) {
            return null;
        }
        int index = new Random().nextInt(aPossibles.size());
        return aPossibles.get(index);
    }

    @Override
    public double getValeur(Etat _e) {
        return this.values.get(_e) == null ? 0 : this.values.get(_e);
    }

    /**
     * renvoi la (les) action(s) de plus forte(s) valeur(s) dans l'etat e
     * (plusieurs actions sont renvoyees si valeurs identiques, liste vide si
     * aucune action n'est possible)
     */
    @Override
    public List<Action> getPolitique(Etat _e) {
        try {
            List<Action> l = new ArrayList<>();
            List<Action> aPossibles = this.mdp.getActionsPossibles(_e);
            Double maxV = null;
            for (Action a : aPossibles) {
                double somme = this.getSomme(_e, a);
                if (maxV == null || somme > maxV) {
                    maxV = somme;
                    l.clear();
                    l.add(a);
                } else if (somme == maxV) {
                    l.add(a);
                }
            }
            return l;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public double getSomme(Etat e, Action a) throws Exception {
        Map<Etat, Double> etats = this.mdp.getEtatTransitionProba(e, a);
        double somme = 0;
        for (Etat _e : etats.keySet()) {
            double T = etats.get(_e);
            double R = this.mdp.getRecompense(e, a, _e);
            somme += T * (R + this.gamma * this.getValeur(_e));
        }
        return somme;
    }

    @Override
    public void reset() {
        super.reset();
        this.initValues();
        this.notifyObs();

    }

    @Override
    public void setGamma(double arg0) {
        this.gamma = arg0;
    }

}
