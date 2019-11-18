package theWeaponMaster.util;

import java.util.HashSet;

public interface Scissors {
    int scissorCombo = 0;
    int comboBonus = 2;
    HashSet<String> scissors = new HashSet<>();

    void addScissors();
}
