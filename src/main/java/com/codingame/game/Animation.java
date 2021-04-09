package com.codingame.game;

import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.tooltip.TooltipModule;
import com.codingame.gameengine.module.toggle.ToggleModule;
import com.codingame.gameengine.module.entities.*;

public class Animation {
	private GraphicEntityModule graphics;
	private TooltipModule tooltips;
	private final static int SCREEN_WIDTH = 1920;
    private final static int SCREEN_HEIGHT = 1080;
    private final static int AVATAR_WIDTH = 250;
    private final static int AVATAR_HEIGHT = 250;
    private final static int AVATARX = 170;
    private final static int AVATARY = 275;
    private final static int NAMEX = AVATARX + (AVATAR_WIDTH / 2);
    private final static int NAMEY = AVATARY - 40;
    private final static int NAMESIZE = 70;
    private final static int NAMECOLOR = 0x4060ff;
    private final static int MSG_COLOR = 0xfeb67b;
    private final static int TURN_BLOCK_COLOR = 0x00c0ff;
    private final static int TURN_COLOR = 0xfa6607;
    private final static int TITLE_COLOR = 0xff1493;
    private final static int TITLE_SIZE = 100;
    private final static int SUBTITLE_COLOR = 0xf60000;
    private final static int SUBTITLE_SIZE = 50;
    private final static int MSG_TEXT_COLOR = 0xfa6607;
    private final static int MSG_SIZE = 50;
    private final static int BLOCK_SIZE = 20;
    private final static int CUBE_BLOCK_SIZE = 120;
    private final static int CUBE_BLOCK_SPACE_SIZE = 10;
    private int colors[] = {0xffffff, 0xff0000, 0xffe700, 0xff8100, 0x002bff ,0x09ff00};
    private int sx[] = {1560, 1625, 1690, 1495, 1560, 1560};
    private int sy[] = {650, 650, 650, 650, 585, 715};
    private int csx[] = {650, 950, 950, 650, 650, 650};
    private int csy[] = {360, 590, 590, 360, 350, 350};
    private int SkewXdiffer[] = {-30, -30, -30, -30, 100, 100};
    private int SkewYdiffer[] = {80, -80, -80, 80, -80, -80};
    private int skewXXdiffer = -30;
    private int skewYYdiffer = -50;
    private double SkewsX[] = {0, 0, 0, 0, 0.9, 0.9};
    private double Skewsy[] = {0.7, -0.7, -0.7, 0.7, -0.75, -0.75};
    private String playerId;
    private String playerAvatar;
    private RoundedRectangle player;
    private RoundedRectangle turn;
    private Rectangle structuredCube[][][];
    private RoundedRectangle ThDCube[][][];
    private Text msg;
    private Text move;
    private Text turnText;
    private Text turnScore;

    Animation(GraphicEntityModule graphicEntityModule, TooltipModule Tooltips, String playerName, String playerAvatarUrl) {
    	this.graphics = graphicEntityModule;
    	this.tooltips = Tooltips;
    	this.playerId = playerName;
    	this.playerAvatar = playerAvatarUrl;
    	this.structuredCube = new Rectangle[6][3][3];
    	this.ThDCube = new RoundedRectangle[6][3][3];
    }
    
    public void init(Cube cube, int score, ToggleModule toggles) {
    	this.graphics.createSprite().setImage("background.jpg").setX(0).setY(0).setBaseWidth(SCREEN_WIDTH).setBaseHeight(SCREEN_HEIGHT);
    	this.player = this.graphics.createRoundedRectangle().setX(AVATARX - 70).setY(AVATARY - 80).setWidth(AVATAR_WIDTH + 140).setHeight(AVATAR_HEIGHT + 350).setFillColor(MSG_COLOR);
    	this.graphics.createSprite().setImage(this.playerAvatar).setX(AVATARX).setY(AVATARY).setBaseWidth(AVATAR_WIDTH).setBaseHeight(AVATAR_HEIGHT);
    	this.graphics.createText().setText(this.playerId).setX(NAMEX).setY(NAMEY).setAnchor(0.5).setFontSize(NAMESIZE).setFillColor(NAMECOLOR);
    	this.turn = this.graphics.createRoundedRectangle().setX(SCREEN_WIDTH - AVATAR_WIDTH - AVATARX - 80).setY(AVATARY - 80).setWidth(AVATAR_WIDTH + 140).setHeight(AVATAR_HEIGHT + 350).setFillColor(TURN_BLOCK_COLOR);
    	this.graphics.createSprite().setImage("cube.png").setX(SCREEN_WIDTH - AVATAR_WIDTH - AVATARX - 10).setY(AVATARY -20).setBaseWidth(AVATAR_WIDTH + 20).setBaseHeight(AVATAR_HEIGHT + 40);
    	
    	for (int face = 0; face < 6; face ++) {
    		for (int y = 0; y < 3; y++) {
    			for (int x = 0; x < 3; x++) {
    				this.structuredCube[face][x][y] = this.graphics.createRectangle().setX(this.sx[face] + (x * BLOCK_SIZE)).setY(this.sy[face] + (BLOCK_SIZE * y)).setWidth(BLOCK_SIZE).setHeight(BLOCK_SIZE);
    				toggles.displayOnToggleState(this.structuredCube[face][x][y], "structuredCube", true);
    			}
    		}
    	}
    	for (int face = 0; face < 4; face ++) {
    		for (int y = 0; y < 3; y++) {
    			for (int x = 0; x < 3; x++) {
    				this.ThDCube[face][x][y] = this.graphics.createRoundedRectangle().setX(this.csx[face] + (x * CUBE_BLOCK_SIZE) + (x * this.SkewXdiffer[face]) + (x * CUBE_BLOCK_SPACE_SIZE)).setY(this.csy[face] + (y * CUBE_BLOCK_SIZE) + (x * this.SkewYdiffer[face]) + (y * CUBE_BLOCK_SPACE_SIZE)).setWidth(CUBE_BLOCK_SIZE).setHeight(CUBE_BLOCK_SIZE).setSkewX(this.SkewsX[face]).setSkewY(this.Skewsy[face]);
    				if (face < 2) toggles.displayOnToggleState(this.ThDCube[face][x][y], "cubeview", true);
    				else toggles.displayOnToggleState(this.ThDCube[face][x][y], "cubeview", false);
    			}
    		}
    	}
    	for (int face = 4; face < 6; face ++) {
    		for (int y = 0; y < 3; y++) {
    			for (int x = 0; x < 3; x++) {
    				this.ThDCube[face][x][y] = this.graphics.createRoundedRectangle().setX(this.csx[face] + (x * CUBE_BLOCK_SIZE) + (y * this.SkewXdiffer[face]) + (x * CUBE_BLOCK_SPACE_SIZE) + (x * skewXXdiffer)).setY(this.csy[face] + (y * CUBE_BLOCK_SIZE) + (x * this.SkewYdiffer[face]) + (y * CUBE_BLOCK_SPACE_SIZE) + (y * skewYYdiffer)).setWidth(CUBE_BLOCK_SIZE).setHeight(CUBE_BLOCK_SIZE).setSkewX(this.SkewsX[face]).setSkewY(this.Skewsy[face]);
    				if (face == 4) toggles.displayOnToggleState(this.ThDCube[face][x][y], "cubeview", true);
    				else toggles.displayOnToggleState(this.ThDCube[face][x][y], "cubeview", false);
    			}
    		}
    	}
    	this.updateStructuredCube(cube);
    	this.updateCubeTooltips(cube);

    	this.graphics.createText().setText("The Lost Child Episode-2").setX(SCREEN_WIDTH / 2).setY(70).setAnchor(0.5).setFontSize(TITLE_SIZE).setFillColor(TITLE_COLOR);
    	this.graphics.createText().setText("The Cube Enigma!").setX((SCREEN_WIDTH - AVATAR_WIDTH - AVATARX - 10) + AVATAR_WIDTH / 2).setY(AVATARY - 30).setAnchor(0.5).setFontSize(SUBTITLE_SIZE).setFillColor(SUBTITLE_COLOR);
    	
    	this.turnText = this.graphics.createText().setText("TURN : 0").setX(SCREEN_WIDTH - AVATAR_WIDTH - AVATARX - 65).setY(AVATARY + AVATAR_HEIGHT + 30).setAnchorX(0).setAnchorY(0.5).setFillColor(TURN_COLOR).setFontSize(30);
    	this.msg = this.graphics.createText().setText("").setX(AVATARX + (AVATAR_WIDTH / 2)).setY(AVATARY + AVATAR_HEIGHT + 130).setAnchor(0.5).setFillColor(MSG_TEXT_COLOR).setFontSize(MSG_SIZE);
    	this.move = this.graphics.createText().setText("").setX(SCREEN_WIDTH / 2 - 15).setY(1010).setFontSize(60).setFillColor(this.colors[5]).setAnchor(0.5);
    	this.turnScore = this.graphics.createText().setText("SCORE : " + score).setX(SCREEN_WIDTH - AVATARX + 45).setY(AVATARY + AVATAR_HEIGHT + 30).setAnchorX(1).setAnchorY(0.5).setFillColor(TURN_COLOR).setFontSize(30);
    	
    	this.tooltips.setTooltipText(this.player, "PLAYER\n----------------------------------------\nMESSAGE :\nMOVE :");
    	this.tooltips.setTooltipText(this.turn, "THE CUBE ENIGMA!\n--------------------------------------\nTURN INDEX : 0\nSCORE : " + score);
    	toggles.displayOnToggleState(this.msg, "message", true);
    }

    public void turn(int turn, Cube cube, int score, String msg, String move) {
    	this.updateStructuredCube(cube);
    	this.updateCubeTooltips(cube);
    	this.turnText.setText("TURN : " + turn);
    	this.turnScore.setText("SCORE : " + score);
    	this.msg.setText(msg);
    	this.move.setText(move);
    	this.tooltips.setTooltipText(this.player, "PLAYER\n----------------------------------------\nMESSAGE : " + msg + "\nMOVE : " + move);
    	this.tooltips.setTooltipText(this.turn, "THE CUBE ENIGMA!\n--------------------------------------\nTURN INDEX : " + turn + "\nSCORE : " + score);
    }
    
    private void updateStructuredCube(Cube cube) {
    	for (int face = 0; face < 6; face ++) {
    		for (int y = 0; y < 3; y++) {
    			for (int x = 0; x < 3; x++) {
    				this.structuredCube[face][x][y].setFillColor(this.colors[cube.faces[face][x][y]]).setRotation(0.1);
    				if (face < 2) this.ThDCube[face][x][y].setFillColor(this.colors[cube.faces[face][x][y]]);
    				else if (face ==  4) this.ThDCube[face][x][y].setFillColor(this.colors[cube.faces[face][y][2-x]]);
    				else {
    					if (face != 5) this.ThDCube[face][x][y].setFillColor(this.colors[cube.faces[face][2-x][2-y]]);
    					else this.ThDCube[5][x][y].setFillColor(this.colors[cube.faces[face][x][y]]);
    				}
    				this.graphics.commitEntityState(0.8, this.ThDCube[face][x][y]);
    				this.graphics.commitEntityState(0.8, this.structuredCube[face][x][y]);
    			}
    		}
    	}
    }
    
    private void updateCubeTooltips(Cube cube) {
    	String Colors = "wryobg";
    	for (int face = 0; face < 6; face ++) {
    		for (int y = 0; y < 3; y++) {
    			for (int x = 0; x < 3; x++) {
    				String TOOL = "BLOCK:\n---------------\nFace : " + face + "\nx : " + x + "\ny : " + y + "\nColor : " + Colors.charAt(cube.faces[face][x][y]);
    				if (face < 2) this.tooltips.setTooltipText(this.ThDCube[face][x][y], TOOL);
    				else if (face  == 4) this.tooltips.setTooltipText(this.ThDCube[face][2-y][x], TOOL);
    				else if (face != 5) this.tooltips.setTooltipText(this.ThDCube[face][2-x][2-y], TOOL);
    				else this.tooltips.setTooltipText(this.ThDCube[face][x][y], TOOL);
    			}
    		}
    	}
    }
}
