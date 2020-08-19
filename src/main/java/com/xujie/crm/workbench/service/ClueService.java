package com.xujie.crm.workbench.service;

import com.xujie.crm.workbench.domain.Activity;
import com.xujie.crm.workbench.domain.Clue;

import java.util.List;

public interface ClueService {
    String  save(Clue clue);

    Clue detail(String id);


    String unbund(String id);

    String bund(String cid, String[] aids);
}
