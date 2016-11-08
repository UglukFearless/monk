package net.uglukfearless.monk.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;

import net.uglukfearless.monk.actors.menu.MenuBackground;
import net.uglukfearless.monk.enums.ArmourType;
import net.uglukfearless.monk.screens.GameScreen;
import net.uglukfearless.monk.utils.file.AssetLoader;
import net.uglukfearless.monk.utils.file.PreferencesManager;

/**
 * Created by Ugluk on 04.11.2016.
 */
public class ShopStage extends Stage {

    private static int VIEWPORT_WIDTH;
    private static int VIEWPORT_HEIGHT;
    private final float mYGameHeight;
    private final GameScreen mScreen;
    private final ShopStage THIS = this;

    private Table mWeaponCell;
    private Table mArmourCell;

    private Label mBalanceLabel;

    private int mCash;

    private ArmourType mCurrentArmour;
    private TextureRegion mTreasureRegion;

    private Window mAcceptWindow;
    private Label mAcceptCost;

    public ShopStage(GameScreen screen, float yViewportHeight) {
        super(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
                new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())));

        VIEWPORT_WIDTH = Gdx.graphics.getWidth();
        VIEWPORT_HEIGHT = Gdx.graphics.getHeight();

        mYGameHeight = yViewportHeight;
        mScreen = screen;

        mCash = PreferencesManager.getTreasures();
        mCurrentArmour = PreferencesManager.getArmour();

        mTreasureRegion = AssetLoader.bonusesAtlas.findRegion("rupee");

        setupBackground();
        setupWeaponTable();
        setupArmourTable();
        setupSelectItems();
        setupAcceptWindow();
        setupBackButton();
    }

    private void setupAcceptWindow() {

        mAcceptWindow = new Window("", AssetLoader.sGuiSkin);
        mAcceptWindow.setSize(VIEWPORT_WIDTH * 0.55f, VIEWPORT_HEIGHT * 0.65f);
        mAcceptWindow.setPosition(VIEWPORT_WIDTH / 2f - mAcceptWindow.getWidth() / 2f, VIEWPORT_HEIGHT / 2f - mAcceptWindow.getHeight() / 2f);
        mAcceptWindow.setMovable(false);
        mAcceptWindow.setResizable(false);
        mAcceptWindow.setModal(true);
        addActor(mAcceptWindow);

        Label acceptLabel = new Label(AssetLoader.sBundle.get("MENU_SHOP_LABEL_ACCEPT"), AssetLoader.sGuiSkin);
        mAcceptWindow.add(acceptLabel);
        mAcceptWindow.row();

        Table acceptHelpTable = new Table();
        mAcceptCost = new Label("x".concat(String.valueOf(PreferencesManager.getTreasures() - mCash)), AssetLoader.sGuiSkin);
        acceptHelpTable.add(mAcceptCost);
        Image acceptImage = new Image(mTreasureRegion);
        acceptHelpTable.add(acceptImage);
        mAcceptWindow.add(acceptHelpTable);
        mAcceptWindow.row();

        acceptHelpTable = new Table();
        TextButton acceptButton = new TextButton(AssetLoader.sBundle.get("MENU_SHOP_BUTTON_ACCEPT"), AssetLoader.sGuiSkin);
        acceptButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                PreferencesManager.purchase(PreferencesManager.getTreasures() - mCash);
                if (mCurrentArmour != null) {
                    PreferencesManager.setArmour(mCurrentArmour);
                } else {
                    PreferencesManager.clearArmour();
                }
                mScreen.returnGame();
            }
        });
        acceptHelpTable.add(acceptButton);

        TextButton cancelButton = new TextButton(AssetLoader.sBundle.get("MENU_SHOP_BUTTON_CANCEL"), AssetLoader.sGuiSkin);
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                mScreen.returnGame();
            }
        });
        acceptHelpTable.add(cancelButton);
        mAcceptWindow.add(acceptHelpTable);

        mAcceptWindow.setVisible(false);
    }

    private void setupSelectItems() {
        Table mainSelect = new Table();
        mainSelect.background(new NinePatchDrawable(AssetLoader.broadbord));
        mainSelect.setBounds(0, 0, VIEWPORT_WIDTH, VIEWPORT_HEIGHT * 0.35f);
        addActor(mainSelect);

        mWeaponCell = new Table();
        mWeaponCell.background(new NinePatchDrawable(AssetLoader.broadbord));
        mainSelect.add(mWeaponCell).prefWidth(VIEWPORT_WIDTH * 0.25f).prefHeight(VIEWPORT_HEIGHT * 0.25f)
                .padLeft(VIEWPORT_WIDTH * 0.1f);

        Table midTable = new Table();
        Table balance = new Table();
        Image balanceImage = new Image(AssetLoader.bonusesAtlas.findRegion("rupee"));
        balance.add(balanceImage).prefWidth(VIEWPORT_WIDTH*0.03f).prefHeight(VIEWPORT_HEIGHT * 0.05f);
        mBalanceLabel = new Label("x".concat(String.valueOf(mCash)), AssetLoader.sGuiSkin);
        balance.add(mBalanceLabel);
        midTable.add(balance);
        midTable.row();

        TextButton donateButton = new TextButton(AssetLoader.sBundle.get("MENU_SHOP_BUTTON_BUY"), AssetLoader.sGuiSkin);
        Image donateImage = new Image(AssetLoader.bonusesAtlas.findRegion("rupee"));
        donateButton.add(donateImage).prefWidth(VIEWPORT_WIDTH*0.03f).prefHeight(VIEWPORT_HEIGHT * 0.05f);
        midTable.add(donateButton);

        mainSelect.add(midTable);


        mArmourCell = new Table();
        mArmourCell.background(new NinePatchDrawable(AssetLoader.broadbord));
        if (mCurrentArmour!=null) {
            mArmourCell.add(new Image(mCurrentArmour.getImage()));
        }
        mArmourCell.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (mCurrentArmour != null) {
                    mCash += mCurrentArmour.getPrice();
                }
                mCurrentArmour=null;
                mBalanceLabel.setText("x".concat(String.valueOf(mCash)));
                mArmourCell.clearChildren();
            }
        });
        mainSelect.add(mArmourCell).prefWidth(VIEWPORT_WIDTH * 0.25f).prefHeight(VIEWPORT_HEIGHT * 0.25f)
                .padRight(VIEWPORT_WIDTH * 0.1f);

    }

    private void setupArmourTable() {
        Table mainArmour = new Table();
        mainArmour.background(new NinePatchDrawable(AssetLoader.broadbord));
        mainArmour.setBounds(VIEWPORT_WIDTH * 0.55f, VIEWPORT_HEIGHT * 0.33f
                , VIEWPORT_WIDTH * 0.3f, VIEWPORT_HEIGHT * 0.55f);
        addActor(mainArmour);

        Table tableArmours = new Table();

        for (final ArmourType armourType : ArmourType.values()) {
            Table tableArmour = new Table();
            Image imageArmour = new Image(armourType.getImage());
            tableArmour.add(imageArmour);
            Label labelArmour = new Label(String.valueOf(armourType.getPrice()), AssetLoader.sGuiSkin);
            tableArmour.add(labelArmour);
            Image imageTreasures = new Image(AssetLoader.bonusesAtlas.findRegion("rupee"));
            tableArmour.add(imageTreasures);
            tableArmour.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    THIS.buy(armourType);
                }
            });
            tableArmours.add(tableArmour);
            tableArmours.row();
        }

        ScrollPane scrollArmour = new ScrollPane(tableArmours);

        mainArmour.add(scrollArmour);
    }

    private void buy(ArmourType armourType) {
        if ((mCurrentArmour!=null&&(mCash+mCurrentArmour.getPrice()-armourType.getPrice()>=0))
                ||(mCash-armourType.getPrice()>=0)) {
            if (mCurrentArmour!=null) {
                mCash +=mCurrentArmour.getPrice();
            }
            mCash-=armourType.getPrice();
            mBalanceLabel.setText("x".concat(String.valueOf(mCash)));
            mCurrentArmour = armourType;
            mArmourCell.clearChildren();
            mArmourCell.add(new Image(armourType.getImage()));
        }
    }

    private void setupWeaponTable() {

        Table mainWeapon = new Table();
        mainWeapon.background(new NinePatchDrawable(AssetLoader.broadbord));
        mainWeapon.setBounds(VIEWPORT_WIDTH * 0.15f, VIEWPORT_HEIGHT * 0.33f
                , VIEWPORT_WIDTH * 0.3f, VIEWPORT_HEIGHT * 0.55f);
        addActor(mainWeapon);
    }

    private void setupBackButton() {
        Button backButton = new Button(AssetLoader.sGuiSkin, "back");
        backButton.setSize(VIEWPORT_WIDTH*0.125f, VIEWPORT_HEIGHT*0.13f);
        backButton.setPosition(0, VIEWPORT_HEIGHT - backButton.getHeight() - 50);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (PreferencesManager.getTreasures()==mCash) {
                    if (mCurrentArmour != null) {
                        PreferencesManager.setArmour(mCurrentArmour);
                    } else {
                        PreferencesManager.clearArmour();
                    }
                    mScreen.returnGame();
                } else {
                    if (PreferencesManager.getTreasures() - mCash>=0) {
                        mAcceptCost.setText("x".concat(String.valueOf(mCash - PreferencesManager.getTreasures())));
                    } else {
                        mAcceptCost.setText("x+".concat(String.valueOf(mCash - PreferencesManager.getTreasures())));
                    }
                    mAcceptWindow.setVisible(true);
                }
            }
        });
        addActor(backButton);
    }

    private void setupBackground() {
        addActor(new MenuBackground());
    }
}