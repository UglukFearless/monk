package net.uglukfearless.monk.stages;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;

import net.uglukfearless.monk.actors.ParticleActor;
import net.uglukfearless.monk.actors.menu.MenuBackground;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.constants.PreferencesConstants;
import net.uglukfearless.monk.screens.MainMenuScreen;
import net.uglukfearless.monk.ui.BackButton;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;
import net.uglukfearless.monk.utils.gameplay.models.LevelModel;

public class SelectLevelStage extends Stage {

    private static int VIEWPORT_WIDTH = Constants.APP_WIDTH;
    private static int VIEWPORT_HEIGHT;
    private final MainMenuScreen mScreen;
    private final BackButton mBackButton;

    private float mYGameHeight;

    private Json mJsonIn;
    private FileHandle mFileLevel;

    private Array<LevelModel> mLevelModels;
    private Array<Texture> mLevelLogos;

    private Table mRootTable, mLevelsTable;
    private ScrollPane mScrollPane;
    private Label mLabelTitle;

    private Table mLevelImage;
    private Label mLevelName, mLevelRecord;

    private Cell mCell;
    private boolean mStopSeek;
    private int mSeekNumber;


    public SelectLevelStage(MainMenuScreen screen, float yViewportHeight) {

        super(new FitViewport(VIEWPORT_WIDTH, yViewportHeight,
                new OrthographicCamera(VIEWPORT_WIDTH, yViewportHeight)));

        VIEWPORT_WIDTH = Constants.APP_WIDTH;
        VIEWPORT_HEIGHT = (int)yViewportHeight;

        mYGameHeight = yViewportHeight;
        mScreen = screen;


        mJsonIn = new Json();
        mLevelModels = new Array<LevelModel>();
        mLevelLogos = new Array<Texture>();

        Gdx.input.setInputProcessor(this);
        addActor(new MenuBackground(AssetLoader.menuBackgroundTexture1, VIEWPORT_WIDTH, VIEWPORT_HEIGHT, yViewportHeight, true));
        setupParticles(AssetLoader.sSnowParticleBack);
        addActor(new MenuBackground(AssetLoader.menuBackgroundTexture2, VIEWPORT_WIDTH, Constants.APP_HEIGHT, yViewportHeight, false));
        setupParticles(AssetLoader.sSnowParticle);

        findLevels();

        setupGui();

        mBackButton = new BackButton(AssetLoader.sGuiSkin, screen);
        mBackButton.setSize(100, 62);
        mBackButton.setPosition(0, VIEWPORT_HEIGHT - mBackButton.getHeight() - 50);
        addActor(mBackButton);

        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);

    }

    private void findLevels() {

        mSeekNumber=1;

        while (!mStopSeek) {
            try {
                mFileLevel = Gdx.files.internal("levels/level".concat(String.valueOf(mSeekNumber)).concat(".json"));
                mLevelModels.add(mJsonIn.fromJson(LevelModel.class, mFileLevel));
                mLevelLogos.add(new Texture(Gdx.files.internal("levels/level".concat(String.valueOf(mSeekNumber)).concat(".png"))));
            } catch (Exception e) {
                mStopSeek = true;

                if (mLevelModels.size>mLevelLogos.size) {
                    mLevelModels.removeIndex(mLevelModels.size - 1);
                }
            }

            mSeekNumber++;
        }
    }

    private void setupGui() {

        mLevelsTable = new Table();

        for (int i=0;i<mLevelModels.size;i++) {
            mLevelImage = new Table();
            mLevelImage.background(new TextureRegionDrawable(new TextureRegion(mLevelLogos.get(i))));

            mLevelName = new Label(AssetLoader.sBundle.format("MENU_SELECT_LEVEL_NAME"
                                                            , mLevelModels.get(i).getEN_NAME()
                                                            , mLevelModels.get(i).getRU_NAME()), AssetLoader.sGuiSkin);
            Cell cell = mLevelImage.add(mLevelName).expand().align(Align.center).fill().align(Align.bottom);
            mLevelName.setAlignment(Align.bottom);
            mLevelImage.row();

            final LevelModel levelModel = mLevelModels.get(i);

            Image lockImage = null;

            if (levelModel.checkUnlock()) {
                mLevelRecord = new Label(String.valueOf(
                        PreferencesManager.getLevelHighScore(mLevelModels.get(i).getLEVEL_NAME())), AssetLoader.sGuiSkin);
                mLevelImage.add(mLevelRecord).expand().align(Align.center).fill();
                mLevelRecord.setAlignment(Align.top);



                mLevelImage.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        mScreen.newGame(levelModel);
                    }
                });
            } else {
                mLevelImage.addAction(Actions.alpha(0.6f));
                mLevelName.setAlignment(Align.center);

                lockImage = new Image(AssetLoader.levelLock);
            }


            mLevelsTable.add(mLevelImage).padTop(20).padBottom(20);

            if (lockImage!=null) {
                mLevelImage.add(lockImage).expand().align(Align.top);
            }

            mLevelsTable.row();
        }

        mScrollPane = new ScrollPane(mLevelsTable);

        mRootTable = new Table();
        mRootTable.background(new NinePatchDrawable(AssetLoader.broadbord));
        mRootTable.pad(15);
        mRootTable.setBounds(100, VIEWPORT_HEIGHT / 9, VIEWPORT_WIDTH * 0.75f
                , VIEWPORT_HEIGHT - VIEWPORT_HEIGHT / 6);
        mRootTable.add(mScrollPane).fill().expand();
        addActor(mRootTable);


    }

    @Override
    public void act(float delta) {
        super.act(delta);
//        effect.update(delta);
//        effect.getEmitters().get(1).setPosition(effect.getEmitters().get(1).getX(), effect.getEmitters().get(1).getY() + 0.5f);
    }

    @Override
    public void draw() {
        if (mLevelModels.size==1) {
            mScreen.newGame(mLevelModels.get(0));
        } else {
            super.draw();
        }

//        getBatch().begin();
//        effect.draw( getBatch() );
//        getBatch().end();
    }

    @Override
    public void dispose() {
        for(Texture texture:mLevelLogos) {
            texture.dispose();
        }
//        effect.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keyCode) {


        if (keyCode== Input.Keys.BACK||keyCode== Input.Keys.MENU) {
            mScreen.mainMenu();
        }
        return super.keyDown(keyCode);
    }

    private void setupParticles(ParticleEffect particleEffect) {
        ParticleActor particleActor = new ParticleActor(particleEffect);
        particleActor.setPosition(VIEWPORT_WIDTH/2, VIEWPORT_HEIGHT);
        addActor(particleActor);
        particleActor.start();
    }
}
