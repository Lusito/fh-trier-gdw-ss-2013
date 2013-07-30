package de.fhtrier.gdw.ss2013.game.world.objects;

import org.jbox2d.dynamics.Fixture;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.physics.ICollidable;

/**
 * Switch class
 * 
 * @author Kevin, Georg
 * 
 */
public class Button extends Switch implements ICollidable {
	private boolean collision, lastFrameCollision;

	public Button(Vector2f pos) {
		super(pos.copy());
		collision = lastFrameCollision = false;
	}

	public Button() {
		this(new Vector2f());
	}

	@Override
	public void render(GameContainer container, Graphics g)
	        throws SlickException {
	}
	
	@Override
	public void onCollision(Entity e) {
		if (e instanceof Astronaut || e instanceof Box) {
			this.setSwitch(true);
			collision = true;
		}
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		if (lastFrameCollision) {
			this.setSwitch(true);
		} else {
			this.setSwitch(false);
		}
		lastFrameCollision = collision;
		collision = false;
	}

	@Override
	public Fixture getFixture() {
		// TODO Auto-generated method stub
		return null;
	}
}
