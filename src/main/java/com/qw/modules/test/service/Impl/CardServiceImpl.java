package com.qw.modules.test.service.Impl;

import com.qw.modules.common.vo.Result;
import com.qw.modules.test.pojo.Card;
import com.qw.modules.test.repository.CardRepository;
import com.qw.modules.test.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ClassName: CardServiceImpl <br/>
 * Description: <br/>
 * date: 2020/8/12 18:57<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Override
    @Transactional
    public Result<Card> insertCard(Card card) {
        cardRepository.saveAndFlush(card);
        return new Result<Card>(Result.ResultStatus.SUCCESS.status,"insertCard success.",card);
    }
}
