//Author: Jerry

package de.fhtrier.gdw.ss2013.physics;

import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import de.fhtrier.gdw.ss2013.game.Entity;

public class RectanglePhysicsObject extends PhysicsObject {

	public RectanglePhysicsObject(Entity owner) {
		this(owner, BodyType.STATIC);
	}

	public RectanglePhysicsObject(Entity owner, BodyType bodyType) {
		this(owner, bodyType, new Vec2(10, 10));
	}

	public RectanglePhysicsObject(Entity owner, BodyType bodyType, Vec2 rec) {
		this(owner, bodyType, rec, new Vec2());
	}
	
   public RectanglePhysicsObject(Entity owner,Vec2 rec,
            Vec2 pos) {
        this(owner, BodyType.STATIC, rec, pos);
    }

	public RectanglePhysicsObject(Entity owner, BodyType bodyType, Vec2 rec,
			Vec2 pos) {
		this(owner, bodyType, rec, pos, 1);
	}

	public RectanglePhysicsObject(Entity owner, BodyType bodyType, Vec2 rec,
			Vec2 pos, float restitution) {
		this(owner, bodyType, rec, pos, restitution, 1);
	}
	
	public RectanglePhysicsObject(Entity owner, BodyType bodyType, Vec2 rec,
            Vec2 pos, boolean isSensor) {
        this(owner, bodyType, rec, pos, 0, 0, 0,isSensor);
    }

	public RectanglePhysicsObject(Entity owner, BodyType bodyType, Vec2 rec,
			Vec2 pos, float restitution, float density) {
		this(owner, bodyType, rec, pos, restitution, density, 1);
	}

	public RectanglePhysicsObject(Entity owner, BodyType bodyType, Vec2 rec,
			Vec2 pos, float restitution, float density, float friction) {
		this(owner, bodyType, rec, pos, restitution, density, friction, false);
	}

	public RectanglePhysicsObject(Entity owner, BodyType bodyType, Vec2 rec,
			Vec2 pos, float restitution, float density, float friction,
			boolean isSensor) {
		super(owner, restitution, density, friction, isSensor);
		PolygonShape myShape = new PolygonShape();
		myShape.setAsBox(rec.x, rec.y);
		init(myShape, bodyType,pos);
	}

}
