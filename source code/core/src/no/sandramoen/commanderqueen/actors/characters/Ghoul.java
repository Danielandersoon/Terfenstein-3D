package no.sandramoen.commanderqueen.actors.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.utils.Array;

import no.sandramoen.commanderqueen.actors.utils.Enemy;
import no.sandramoen.commanderqueen.utils.BaseGame;
import no.sandramoen.commanderqueen.utils.Stage3D;

public class Ghoul extends Enemy {
    private Animation<TextureRegion> currentAnimation;
    private Animation<TextureRegion> walkFrontAnimation;
    private Animation<TextureRegion> walkFrontSideLeftAnimation;
    private Animation<TextureRegion> walkFrontSideRightAnimation;
    private Animation<TextureRegion> walkSideLeftAnimation;
    private Animation<TextureRegion> walkSideRightAnimation;
    private Animation<TextureRegion> walkBackSideLeftAnimation;
    private Animation<TextureRegion> walkBackSideRightAnimation;
    private Animation<TextureRegion> walkBackAnimation;
    private Animation<TextureRegion> dieAnimation;
    private float timeToStopMoving = 1.1f;
    private float attackCounter = 0f;
    private final float ATTACK_FREQUENCY = 2f;

    public Ghoul(float y, float z, Stage3D s, Player player) {
        super(y, z, s, player);
        movementSpeed = .05f;
        initializeAnimation();
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        if (isPause)
            return;
        if (isForcedToMove)
            forceMove(dt);
        else if (!isDead || totalTime < timeToStopMoving) {
            setTurnAngle(angleTowardPlayer);
            moveForward(movementSpeed);
        }

        if (isDead)
            return;

        if (attackCounter > ATTACK_FREQUENCY) {
            isReadyToAttack = true;
        } else {
            attackCounter += dt;
            isReadyToAttack = false;
        }

        setDirectionalSprites();
    }

    @Override
    public void draw(ModelBatch batch, Environment env) {
        sprite.loadImage(currentAnimation.getKeyFrame(totalTime).toString());
    }

    @Override
    public void die() {
        isDead = true;
        BaseGame.ghoulDeathSound.play(BaseGame.soundVolume);
        totalTime = 0f;
        currentAnimation = dieAnimation;
        isCollisionEnabled = false;
    }

    public boolean isReadyToAttack() {
        if (isReadyToAttack == true)
            attackCounter = 0;
        return isReadyToAttack;
    }

    private void setDirectionalSprites() {
        if (direction == Directions.FRONT)
            currentAnimation = walkFrontAnimation;
        else if (direction == Directions.LEFT_FRONT)
            currentAnimation = walkFrontSideLeftAnimation;
        else if (direction == Directions.LEFT_SIDE)
            currentAnimation = walkSideLeftAnimation;
        else if (direction == Directions.LEFT_BACK)
            currentAnimation = walkBackSideLeftAnimation;
        else if (direction == Directions.BACK)
            currentAnimation = walkBackAnimation;
        else if (direction == Directions.RIGHT_BACK)
            currentAnimation = walkBackSideRightAnimation;
        else if (direction == Directions.RIGHT_SIDE)
            currentAnimation = walkSideRightAnimation;
        else if (direction == Directions.RIGHT_FRONT)
            currentAnimation = walkFrontSideRightAnimation;
    }

    private void initializeAnimation() {
        Array<TextureAtlas.AtlasRegion> animationImages = new Array();
        for (int i = 0; i < 4; i++)
            animationImages.add(BaseGame.textureAtlas.findRegion("enemies/demo imp/front " + i));
        walkFrontAnimation = new Animation(.2f, animationImages, Animation.PlayMode.LOOP);
        animationImages.clear();

        for (int i = 0; i < 4; i++)
            animationImages.add(BaseGame.textureAtlas.findRegion("enemies/demo imp/front side left " + i));
        walkFrontSideLeftAnimation = new Animation(.2f, animationImages, Animation.PlayMode.LOOP);
        animationImages.clear();

        for (int i = 0; i < 4; i++)
            animationImages.add(BaseGame.textureAtlas.findRegion("enemies/demo imp/front side right " + i));
        walkFrontSideRightAnimation = new Animation(.2f, animationImages, Animation.PlayMode.LOOP);
        animationImages.clear();

        for (int i = 0; i < 4; i++)
            animationImages.add(BaseGame.textureAtlas.findRegion("enemies/demo imp/side left " + i));
        walkSideLeftAnimation = new Animation(.2f, animationImages, Animation.PlayMode.LOOP);
        animationImages.clear();

        for (int i = 0; i < 4; i++)
            animationImages.add(BaseGame.textureAtlas.findRegion("enemies/demo imp/side right " + i));
        walkSideRightAnimation = new Animation(.2f, animationImages, Animation.PlayMode.LOOP);
        animationImages.clear();

        for (int i = 0; i < 4; i++)
            animationImages.add(BaseGame.textureAtlas.findRegion("enemies/demo imp/back side left " + i));
        walkBackSideLeftAnimation = new Animation(.2f, animationImages, Animation.PlayMode.LOOP);
        animationImages.clear();

        for (int i = 0; i < 4; i++)
            animationImages.add(BaseGame.textureAtlas.findRegion("enemies/demo imp/back side right " + i));
        walkBackSideRightAnimation = new Animation(.2f, animationImages, Animation.PlayMode.LOOP);
        animationImages.clear();

        for (int i = 0; i < 4; i++)
            animationImages.add(BaseGame.textureAtlas.findRegion("enemies/demo imp/back " + i));
        walkBackAnimation = new Animation(.2f, animationImages, Animation.PlayMode.LOOP);
        animationImages.clear();

        for (int i = 0; i < 5; i++)
            animationImages.add(BaseGame.textureAtlas.findRegion("enemies/ghoul die " + i));
        dieAnimation = new Animation(.2f, animationImages, Animation.PlayMode.NORMAL);
        animationImages.clear();

        currentAnimation = walkFrontAnimation;
    }
}
