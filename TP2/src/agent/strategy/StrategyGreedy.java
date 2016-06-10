package agent.strategy;

import java.util.List;
import java.util.Random;

import agent.rlagent.RLAgent;
import environnement.Action;
import environnement.Etat;

/**
 * Strategie qui renvoit une action aleatoire avec probabilite epsilon, une
 * action gloutonne (qui suit la politique de l'agent) sinon
 * Cette classe a acces a un RLAgent par l'intermediaire de sa classe mere.
 *
 * @author lmatignon
 *
 */
public class StrategyGreedy extends StrategyExploration {

    private double epsilon;

    private Random rand = new Random();

    public StrategyGreedy(RLAgent agent, double epsilon) {
        super(agent);
        setEpsilon(epsilon);
    }

    /**
     * @return action selectionnee par la strategie d'exploration
     */
    @Override
    public Action getAction(Etat _e) {
        Action actionSelectionnee = null;
        if (!agent.getActionsLegales(_e).isEmpty()) {
            double numProba = rand.nextDouble();
            if (numProba > epsilon) {
                actionSelectionnee = getActionGlouton(_e);
            }
            if (actionSelectionnee == null) {
                actionSelectionnee = getActionRandom(_e);
            }
        }
        return actionSelectionnee;

    }
    
    public Action getActionRandom(Etat _e) {
	int numAction = rand.nextInt(agent.getActionsLegales(_e).size());
        return agent.getActionsLegales(_e).get(numAction);
    }
    
    public Action getActionGlouton(Etat _e) {
        if (agent.getPolitique(_e).size() > 0) {
            int numAction = rand.nextInt(agent.getPolitique(_e).size());
            return agent.getPolitique(_e).get(numAction);
        } else return null;
    }

    public void setEpsilon(double epsilon) {
	this.epsilon = epsilon;
    }

}
