package gdx.trajectory;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

public class Trajectory extends ApplicationAdapter implements InputProcessor {

    SpriteBatch batch;
    Sprite Hero;
    Texture img;
    Vector2 heroPosition, bulletPosition, mousePosition, vDir;
    ShapeRenderer SR;
    float fHeroWidth, fHeroHeight, fVelocity, fGravity;
   // ArrayList<gdx.trajectory.Platforms> alPlatforms = new ArrayList<Platforms>();
    ArrayList<gdx.trajectory.Bullets> alBullets = new ArrayList<Bullets>();
    int nMax; // max number of bullets at a time
    boolean canJump, isOnground;

    @Override
    public void create() {
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();
        img = new Texture("smiley face.png");
        Hero = new Sprite(img);
        SR = new ShapeRenderer();
        fHeroWidth = 50;
        fHeroHeight = 50;
        fGravity = (float) 0.1;
        nMax = 0;
        heroPosition = new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        createHero();
        //platforms();
        move();
        spawnBullets();
    }

    public void createHero() {
        batch.begin();
        batch.draw(Hero, heroPosition.x, heroPosition.y, fHeroWidth, fHeroHeight);
        batch.end();
        Holster();
    }

//    public void platforms() {
//        alPlatforms.add(new Platforms(0, 50, batch));
//        alPlatforms.add(new Platforms(150, 250, batch));
//        alPlatforms.add(new Platforms(400, 450, batch));
//        alPlatforms.add(new Platforms(450, 250, batch));
//        alPlatforms.add(new Platforms(600, 50, batch));
//        for (int i = 0; i < alPlatforms.size(); i++) {
//            alPlatforms.get(i).Update();
////            if((heroPosition.x + fHeroWidth > alPlatforms.get(i).fX || heroPosition.x < alPlatforms.get(i).fX + 100) &&
////                    (heroPosition.y < alPlatforms.get(i).fY + 50 || heroPosition.y + fHeroHeight > alPlatforms.get(i).fY)) {
////            }
//        }
//
//    }

    public void move() {
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            heroPosition.x += 5;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            heroPosition.x -= 5;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (isOnground == true) {
                canJump = true;
            }
        }
        if (canJump == true) {
            heroPosition.y += fVelocity;
            if (fVelocity >= -8) {
                fVelocity -= fGravity;
            }
        }
        if (heroPosition.y <= 0) {  //on the ground
            fVelocity = 6;
            isOnground = true;
            canJump = false;

        } else {
            heroPosition.y += fVelocity;
            if (fVelocity >= -8) {
                fVelocity -= fGravity;
            }
        }
//            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//                heroPosition.y += 5;
//            } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//                heroPosition.y -= 5;
//            }
//        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//            heroPosition.y += 5;
//        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//            heroPosition.y -= 5;
//        }
//        if(heroPosition.y <= 0) {
//            canJump = true;
//            fVelocity = 10;
//        } else {
//            heroPosition.y -= fVelocity;
//            fVelocity -= fGravity;
//        }
    }

    public void spawnBullets() { // showing the bullets 
        for (int i = 0; i < alBullets.size(); i++) {
            alBullets.get(i).Update();
            if (isOutOfRange(alBullets.get(i))) {
                alBullets.get(i).canMove = false;
            }
            if (isCollected(alBullets.get(i)) && alBullets.get(i).canMove == false) {
                alBullets.remove(i);
                nMax--;

            }
        }
    }

    public void Holster() { //visual representation of the bullets
        SR.begin(ShapeType.Filled);
        SR.setColor(Color.CYAN);
        if (nMax < 4) {
            SR.ellipse(heroPosition.x + 1, heroPosition.y + fHeroHeight, 10, 10);
        }
        if (nMax < 3) {
            SR.ellipse(heroPosition.x + 13, heroPosition.y + fHeroHeight, 10, 10);
        }
        if (nMax < 2) {
            SR.ellipse(heroPosition.x + 25, heroPosition.y + fHeroHeight, 10, 10);
        }
        if (nMax < 1) {
            SR.ellipse(heroPosition.x + 37, heroPosition.y + fHeroHeight, 10, 10);
        }
        SR.end();
    }

    private boolean isOutOfRange(Bullets bullet) { // touching the sides of the screen
        return bullet.vPos.x < 0 || bullet.vPos.x + 10 > Gdx.graphics.getWidth()
                || bullet.vPos.y <= 0 || bullet.vPos.y + 10 >= Gdx.graphics.getHeight();
    }

    private boolean isCollected(Bullets bullet) { // if player touches the bullets 
        return heroPosition.x <= bullet.vPos.x + 10 && heroPosition.x + fHeroWidth >= bullet.vPos.x
                && heroPosition.y <= bullet.vPos.y + 10 && heroPosition.y + fHeroHeight >= bullet.vPos.y;
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        if (nMax < 4) { // maximum of 4 bullets
            mousePosition = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            bulletPosition = new Vector2(heroPosition.x + fHeroWidth / 2, heroPosition.y + fHeroHeight / 2);
            vDir = mousePosition.sub(heroPosition);
            alBullets.add(new Bullets(bulletPosition, vDir, SR));
            nMax++;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }
}
