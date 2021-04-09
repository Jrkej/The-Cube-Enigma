package com.codingame.game;
//import com.codingame.gameengine.core.AbstractMultiplayerPlayer;
import com.codingame.gameengine.core.AbstractSoloPlayer;

//Uncomment the line below and comment the line under it to create a Multiplayer Game
//public class Player extends AbstractMultiplayerPlayer {
public class Player extends AbstractSoloPlayer {

 @Override
 public int getExpectedOutputLines() {
 	//No of lines of output the player should print 
     return 1;
 }
}