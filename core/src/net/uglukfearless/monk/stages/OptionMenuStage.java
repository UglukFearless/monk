package net.uglukfearless.monk.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import net.uglukfearless.monk.actors.menu.MenuBackground;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.screens.MainMenu;
import net.uglukfearless.monk.ui.BackButton;
import net.uglukfearless.monk.utils.AssetLoader;

/**
 * Created by Ugluk on 08.08.2016.
 */
public class OptionMenuStage extends Stage {

    public static final int VIEWPORT_WIDTH = Constants.APP_WIDTH;
    public static  int VIEWPORT_HEIGHT;

    private MainMenu mScreen;

    private Vector3 mTouchPoint;

    private BackButton mBackButton;

    private Button mCheckMusic;
    private Button mCheckSound;

    private Label mTitle;

    private Label mMusicLbl, mSoundLbl, mMusicValLbl, mSoundValLbl;

    public OptionMenuStage(MainMenu screen, float yViewportHeight) {
        super(new FitViewport(VIEWPORT_WIDTH, yViewportHeight,
                new OrthographicCamera(VIEWPORT_WIDTH, yViewportHeight)));
        VIEWPORT_HEIGHT = (int) yViewportHeight;

        mScreen = screen;

        addActor(new MenuBackground());
        Gdx.input.setInputProcessor(this);

        mTouchPoint = new Vector3();

        setupTitle("Options");

        setupLabels();

        mBackButton = new BackButton(AssetLoader.sGuiSkin, screen);
        mBackButton.setSize(100, 62);
        mBackButton.setPosition(0, VIEWPORT_HEIGHT - mBackButton.getHeight() - 50);
        addActor(mBackButton);

        mCheckMusic = new Button(AssetLoader.sGuiSkin, "check");
        mCheckMusic.setChecked(true);
        mCheckMusic.setSize(mMusicLbl.getHeight(), mMusicLbl.getHeight());
        mCheckMusic.setPosition(VIEWPORT_WIDTH - 100 - mCheckMusic.getWidth(), mMusicLbl.getY());
        addActor(mCheckMusic);

        mCheckSound = new Button(AssetLoader.sGuiSkin, "check");
        mCheckSound.setChecked(true);
        mCheckSound.setSize(mSoundLbl.getHeight(), mSoundLbl.getHeight());
        mCheckSound.setPosition(VIEWPORT_WIDTH - 100 - mCheckSound.getWidth(), mSoundLbl.getY());
        addActor(mCheckSound);

    }

    private void setupLabels() {

        mMusicLbl = new Label("Music", AssetLoader.sGuiSkin);
        mMusicValLbl = new Label("Music Value", AssetLoader.sGuiSkin);
        mSoundLbl = new Label("Sounds", AssetLoader.sGuiSkin);
        mSoundValLbl = new Label("Sounds Value", AssetLoader.sGuiSkin);

        Array<Label> labelList = new Array<Label>();

        labelList.add(mMusicLbl);
        labelList.add(mMusicValLbl);
        labelList.add(mSoundLbl);
        labelList.add(mSoundValLbl);

        for (int i = 0; i<labelList.size; i++) {
            Label label = labelList.get(i);
            label.setPosition(100, VIEWPORT_HEIGHT / 2 - (label.getHeight() + 15) * i);
            addActor(label);
        }
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
