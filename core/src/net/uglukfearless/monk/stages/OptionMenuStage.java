package net.uglukfearless.monk.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
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
    private boolean mHack;

    //Локализация
    private ButtonGroup<Button> mLanguageGroup;
    private Button mCheckEnglish;
    private Button mCheckRussian;
    private Label mEnglish;
    private Label mRussian;
    private HorizontalGroup mHorizontal;

    public OptionMenuStage(MainMenuScreen screen, float yViewportHeight) {
        super(new FitViewport(VIEWPORT_WIDTH, yViewportHeight,
                new OrthographicCamera(VIEWPORT_WIDTH, yViewportHeight)));
        VIEWPORT_HEIGHT = (int) yViewportHeight;

        mScreen = screen;

        addActor(new MenuBackground());
        Gdx.input.setInputProcessor(this);

        mTouchPoint = new Vector3();

        setupTitle(AssetLoader.sBundle.get("MENU_OPTIONS"));

        setupButtons(screen);
        setupSliders();
        setupLabels();

        mBackButton = new BackButton(AssetLoader.sGuiSkin, screen);
        mBackButton.setSize(100, 62);
        mBackButton.setPosition(0, VIEWPORT_HEIGHT - mBackButton.getHeight() - 50);
        addActor(mBackButton);

        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);
    }

    private void setupSliders() {

        mHack = false;

        mMusicSlider = new Slider(0f, 1f, 0.05f, false, AssetLoader.sGuiSkin);
        mMusicSlider.setValue(PreferencesManager.getMusicValue());
        mMusicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mScrollPane.cancel();
                SoundSystem.setMusicValue(mMusicSlider.getValue());
                SoundSystem.setCurrentMusicValue(mMusicSlider.getValue());
                PreferencesManager.setMusicValue(mMusicSlider.getValue());
                mCheckMusic.setChecked(true);
                checkMusic();
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
                mCheckSound.setChecked(true);
                checkSound();
                if (!mHack) {
                    AssetLoader.monkStrikeSound.play(SoundSystem.getSoundValue());
                }
                mHack = !mHack;
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
                checkMusic();
            }
        });

        mCheckSound = new Button(AssetLoader.sGuiSkin, "check");
        mCheckSound.setSize(60, 60);
        mCheckSound.setChecked(PreferencesManager.getSoundEnable());
        mCheckSound.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                checkSound();
            }
        });

        mCheckEnglish = new Button(AssetLoader.sGuiSkin, "check");
        mCheckEnglish.setSize(60, 60);
        mCheckEnglish.setChecked(PreferencesManager.isRussianLanguage());
        mCheckEnglish.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                PreferencesManager.setLanguage("en");
                AssetLoader.changeLanguage();
                mScreen.optionMenu();
            }
        });

        mCheckRussian = new Button(AssetLoader.sGuiSkin, "check");
        mCheckRussian.setSize(60, 60);
        mCheckRussian.setChecked(PreferencesManager.isRussianLanguage());
        mCheckRussian.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                PreferencesManager.setLanguage("ru");
                AssetLoader.changeLanguage();
                mScreen.optionMenu();
            }
        });

        mLanguageGroup = new ButtonGroup<Button>();
        mLanguageGroup.add(mCheckEnglish);
        mLanguageGroup.add(mCheckRussian);
        mLanguageGroup.setMaxCheckCount(1);
    }

    private void checkMusic() {
        SoundSystem.setMusicEnable(mCheckMusic.isChecked());
        PreferencesManager.setMusicEnable(mCheckMusic.isChecked());
    }

    private void checkSound() {
        SoundSystem.setSoundEnable(mCheckSound.isChecked());
        PreferencesManager.setSoundEnable(mCheckSound.isChecked());
    }

    private void setupLabels() {

        mMusicLbl = new Label(AssetLoader.sBundle.get("MENU_OPTIONS_MUSIC"), AssetLoader.sGuiSkin);
        mMusicValLbl = new Label(AssetLoader.sBundle.get("MENU_OPTIONS_MUSIC_VAL"), AssetLoader.sGuiSkin);
        mSoundLbl = new Label(AssetLoader.sBundle.get("MENU_OPTIONS_SOUND"), AssetLoader.sGuiSkin);
        mSoundValLbl = new Label(AssetLoader.sBundle.get("MENU_OPTIONS_SOUND_VAL"), AssetLoader.sGuiSkin);

        mEnglish = new Label("English", AssetLoader.sGuiSkin);
        mRussian = new Label("Русский", AssetLoader.sGuiSkin);

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
        mContainer.row();

        mHorizontal = new HorizontalGroup();
        mHorizontal.addActor(mCheckEnglish);
        mHorizontal.addActor(mEnglish);
        mContainer.add(mHorizontal).padTop(15);

        mHorizontal = new HorizontalGroup();
        mHorizontal.addActor(mCheckRussian);
        mHorizontal.addActor(mRussian);
        mContainer.add(mHorizontal).padTop(15);

        mScrollPane = new ScrollPane(mContainer);

        mMainTable = new Table();
        mMainTable.background(new NinePatchDrawable(AssetLoader.broadbord));
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

    @Override
    public boolean keyDown(int keyCode) {


        if (keyCode== Input.Keys.BACK||keyCode== Input.Keys.MENU) {
            mScreen.mainMenu();
        }
        return super.keyDown(keyCode);
    }
}
