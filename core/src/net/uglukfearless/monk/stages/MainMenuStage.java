package net.uglukfearless.monk.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;

import net.uglukfearless.monk.actors.menu.MenuBackground;
import net.uglukfearless.monk.constants.Constants;
import net.uglukfearless.monk.listeners.baction.MenuOptionAction;
import net.uglukfearless.monk.listeners.baction.MenuPlayAction;
import net.uglukfearless.monk.listeners.baction.MenuStatisticsAction;
import net.uglukfearless.monk.screens.MainMenuScreen;
import net.uglukfearless.monk.ui.MenuTextButtonLib;
import net.uglukfearless.monk.utils.file.AssetLoader;

/**
 * Created by Ugluk on 30.06.2016.
 */
public class MainMenuStage extends Stage {

    public static final int VIEWPORT_WIDTH = Constants.APP_WIDTH;
    public static  int VIEWPORT_HEIGHT;



    private MainMenuScreen screen;

    private  Batch mBatch;

    private Vector3 touchPoint;

    private MenuTextButtonLib mTextButtonPlay;
    private MenuTextButtonLib mTextButtonOptions;
    private MenuTextButtonLib mTextButtonStatistics;

    private Label mTitle;

    public MainMenuStage(MainMenuScreen screen, float yViewportHeight) {

        super(new FitViewport(VIEWPORT_WIDTH, yViewportHeight,
                new OrthographicCamera(VIEWPORT_WIDTH, yViewportHeight)));
        VIEWPORT_HEIGHT = (int) yViewportHeight;

        this.screen = screen;
        addActor(new MenuBackground());
        Gdx.input.setInputProcessor(this);

        touchPoint = new Vector3();


        setupTitle(AssetLoader.sBundle.get("GAME_NAME"));

        setupButtons(yViewportHeight);

        Gdx.input.setCatchBackKey(false);
        Gdx.input.setCatchMenuKey(false);
    }


    private void setupTitle(String title) {
        mTitle = new Label(title , AssetLoader.sGuiSkin, "title");
        mTitle.setPosition(VIEWPORT_WIDTH/2f - mTitle.getWidth()/2f, VIEWPORT_HEIGHT - 30 - mTitle.getHeight());
        addActor(mTitle);
    }

    private void setupButtons(float yViewportHeight) {

        mTextButtonPlay = new MenuTextButtonLib(AssetLoader.sBundle.get("MENU_BUTTON_PLAY")
                , AssetLoader.sGuiSkin, new MenuPlayAction(screen));
        mTextButtonPlay.setSize(Constants.BUTTON_MENU_WIDTH, Constants.BUTTON_MENU_HEIGHT);
        mTextButtonPlay.setPosition(75, VIEWPORT_HEIGHT / 2f);
        addActor(mTextButtonPlay);

        mTextButtonOptions = new MenuTextButtonLib(AssetLoader.sBundle.get("MENU_BUTTON_OPTIONS")
                , AssetLoader.sGuiSkin, new MenuOptionAction(screen));
        mTextButtonOptions.setSize(Constants.BUTTON_MENU_WIDTH, Constants.BUTTON_MENU_HEIGHT);
        mTextButtonOptions.setPosition(75, VIEWPORT_HEIGHT / 2f - mTextButtonOptions.getHeight());
        addActor(mTextButtonOptions);

        mTextButtonStatistics = new MenuTextButtonLib(AssetLoader.sBundle.get("MENU_BUTTON_STATS")
                , AssetLoader.sGuiSkin, new MenuStatisticsAction(screen));
        mTextButtonStatistics.setSize(Constants.BUTTON_MENU_WIDTH, Constants.BUTTON_MENU_HEIGHT);
        mTextButtonStatistics.setPosition(75, VIEWPORT_HEIGHT / 2f- mTextButtonOptions.getHeight()*2);
        addActor(mTextButtonStatistics);

    }


    private void translateScreenToWorldCoordinates(int screenX, int screenY) {
        getCamera().unproject(touchPoint.set(screenX, screenY, 0));
    }

}
