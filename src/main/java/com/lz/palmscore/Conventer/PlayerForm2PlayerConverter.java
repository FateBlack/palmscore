package com.lz.palmscore.Conventer;

import com.lz.palmscore.entity.Player;
import com.lz.palmscore.form.PlayerForm;

/**
 * Created by 白 on 2018/12/15.
 */
public class PlayerForm2PlayerConverter {
    public static Player conventer(PlayerForm form) {
        Player player = new Player();
        player.setPId(form.getPid());
        player.setName(form.getName());
        player.setWorkplace(form.getWorkplace());
        player.setCourse(form.getCourse());
        player.setGroups(form.getGroups());
        player.setOrder(form.getOrder());
        return player;
    }
}
