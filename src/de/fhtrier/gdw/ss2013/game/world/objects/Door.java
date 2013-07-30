/**
 * 
 * Door class
 * @author Justin, andra
 *
 * erzeugt Tür-Objekt mit Zustand I|O 
 */

package de.fhtrier.gdw.ss2013.game.world.objects;

import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.filter.Interactable;

public class Door extends Entity implements Interactable {

	private boolean open;
//	private AssetLoader a = AssetLoader.getInstance();
//	private Image img = a.getImage("Door");
	public Door(Vector2f position) {
		super(position);
		open = false;
	}

	public Door(Vector2f position, boolean open) {
		super(position);
		this.open = open;

	}

	public void open() {
		if (!open) {
			open = true;
		}
	}

	public boolean isOpen() {
		return open;
	}

}
