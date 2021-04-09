import com.codingame.gameengine.runner.SoloGameRunner;

public class Main {
	public static void main(String[] args) {
		SoloGameRunner gameRunner = new SoloGameRunner();

		gameRunner.setAgent(Agent1.class);
		//gameRunner.setAgent(Agent1.class);
		
		gameRunner.setTestCase("test0.json");

		// Another way to add a player
		// gameRunner.addAgent("python3 /home/user/player.py");

		gameRunner.start();
	}
}