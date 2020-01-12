package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import theWeaponMaster.TheWeaponMaster;
import theWeaponMaster.patches.WeaponMasterTags;

import java.util.ArrayList;

public class CustomCartridgeAction extends AbstractGameAction {
    private static AbstractPlayer p = AbstractDungeon.player;
    CardGroup ammoSelection = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    private boolean pickCard = false;
    private int magicNumber;
    private int secondValue;
    private AbstractMonster m;
    private boolean upgraded = false;

    public CustomCartridgeAction(AbstractMonster monster, int mainDamageBonus, int secondaryDamageBonus, AbstractCard CustomCartridge) {

        if (CustomCartridge.upgraded) {
            upgraded = true;
        }

        for (AbstractCard card : p.hand.group) {
            if (card.hasTag(WeaponMasterTags.AMMUNITION)) {
                ammoSelection.addToTop(card);
            }
        }
        m = monster;
        magicNumber = mainDamageBonus;
        secondValue = secondaryDamageBonus;
        duration = Settings.ACTION_DUR_XFAST;
        actionType = ActionType.WAIT;

    }

    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_XFAST) {
            if (ammoSelection.isEmpty()) {
                this.isDone = true;
                return;
            }

            //ammoSelection.clear();
            if (ammoSelection.size() >= 1) {
                AbstractDungeon.gridSelectScreen.open(ammoSelection, 1, "Purge one Ammo card:", false, false, true, true);
                this.tickDuration();
                return;
            }
        } else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {


            bigBoomShot(AbstractDungeon.gridSelectScreen.selectedCards.get(0), m);
            p.hand.moveToExhaustPile(AbstractDungeon.gridSelectScreen.selectedCards.get(0));

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();
        }
        isDone = true;
    }

    public void bigBoomShot(AbstractCard card, AbstractMonster m) {

        ArrayList<AbstractMonster> monsterList = AbstractDungeon.getMonsters().monsters;
        boolean hitMinions = false;

        int firstEnemy = monsterList.indexOf(m);
        ArrayList<AbstractMonster> minionList = new ArrayList<>();

        int customDamge = card.damage * (magicNumber / 100);

        if (upgraded) {
            for (AbstractMonster monster : monsterList) {
                if (monster.hasPower(MinionPower.POWER_ID)) {
                    minionList.add(monster);
                } else firstEnemy = monsterList.indexOf(monster);
            }
            for (AbstractMonster monster : minionList) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(p, customDamge / 2, DamageInfo.DamageType.NORMAL), AttackEffect.BLUNT_LIGHT));
                hitMinions = true;
            }
            minionList.clear();
        }
        TheWeaponMaster.logger.info("CustomDamage: " + customDamge);
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.getMonsters().monsters.get(firstEnemy), new DamageInfo(p, customDamge, DamageInfo.DamageType.NORMAL), AttackEffect.BLUNT_HEAVY));
        if (monsterList.size() > firstEnemy + 1 && !hitMinions) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.getMonsters().monsters.get(firstEnemy + 1), new DamageInfo(p, (customDamge / 2), DamageInfo.DamageType.NORMAL), AttackEffect.BLUNT_LIGHT));
        }
    }
}
