package com.lz.palmscore.repository;

import com.lz.palmscore.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.Max;
import java.util.List;

/**
 * Created by 白 on 2018/12/11.
 */
public interface AdminRepository extends JpaRepository<Admin,Integer> {
    List<Admin> findByAccountAndPassword(String account,String password);
}



