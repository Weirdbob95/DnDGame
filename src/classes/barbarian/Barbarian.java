package classes.barbarian;

import actions.Action;
import static actions.Action.Type.BONUS_ACTION;
import amounts.AddedAmount;
import amounts.ConditionalAmount;
import amounts.Value;
import classes.PlayerClass;
import conditions.Incapacitated;
import creature.Creature;
import enums.AbilityScore;
import static enums.AbilityScore.*;
import enums.Skill;
import static enums.Skill.*;
import events.AbilityCheckEvent;
import events.SavingThrowEvent;
import events.TakeDamageEvent;
import events.TurnEndEvent;
import events.attack.AttackDamageRollEvent;
import events.attack.AttackEvent;
import events.attack.AttackRollEvent;
import player.Player;
import queries.BooleanQuery;
import queries.Query;
import util.Mutable;

public class Barbarian extends PlayerClass {

    public Barbarian(Player player) {
        super(player);
    }

    @Override
    public int archetypeLevel() {
        return 3;
    }

    @Override
    public int hitDie() {
        return 12;
    }

    @Override
    public void levelUp(int newLevel) {
        switch (newLevel) {
            case 1:
                player.ac.AC.set("Base", new ConditionalAmount(() -> player.ac.armor == null,
                        new AddedAmount(new Value(10), player.asc.mod(DEX), player.asc.mod(CON)), player.ac.AC.components.get("Base")));

                player.amc.addAction(new Rage(player));
                break;
            case 2:
                Mutable<Boolean> isReckless = new Mutable(null);
                add(AttackEvent.class, e -> {
                    if (isReckless.o == null) {
                        isReckless.o = Query.ask(player, new BooleanQuery("Attack recklessly?")).response;
                    }
                });
                add(AttackRollEvent.class, e -> {
                    if (isReckless.o != null && isReckless.o) {
                        if (e.a.attacker == player) {
                            e.a.advantage = true;
                        }
                        if (e.a.target == player) {
                            e.a.advantage = true;
                        }
                    }
                });

                add(SavingThrowEvent.class, e -> {
                    if (e.creature == player) {
                        if (e.abilityScore == DEX) {
                            if (!player.cnc.hasAny(Incapacitated.class)) {
                                //Unfinished
                            }
                        }
                    }
                });
                break;
        }
    }

    @Override
    public AbilityScore[] savingThrows() {
        return new AbilityScore[]{STR, CON};
    }

    @Override
    public Skill[] skills() {
        return new Skill[]{Animal_Handling, Athletics, Intimidation, Nature, Perception, Survival};
    }

    public class Rage extends Action {

        public boolean raging;
        public int timesUsed;
        public int turnsElapsed;
        public boolean continueRage;

        public Rage(Creature creature) {
            super(creature);
            creature.amc.addAction(new End_Rage(creature));

            add(AbilityCheckEvent.class, e -> {
                if (raging) {
                    if (e.creature == creature) {
                        if (e.abilityScore == STR) {
                            e.advantage = true;
                        }
                    }
                }
            });
            add(SavingThrowEvent.class, e -> {
                if (raging) {
                    if (e.creature == creature) {
                        if (e.abilityScore == STR) {
                            e.advantage = true;
                        }
                    }
                }
            });
            add(AttackDamageRollEvent.class, e -> {
                if (raging) {
                    if (e.a.attacker == creature) {
                        e.a.damage.set("Rage", () -> {
                            if (level < 9) {
                                return 2;
                            }
                            if (level < 16) {
                                return 3;
                            }
                            return 4;
                        });
                    }
                }
            });
            add(TurnEndEvent.class, e -> {
                if (e.creature == creature) {
                    turnsElapsed++;
                    raging = raging && continueRage && turnsElapsed < 10;
                    continueRage = false;
                }
            });
            add(AttackEvent.class, e -> continueRage = continueRage || e.attacker == creature);
            add(TakeDamageEvent.class, e -> continueRage = continueRage || e.creature == creature);
        }

        @Override
        protected void act() {
            raging = true;
            turnsElapsed = 0;
        }

        @Override
        public String[] defaultTabs() {
            return new String[]{};
        }

        @Override
        public String getDescription() {
            return "In battle, you fight with primal ferocity.";
        }

        @Override
        public Type getType() {
            return BONUS_ACTION;
        }

        @Override
        public boolean isAvailable() {
            return !raging && timesUsed < uses();
        }

        private int uses() {
            if (level < 3) {
                return 2;
            }
            if (level < 6) {
                return 3;
            }
            if (level < 12) {
                return 4;
            }
            if (level < 17) {
                return 5;
            }
            if (level < 20) {
                return 6;
            }
            return 9999;
        }

        public class End_Rage extends Action {

            public End_Rage(Creature creature) {
                super(creature);
            }

            @Override
            protected void act() {
                raging = false;
            }

            @Override
            public String[] defaultTabs() {
                return new String[]{};
            }

            @Override
            public String getDescription() {
                return "Stop raging";
            }

            @Override
            public Type getType() {
                return BONUS_ACTION;
            }

            @Override
            public boolean isAvailable() {
                return raging;
            }
        }
    }
}
