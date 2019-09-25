package darkSouls.cards.weapons;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import darkSouls.DefaultMod;
import darkSouls.characters.TheDefault;
import darkSouls.powers.BleedPower;

import static darkSouls.DefaultMod.makeCardPath;

public class BanditsKnife extends CustomCard {
    // General
    public static final String ID = DefaultMod.makeID(BanditsKnife.class.getSimpleName());
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
    private static final int COST = 0;
    private static final int DAMAGE = 2;
    private static final int UPGRADE_DAMAGE = 1;
    private static final int BLEED = 2;
    private static final int UPGRADE_BLEED = 1;
    private static final int ATTACK_COUNT = 2;
    private static final int UPGRADE_ATTACK_COUNT = 1;

    public BanditsKnife(){
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = BLEED;
        block = baseBlock = ATTACK_COUNT;
        isMultiDamage = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            upgradeMagicNumber(UPGRADE_BLEED);
            upgradeBlock(UPGRADE_ATTACK_COUNT);
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        for (int i=0; i<block; i++){
            addToBot(new DamageAction(monster, new DamageInfo(player, damage)));
            addToBot(new ApplyPowerAction(monster,player,new BleedPower(monster,player,magicNumber)));
        }
    }
}
