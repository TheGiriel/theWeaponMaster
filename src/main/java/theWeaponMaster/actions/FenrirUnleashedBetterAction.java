package theWeaponMaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FenrirUnleashedBetterAction extends AbstractGameAction {

    private AbstractCard card;
    private AbstractGameAction.AttackEffect effect;

    public FenrirUnleashedBetterAction(AbstractCard card, AbstractGameAction.AttackEffect effect) {
        this.card = card;
        this.effect = effect;
    }

    public FenrirUnleashedBetterAction(AbstractCard card) {
        this(card, AbstractGameAction.AttackEffect.NONE);
    }

    public void update() {
        this.target = AbstractDungeon.getMonsters().monsters.get(0);
        for (int i = 0; i < AbstractDungeon.getMonsters().monsters.size() - 1; i++) {
            if (AbstractDungeon.getMonsters().monsters.get(i).currentHealth != 0 && AbstractDungeon.getMonsters().monsters.get(i).currentHealth < target.currentHealth) {
                this.target = AbstractDungeon.getMonsters().monsters.get(i);
            }
        }

        if (this.target != null) {
            this.card.calculateCardDamage((AbstractMonster) this.target);
            AbstractDungeon.actionManager.addToTop(new DamageAction(this.target, new DamageInfo(AbstractDungeon.player, this.card.damage, this.card.damageTypeForTurn), this.effect));
        }

        this.isDone = true;
    }
}