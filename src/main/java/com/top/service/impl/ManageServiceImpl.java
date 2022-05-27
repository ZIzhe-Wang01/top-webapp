package com.top.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.top.dao.entity.Manage;
import com.top.dao.mapper.ManageMapper;
import com.top.service.ManageService;

import org.springframework.stereotype.Service;

@Service
public class ManageServiceImpl extends ServiceImpl<ManageMapper, Manage> implements ManageService {
    
}
