package agent.planningagent;

import java.util.List;

import environnement.Action;
import environnement.Etat;
import environnement.MDP;
import java.util.Random;

/**
 * Cet agent choisit une action aleatoire parmi toutes les autorisees dans
 * chaque etat
 *
 * @author lmatignon
 *
 */
public class AgentRandom extends PlanningValueAgent {

    public AgentRandom(MDP _m) {
        super(_m);
    }

    @Override
    public Action getAction(Etat e) {
        List<Action> aPossibles = this.getPolitique(e);
        if(aPossibles.size() == 0)
            return null;
        int index = new Random().nextInt(aPossibles.size());
        return aPossibles.get(index);
    }

    @Override
    public double getValeur(Etat _e) {
        return 0.0;
    }

    @Override
    public List<Action> getPolitique(Etat _e) {
        return this.mdp.getActionsPossibles(_e);
    }

    @Override
    public void updateV() {
        System.out.println("l'agent random ne planifie pas");
    }

    @Override
    public void setGamma(double parseDouble) {}

}
