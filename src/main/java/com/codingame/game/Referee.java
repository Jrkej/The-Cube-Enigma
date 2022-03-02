package com.codingame.game;

import com.codingame.gameengine.core.AbstractPlayer.TimeoutException;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.SoloGameManager;
import com.codingame.gameengine.module.endscreen.EndScreenModule;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.tooltip.TooltipModule;
import com.codingame.gameengine.module.toggle.ToggleModule;
import com.google.inject.Inject;
import modules.cubeView;

import java.util.ArrayList;
import java.util.List;

public class Referee extends AbstractReferee {
    @Inject private SoloGameManager<Player> gameManager;
	@Inject private GraphicEntityModule graphics;
	@Inject private TooltipModule tooltips;
	@Inject	private EndScreenModule endScreenModule;
	@Inject	private cubeView tdViewer;
	@Inject ToggleModule toggles;
	private final int MAX_TURNS = 300;
	private static final int TIME_PER_TURN = 50;
	private static final int FIRST_TURN_TIME = 1000;
	Player player = new Player();
	private Cube cube;
	private Animation SDK;
	private int SCORE;
	public String message;

    @Override
    public void init() {
    	// Initialize your game here.
    		try {
    			this.gameManager.setMaxTurns(MAX_TURNS);
    			this.gameManager.setTurnMaxTime(TIME_PER_TURN);
    			this.gameManager.setFirstTurnMaxTime(FIRST_TURN_TIME);
    			this.cube = new Cube();
    			this.cube.load(this.gameManager.getTestCaseInput().get(0));
    			this.tdViewer.setCube(this.cube);
    			SDK = new Animation(this.graphics, this.tooltips, this.gameManager.getPlayer().getNicknameToken(), this.gameManager.getPlayer().getAvatarToken());
    			this.updateScores(0);
    			SDK.init(cube, this.SCORE, toggles);
    				
    		} catch (Exception e) {
    	        e.printStackTrace();
    	        System.err.println("Referee CRASHED! Ending Game");
    	    }
    }

    @Override
    public void gameTurn(int turn) {
    	this.message = "";
    	Player cilent = this.gameManager.getPlayer();
    	this.SendInputs(cilent, turn-1);
    	cilent.execute();
    	try {
    		List<String> outputs = cilent.getOutputs();
    		List<String> Splits = this.strSplits(' ', outputs.get(0));
    		if (Splits.size() > 1) {
    			this.message = Splits.get(1);
    			for (int i = 2; i < Splits.size(); i++) {
    				this.message += " " + Splits.get(i);
    			}
    			if (this.message.length() >= 20) this.message = this.message.substring(0, 20);
    		}
    		this.tdViewer.setState();
    		boolean correct = this.cube.play(Splits.get(0));
    		if (correct) {
    			this.tdViewer.setMove(Splits.get(0));
    			this.updateScores(turn);
    			this.gameManager.addToGameSummary(this.gameManager.getPlayer().getNicknameToken() + " Played - " + Splits.get(0));
    			if (turn >= this.MAX_TURNS || this.cube.isCubeSolved() || Splits.get(0).equals("E")) {
    				this.gameManager.winGame(this.cube.isCubeSolved()?("You solved cube in " + turn + " moves!"):("Your Score = " + this.SCORE));
    			}
    		}  else {
    			this.gameManager.addToGameSummary(this.gameManager.getPlayer().getNicknameToken() + " Sended invalid command - " + Splits.get(0));
    			this.SCORE = -1;
    			this.gameManager.loseGame("Got Invalid command!");
    		}
    		SDK.turn(turn, cube, this.SCORE, this.message, Splits.get(0));
    	} catch(TimeoutException e) {
    		this.SCORE = -1;
			this.gameManager.loseGame(cilent.getNicknameToken() + " Timeout!");
		} catch(Exception e) {
			this.SCORE = -1;
			gameManager.loseGame(cilent.getNicknameToken() + " Unexpected output!");
		}
    }
    
    private List<String> strSplits(char splitter, String component) {
    	List<String> Splits = new ArrayList<String>();
    	String temp = "";
    	for (int i = 0; i < component.length(); i++) {
    		char at = component.charAt(i);
    		if (at == splitter) {
    			Splits.add(temp);
    			temp = "";
    		} else {
    			temp += at;
    		}
    	}
    	if (temp != "") Splits.add(temp);
    	return Splits;
    }

    private void SendInputs(Player cilent, int turnIndex) {
    	cilent.sendInputLine(String.valueOf(this.SCORE));
    	cilent.sendInputLine(String.valueOf(turnIndex));
    	for (int face = 0; face < 6; face ++) {
    		cilent.sendInputLine(this.cube.state(face));
    	}
    }
    
    private void updateScores(int turn) {
    	this.SCORE = this.cube.value(turn);
    }

    @Override
    public void onEnd() {
    	this.gameManager.putMetadata("score", String.valueOf(this.SCORE));
    	this.endScreenModule.setScores(new int[] {this.SCORE}, new String[] {(this.SCORE == -1?"ERROR!":"YOUR SCORE = " + this.SCORE)});
    }
}