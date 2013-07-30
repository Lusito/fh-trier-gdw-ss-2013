package de.fhtrier.gdw.ss2013.physics.test;

import java.awt.Point;
import java.util.ArrayList;

import org.jbox2d.common.IViewportTransform;
import org.jbox2d.common.OBBViewportTransform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.commons.tiled.TiledMap;
import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.game.camera.Camera;
import de.fhtrier.gdw.ss2013.game.player.Astronaut;
import de.fhtrier.gdw.ss2013.game.world.LevelLoader;
import de.fhtrier.gdw.ss2013.game.world.enemies.Meteroid;
import de.fhtrier.gdw.ss2013.input.InputManager;
import de.fhtrier.gdw.ss2013.physics.CirclePhysicsObject;
import de.fhtrier.gdw.ss2013.physics.DebugDrawer;
import de.fhtrier.gdw.ss2013.physics.PhysicsManager;
import de.fhtrier.gdw.ss2013.physics.PhysicsObject;
import de.fhtrier.gdw.ss2013.physics.PolygonPhysicsObject;
import de.fhtrier.gdw.ss2013.physics.RectanglePhysicsObject;
import de.fhtrier.gdw.ss2013.physics.Transform;
import de.fhtrier.gdw.ss2013.renderer.MapRenderer;
import de.fhtrier.gdw.ss2013.sound.SoundLocator;
import de.fhtrier.gdw.ss2013.sound.services.DefaultSoundPlayer;

public class TestWorld {

    private TiledMap map;
    private MapRenderer mapRender;
    private Camera camera;
    private Astronaut astronaut;
    private Input input;

    // physics debug
    private DebugDrawer physicDebug;
    public boolean debugDraw = true;

    private EntityManager entityManager;
    private final PhysicsManager physicsManager;

    public TestWorld(GameContainer container, StateBasedGame game) {
        input = container.getInput();
        map = null;
        entityManager = new EntityManager();
        physicsManager = new PhysicsManager();

        try {
            map = AssetLoader.getInstance().loadMap("demo");
            LevelLoader.load(map, entityManager, physicsManager);

            mapRender = new MapRenderer(map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        camera = new Camera(map);

        //entityManager = new EntityManager();

        // physic debug stuff
        IViewportTransform viewport = new OBBViewportTransform();
        physicsManager.setTransformViewport(viewport);
        if (debugDraw) {

            physicDebug = new DebugDrawer(viewport, container, camera);

            physicsManager.getPhysicsWorld()
                    .setDebugDraw(physicDebug);
        }

        astronaut = entityManager.createEntityAt(Astronaut.class, new Vector2f(
                200, 200));
                
        astronaut.setPhysicsObject(new RectanglePhysicsObject(BodyType.DYNAMIC, new Vec2(95, 105), new Vec2(astronaut.getPosition().x, astronaut.getPosition().y)));

        System.out.println(Input.KEY_A);
        System.out.println(Input.KEY_D);
        System.out.println(Input.KEY_W);
        System.out.println(Input.KEY_LEFT);
        System.out.println(Input.KEY_RIGHT);
        System.out.println(Input.KEY_UP);
        
        SoundLocator.provide(new DefaultSoundPlayer(astronaut));
        
        int base = 1000;
        int xbase = 0;
        
        ArrayList<Point> li = new ArrayList<Point>();
        for(int pcount = 0;pcount <= 5;pcount++)
        {
            li.add(new Point(xbase+pcount*(1000/5), (int)(base-100+Math.random()*50-25)));
        }
        li.add(new Point(xbase+1000, base));
        li.add(new Point(xbase, base));
        
        PolygonPhysicsObject PPO = new PolygonPhysicsObject(BodyType.STATIC, li);


    }
    public void onEnter()
    {
        InputManager.getInstance().getKeyboard().setAstronautController(astronaut);
    }

    public void render(GameContainer container, Graphics g)
            throws SlickException {
        Vector2f astronautPos = astronaut.getPosition();
        camera.update(container.getWidth(), container.getHeight(),
                astronautPos.x, astronautPos.y);

        // Background image TODO: translate
        g.drawImage(AssetLoader.getInstance().getImage("world_background"), 0,
                0);

        mapRender
                .renderTileLayers(g, -camera.getTileOverlapX(),
                        -camera.getTileOverlapY(), camera.getTileX(),
                        camera.getTileY(), camera.getNumTilesX(),
                        camera.getNumTilesY());

        g.pushTransform();
        g.translate(-camera.getOffsetX(), -camera.getOffsetY());

        // draw entities
        entityManager.render(container, g);

        if (debugDraw)
            physicsManager.getPhysicsWorld().drawDebugData();

        g.popTransform();
    }

    public void update(GameContainer container, int delta)
            throws SlickException {
        physicsManager.setCurrent();
        // update entities
        entityManager.update(container, delta);
        physicsManager.update(container, delta);
        if (input.isKeyPressed(Input.KEY_M)) {
            Vector2f position = new Vector2f(150, -80);
            Meteroid m = entityManager.createEntityAt(Meteroid.class, position);
            PhysicsObject t = new RectanglePhysicsObject(BodyType.DYNAMIC,
                    new Vec2(5, 5), new Vec2(position.x, position.y));

            m.setPhysicsObject(t);
        }
        if (input.isKeyPressed(Input.KEY_SPACE)) {

            PhysicsObject rpo;
            if(Math.random()>0.5)
            {
                rpo = new RectanglePhysicsObject(
                        BodyType.DYNAMIC, new Vec2(100,100), new Vec2(500, 300));
            }
            else
            {
                rpo = new CirclePhysicsObject(
                        BodyType.DYNAMIC, 1, new Vec2(500, 300));
            }
            rpo.setMassData(1f);
            Vec2 force = new Vec2(2, 0);
            System.out.println(force);
            rpo.applyImpulse(force);
          }
    }

    public Vector2f screenToWorldPosition(Vector2f screenPosition) {
        /**
         * Top-left (0,0) / Bottom-right (width,height)
         */
        Vector2f worldPos = new Vector2f(camera.getOffsetX(),
                camera.getOffsetY());

        return worldPos.add(screenPosition);

    }

    public Vector2f worldToScreenPosition(Vector2f worldPosition) {
        Vector2f screenPos = new Vector2f(-camera.getOffsetX(),
                -camera.getOffsetY());

        return screenPos.add(worldPosition);
    }

    public Astronaut getAstronaut() {
        return astronaut;
    }

    public Camera getCamera() {
        return camera;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public PhysicsManager getPhysicsManager() {
        return physicsManager;
    }

}
