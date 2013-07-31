package de.fhtrier.gdw.ss2013.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.MainGame;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.cheats.Cheats;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.gui.HUD;
import de.fhtrier.gdw.ss2013.input.InputManager;

/**
 * Gameplay state
 */
public class GameplayState extends BasicGameState {

    private World world;
    private Cheats cheats;
    private Font font;
    private HUD hud;
    private InputManager inputManager;

    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
        InputManager.init(container);
        inputManager = InputManager.getInstance();
        world = new World(container, game);
        cheats = new Cheats(world);
        font = AssetLoader.getInstance().getFont("verdana_46");
        hud = new HUD(container, world);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException {
        g.setBackground(Color.black);
        g.setColor(Color.white);

        world.render(container, g);
        hud.render(container, game, g);

    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta)
            throws SlickException {
        ((MainGame) game).checkFullscreenToggle();
        inputManager.update(delta);
        // world.getPhysicsManager().update(container, delta);
        world.update(container, delta);
        hud.update(container, game, delta);
        cheats.update(container, game, delta);

    }

    @Override
    public void enter(GameContainer container, StateBasedGame game)
            throws SlickException {
        world.onEnter();
        // world.getPhysicsManager().reset();
    }

    @Override
    public int getID() {
        return MainGame.GAMEPLAYSTATE;
    }

    @Override
    public void keyReleased(int key, char c) {
        cheats.addKey(c);
    }

}
