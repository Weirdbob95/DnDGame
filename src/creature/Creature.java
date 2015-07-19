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
import graphics.SpriteComponent;
import graphics.SpriteSystem;
import grid.GridLocationComponent;
import grid.Square;
import java.io.File;
import java.io.IOException;
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

public class Creature extends AbstractEntity {

    public AbilityScoreComponent asc;
    public ActionManagerComponent amc;
    public ArmorComponent ac;
    public CreatureComponent cc;
    public CreatureDescriptionComponent cdc;
    public GridLocationComponent glc;
    public HealthComponent hc;
    public LanguageComponent lc;
    public ProficienciesComponent pc;
    public ResistancesComponent rc;
    public SpeedComponent spc;

    public Creature(Square square) {
        new CreatureListener(this);
        //Components
        asc = add(new AbilityScoreComponent());
        amc = add(new ActionManagerComponent(this));
        ac = add(new ArmorComponent(this));
        cdc = add(new CreatureDescriptionComponent());
        hc = add(new HealthComponent());
        lc = add(new LanguageComponent());
        pc = add(new ProficienciesComponent());
        rc = add(new ResistancesComponent());
        spc = add(new SpeedComponent());

        cc = add(new CreatureComponent(this, new ManualController(this), (int) (Math.random() * 20), true));
        glc = add(new GridLocationComponent(square, cdc));
        PositionComponent pc = add(new PositionComponent(square.center()));
        RotationComponent rc = add(new RotationComponent());
        SpriteComponent sc = add(new SpriteComponent("red"));
        //Systems
        add(new SpriteSystem(pc, rc, sc));
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
            e.printStackTrace();
        }
        //loadCreature("Lion", World.grid.tileGrid[0][0]);
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
                    //System.out.println(text);
                    switch (n.getNodeName()) {
                        case "name":
                            c.cdc.name = text;
                            break;
                        case "size":
                            c.cdc.size = Size.parse(text);
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
                            c.spc.landSpeed = Integer.parseInt(text.substring(0, text.indexOf(" ")));
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

                            c.amc.actions.add(maa);
                            break;
                    }
                }
            }
        }

        return c;
    }
}
