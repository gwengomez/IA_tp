package simulation;

import javax.swing.SwingUtilities;

import agent.planningagent.ValueIterationAgent;
import vueGridworld.VueGridworldValue;
import environnement.gridworld.GridworldEnvironnement;
import environnement.gridworld.GridworldMDP;

public class testValueIterCours {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		  SwingUtilities.invokeLater(new Runnable(){
				public void run(){
		
						
					GridworldMDP gmdp = GridworldMDP.getBookGrid();
					GridworldEnvironnement g = new GridworldEnvironnement(gmdp);
					
					ValueIterationAgent a = new ValueIterationAgent(gmdp);
					
					VueGridworldValue vue = new VueGridworldValue(g,a);
					
									
					vue.setVisible(true);
				}
			});

	}
}
