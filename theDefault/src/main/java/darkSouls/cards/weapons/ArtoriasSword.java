package darkSouls.cards.weapons;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import darkSouls.DefaultMod;
import darkSouls.characters.TheDefault;

import static darkSouls.DefaultMod.makeCardPath;

public class ArtoriasSword extends CustomCard {
    // General
    public static final String ID = DefaultMod.makeID(ArtoriasSword.class.getSimpleName());
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
    private static final int COST = 3;
    private static final int DAMAGE = 10;
    private static final int UPGRADE_DAMAGE = 5;
    private static final int VULNERABLE = 2;
    private static final int VULNERABLE_UPGRADE = 1;
    private static final int ENERGY = 1;

    public ArtoriasSword(){
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = VULNERABLE;
        block = baseBlock = ENERGY;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            upgradeMagicNumber(VULNERABLE_UPGRADE);
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(new DamageAction(monster, new DamageInfo(player, damage)));
        addToBot(new ApplyPowerAction(monster,player,new VulnerablePower(monster, magicNumber, false)));
        addToBot(new GainEnergyAction(block));
    }
}
