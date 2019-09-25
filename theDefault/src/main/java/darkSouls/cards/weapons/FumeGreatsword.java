package darkSouls.cards.weapons;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import darkSouls.DefaultMod;
import darkSouls.characters.TheDefault;

import static darkSouls.DefaultMod.makeCardPath;

public class FumeGreatsword extends CustomCard {
    // General
    public static final String ID = DefaultMod.makeID(FumeGreatsword.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    // Card Properties
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.ATTACK;
    public static final AbstractCard.CardColor COLOR = TheDefault.Enums.COLOR_GRAY;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    public static final String IMG = makeCardPath("Attack.png");
    // Numbers
    private static final int COST = 4;
    private static final int DAMAGE = 4;
    private static final int UPGRADE_DAMAGE = 1;
    private static final int VULNERABLE = 4;
    private static final int VULNERABLE_UPGRADE = 1;
    private static final int STRENGTH_SCALE = 4;
    private static final int STRENGTH_SCALE_UPGRADE = 1;

    public FumeGreatsword(){
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = VULNERABLE;
        block = baseBlock = STRENGTH_SCALE;

    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            upgradeMagicNumber(VULNERABLE_UPGRADE);
            upgradeBlock(STRENGTH_SCALE_UPGRADE);
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(new DamageAction(monster, new DamageInfo(player, damage)));
        addToBot(new ApplyPowerAction(monster,player,new VulnerablePower(monster, magicNumber, false)));
        int s = 0;
        for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo==monster){
                if (s!=0){
                    AbstractMonster before = AbstractDungeon.getCurrRoom().monsters.monsters.get(s-1);
                    addToBot(new DamageAction(before, new DamageInfo(player, damage)));
                    addToBot(new ApplyPowerAction(before,player,new VulnerablePower(before, magicNumber, false)));
                }
                if (s+1!=AbstractDungeon.getCurrRoom().monsters.monsters.size()){
                    AbstractMonster after = AbstractDungeon.getCurrRoom().monsters.monsters.get(s+1);
                    addToBot(new DamageAction(after, new DamageInfo(player, damage)));
                    addToBot(new ApplyPowerAction(after,player,new VulnerablePower(after, magicNumber, false)));
                }
            }
            s++;
        }
    }
}
