package com.Gu.es.controller;

import com.Gu.es.service.IEsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("es")
@Slf4j
@ResponseBody
public class EsController {

    @Resource
    private IEsService iEsService;



}
