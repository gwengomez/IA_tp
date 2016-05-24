package simulation;



import javax.swing.SwingUtilities;

import vueGridworld.VueGridworldManuel;
import environnement.gridworld.GridworldEnvironnement;
import environnement.gridworld.GridworldMDP;

public class testMoveGridworld {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				//instanciation du modele MDP
				GridworldMDP gmdp = GridworldMDP.getBookGrid();
				//GridworldMDP gmdp = GridworldMDP.getDiscountGrid();
				
				//instanciation de l'environnement
				GridworldEnvironnement g = new GridworldEnvironnement(gmdp);
	
				
				VueGridworldManuel vue = new VueGridworldManuel(g);
				vue.setVisible(true);
				
				
				
			}
		});


	}

}
