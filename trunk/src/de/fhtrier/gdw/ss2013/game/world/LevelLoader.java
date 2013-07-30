package de.fhtrier.gdw.ss2013.game.world;

import de.fhtrier.gdw.commons.tiled.Layer;
import de.fhtrier.gdw.commons.tiled.LayerObject;
import de.fhtrier.gdw.commons.tiled.TiledMap;
import de.fhtrier.gdw.commons.utils.SafeProperties;
import de.fhtrier.gdw.ss2013.game.Entity;
import de.fhtrier.gdw.ss2013.game.EntityManager;
import de.fhtrier.gdw.ss2013.physics.CirclePhysicsObject;
import de.fhtrier.gdw.ss2013.physics.PhysicsManager;
import de.fhtrier.gdw.ss2013.physics.RectanglePhysicsObject;
import java.awt.Point;
import java.util.ArrayList;
import org.jbox2d.common.Vec2;

/**
 *
 * @author Santo
 */
public class LevelLoader {
    private static EntityManager entityManager;

    public static void load(TiledMap map, EntityManager entityManager) {
        LevelLoader.entityManager = entityManager;
        entityManager.reset();
        PhysicsManager.getInstance().reset();
        
        for (Layer layer : map.getLayers()) {
            if (layer.isObjectLayer()) {
                loadObjectLayer(layer);
            }
        }
    }

    private static void loadObjectLayer(Layer layer) {
        for (LayerObject object : layer.getObjects()) {
            switch (object.getPrimitive()) {
                case POINT:
                    createPoint(object.getType(), object.getX(), object.getY(), object.getProperties());
                    break;
                case RECT:
                    createRect(object.getType(), object.getX(), object.getY(), object.getWidth(), object.getHeight(), object.getProperties());
                    break;
                case TILE:
                    createTile(object.getType(), object.getX(), object.getY(), object.getWidth(), object.getHeight(), object.getProperties());
                    break;
                case POLYGON:
                    createPolygon(object.getType(), object.getPoints(), object.getProperties());
                    break;
                case POLYLINE:
                    createPolyLine(object.getType(), object.getPoints(), object.getProperties());
                    break;
            }
        }
    }

    /**
     * Create ground, paths, etc here
     *
     * @param type the type set in the editor
     * @param points the points of the line (absolute points)
     * @param properties the object properties
     */
    private static void createPolyLine(String type, ArrayList<Point> points, SafeProperties properties) {
        switch (type) {
            case "solid":
                /// TODO: create a solid line (static)
                break;
        }
    }

    /**
     * Create deadzones, triggers, etc here
     *
     * @param type the type set in the editor
     * @param points the points of the line (absolute points)
     * @param properties the object properties
     */
    private static void createPolygon(String type, ArrayList<Point> points, SafeProperties properties) {
        Entity entity;
        switch (type) {
            case "solid":
                /// TODO: create a solid (static) object
                break;
            case "deadzone":
                entity = entityManager.createEntity(type, properties);
                /// TODO: create a physics trigger
                entity.setPhysicsObject(null);
                break;
        }
    }

    /**
     * Create rectangle deadzones, triggers, etc here
     *
     * @param type the type set in the editor
     * @param x the distance from left in pixels
     * @param y the distance from top in pixels
     * @param width width in pixels
     * @param height height in pixels
     * @param properties the object properties
     */
    private static void createRect(String type, int x, int y, int width, int height, SafeProperties properties) {
        Entity entity;
        switch (type) {
            case "solid":
                new RectanglePhysicsObject(new Vec2(width, height), new Vec2(x, y));
                break;
            case "deadzone":
                entity = entityManager.createEntity(type, properties);
                entity.setPhysicsObject(new RectanglePhysicsObject(new Vec2(width, height), new Vec2(x, y)));
                break;
        }
    }

    /**
     * Create items, enemies, etc here
     *
     * @param type the type set in the editor
     * @param x the distance from left in pixels
     * @param y the distance from top in pixels
     * @param width width in pixels
     * @param height height in pixels
     * @param properties the object properties
     */
    private static void createTile(String type, int x, int y, int width, int height, SafeProperties properties) {
        Entity entity = entityManager.createEntity(type, properties);
        if(properties.getBoolean("circle", false)) {
            float radius = Math.max(width, height)/2;
            entity.setPhysicsObject(new CirclePhysicsObject(radius, new Vec2(x + width/2, y + height/2)));
        } else {
            entity.setPhysicsObject(new RectanglePhysicsObject(new Vec2(width, height), new Vec2(x, y)));
        }
    }

    /**
     * Currently no plan for use
     *
     * @param type the type set in the editor
     * @param x the distance from left in pixels
     * @param y the distance from top in pixels
     * @param properties the object properties
     */
    private static void createPoint(String type, int x, int y, SafeProperties properties) {
    }
}