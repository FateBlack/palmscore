package com.lz.palmscore.repository;

import com.lz.palmscore.entity.GroupInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 白 on 2018/12/21.
 */
public interface GroupInfoRepository extends JpaRepository<GroupInfo,Integer> {

    List<GroupInfo> findByGroupName(Integer groupName);
}
