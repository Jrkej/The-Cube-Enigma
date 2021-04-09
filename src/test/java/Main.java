import com.codingame.gameengine.runner.SoloGameRunner;

public class Main {
	public static void main(String[] args) {
		SoloGameRunner gameRunner = new SoloGameRunner();

		gameRunner.setAgent("src\\test\\java\\solver.exe", "jrke", "https://www.codingame.com/servlet/fileservlet?id=50728347735682&format=codingamercard_avatar");
		//gameRunner.setAgent(Agent1.class);
		
		gameRunner.setTestCase("test0.json");

		// Another way to add a player
		// gameRunner.addAgent("python3 /home/user/player.py");

		gameRunner.start();
	}
}