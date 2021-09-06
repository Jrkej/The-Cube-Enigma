import com.codingame.gameengine.runner.SoloGameRunner;

public class Main {
	public static void main(String[] args) {
		SoloGameRunner gameRunner = new SoloGameRunner();

		gameRunner.setAgent(Agent1.class, "jrke", "https://www.codingame.com/servlet/fileservlet?id=68983084061211");
		//gameRunner.setAgent(Agent1.class);
		
		gameRunner.setTestCase("test1.json");

		// Another way to add a player
		// gameRunner.addAgent("python3 /home/user/player.py");

		gameRunner.start();
	}
}