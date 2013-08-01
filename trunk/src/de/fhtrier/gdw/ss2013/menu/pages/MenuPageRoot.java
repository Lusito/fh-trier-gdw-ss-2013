
package de.fhtrier.gdw.ss2013.menu.pages;

import de.fhtrier.gdw.ss2013.MainGame;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.world.World;
import de.fhtrier.gdw.ss2013.menu.IActionListener;
import de.fhtrier.gdw.ss2013.menu.MenuManager;
import de.fhtrier.gdw.ss2013.menu.MenuPage;
import de.fhtrier.gdw.ss2013.menu.MenuPageAction;
import de.fhtrier.gdw.ss2013.states.GameplayState;


public class MenuPageRoot extends MenuPage {

	public MenuPageRoot (final GameContainer container, final StateBasedGame _game, final MenuManager manager, final boolean ingame)
		throws SlickException {
		super(container, _game, manager, null, null, "root");
		
		Font font = AssetLoader.getInstance().getFont("verdana_46");
		
		float x = 100;
		float y = 480;
		float h = font.getLineHeight() * 1.2f;
		int i=2;
        
		if(!ingame) {
            addLeftAlignedButton("Symbion", x, y - h * (i--), font, 
                new IActionListener() {
                    public void onAction() {
                        MainGame.changeState(MainGame.GAMEPLAYSTATE);
                    }
                });
		}
		else {
            addLeftAlignedButton("Fortsetzen", x, y - h * (i--), font, 
                new IActionListener() {
                    public void onAction() {
                        GameplayState.hideMenu();
                    }
                });

            addLeftAlignedButton("Reset Game", x, y - h * (i--), font, 
                new IActionListener() {
                    public void onAction() {
                        World.getInstance().reset();
                    }
                });
		}
        
		MenuPage credits = new MenuPageCredits(container, _game, manager, this);
		addLeftAlignedButton("Credits", x, y - h * (i--), font, new MenuPageAction(manager, credits));
        
        addCenteredButton("Exit", 943, 710, font,
			new IActionListener() {
				public void onAction() {
					System.exit(0); // todo
				}
			});
	}

}
