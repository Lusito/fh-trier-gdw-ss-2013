package de.fhtrier.gdw.ss2013.sound;

import org.newdawn.slick.Sound;

import de.fhtrier.gdw.ss2013.game.Entity;

public interface ISoundPlayer {

    public void playSound(Sound sound);

    public void stopSound(Sound sound);

    /**
     * 
     * @param sound
     *            Sound der abgespielt werden soll
     * @param listener
     *            Position des Höhrers
     * @param emitter
     *            Position des zu höhrenden Sounds
     */
    public void playSoundAt(Sound sound, Entity listener, Entity emitter);

    public void stopAllSounds();

    public void playMusic(Sound music);

}
