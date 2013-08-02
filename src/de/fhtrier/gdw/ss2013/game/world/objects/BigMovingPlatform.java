package de.fhtrier.gdw.ss2013.game.world.objects;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;

public class BigMovingPlatform extends MovingPlatform {
    public BigMovingPlatform() {
        super(AssetLoader.getInstance().getImage("big_platform"));
    }
}
