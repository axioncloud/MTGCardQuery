package net.skyestudios.mtgcardquery.data;

import net.skyestudios.mtgcardquery.views.CardView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arkeonet64 on 4/4/2017.
 */

public class Card implements Serializable {
    private String name;
    private String layout;
    private String colors;
    private Double cmc;
    private String manaCost;
    private String type;
    private String types;
    private String subtypes;
    private String text;
    private String power;
    private String toughness;
    private String imageName;
    private String printings;
    private String source;
    private String rulings;
    private String colorIdentity;
    private String legalities;
    private String supertypes;
    private boolean starter;
    private Integer loyalty;
    private Integer hand;
    private Integer life;
    private String names;

    public Card() {
        name = "";
        layout = "";
        colors = "";
        cmc = 0.0;
        manaCost = "";
        type = "";
        types = "";
        subtypes = "";
        text = "";
        power = "";
        toughness = "";
        imageName = "";
        printings = "";
        source = "";
        rulings = "";
        colorIdentity = "";
        legalities = "";
        supertypes = "";
        starter = false;
        loyalty = 0;
        hand = 0;
        life = 0;
        names = "";
    }

    public static List<Card> createListFromCardViews(List<CardView> cardViews) {
        ArrayList<Card> cards = new ArrayList<>();
        for (CardView cardView :
                cardViews) {
            cards.add(cardView.getCard());
        }
        return cards;
    }

    @Override
    public boolean equals(Object obj) {
        return this.name.equals(((Card) obj).getName());
    }

    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + '\'' +
                ", layout='" + layout + '\'' +
                ", colors='" + colors + '\'' +
                ", cmc=" + cmc +
                ", manaCost='" + manaCost + '\'' +
                ", type='" + type + '\'' +
                ", types='" + types + '\'' +
                ", subtypes='" + subtypes + '\'' +
                ", text='" + text + '\'' +
                ", power='" + power + '\'' +
                ", toughness='" + toughness + '\'' +
                ", imageName='" + imageName + '\'' +
                ", printings='" + printings + '\'' +
                ", source='" + source + '\'' +
                ", rulings='" + rulings + '\'' +
                ", colorIdentity='" + colorIdentity + '\'' +
                ", legalities='" + legalities + '\'' +
                ", supertypes='" + supertypes + '\'' +
                ", starter=" + starter +
                ", loyalty=" + loyalty +
                ", hand=" + hand +
                ", life=" + life +
                ", names='" + names + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }

    public Double getCmc() {
        return cmc;
    }

    public void setCmc(Double cmc) {
        this.cmc = cmc;
    }

    public String getManaCost() {
        return manaCost;
    }

    public void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getSubtypes() {
        return subtypes;
    }

    public void setSubtypes(String subtypes) {
        this.subtypes = subtypes;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getToughness() {
        return toughness;
    }

    public void setToughness(String toughness) {
        this.toughness = toughness;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getPrintings() {
        return printings;
    }

    public void setPrintings(String printings) {
        this.printings = printings;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRulings() {
        return rulings;
    }

    public void setRulings(String rulings) {
        this.rulings = rulings;
    }

    public String getColorIdentity() {
        return colorIdentity;
    }

    public void setColorIdentity(String colorIdentity) {
        this.colorIdentity = colorIdentity;
    }

    public String getLegalities() {
        return legalities;
    }

    public void setLegalities(String legalities) {
        this.legalities = legalities;
    }

    public String getSupertypes() {
        return supertypes;
    }

    public void setSupertypes(String supertypes) {
        this.supertypes = supertypes;
    }

    public Boolean getStarter() {
        return starter;
    }

    public void setStarter(Boolean starter) {
        this.starter = starter;
    }

    public Integer getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(Integer loyalty) {
        this.loyalty = loyalty;
    }

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    public Integer getLife() {
        return life;
    }

    public void setLife(Integer life) {
        this.life = life;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }
}
