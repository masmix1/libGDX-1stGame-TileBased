package com.masmix.gra;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/*
 * Created by MasmiX on 24.11.2016.
 */
public class GameScreen extends ApplicationAdapter {
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 480;
    private SpriteBatch batch;
    private TextureAtlas playerAtlas;
    private TextureAtlas tilesAtlas;
    private FPSLogger fpsLogger;
    private Camera camera;
    private Viewport viewport;
    private Player player;


    @Override
    public void create() {
        batch = new SpriteBatch();
        fpsLogger = new FPSLogger();
        camera = new OrthographicCamera();
        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
        playerAtlas = new TextureAtlas("gra.pack");
        tilesAtlas = new TextureAtlas("tiles.pack");
        
        camera.position.set(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, 0);
        player = new Player(new Character("MasmiX"),
                new TextureRegion(playerAtlas.findRegion("standDown")), new TextureRegion(playerAtlas.findRegion("standLeft")),
                new TextureRegion(playerAtlas.findRegion("standRight")), new TextureRegion(playerAtlas.findRegion("standUp")),
                new TextureRegion(playerAtlas.findRegion("walkDown")), new TextureRegion(playerAtlas.findRegion("walkUp")),
                new TextureRegion(playerAtlas.findRegion("walkLeft1")), new TextureRegion(playerAtlas.findRegion("walkLeft2")),
                new TextureRegion(playerAtlas.findRegion("walkLeft3")), new TextureRegion(playerAtlas.findRegion("walkLeft4")),
                new TextureRegion(playerAtlas.findRegion("walkRight1")), new TextureRegion(playerAtlas.findRegion("walkRight2")),
                new TextureRegion(playerAtlas.findRegion("walkRight3")), new TextureRegion(playerAtlas.findRegion("walkRight4")));
    }

    private void updateScene() {
        camera.update();
        float deltaTime = Gdx.graphics.getDeltaTime();
        if (!player.getWalkRightAnim().isAnimationFinished(player.getWalkRightAnimTime())) {
            player.setWalkRightAnimTime(player.getWalkRightAnimTime() + deltaTime);
            player.positionAdd(50 * deltaTime, 0);
        }
    }

    private void drawScene() {
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        batch.draw(player.getWalkRightAnim().getKeyFrame(player.getWalkRightAnimTime()), player.getPositionX(), player.getPositionY());
        batch.end();
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        fpsLogger.log();

        updateScene();
        drawScene();
    }

    @Override
    public void resize (int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose () {
        batch.dispose();
        playerAtlas.dispose();
        tilesAtlas.dispose();
    }
}
