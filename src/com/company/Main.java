package com.company;

import java.util.Random;

public class Main {

    public static int bossHealth = 1800;
    public static int bossDamage = 50;
    public static String bossDefenceType = "";
    public static int[] heroesHealth = {270, 260, 250, 200, 600, 200, 300, 200};
    public static String[] heroes = {"Phisical atacker", "Magical atacker", "Kinetical atacker", "Medic", "Golem", "Lucky", "Berserk", "Thor"};
    public static int[] heroesDamages = {20, 25, 15, 5, 5, 10};
    public static String[] heroesAttackType = {"Physical",
            "Magical", "Kinetic"};
    public static int roundCounter = 0;
    public static int counter = 0;
    public static int damageTakerForBerserk = 0;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            round();
        }
    }

    public static void bossAngryState() {
        if (bossHealth <= 200) {
            Random r = new Random();
            int healthRand = r.nextInt(31) + 20;
            int damageRand = r.nextInt(11) + 10;
            bossHealth = bossDamage + healthRand;
            bossDamage = bossDamage + damageRand;
            System.out.println("Boss became angry: " +
                    "increased health by " + healthRand
                    + " and damage " + damageRand);
        }
    }

    public static void changeBossDefence() {
        Random r = new Random();
        int randomIndex = r.nextInt(heroesAttackType.length); // 0, 1, 2
        bossDefenceType = heroesAttackType[randomIndex];
    }

    public static void round() {
        roundCounter++;
        changeBossDefence();
        bossAngryState();
        if (!Thor()) {
            if (heroesHealth[4] > 170) {
                takingHitGolem();
            } else {
                bossHits();
            }
        } else {
            System.out.println("The boss is paralyzed!!!");
            medicHealing();
        }
        heroesHit();
        printStatistics();
        medicHealing();
        counter = 0;

    }

    public static void heroesHit() {
        for (int i = 0; i < heroesAttackType.length; i++) {
            if (bossHealth > 0 && heroesHealth[i] > 0) {
                if (heroesDamages[i] == heroesDamages[5]) {
                    bossHealth = bossHealth - (heroesDamages[i] + damageTakerForBerserk);
                } else {
                    if (heroesAttackType[i].equals(bossDefenceType)) {
                        Random r = new Random();
                        int coeff = r.nextInt(7) + 2; // 2,3,4,5,6,7,8
                        if (bossHealth - heroesDamages[i] * coeff < 0) {
                            bossHealth = 0;
                        } else {
                            bossHealth = bossHealth - heroesDamages[i] * coeff;
                        }
                        System.out.println(heroesAttackType[i] +
                                " critical damage " + heroesDamages[i] * coeff);
                    } else {
                        if (bossHealth - heroesDamages[i] < 0) {
                            bossHealth = 0;
                        } else {
                            bossHealth = bossHealth - heroesDamages[i];
                        }
                    }
                }


            }
        }


    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesHealth[i] == heroesHealth[6]) {
                    if (heroesHealth[i] - (bossDamage / 5) * 2 >= 0) {
                        heroesHealth[i] = 0;
                    } else {
                        heroesHealth[i] = heroesHealth[i] - (bossDamage / 5) * 2;
                        damageTakerForBerserk = (bossDamage / 5) * 3;
                    }
                } else {

                    if (heroesHealth[i] == heroesHealth[5]) {
                        if (isHeLucky()) {
                            System.out.println("Lucky убежал от удара!!!");
                        } else {
                            if (heroesHealth[i] - bossDamage < 0) {
                                heroesHealth[i] = 0;
                            } else {
                                heroesHealth[i] = heroesHealth[i] - bossDamage;
                            }
                        }
                    } else {
                        if (heroesHealth[i] - bossDamage < 0) {
                            heroesHealth[i] = 0;
                        } else {
                            heroesHealth[i] = heroesHealth[i] - bossDamage;
                        }
                    }
                }
            }
        }
    }

    public static void printStatistics() {
        System.out.println("ROUND ----- " + roundCounter);
        System.out.println("______________");
        System.out.println("Boss health: " + bossHealth);
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroes[i] +
                    " health: " + heroesHealth[i]);

        }

        System.out.println("______________");
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }

        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }

        return allHeroesDead;

    }

    public static void medicHealing() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && counter < 1 && heroesHealth[i] != heroesHealth[3] && heroesHealth[3] > 0) {
                if (heroesHealth[i] >= 1 && heroesHealth[i] < 100) {
                    Random r = new Random();
                    int randomHealing = r.nextInt(51) + 50;
                    heroesHealth[i] = heroesHealth[i] + randomHealing;
                    counter++;
                }
            }


        }
    }

    public static void takingHitGolem() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && heroesHealth[4] > 0 && bossHealth > 0) {

                if (heroesHealth[i] == heroesHealth[6]) {
                    if (heroesHealth[i] - (bossDamage / 5) * 2 >= 0) {
                        heroesHealth[i] = 0;

                    } else {
                        heroesHealth[i] = heroesHealth[i] - (bossDamage / 5) * 2;
                        damageTakerForBerserk = (bossDamage / 5) * 3;
                    }
                } else {

                    int a;
                    int b;
                    a = bossDamage + bossDamage / 5;
                    b = (bossDamage / 5) * 4;
                    if (heroesHealth[i] == heroesHealth[4]) {
                        heroesHealth[4] = heroesHealth[4] - a;
                    } else {
                        if (heroesHealth[i] - b <= 0) {
                            heroesHealth[i] = 0;
                        } else {
                            heroesHealth[i] = heroesHealth[i] - b;
                        }
                    }
                }
            }
        }
    }

    public static boolean isHeLucky() {
        Random r = new Random();
        int temp = r.nextInt(2);
        if (temp == 1) {
            return true;
        } else {

            return false;
        }
    }

    public static boolean Thor() {
        Random r = new Random();
        int temp = r.nextInt(2);
        if (temp == 1) {
            return true;
        } else {
            return false;
        }
    }
}
