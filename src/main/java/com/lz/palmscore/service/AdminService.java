package com.lz.palmscore.service;


import com.lz.palmscore.entity.Admin;

/**
 * Created by 白 on 2018/12/11.
 */

public interface AdminService {

    Admin login(Admin admin);

    Admin edit(Admin admin);

    Admin getPassById(int id);
}
