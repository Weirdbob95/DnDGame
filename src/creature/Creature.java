package creature;

import actions.ActionManagerComponent;
import actions.MonsterAttackAction;
import amounts.Value;
import controllers.ManualController;
import core.AbstractEntity;
import static enums.AbilityScore.*;
import enums.CreatureType;
import enums.DamageType;
import enums.Size;
import events.AbilityCheckEvent;
import graphics.SpriteComponent;
import graphics.SpriteSystem;
import grid.GridLocationComponent;
import grid.Square;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import movement.PositionComponent;
import movement.RotationComponent;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import util.Log;
import util.Vec2;

public class Creature extends AbstractEntity implements Serializable {

    public AbilityScoreComponent asc;
    public ActionManagerComponent amc;
    public ArmorComponent ac;
    public ConditionComponent cnc;
    public CreatureComponent cc;
    public CreatureDescriptionComponent cdc;
    public EventListenersComponent elc;
    public ExhaustionComponent exc;
    public GridLocationComponent glc;
    public HealthComponent hc;
    public LanguageComponent lc;
    public ResistancesComponent rc;
    public SpeedComponent spc;
    public WieldingComponent wc;

    public Creature(Square square) {
        //Components
        elc = add(new EventListenersComponent());
        asc = add(new AbilityScoreComponent());
        amc = add(new ActionManagerComponent(this));
        ac = add(new ArmorComponent(this));
        cnc = add(new ConditionComponent(this));
        cdc = add(new CreatureDescriptionComponent());
        exc = add(new ExhaustionComponent(this));
        hc = add(new HealthComponent());
        lc = add(new LanguageComponent(this));
        rc = add(new ResistancesComponent());
        spc = add(new SpeedComponent(this));
        wc = add(new WieldingComponent(0));

        cc = add(new CreatureComponent(this, new ManualController(this), new AbilityCheckEvent(this, DEX, null, 0).get(), true));
        glc = add(new GridLocationComponent(square, this));

        RotationComponent rc = add(new RotationComponent());
        SpriteComponent sc = add(new SpriteComponent("red"));
        if (square != null) {
            PositionComponent pc = add(new PositionComponent(square.center()));
            //Systems
            add(new SpriteSystem(pc, rc, sc));
            add(new HealthbarSystem(pc, hc, cdc));
        } else {
            PositionComponent pc = add(new PositionComponent());
        }
    }

    private static DocumentBuilderFactory dbf;
    private static DocumentBuilder db;
    private static Document doc;

    static {
        try {
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            doc = db.parse(new File("monsters.xml"));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            Log.error(e);
        }
    }

    private static Node findMonsterNode(String name) {
        NodeList nodeList = doc.getDocumentElement().getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node n = nodeList.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                if (n.getChildNodes().item(1).getFirstChild().getNodeValue().equals(name)) {
                    return n;
                }
            }
        }
        return null;
    }

    private static Node findChildNode(Node n, String name) {
        NodeList nodeList = n.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node n2 = nodeList.item(i);
            if (n2.getNodeType() == Node.ELEMENT_NODE) {
                if (n2.getNodeName().equals(name)) {
                    return n2;
                }
            }
        }
        return null;
    }

    public static Creature loadCreature(String name, Square square) {
        Creature c = new Creature(square);
        Node creatureNode = findMonsterNode(name);
        if (creatureNode == null) {
            return c;
        }
        NodeList nodeList = creatureNode.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node n = nodeList.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                if (n.getFirstChild() != null) {
                    String text = n.getFirstChild().getNodeValue();
                    switch (n.getNodeName()) {
                        case "name":
                            c.cdc.name = text;
                            break;
                        case "size":
                            c.cdc.size = Size.parse(text);
                            c.getComponent(SpriteComponent.class).scale = new Vec2(1, 1).multiply(c.cdc.size.squares);
                            c.glc.moveToSquare(c.glc.lowerLeft);
                            c.glc.updateSpritePos();
                            break;
                        case "type":
                            c.cdc.type = CreatureType.valueOf(text.substring(0, text.indexOf(",")).toUpperCase());
                            break;
                        case "alignment":
                            c.cdc.alignment = text;
                            break;
                        case "ac":
                            c.ac.AC.set(Integer.parseInt(text));
                            c.ac.AC.set("Dex", 0);
                            break;
                        case "hp":
                            c.hc.maxHealth.set(Integer.parseInt(text.substring(0, text.indexOf(" "))));
                            break;
                        case "speed":
                            c.spc.landSpeed.set(Integer.parseInt(text.substring(0, text.indexOf(" "))));
                            break;
                        case "str":
                            c.asc.set(STR, Integer.parseInt(text));
                            break;
                        case "dex":
                            c.asc.set(DEX, Integer.parseInt(text));
                            break;
                        case "con":
                            c.asc.set(CON, Integer.parseInt(text));
                            break;
                        case "int":
                            c.asc.set(INT, Integer.parseInt(text));
                            break;
                        case "wis":
                            c.asc.set(WIS, Integer.parseInt(text));
                            break;
                        case "cha":
                            c.asc.set(CHA, Integer.parseInt(text));
                            break;
                        case "languages":
                            c.lc.languages.addAll(Arrays.asList(text.split(", ")));
                            break;
                        case "action":
                            Node attack = findChildNode(n, "attack");
                            if (attack == null) {
                                break;
                            }
                            String[] attackParts = attack.getFirstChild().getNodeValue().split("\\|");
                            String desc = findChildNode(n, "text").getFirstChild().getNodeValue();
                            String[] firstBits = desc.substring(0, desc.indexOf(":")).split(" ");
                            if (firstBits.length != 3 && firstBits.length != 5) {
                                break;
                            }
                            int parenPos = desc.indexOf(")") + 2;
                            MonsterAttackAction maa = new MonsterAttackAction(c);

                            maa.name = attackParts[0];
                            maa.toHit = Integer.parseInt(attackParts[1]);
                            maa.damage = Value.parseValue(attackParts[2]);
                            maa.isRanged = firstBits[0].equals("Ranged");
                            maa.isWeapon = firstBits[firstBits.length - 1].equals("Weapon");
                            maa.damageType = DamageType.valueOf(desc.substring(parenPos, desc.indexOf(" ", parenPos)).toUpperCase());

                            if (!maa.isRanged) {
                                int reachPos = desc.indexOf("reach ");
                                maa.range = Integer.valueOf(desc.substring(reachPos + 6, desc.indexOf(" ", reachPos + 6)));
                            } else {
                                int rangePos = desc.indexOf("range ");
                                String[] ranges = desc.substring(rangePos + 6, desc.indexOf(" ", rangePos + 6)).split("/");
                                maa.range = Integer.valueOf(ranges[0]);
                                if (ranges.length > 1) {
                                    maa.rangeLong = Integer.valueOf(ranges[1]);
                                }
                            }

                            c.amc.addAction(maa);
                            break;
                    }
                }
            }
        }

        return c;
    }
}
