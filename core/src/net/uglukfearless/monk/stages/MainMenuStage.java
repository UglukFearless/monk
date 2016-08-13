package net.uglukfearless.monk.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import net.uglukfearless.monk.actors.menu.MenuBackground;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.listeners.baction.MenuOptionAction;
import net.uglukfearless.monk.listeners.baction.MenuPlayAction;
import net.uglukfearless.monk.listeners.baction.MenuStatisticsAction;
import net.uglukfearless.monk.screens.MainMenu;
import net.uglukfearless.monk.ui.MenuButton;
import net.uglukfearless.monk.ui.MenuTextButtonLib;
import net.uglukfearless.monk.utils.AssetLoader;

/**
 * Created by Ugluk on 30.06.2016.
 */
public class MainMenuStage extends Stage {

    public static final int VIEWPORT_WIDTH = Constants.APP_WIDTH;
    public static  int VIEWPORT_HEIGHT;



    private MainMenu screen;

    private  Batch mBatch;

    private Vector3 touchPoint;

//    private MenuButton mPlayButton;
//    private MenuButton mOptionButton;
//    private MenuButton mStatisticsButton;
//    private String title;

    private MenuTextButtonLib mTextButtonPlay;
    private MenuTextButtonLib mTextButtonOptions;
    private MenuTextButtonLib mTextButtonStatistics;

    private Label mTitle;

    public MainMenuStage(MainMenu screen, float yViewportHeight) {

        super(new FitViewport(VIEWPORT_WIDTH, yViewportHeight,
                new OrthographicCamera(VIEWPORT_WIDTH, yViewportHeight)));
        VIEWPORT_HEIGHT = (int) yViewportHeight;

        this.screen = screen;
        addActor(new MenuBackground());
        Gdx.input.setInputProcessor(this);

        touchPoint = new Vector3();

        setupTitle("Running Monk");
        setupButtons(yViewportHeight);

    }


    private void setupTitle(String title) {
        mTitle = new Label(title , AssetLoader.sGuiSkin, "title");
        mTitle.setPosition(VIEWPORT_WIDTH/2f - mTitle.getWidth()/2f, VIEWPORT_HEIGHT - 30 - mTitle.getHeight());
        addActor(mTitle);
    }

    private void setupButtons(float yViewportHeight) {

        mTextButtonPlay = new MenuTextButtonLib("PLAY", AssetLoader.sGuiSkin, new MenuPlayAction(screen));
        mTextButtonPlay.setSize(Constants.BUTTON_MENU_WIDTH, Constants.BUTTON_MENU_HEIGHT);
        mTextButtonPlay.setPosition(75, VIEWPORT_HEIGHT / 2f);
        addActor(mTextButtonPlay);

        mTextButtonOptions = new MenuTextButtonLib("OPTIONS", AssetLoader.sGuiSkin, new MenuOptionAction(screen));
        mTextButtonOptions.setSize(Constants.BUTTON_MENU_WIDTH, Constants.BUTTON_MENU_HEIGHT);
        mTextButtonOptions.setPosition(75, VIEWPORT_HEIGHT / 2f - mTextButtonOptions.getHeight());
        addActor(mTextButtonOptions);

        mTextButtonStatistics = new MenuTextButtonLib("STATS", AssetLoader.sGuiSkin, new MenuStatisticsAction(screen));
        mTextButtonStatistics.setSize(Constants.BUTTON_MENU_WIDTH, Constants.BUTTON_MENU_HEIGHT);
        mTextButtonStatistics.setPosition(75, VIEWPORT_HEIGHT / 2f- mTextButtonOptions.getHeight()*2);
        addActor(mTextButtonStatistics);


//        mPlayButton = new MenuButton(75, yViewportHeight/2, Constants.BUTTON_MENU_WIDTH,
//                Constants.BUTTON_MENU_HEIGHT, AssetLoader.buttonMenuOn, AssetLoader.buttonMenuOff);
//        mPlayButton.setTitle("Play");
//        mPlayButton.setAction(new MenuPlayAction(screen));
//
//        mOptionButton = new MenuButton(75, yViewportHeight/2 - Constants.BUTTON_MENU_HEIGHT*1, Constants.BUTTON_MENU_WIDTH,
//                Constants.BUTTON_MENU_HEIGHT, AssetLoader.buttonMenuOn, AssetLoader.buttonMenuOff);
//        mOptionButton.setTitle("Option");
//        mOptionButton.setAction(new MenuOptionAction(screen));
//
//        mStatisticsButton = new MenuButton(75, yViewportHeight/2 - Constants.BUTTON_MENU_HEIGHT*2, Constants.BUTTON_MENU_WIDTH,
//                Constants.BUTTON_MENU_HEIGHT, AssetLoader.buttonMenuOn, AssetLoader.buttonMenuOff);
//        mStatisticsButton.setTitle("Stats");
//        mStatisticsButton.setAction(new MenuStatisticsAction(screen));
    }

//    @Override
//    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//
//        translateScreenToWorldCoordinates(screenX, screenY);
//
//        mPlayButton.isTouchDown((int) touchPoint.x, (int) touchPoint.y);
//        mOptionButton.isTouchDown((int) touchPoint.x, (int) touchPoint.y);
//        mStatisticsButton.isTouchDown((int)touchPoint.x, (int)touchPoint.y);
//        return super.touchDown(screenX, screenY, pointer, button);
//    }

//    @Override
//    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//
//        translateScreenToWorldCoordinates(screenX, screenY);
//
//        mPlayButton.isTouchUp((int) touchPoint.x, (int) touchPoint.y);
//        mOptionButton.isTouchUp((int) touchPoint.x, (int) touchPoint.y);
//        mStatisticsButton.isTouchUp((int)touchPoint.x, (int)touchPoint.y);
//        return super.touchUp(screenX, screenY, pointer, button);
//    }

    private void translateScreenToWorldCoordinates(int screenX, int screenY) {
        getCamera().unproject(touchPoint.set(screenX, screenY, 0));
    }

    @Override
    public void draw() {
        super.draw();

        mBatch = super.getBatch();
        if (mBatch != null) {
            mBatch.setProjectionMatrix(super.getCamera().combined);
            mBatch.begin();
//            mPlayButton.draw((SpriteBatch) mBatch);
//            mOptionButton.draw((SpriteBatch)mBatch);
//            mStatisticsButton.draw((SpriteBatch)mBatch);
            mBatch.end();
        }
    }
}
