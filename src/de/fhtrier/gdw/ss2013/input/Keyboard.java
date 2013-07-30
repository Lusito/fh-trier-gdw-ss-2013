package de.fhtrier.gdw.ss2013.input;

import java.util.HashSet;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.util.Log;

/*
 * Team Input
 * Dennis, Valentin
 * 
 */

public class Keyboard extends InputDevice {

	public Keyboard(GameContainer gc) {
		super(gc);
	}

	@Override
	public void update() {

		HashSet<ACTION> actions = new HashSet<>(keymapping.values());
		for (int key : keymapping.keySet()) {
			if (container.getInput().isKeyDown(key)) {
				ACTION action = keymapping.get(key);
				if (actions.contains(action)) {
					actions.remove(action);
					doAction(action);
				}
			}
		}
	}


	@Override
	public void loadKeymapping() {
		keymapping.put(Input.KEY_D, ACTION.MOVEFORWARD);
		keymapping.put(Input.KEY_RIGHT, ACTION.MOVEFORWARD);

		keymapping.put(Input.KEY_A, ACTION.MOVEBACKWARD);
		keymapping.put(Input.KEY_LEFT, ACTION.MOVEBACKWARD);

		keymapping.put(Input.KEY_W, ACTION.JUMP);
		keymapping.put(Input.KEY_UP, ACTION.JUMP);

		keymapping.put(Input.KEY_S, ACTION.ACTION);
		keymapping.put(Input.KEY_DOWN, ACTION.ACTION);
		
		Log.debug(String.valueOf(ACTION.MOVEBACKWARD.ordinal()));
	}

}