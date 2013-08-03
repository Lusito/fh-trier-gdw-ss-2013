package de.fhtrier.gdw.ss2013.game.world;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.commons.tiled.TiledMap;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.camera.CameraInfo;
import de.fhtrier.gdw.ss2013.game.camera.ThreePointCamera;
import de.fhtrier.gdw.ss2013.game.player.Alien;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.score.ScoreCounter;
import de.fhtrier.gdw.ss2013.input.InputManager;
import de.fhtrier.gdw.ss2013.physix.PhysixManager;
import de.fhtrier.gdw.ss2013.renderer.DynamicParticleSystem;
import de.fhtrier.gdw.ss2013.renderer.MapRenderer;
import de.fhtrier.gdw.ss2013.settings.DebugModeStatus;
import de.fhtrier.gdw.ss2013.sound.SoundLocator;
import de.fhtrier.gdw.ss2013.sound.services.DefaultSoundPlayer;

public class World {

    private boolean shallBeReseted;

    private TiledMap map;
    private MapRenderer mapRender;
    private ThreePointCamera camera;
    private Astronaut astronaut;
    private Alien alien;
    private static ScoreCounter scoreCounter;

    private static World instance;

    public boolean debugDraw = DebugModeStatus.isTest();

    private EntityManager entityManager;
    private final PhysixManager physicsManager;

    private ArrayList<DynamicParticleSystem> particleList;
    private final GameContainer container;

    private String levelName;

    public World(GameContainer container, StateBasedGame game) {
        this.container = container;
        instance = this;
        levelName = DebugModeStatus.getLevelName();
        map = null;
        entityManager = new EntityManager();
        physicsManager = new PhysixManager(container);
        particleList = new ArrayList<>();
        scoreCounter = new ScoreCounter();
        reset();
    }

    private void reset() {
        entityManager.reset();
        physicsManager.reset();
        particleList.clear();

        try {
            map = AssetLoader.getInstance().loadMap(levelName);
            LevelLoader.load(map, entityManager, physicsManager);

            mapRender = new MapRenderer(map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        CameraInfo info = new CameraInfo(5, map);
        camera = new ThreePointCamera(info);

        // physic debug stuff
        physicsManager.enableDebugDraw(container);

        Vector2f startpos = LevelLoader.getStartPosition();

        astronaut = entityManager.createEntity(Astronaut.class);
        astronaut.setOrigin(startpos);
        astronaut.setFootSteepSound(map.getIntProperty("footStepSound", 0));

        // astronaut.setPhysicsObject(new
        // RectanglePhysicsObject(BodyType.DYNAMIC, new Vec2(95,105), new
        // Vec2(astronaut.getPosition().x,astronaut.getPosition().y)));

        alien = entityManager.createEntity(Alien.class);
        alien.setOrigin(startpos);
        alien.setContainer(container);
        astronaut.setAlien(alien);

        SoundLocator.provide(new DefaultSoundPlayer(astronaut));

        InputManager.getInstance().setAstronautController(astronaut);
        InputManager.getInstance().setAlienController(astronaut);

        scoreCounter.reset();

        entityManager.initalUpdate();
    }

    public void render(GameContainer container, Graphics g)
            throws SlickException {
        Vector2f astronautPos = astronaut.getPosition();

        // Background image TODO: translate
        g.drawImage(AssetLoader.getInstance().getImage("world_background"), 0,
                0);
        camera.pushViewMatrix(g);

        camera.debugdraw(g);

        mapRender.renderTileLayers(g, 0, 0, 0, 0, map.getWidth(),
                map.getHeight());

        entityManager.render(container, g);
        for (DynamicParticleSystem p : particleList) {
            p.render();
        }

        if (debugDraw) {
            physicsManager.render();
        }

        g.popTransform();
        scoreCounter.render(g);
    }

    public void update(GameContainer container, int delta)
            throws SlickException {
        if (shallBeReseted) {
            reset();
            shallBeReseted = false;
        }

        physicsManager.update(delta);

        entityManager.update(container, delta);

        camera.update(delta, container.getWidth(), container.getHeight());

        if (container.getInput().isKeyDown(Input.KEY_1)) {
            camera.zoomIn(0.9f);
        }
        if (container.getInput().isKeyDown(Input.KEY_2)) {
            camera.zoomOut(0.9f);
        }

        for (DynamicParticleSystem p : particleList) {
            p.update(delta);
        }

    }

    public Vector2f screenToWorldPosition(Vector2f screenPosition) {
        return camera.screenToWorldCoordinates(screenPosition);
    }

    public Vector2f worldToScreenPosition(Vector2f worldPosition) {
        return camera.worldToScreenCoordinates(worldPosition);
    }

    public Astronaut getAstronaut() {
        return astronaut;
    }

    public Alien getAlien() {
        return alien;
    }

    public ThreePointCamera getTPCamera() {
        return camera;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public PhysixManager getPhysicsManager() {
        return physicsManager;
    }

    public static World getInstance() {
        return instance;
    }

    public void addParticle(DynamicParticleSystem p) {
        particleList.add(p);
    }

    public void removeParticle(DynamicParticleSystem p) {
        particleList.remove(p);
    }

    public void shallBeReseted(boolean shallBeReseted) {
        this.shallBeReseted = shallBeReseted;
    }

    public static ScoreCounter getScoreCounter() {
        return scoreCounter;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

}
