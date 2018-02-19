/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gdx.trajectory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author benny
 */
public class Platforms {

    float fX, fY;
    SpriteBatch batch;
    Sprite Plat;
    Texture img = new Texture("badlogic.jpg");

    Platforms(float fTempX, float fTempY, SpriteBatch TempBatch) {
        this.fX = fTempX;
        this.fY = fTempY;
        this.batch = TempBatch;
        Plat = new Sprite(img);
    }

    public void Update() {
        drawPlatform();
       // detection();
    }

    public void drawPlatform() {
        batch.begin();
        batch.draw(Plat, fX, fY, 100, 50);
        batch.end();
    }
    

}
