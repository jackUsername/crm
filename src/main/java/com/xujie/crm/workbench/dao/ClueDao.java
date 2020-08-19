package com.xujie.crm.workbench.dao;


import com.xujie.crm.workbench.domain.Clue;

public interface ClueDao {
    int save(Clue clue);

    Clue detail(String id);
}
