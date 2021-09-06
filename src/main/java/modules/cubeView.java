package modules;

import com.codingame.gameengine.core.AbstractSoloPlayer;
import com.codingame.gameengine.core.SoloGameManager;
import com.codingame.gameengine.core.Module;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.codingame.game.*;

@Singleton
public class cubeView implements Module {
    private SoloGameManager<AbstractSoloPlayer> gameManager;
    private Cube cube;
    private String move;
    private String state;

    @Inject
    cubeView(SoloGameManager<AbstractSoloPlayer> gameManager) {
        this.gameManager = gameManager;
        gameManager.registerModule(this);
    }

    /**
     * Called at the beginning of the game
     */
    @Override
    public final void onGameInit() {
    	this.setState();
    	this.gameManager.setViewGlobalData("cubeviewer", this.state);
    }

    /**
     * Called at the end of every turn, after the Referee's gameTurn()
     */
    @Override
    public final void onAfterGameTurn() {
    	this.gameManager.setViewData("cubeviewer", this.state + this.move);
    }
    
    /**
     * Called at the end of the game, after the Referee's onEnd()
     */
    @Override
    public final void onAfterOnEnd() {
    }
    
    public void setCube(Cube cube) {
    	this.cube = cube;
    }
    
    public void setMove(String action) {
    	this.move = action;
    }
    
    public void setState() {
    	this.state = "";
    	for (int face = 0; face < 6; face++) this.state += this.cube.state(face);
    }
}