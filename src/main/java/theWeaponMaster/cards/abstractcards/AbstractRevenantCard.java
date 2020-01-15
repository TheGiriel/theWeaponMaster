package theWeaponMaster.cards.abstractcards;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWeaponMaster.actions.RevenantStarveAction;
import theWeaponMaster.patches.WeaponMasterTags;

import static theWeaponMaster.relics.ArsenalRelic.revenantHunger;

public abstract class AbstractRevenantCard extends AbstractDynamicCard {

    public static final TextureAtlas.AtlasRegion REVENANT_HUNGRY_SMALL_FRAME = new TextureAtlas.AtlasRegion(new Texture("theWeaponMasterResources/images/512/revenant_hunger_attack_frame_small.png"), 0, 0, 512, 512);
    public static final TextureAtlas.AtlasRegion REVENANT_HUNGRY_LARGE_FRAME = new TextureAtlas.AtlasRegion(new Texture("theWeaponMasterResources/images/1024/revenant_hunger_attack_frame_large.png"), 0, 0, 1024, 1024);
    public static final TextureAtlas.AtlasRegion REVENANT_SATED_SMALL_FRAME = new TextureAtlas.AtlasRegion(new Texture("theWeaponMasterResources/images/512/revenant_hunger_attack_frame_small.png"), 0, 0, 512, 512);
    public static final TextureAtlas.AtlasRegion REVENANT_SATED_LARGE_FRAME = new TextureAtlas.AtlasRegion(new Texture("theWeaponMasterResources/images/1024/revenant_hunger_attack_frame_large.png"), 0, 0, 1024, 1024);
    public static int hungerThreshold;
    public int HUNGERCOST;

    public AbstractRevenantCard(String id,
                                String img,
                                int cost,
                                CardType type,
                                CardColor color,
                                CardRarity rarity,
                                CardTarget target,
                                int hunger) {

        super(id, img, cost, type, color, rarity, target);

    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (!c.hasTag(WeaponMasterTags.REVENANT)) {
            revenantHunger = Math.min(Math.max(revenantHunger, 0), 10);
            AbstractDungeon.actionManager.addToBottom(new RevenantStarveAction(1, false));
        }
    }

    /*@SpireOverride
    public void renderAttackPortrait(SpriteBatch sb, float x, float y){
        if (revenantHunger>= hungerThreshold){
            renderHelper(sb, getRenderColor(), REVENANT_HUNGRY_SMALL_FRAME, x, y);
        } else {
            renderHelper(sb, getRenderColor(), REVENANT_SATED_SMALL_FRAME, x, y);
        }
    }

    @SpireOverride
    public void renderHelper(SpriteBatch sb, Color renderColor, TextureAtlas.AtlasRegion img , float x, float y) {
        SpireSuper.call(sb, renderColor, img, x,y);
    }

    private Field renderColorField = null;

    private Color getRenderColor(){
        Color reflectedColor = null;
        if (renderColorField == null){
            try{
                renderColorField = AbstractCard.class.getDeclaredField("renderColor");
                renderColorField.setAccessible(true);
            }catch (NoSuchFieldException e){
                e.printStackTrace();
            }
        }
        try {
            reflectedColor = (Color)renderColorField.get(this);
        } catch (IllegalAccessException e){
            e.printStackTrace();
        }
        if (reflectedColor == null){
            return Color.WHITE.cpy();
        } else {
            return reflectedColor;
        }
    }*/
}