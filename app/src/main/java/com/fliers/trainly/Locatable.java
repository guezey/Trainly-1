package com.fliers.trainly;

/**
 * Interface giving x and y coordinate management to implemented classes
 * @author Alp Afyonluoğlu
 * @version 23.04.2021
 */
public interface Locatable {

    /**
     * Sets x and y coordinates
     * @param posX x coordinate
     * @param posY y coordinate
     */
    void setPos( int posX, int posY);

    /**
     * Getter method for x coordinate
     * @return x coordinate
     */
    int getPosX();

    /**
     * Getter method for y coordinate
     * @return y coordinate
     */
    int getPosY();
}
