package entity;

import engine.DrawManager;

import java.awt.*;

public class Dropitem extends Entity{

    private int speed;

    public int getItemNumber() {
        return itemNumber;
    }

    private int itemNumber;
    /**
     * Constructor, establishes the entity's generic properties.
     *
     * @param positionX Initial position of the entity in the X axis.
     * @param positionY Initial position of the entity in the Y axis.
     * @param width     Width of the entity.
     * @param height    Height of the entity.
     * @param color
     */
    public Dropitem(final int positionX,final int positionY,final int speed,final int itemNumber) {
        super(positionX, positionY, 3 * 2, 5 * 2);
        if(itemNumber == 0) {
            this.setColor(Color.MAGENTA);
        }
        if(itemNumber == 1) {
            this.setColor(Color.ORANGE);
        }
        this.itemNumber = itemNumber;
        this.speed = speed;
        setSprite();
    }

    private void setSprite() {
        this.spriteType = DrawManager.SpriteType.Dropped;
    }

    /**
     * Updates the bullet's position.
     */
    public final void update() {
        this.positionY += this.speed;
    }

    /**
     * Setter of the speed of the bullet.
     *
     * @param speed
     *            New speed of the bullet.
     */
    public final void setSpeed(final int speed) {
        this.speed = speed;
    }

    /**
     * Getter for the speed of the bullet.
     *
     * @return Speed of the bullet.
     */
    public final int getSpeed() {
        return this.speed;
    }
}
