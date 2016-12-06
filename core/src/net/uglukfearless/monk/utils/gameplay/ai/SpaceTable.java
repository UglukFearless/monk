package net.uglukfearless.monk.utils.gameplay.ai;

import com.badlogic.gdx.math.Vector2;

import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.constants.PlacingCategory;
import net.uglukfearless.monk.utils.gameplay.ai.Situation;

/**
 * Created by Ugluk on 03.09.2016.
 */
public class SpaceTable {

    private static short [][] sTableNow = new short[4][2];
    private static short [][] sTableOld = new short[4][2];

    private static short sCategory;


    public static void setPit(float startPit, boolean isColumns) {

        if (isColumns) {
            sCategory = PlacingCategory.CATEGORY_PLACING_COLUMNS;
        } else {
            sCategory = PlacingCategory.CATEGORY_PLACING_PIT;
        }

        if (startPit<Constants.GAME_WIDTH&&startPit>0) {
            if (startPit>Constants.GAME_WIDTH*3f/4f) {
                sTableNow[3][0] = sCategory;
            } else if (startPit>Constants.GAME_WIDTH/2f) {
                sTableNow[2][0] = sCategory;
            } else if (startPit>Constants.GAME_WIDTH/4f) {
                sTableNow[1][0] = sCategory;
            } else {
                sTableNow[0][0] = sCategory;
            }
        }
    }

    public static short checkCell(int i, int j) {
        return sTableOld[i][j];
    }

    public static boolean checkCellIsEmpty(int i, int j) {
        return sTableOld[i][j]==0||sTableOld[i][j]== PlacingCategory.CATEGORY_PLACING_PIT;
    }

    public static void leaf() {
        for (int i=0; i<4; i++) {
            for (int j=0; j<2; j++) {
//                if (sTableNow[i][j] != 0) {
//                    System.out.print(i + " -- " + j + " -- " + sTableNow[i][j] + " | ");
//                }
                sTableOld[i][j] = sTableNow[i][j];
                sTableNow[i][j] = 0;
            }
//            System.out.println();
        }

//        System.out.println("---------------------");
    }

    public static short checkCell(float x, float y) {
        int j=0;
        if (y>Constants.GAME_HEIGHT/2 + 4) {
            j=1;
        }

        if (x<Constants.GAME_WIDTH&&x>0) {
            if (x>Constants.GAME_WIDTH*3f/4f) {
                return sTableOld[3][j];
            } else if (x>Constants.GAME_WIDTH/2f) {
                return sTableOld[2][j];
            } else if (x>Constants.GAME_WIDTH/4f) {
                return sTableOld[1][j];
            } else {
                return sTableOld[0][j];
            }
        } else {
            return -1;
        }
    }

    public static boolean checkPit(float x) {

        if (x<Constants.GAME_WIDTH&&x>0) {
            if (x>Constants.GAME_WIDTH*3f/4f) {
                return (sTableOld[3][0]&(PlacingCategory.CATEGORY_PLACING_PIT|PlacingCategory.CATEGORY_PLACING_COLUMNS))!=0;
            } else if (x>Constants.GAME_WIDTH/2f) {
                return (sTableOld[2][0]&(PlacingCategory.CATEGORY_PLACING_PIT|PlacingCategory.CATEGORY_PLACING_COLUMNS))!=0;
            } else if (x>Constants.GAME_WIDTH/4f) {
                return (sTableOld[1][0]&(PlacingCategory.CATEGORY_PLACING_PIT|PlacingCategory.CATEGORY_PLACING_COLUMNS))!=0;
            } else {
                return (sTableOld[0][0]&(PlacingCategory.CATEGORY_PLACING_PIT|PlacingCategory.CATEGORY_PLACING_COLUMNS))!=0;
            }
        } else {
            return false;
        }
    }

    public static boolean checkShootWay(Vector2 position, short categoryBit) {
        int j=0;
        if (position.y>Constants.GROUND_HEIGHT/2 + 1) {
            j=1;
        }
        short cellContent = 0;
        for (int i=3;i>-1;i--) {

            if (position.x>Constants.GAME_WIDTH*(float)i/4f) {
                cellContent = 0;
                for (int ii=i;ii>0;ii--) {
//                    System.out.println("fill shell way " + sTableOld[ii][j]);
                    cellContent = (short) (cellContent|sTableOld[ii][j]&PlacingCategory.CATEGORY_ANY_OBJECT);
                    if (ii==i) {
                        cellContent = (short) (cellContent&(~categoryBit));
                    }
                }
                return cellContent==0;
            }
        }

        return false;
    }

    public static void setCell(float x, float y,float width,short categoryBit) {
        int j=0;
        if (y>Constants.GROUND_HEIGHT/2f + 1) {
            j=1;
        }
        for (int i=3;i>-1;i--) {
            if (x + width/2>Constants.GAME_WIDTH*(float)i/4f
                    &&x + width/2<Constants.GAME_WIDTH*(float)(i+1)/4f) {
                sTableNow[i][j] = (short)(sTableNow[i][j]|categoryBit);
            }
        }
    }

    public static Situation getSituation(float x, float y, float width ,short categoryBit, Situation situation) {

        situation.reset();

        if (x<Constants.GAME_WIDTH) {

            situation.stop = false;
            situation.stopFly = false;

            int j=0;

            if (y>Constants.GROUND_HEIGHT/2f + 2f) {
                j=1;
            }

            short cellContent = 0;

            for (int i=3;i>-1;i--) {

                if (x + width/2>Constants.GAME_WIDTH*(float)i/4f
                        &&x + width/2<Constants.GAME_WIDTH*(float)(i+1)/4f) {
                    sTableNow[i][j] = (short) (sTableNow[i][j] | categoryBit);
                }

                if (x>Constants.GAME_WIDTH*(float)i/4f&&x<Constants.GAME_WIDTH*(float)(i+1)/4f) {
                    cellContent = 0;
                    for (int ii=i;ii>-1;ii--) {
                        cellContent = (short) (cellContent|sTableOld[ii][j] &(PlacingCategory.CATEGORY_ANY_OBJECT|PlacingCategory.CATEGORY_PLACING_COLUMNS));
                        if (ii==i) {
                            cellContent = (short) (cellContent&(~categoryBit));
                        }
                    }
                    situation.shoot = (cellContent==0);

                    if (j==0) {
                        situation.jump = (((sTableOld[i][j]&PlacingCategory.CATEGORY_ANY_OBSTACLE)!=0)
                                && (sTableOld[i][1]&PlacingCategory.CATEGORY_ANY_OBSTACLE)==0)
                                || ((sTableOld[i][0]&PlacingCategory.ANY_PIT)!=0);
                    }

                    situation.stop = (((sTableOld[i][j]&PlacingCategory.CATEGORY_ANY_OBSTACLE)!=0)
                            ||((sTableOld[i][0]&PlacingCategory.ANY_PIT)!=0));
                    situation.stopFly = ((sTableOld[i][j]&PlacingCategory.CATEGORY_ANY_OBSTACLE)!=0);

                    if (!situation.stop&&checkCell(x-5,y)!=-1) {
                        situation.start = (((checkCell(x-5,y)&PlacingCategory.CATEGORY_ANY_OBSTACLE)==0)
                        &&((checkCell(x-5,0)&PlacingCategory.ANY_PIT)==0));
                    }
                    if (!situation.stopFly&&checkCell(x-5,y)!=-1) {
                        situation.startFly = ((checkCell(x-5,y)&PlacingCategory.CATEGORY_ANY_OBSTACLE)==0);
                    }

                    if ((x<Constants.RUNNER_X + Constants.RUNNER_WIDTH + 2)&&(x>1.5f)) {
                        situation.strike = true;
                    }

                    situation.strongBeat = situation.stop&&(((sTableOld[i][j]&PlacingCategory.CATEGORY_HARD_OBSTACLE)==0)
                            &&((sTableOld[i][0]&PlacingCategory.ANY_PIT)==0));
                }

            }

        } else {
            situation.stop = true;
            situation.stopFly = true;
        }

        return situation;
    }

    public static void printSpaceTable() {

        for (int j=0;j<2;j++) {
            System.out.print("|");
            for (int i=0;i<4;i++) {
                System.out.print(" " + sTableOld[i][j] + " |");
            }
            System.out.println();
        }
    }

}
