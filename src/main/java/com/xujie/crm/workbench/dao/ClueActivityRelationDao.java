package com.xujie.crm.workbench.dao;


import com.xujie.crm.workbench.domain.ClueActivityRelation;

public interface ClueActivityRelationDao {


    int unband(String id);

    int bund(ClueActivityRelation car);
}
