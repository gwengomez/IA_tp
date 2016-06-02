package agent.strategy;

import java.util.List;
import java.util.Random;

import agent.rlagent.RLAgent;
import environnement.Action;
import environnement.Etat;

/**
 * 
 * @author lmatignon
 *
 */
public class StrategySoftmax extends StrategyExploration{
	private Random rand=new Random();

	
	
	public StrategySoftmax(RLAgent agent,double tau) {
		super(agent);
	}

	@Override
	public Action getAction(Etat _e) {
		return null;
		
	}

	public void setTau(double tau) {
		// TODO Auto-generated method stub
		
	}

	

}
