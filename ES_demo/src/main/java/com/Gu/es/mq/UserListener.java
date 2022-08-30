package com.Gu.es.mq;

import com.Gu.es.constants.MqConstants;
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
     * @param id id
     */
    @RabbitListener(queues = MqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate(Integer id) throws IOException {
        iEsService.insertById(id);
    }
}
