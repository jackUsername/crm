package com.xujie.crm.setting.servlet;

import com.xujie.crm.setting.domian.DicValue;

import java.util.List;
import java.util.Map;

public interface DicService {
    Map<String, List<DicValue>> getAll();
}
