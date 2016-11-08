package net.uglukfearless.monk.utils;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;

import net.uglukfearless.monk.utils.file.AssetLoader;

/**
 * Created by Ugluk on 29.10.2016.
 */
public class BonusShow extends Table {

    private Stage mContext;
    private float mWidth, mHeight;
    private String mText, mCount, mRegionName;

    private Table HelpTable;
    private Label mBonusLabel, mTextLabel, mCountLabel;
    private Image mBonusImage;

    private BonusShow(Stage context, int viewportWidth, int viewportHeight, String text, int count, String bonusRegName) {

        super();

        mContext = context;
        mWidth = 0.75f*viewportWidth;
        mHeight = 0.7f*viewportHeight;

        mText = text;
        mCount = String.valueOf(count);
        mRegionName = bonusRegName;

        mBonusLabel = new Label(AssetLoader.sBundle.get("MENU_SHOW_BONUS_TITLE"), AssetLoader.sGuiSkin, "title");
        mBonusLabel.setAlignment(Align.center);
        this.add(mBonusLabel).fill().expand();
        this.row();
        mTextLabel = new Label(text, AssetLoader.sGuiSkin);
        mTextLabel.setAlignment(Align.center);
        this.add(mTextLabel).fill().expand();
        this.row();

        HelpTable = new Table();
        HelpTable.align(Align.center);

        HelpTable.add(new Label("", AssetLoader.sGuiSkin)).fill().expand(); //чисто для выравнивания и чтобы клики ловить
        mBonusImage = new Image(AssetLoader.bonusesAtlas.findRegion(mRegionName));
        HelpTable.add(mBonusImage).align(Align.right).prefWidth(64).prefHeight(64);
        mCountLabel = new Label(" + ".concat(mCount), AssetLoader.sGuiSkin);
        HelpTable.add(mCountLabel).align(Align.left);
        HelpTable.add(new Label("", AssetLoader.sGuiSkin)).fill().expand(); //чисто для выравнивания и чтобы клики ловить

        this.add(HelpTable).fill().expand();

        this.background(new NinePatchDrawable(AssetLoader.broadbord));
        this.setBounds((viewportWidth - mWidth) / 2f, (viewportHeight - mHeight) / 2f, mWidth, mHeight);

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                removeTable();
            }
        });
    }

    public static BonusShow showBonus(Stage context, int viewportWidth, int viewportHeight, String text, int count,  String bonusRegName) {
        BonusShow bonusShow = new BonusShow(context, viewportWidth, viewportHeight, text, count, bonusRegName);
        context.addActor(bonusShow);

        return bonusShow;
    }

    public void removeTable() {
        addAction(Actions.sequence(Actions.alpha(0,0.2f), Actions.removeActor()));
    }
}
