package de.fhtrier.gdw.ss2013.states;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.MainGame;
import de.fhtrier.gdw.ss2013.input.InputManager;
import de.fhtrier.gdw.ss2013.physics.PhysicsManager;
import de.fhtrier.gdw.ss2013.physics.RectanglePhysicsObject;
import de.fhtrier.gdw.ss2013.physics.test.TestWorld;

public class PhysicsTestState extends BasicGameState{

	private InputManager inputManager;
	private TestWorld world;
	public RectanglePhysicsObject rec;

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	    InputManager.init(container);
	    inputManager = InputManager.getInstance();
		world = new TestWorld(container, game);
		
		//Physic Test Objects
		rec = new RectanglePhysicsObject(BodyType.DYNAMIC, new Vec2(50.0f, 50.0f), new Vec2(400.0f, 400.0f));
		
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		world.render(container, g);
		
	}

	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		MainGame.checkFullscreenToggle();
		world.update(container, delta);
		inputManager.update(delta);
		PhysicsManager.getInstance().update(container, delta);
		
	}

	public int getID() {
		return MainGame.PHYSIC_TEST;
	}
	
	public void keyReleased(int key, char c) {

		//triggers PhysicTestState
		if (key == Input.KEY_EQUALS || key == Input.KEY_P) {
			MainGame.changeState(MainGame.PHYSIC_TEST);
		}
	}

}