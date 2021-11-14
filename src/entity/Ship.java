package entity;

import java.awt.Color;
import java.util.Set;
import java.util.logging.Logger;

import engine.Cooldown;
import engine.Core;
import engine.DrawManager.SpriteType;

/**
 * Implements a ship, to be controlled by the player.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class Ship extends Entity {

	/** Time between shots. */
	private static int SHOOTING_INTERVAL = 750;
	/** Speed of the bullets shot by the ship. */
	private static final int BULLET_SPEED = -6;
	/** Movement of the ship for each unit of time. */
	private static final int SPEED = 2;

	private static int FASTER_SHOOTING_INTERVAL = 150;
	private static int FASTER_SHOOTING_ITEM_DURATION = 2000;
	private static int shipItemState = 0;
	private static final Logger LOGGER = Logger.getLogger(Core.class
			.getSimpleName());

	private Cooldown shipFasterItemCooldown;
	/** Minimum time between shots. */
	private Cooldown shootingCooldown;
	/** Time spent inactive between hits. */
	private Cooldown destructionCooldown;

	/**
	 * Constructor, establishes the ship's properties.
	 * 
	 * @param positionX
	 *            Initial position of the ship in the X axis.
	 * @param positionY
	 *            Initial position of the ship in the Y axis.
	 */
	public Ship(final int positionX, final int positionY) {
		super(positionX, positionY, 13 * 2, 8 * 2, Color.GREEN);

		this.spriteType = SpriteType.Ship;
		this.shootingCooldown = Core.getCooldown(SHOOTING_INTERVAL);
		this.destructionCooldown = Core.getCooldown(1000);
		this.shipFasterItemCooldown = Core.getCooldown(FASTER_SHOOTING_ITEM_DURATION);
	}

	public void eatFast(){
		LOGGER.info("Fast_Item_Gained.");
		if (this.shipItemState % 2 == 0){
			this.shipItemState += 1;
			this.shootingCooldown = Core.getCooldown(FASTER_SHOOTING_INTERVAL);
			this.shootingCooldown.reset();
			this.shipFasterItemCooldown.reset();
		}
		else {
			this.shipFasterItemCooldown.reset();
		}
	}
	private void doneFast(){ //(shipItemState % 2 == 1) &&
		if((this.shipItemState % 2 == 1) && this.shipFasterItemCooldown.checkFinished()){
			LOGGER.info("Fast_Item_Finished.");
			this.shipItemState -= 1;
			this.shootingCooldown = Core.getCooldown(SHOOTING_INTERVAL);
			this.shootingCooldown.reset();
		}
	}

	/**
	 * Moves the ship speed uni ts right, or until the right screen border is
	 * reached.
	 */
	public final void moveRight() {
		this.positionX += SPEED;
	}

	/**
	 * Moves the ship speed units left, or until the left screen border is
	 * reached.
	 */
	public final void moveLeft() {
		this.positionX -= SPEED;
	}

	/**
	 * Shoots a bullet upwards.
	 * 
	 * @param bullets
	 *            List of bullets on screen, to add the new bullet.
	 * @return Checks if the bullet was shot correctly.
	 */
	public final boolean shoot(final Set<Bullet> bullets) {
		if (this.shootingCooldown.checkFinished()) { //this.shootingCooldown.checkFinished()
			this.shootingCooldown.reset();
			bullets.add(BulletPool.getBullet(positionX + this.width / 2,
					positionY, BULLET_SPEED));
			return true;
		}
		return false;
	}

	/**
	 * Updates status of the ship.
	 */
	public final void update() {
		if (!this.destructionCooldown.checkFinished())
			this.spriteType = SpriteType.ShipDestroyed;
		else
			this.spriteType = SpriteType.Ship;
		doneFast();
	}

	/**
	 * Switches the ship to its destroyed state.
	 */
	public final void destroy() {
		this.destructionCooldown.reset();
	}

	/**
	 * Checks if the ship is destroyed.
	 * 
	 * @return True if the ship is currently destroyed.
	 */
	public final boolean isDestroyed() {
		return !this.destructionCooldown.checkFinished();
	}

	/**
	 * Getter for the ship's speed.
	 * 
	 * @return Speed of the ship.
	 */
	public final int getSpeed() {
		return SPEED;
	}
}
