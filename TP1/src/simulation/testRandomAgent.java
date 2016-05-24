package simulation;

import javax.swing.SwingUtilities;

import vueGridworld.VueGridworldValue;
import agent.planningagent.AgentRandom;
import environnement.gridworld.GridworldEnvironnement;
import environnement.gridworld.GridworldMDP;

public class testRandomAgent {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 SwingUtilities.invokeLater(new Runnable(){
				public void run(){
		
					GridworldMDP gmdp = GridworldMDP.getBookGrid();
					
					GridworldEnvironnement g = new GridworldEnvironnement(gmdp);
					GridworldEnvironnement.setDISP(true);//affichage transitions
					
					AgentRandom a = new AgentRandom(gmdp);
					VueGridworldValue vue = new VueGridworldValue(g,a);
					
									
					vue.setVisible(true);
				}
			});


	}

}
