package net.uglukfearless.monk.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import net.uglukfearless.monk.actors.menu.MenuBackground;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.screens.MainMenuScreen;
import net.uglukfearless.monk.ui.BackButton;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;
import net.uglukfearless.monk.utils.file.SoundSystem;

/**
 * Created by Ugluk on 08.08.2016.
 */
public class OptionMenuStage extends Stage {

    public static final int VIEWPORT_WIDTH = Constants.APP_WIDTH;
    public static  int VIEWPORT_HEIGHT;

    private MainMenuScreen mScreen;

    private Vector3 mTouchPoint;

    private BackButton mBackButton;

    private Button mCheckMusic;
    private Button mCheckSound;

    private Label mTitle;

    private Label mMusicLbl, mSoundLbl, mMusicValLbl, mSoundValLbl;

    private Slider mMusicSlider, mSoundSlider;

    private Table mContainer, mMainTable;
    private ScrollPane mScrollPane;
    private Cell<? extends Actor> mCell;

    public OptionMenuStage(MainMenuScreen screen, float yViewportHeight) {
        super(new FitViewport(VIEWPORT_WIDTH, yViewportHeight,
                new OrthographicCamera(VIEWPORT_WIDTH, yViewportHeight)));
        VIEWPORT_HEIGHT = (int) yViewportHeight;

        mScreen = screen;

        addActor(new MenuBackground());
        Gdx.input.setInputProcessor(this);

        mTouchPoint = new Vector3();

        setupTitle("Options");

        setupButtons(screen);
        setupSliders();
        setupLabels();

        mBackButton = new BackButton(AssetLoader.sGuiSkin, screen);
        mBackButton.setSize(100, 62);
        mBackButton.setPosition(0, VIEWPORT_HEIGHT - mBackButton.getHeight() - 50);
        addActor(mBackButton);
    }

    private void setupSliders() {

        mMusicSlider = new Slider(0f, 1f, 0.05f, false, AssetLoader.sGuiSkin);
        mMusicSlider.setValue(PreferencesManager.getMusicValue());
        mMusicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mScrollPane.cancel();
                SoundSystem.setMusicValue(mMusicSlider.getValue());
                SoundSystem.setCurrentMusicValue(mMusicSlider.getValue());
                PreferencesManager.setMusicValue(mMusicSlider.getValue());
            }
        });

        mSoundSlider = new Slider(0f, 1f, 0.05f, false, AssetLoader.sGuiSkin);
        mSoundSlider.setValue(PreferencesManager.getSoundValue());
        mSoundSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mScrollPane.cancel();
                SoundSystem.setSoundValue(mSoundSlider.getValue());
                PreferencesManager.setSoundValue(mSoundSlider.getValue());
            }
        });
    }

    private void setupButtons(MainMenuScreen screen) {

        mCheckMusic = new Button(AssetLoader.sGuiSkin, "check");
        mCheckMusic.setSize(60, 60);
        mCheckMusic.setChecked(PreferencesManager.getMusicEnable());
        mCheckMusic.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                SoundSystem.setMusicEnable(mCheckMusic.isChecked());
                PreferencesManager.setMusicEnable(mCheckMusic.isChecked());
            }
        });

        mCheckSound = new Button(AssetLoader.sGuiSkin, "check");
        mCheckSound.setSize(60, 60);
        mCheckSound.setChecked(PreferencesManager.getSoundEnable());
        mCheckSound.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                SoundSystem.setSoundEnable(mCheckSound.isChecked());
                PreferencesManager.setSoundEnable(mCheckSound.isChecked());
            }
        });
    }

    private void setupLabels() {

        mMusicLbl = new Label("Music", AssetLoader.sGuiSkin);
        mMusicValLbl = new Label("Music Value", AssetLoader.sGuiSkin);
        mSoundLbl = new Label("Sounds", AssetLoader.sGuiSkin);
        mSoundValLbl = new Label("Sounds Value", AssetLoader.sGuiSkin);

        mContainer = new Table();

        mCell = mContainer.add(mMusicLbl).expandX();
        mCell.align(Align.left);
        mCell = mContainer.add(mCheckMusic);
        mCell.align(Align.right);
        mContainer.row();

        mCell = mContainer.add(mMusicValLbl).expandX();
        mCell.align(Align.left);
        mCell = mContainer.add(mMusicSlider).width(250);
        mCell.align(Align.right);
        mContainer.row();

        mCell = mContainer.add(mSoundLbl).expandX();
        mCell.align(Align.left);
        mCell = mContainer.add(mCheckSound);
        mCell.align(Align.right);
        mContainer.row();

        mCell = mContainer.add(mSoundValLbl).expandX();
        mCell.align(Align.left);
        mCell = mContainer.add(mSoundSlider).width(250);
        mCell.align(Align.right);

        mScrollPane = new ScrollPane(mContainer);

        mMainTable = new Table();
        mMainTable.background(new NinePatchDrawable(new NinePatch(AssetLoader.broadbord)));
        mMainTable.pad(15);
        mMainTable.setBounds(100, VIEWPORT_HEIGHT / 9, VIEWPORT_WIDTH * 0.75f
                , mTitle.getY() - VIEWPORT_HEIGHT / 6);
        mMainTable.add(mScrollPane).fill().expand();

        addActor(mMainTable);
    }


    public void setupTitle(String title) {
        mTitle = new Label(title , AssetLoader.sGuiSkin, "title");
        mTitle.setPosition(VIEWPORT_WIDTH/2f - mTitle.getWidth()/2f, VIEWPORT_HEIGHT - 30 - mTitle.getHeight());
        addActor(mTitle);
    }


    private void translateScreenToWorldCoordinates(int screenX, int screenY) {
        getCamera().unproject(mTouchPoint.set(screenX, screenY, 0));
    }
}
