/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gdx.trajectory;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author benny
 */
public class Bullets {

    Vector2 vPos;
    ShapeRenderer SR;
    Vector2 dir, grav = new Vector2(0, (float) 0.1);
    float fX, fY;
    boolean canMove = true;

    Bullets(Vector2 vPos, Vector2 tempDirection, ShapeRenderer Temp) {
        //bullets(float fTempX, float fTempY, ShapeRenderer Temp) {
        this.vPos = vPos;
//        this.fX = fTempX;
//        this.fY = fTempY;
        this.dir = tempDirection;
        this.SR = Temp;
        dir.setLength(10f);

    }
    //}

    public void Update() {
        ammo();
        move();
//        dir.add(grav);
//        dir.clamp(-0.01f, 0.01f);
    }

    public void ammo() {
        SR.begin(ShapeRenderer.ShapeType.Filled);
        SR.setColor(Color.CYAN);
        SR.ellipse(vPos.x, vPos.y, 10, 10);
        SR.end();

    }

    public void move() {
        if (canMove == true) {
            vPos.add(dir);
            if (dir.y >= -7) {
                dir.sub(grav);
            }
        }
    }

}
