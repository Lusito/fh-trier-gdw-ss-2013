package de.fhtrier.gdw.ss2013.game.world.enemies;

import org.jbox2d.dynamics.Fixture;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.player.Player;
import de.fhtrier.gdw.ss2013.physics.ICollidable;

/**
 * Ground Enemy Class
 * 
 * @author Kevin, Georg
 * 
 */
public class GroundEnemy extends AbstractEnemy implements ICollidable {
    private float movetime, lasttime, hunttime;
    private boolean intelligence, normalMode, huntMode, waitMode;
    private Player p;
    private Image img;
    private AssetLoader a = AssetLoader.getInstance();
    private Vector2f lastvelocity, lastposition;

    private String rechts="animtest", links="animtest", current="animtest";
    
    
	public GroundEnemy(Vector2f pos, Vector2f velo, float dmg) {
		super(pos.copy(), velo.copy(), dmg);
		img = a.getImage("groundEnemy");
/*		if (Math.random() >= 0.5) {
		    intelligence = true;
		} else {
		    intelligence = false;
		}*/
		intelligence = true;
		normalMode = true;
		huntMode = waitMode = false;
		hunttime = 0;
	    setLeft_animation(links);
	    setRight_animation(rechts);
	    setCurrent(current);
	}

	public GroundEnemy() {
		this(new Vector2f(), new Vector2f(), 0);
		setLeft_animation(links);
	    setRight_animation(rechts);
	    setCurrent(current);

	}

	public GroundEnemy(Vector2f pos) {
		this(pos.copy(), new Vector2f(2.5f,0f), 0);
		setLeft_animation(links);
	    setRight_animation(rechts);
	    setCurrent(current);

	}

	@Override
	public void onCollision(Entity e) {
		if (e instanceof Astronaut) {
			((Astronaut) e).setOxygen(((Astronaut) e).getOxygen()
					- this.getDamage());
		}
	}

	@Override
	public Fixture getFixture() {
		// TODO Auto-generated method stub
		return null;
	}
	public void render(GameContainer container, Graphics g)
	         throws SlickException {
	    g.drawImage(img, this.position.x-(img.getWidth()/2), this.position.y-(img.getHeight()/2));
	    // g.drawString(this.hashCode(), position.x, position.y);
	}

    public void update(GameContainer container, int delta)
            throws SlickException {
        // float dt = delta / 1000.f;
        movetime += delta;
        // TODO clamp dt if dt > 1/60.f ?
        this.getPosition().x += this.getVelocity().x;
        if (!intelligence) {
            if (movetime >= 3000) {
                this.getVelocity().x = -this.getVelocity().x;
                movetime = movetime % 3000;
            }
        } else {
            if(normalMode) {
                if (calcPlayerDistance(p) < 300) {
                    normalMode = false;
                    huntMode = true;
                } else {
                    if (movetime >= 3000) {
                        this.getVelocity().x = -this.getVelocity().x;
                        movetime = movetime % 3000;
                    }
                }
            } else if (huntMode) {
                if(hunttime == 0) {
                    lastvelocity = this.getVelocity().copy();
                    lastposition = this.getPosition().copy();
                    lasttime = movetime;
                }
                hunttime += delta;
                if(hunttime >= 3000) {
                   huntMode = false;
                   waitMode = true;
                } else {
                    this.getVelocity().x = p.getVelocity().x;
                    System.out.println(this.getVelocity().x *= calcPlayerDirection(p).x);
                }
            } else if (waitMode) {
                if(Math.abs(this.getPosition().x - lastposition.x) < 0.5f) {
                    normalMode = true;
                    waitMode = false;
                    hunttime = 0;
                    this.movetime = lasttime;
                    this.getVelocity().x = this.lastvelocity.x;
                    this.getPosition().x = this.lastposition.x;
                 } else {
                     this.getVelocity().x = Math.abs(lastvelocity.x) * (new Vector2f(lastposition.x-this.getPosition().x, 0.0f).normalise().x);
                 }
            }
        }
    }
    private Vector2f calcPlayerDirection(Player player) {
        Vector2f direction = new Vector2f();
        direction = calcPlayerPosition(player);
        direction.normalise();
        return direction;
    }
    private float calcPlayerDistance(Player player) {
        Vector2f direction = new Vector2f();
        direction = calcPlayerPosition(player);
        return (float) Math.sqrt((direction.x*direction.x)+(direction.y*direction.y));
    }
    private Vector2f calcPlayerPosition(Player player) {
        Vector2f direction = new Vector2f();
        direction.x = player.getPosition().x - this.position.x;
        direction.y = player.getPosition().y - this.position.y;
        return direction;
    }
    public void setReferences(Player p) {
        this.p = p;
    }
}