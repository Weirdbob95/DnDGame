package items;

import amounts.Value;
import enums.DamageType;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import util.Log;
import util.Selectable;

public class Weapon implements Selectable, Serializable {

    public String name;
    public boolean isSimple;
    public boolean isRanged;
    public double cost;
    public Value damage;
    public DamageType damageType;
    public double weight;

    public boolean ammunition;
    public boolean finesse;
    public boolean heavy;
    public boolean light;
    public boolean loading;
    public int range;
    public int rangeLong;
    public boolean reach;
    public boolean special;
    public boolean thrown;
    public boolean two_handed;
    public Value versatile;

    @Override
    public String getDescription() {
        return "Deals " + damage + " " + damageType.name().toLowerCase() + " damage.";
    }

    @Override
    public String getName() {
        return name;
    }

    private static DocumentBuilderFactory dbf;
    private static DocumentBuilder db;
    private static Document doc;

    static {
        try {
            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            doc = db.parse(new File("weapons.xml"));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            Log.error(e);
        }
    }

    private static Node findNode(String name) {
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

    public static Weapon loadWeapon(String name) {
        Weapon w = new Weapon();
        Node weaponNode = findNode(name);
        if (weaponNode == null) {
            return w;
        }
        NodeList nodeList = weaponNode.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node n = nodeList.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                if (n.getFirstChild() != null) {
                    String text = n.getFirstChild().getNodeValue();
                    switch (n.getNodeName()) {
                        case "name":
                            w.name = text;
                            break;
                        case "type":
                            w.isSimple = text.startsWith("simple");
                            w.isRanged = text.endsWith("ranged");
                            break;
                        case "cost":
                            if (!text.contains(" ")) {
                                break;
                            }
                            double val = Integer.parseInt(text.substring(0, text.indexOf(" ")));
                            if (text.endsWith("cp")) {
                                w.cost = val / 100;
                            } else if (text.endsWith("sp")) {
                                w.cost = val / 10;
                            } else if (text.endsWith("gp")) {
                                w.cost = val;
                            } else {
                                w.cost = 0;
                            }
                            break;
                        case "damage":
                            w.damage = Value.parseValue(text);
                            break;
                        case "damagetype":
                            w.damageType = DamageType.valueOf(text.toUpperCase());
                            break;
                        case "weight":
                            w.weight = Double.parseDouble(text);
                            break;
                        case "property":
                            switch (text.substring(0, text.indexOf(" "))) {
                                case "light":
                                    w.light = true;
                                    break;
                                case "finesse":
                                    w.finesse = true;
                                    break;
                                case "thrown":
                                    w.thrown = true;
                                    w.range = Integer.parseInt(findChildNode(n, "short").getFirstChild().getNodeValue());
                                    w.rangeLong = Integer.parseInt(findChildNode(n, "long").getFirstChild().getNodeValue());
                                    break;
                                case "two-handed":
                                    w.two_handed = true;
                                    break;
                                case "versatile":
                                    w.versatile = Value.parseValue(text.substring(text.indexOf("(") + 1, text.indexOf(")")));
                                    break;
                                case "ammunition":
                                    w.ammunition = true;
                                    w.range = Integer.parseInt(findChildNode(n, "short").getFirstChild().getNodeValue());
                                    w.rangeLong = Integer.parseInt(findChildNode(n, "long").getFirstChild().getNodeValue());
                                    break;
                                case "heavy":
                                    w.heavy = true;
                                    break;
                                case "reach":
                                    w.reach = true;
                                    break;
                                case "loading":
                                    w.loading = true;
                                    break;
                                case "special":
                                    switch (w.name) {
                                        default:
                                            Log.error("Tried to load weapon with unknown special property: " + w.name);
                                            break;
                                    }
                                    break;
                            }
                            break;
                    }
                }
            }
        }

        return w;
    }
}
