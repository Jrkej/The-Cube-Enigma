package com.codingame.game;

class Cube {
    public int faces[][][] = new int[6][3][3];
    private int temp[][] = new int[3][3];
    private int ts[][] = new int[4][3];
	private String colors[] = {"w", "r", "y", "o", "b", "g"};

    Cube() {
        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 3; y++) {
                for (int z = 0; z < 3; z++) faces[x][y][z] = x;
            }
        }
    }

    public void load(String state) {
        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 3; y++) {
                for (int z = 0; z < 3; z++) {
                	int index = 0;
                	for (int i = 0; i < 6; i++) {
                		if (this.colors[i].equals(String.valueOf(state.charAt((x * 9) + (y * 3) + z)))) index = i;
                	}
                	this.faces[x][z][y] = index;
                }
            }
        }
    }
    
    public boolean isCubeSolved() {
        for (int face = 0; face < 6; face++) {
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    if (faces[face][x][y] != faces[face][1][1]) return false;
                }
            }
        }
        return true;
    }

    private void FaceClockwise(int face) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) temp[x][y] = faces[face][x][y];
        }
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) faces[face][x][y] = temp[y][2-x];
        }
    }

    private void FaceAntiClockwise(int face) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) temp[x][y] = faces[face][x][y];
        }
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) faces[face][x][y] = temp[2-y][x];
        }
    }

    private void F(int times) {
        for (int t = 0; t < times; t++) {
            FaceClockwise(0);
            for (int i = 0; i < 3; i++) {
                ts[0][i] = faces[1][0][i];
                ts[1][i] = faces[4][i][2];
                ts[2][i] = faces[3][2][i];
                ts[3][i] = faces[5][i][0];
            }
            for (int i = 0; i < 3; i++) {
                faces[1][0][i] = ts[1][i];
                faces[4][i][2] = ts[2][2-i];
                faces[3][2][i] = ts[3][i];
                faces[5][i][0] = ts[0][2-i];
            }
        }
    }

    private void Fi() {
        FaceAntiClockwise(0);
        for (int i = 0; i < 3; i++) {
            ts[0][i] = faces[1][0][i];
            ts[1][i] = faces[4][i][2];
            ts[2][i] = faces[3][2][i];
            ts[3][i] = faces[5][i][0];
        }
        for (int i = 0; i < 3; i++) {
            faces[3][2][i] = ts[1][2-i];
            faces[5][i][0] = ts[2][i];
            faces[1][0][i] = ts[3][2-i];
            faces[4][i][2] = ts[0][i];
        }
    }

    private void R(int times) {
        for (int t = 0; t < times; t++) {
            FaceClockwise(1);
            for (int i = 0; i < 3; i++) {
                ts[0][i] = faces[2][0][i];
                ts[1][i] = faces[4][2][i];
                ts[2][i] = faces[0][2][i];
                ts[3][i] = faces[5][2][i];
            }
            for (int i = 0; i < 3; i++) {
                faces[2][0][i] = ts[1][2-i];
                faces[4][2][i] = ts[2][i];
                faces[0][2][i] = ts[3][i];
                faces[5][2][i] = ts[0][2-i];
            }
        }
    }

    private void Ri() {
        FaceAntiClockwise(1);
        for (int i = 0; i < 3; i++) {
            ts[0][i] = faces[2][0][i];
            ts[1][i] = faces[4][2][i];
            ts[2][i] = faces[0][2][i];
            ts[3][i] = faces[5][2][i];
        }
        for (int i = 0; i < 3; i++) {
            faces[0][2][i] = ts[1][i];
            faces[5][2][i] = ts[2][i];
            faces[2][0][i] = ts[3][2-i];
            faces[4][2][i] = ts[0][2-i];
        }
    }

    private void L(int times) {
        for (int t = 0; t < times; t++) {
            FaceClockwise(3);
            for (int i = 0; i < 3; i++) {
                ts[0][i] = faces[0][0][i];
                ts[1][i] = faces[4][0][i];
                ts[2][i] = faces[5][0][i];
                ts[3][i] = faces[2][2][i];
            }
            for (int i = 0; i < 3; i++) {
                faces[2][2][i] = ts[2][2-i];
                faces[0][0][i] = ts[1][i];
                faces[5][0][i] = ts[0][i];
                faces[4][0][i] = ts[3][2-i];
            }
        }
    }

    private void Li() {
        FaceAntiClockwise(3);
        for (int i = 0; i < 3; i++) {
            ts[0][i] = faces[0][0][i];
            ts[1][i] = faces[4][0][i];
            ts[2][i] = faces[5][0][i];
            ts[3][i] = faces[2][2][i];
        }
        for (int i = 0; i < 3; i++) {
            faces[2][2][i] = ts[1][2-i];
            faces[0][0][i] = ts[2][i];
            faces[5][0][i] = ts[3][2-i];
            faces[4][0][i] = ts[0][i];
        }
    }

    private void D(int times) {
        for (int t = 0; t < times; t++) {
            FaceClockwise(5);
            for (int i = 0; i < 3; i++) {
                ts[0][i] = faces[0][i][2];
                ts[1][i] = faces[3][i][2];
                ts[2][i] = faces[1][i][2];
                ts[3][i] = faces[2][i][2];
            }
            for (int i = 0; i < 3; i++) {
                faces[0][i][2] = ts[1][i];
                faces[2][i][2] = ts[2][i];
                faces[3][i][2] = ts[3][i];
                faces[1][i][2] = ts[0][i];
            }
        }
    }

    private void Di() {
        FaceAntiClockwise(5);
        for (int i = 0; i < 3; i++) {
            ts[0][i] = faces[0][i][2];
            ts[1][i] = faces[3][i][2];
            ts[2][i] = faces[1][i][2];
            ts[3][i] = faces[2][i][2];
        }
        for (int i = 0; i < 3; i++) {
            faces[2][i][2] = ts[1][i];
            faces[0][i][2] = ts[2][i];
            faces[1][i][2] = ts[3][i];
            faces[3][i][2] = ts[0][i];
        }
    }

    private void U(int times) {
        for (int t = 0; t < times; t++) {
            FaceClockwise(4);
            for (int i = 0; i < 3; i++) {
                ts[0][i] = faces[0][i][0];
                ts[1][i] = faces[3][i][0];
                ts[2][i] = faces[1][i][0];
                ts[3][i] = faces[2][i][0];
            }
            for (int i = 0; i < 3; i++) {
                faces[2][i][0] = ts[1][i];
                faces[0][i][0] = ts[2][i];
                faces[1][i][0] = ts[3][i];
                faces[3][i][0] = ts[0][i];
            }
        }
    }

    private void Ui() {
        FaceAntiClockwise(4);
        for (int i = 0; i < 3; i++) {
            ts[0][i] = faces[0][i][0];
            ts[1][i] = faces[3][i][0];
            ts[2][i] = faces[1][i][0];
            ts[3][i] = faces[2][i][0];
        }
        for (int i = 0; i < 3; i++) {
            faces[0][i][0] = ts[1][i];
            faces[2][i][0] = ts[2][i];
            faces[3][i][0] = ts[3][i];
            faces[1][i][0] = ts[0][i];
        }
    }
    
    private void B(int times) {
        for (int t = 0; t < times; t++) {
            FaceClockwise(2);
            for (int i = 0; i < 3; i++) {
                ts[0][i] = faces[5][i][2];
                ts[1][i] = faces[3][0][i];
                ts[2][i] = faces[1][2][i];
                ts[3][i] = faces[4][i][0];
            }
            for (int i = 0; i < 3; i++) {
                faces[5][i][2] = ts[1][i];
                faces[4][i][0] = ts[2][i];
                faces[3][0][i] = ts[3][2-i];
                faces[1][2][i] = ts[0][2-i];
            }
        }
    }

    private void Bi() {
        FaceAntiClockwise(2);
        for (int i = 0; i < 3; i++) {
            ts[0][i] = faces[5][i][2];
            ts[1][i] = faces[3][0][i];
            ts[2][i] = faces[1][2][i];
            ts[3][i] = faces[4][i][0];
        }
        for (int i = 0; i < 3; i++) {
            faces[4][i][0] = ts[1][2-i];
            faces[5][i][2] = ts[2][2-i];
            faces[1][2][i] = ts[3][i];
            faces[3][0][i] = ts[0][i];
        }
    }

    public int value() {
        int value = 0;
        for (int face = 0; face < 6; face++) {
            int corr = 0;
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) corr += (faces[face][x][y] == faces[face][1][1]?1:0);
            }
            value += corr * corr;
        }
        return value;
    }
    
    public String state(int face) {
    	String state = "";
    	for (int x = 0; x < 3; x++) {
    		for (int y = 0; y < 3; y++) state += colors[faces[face][y][x]];
    	}
    	return state;
    }

    public boolean play(String move) {
    	if (move.equals("F")) F(1);
        else if (move.equals("R")) R(1);
        else if (move.equals("U")) U(1);
        else if (move.equals("B")) B(1);
        else if (move.equals("L")) L(1);
        else if (move.equals("D")) D(1);
        else if (move.equals("Fi")) Fi();
        else if (move.equals("Ri")) Ri();
        else if (move.equals("Ui")) Ui();
        else if (move.equals("Bi")) Bi();
        else if (move.equals("Li")) Li();
        else if (move.equals("Di")) Di();
        else if (move.equals("F2")) F(2);
        else if (move.equals("R2")) R(2);
        else if (move.equals("U2")) U(2);
        else if (move.equals("B2")) B(2);
        else if (move.equals("L2")) L(2);
        else if (move.equals("D2")) D(2);
        else return false;
        return true;
    }
}

