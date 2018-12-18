package com.lz.palmscore.service.impl;

import com.lz.palmscore.entity.Admin;
import com.lz.palmscore.repository.AdminRepository;
import com.lz.palmscore.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 白 on 2018/12/12.
 */
@Service
public class AdminServiceImpl implements AdminService {


    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin login(Admin admin) {
        List<Admin> admins = adminRepository.findByAccountAndPassword(admin.getAccount(),admin.getPassword());
        if (admins == null||admins.isEmpty()){
            return null;
        }
        admins.get(0).setPassword("");
        return admins.get(0);
    }

    @Override
    public Admin edit(Admin admin) {
        Admin admin1=adminRepository.save(admin);
        return admin1;
    }

    @Override
    public Admin getPassById(int id) {
        return adminRepository.getOne(id);
    }


}
