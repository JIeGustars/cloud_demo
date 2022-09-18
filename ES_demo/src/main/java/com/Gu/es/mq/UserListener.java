package com.Gu.es.mq;


import com.Gu.es.entity.MqConstants;
import com.Gu.es.service.IEsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@Component
public class UserListener {

    @Resource
    private IEsService iEsService;

    /**
     * 监听新增或修改的业务
     * @param Id mysql数据库meeting_time表的id
     */
    @RabbitListener(queues = MqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate(Integer Id) throws IOException {
        iEsService.insertById(Id);
    }
}
