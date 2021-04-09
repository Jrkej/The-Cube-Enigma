import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Jrke's Special
 * -The Lost Child Ep2-The Cube Enigma
 * -Score = sum(for each face(total no. of correct colored block)^2) + (400 - TurnIndex)
 * -Score as much as points you can or solve the cube as early as possible
 **/
class Agent1 {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        // game loop
        while (true) {
            int score = in.nextInt(); // Your current score based on current cube's state and turnIndex
            int turnIndex = in.nextInt(); // the Index of this turn starts from 0
            if (in.hasNextLine()) {
                in.nextLine();
            }
            for (int i = 0; i < 6; i++) {
                String face = in.nextLine(); // the (wryobg) representation of the one side of face of cube
                System.err.println(face);
            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            System.out.println("R"); // The desired move you wanna play + message
        }
    }
}