package com.qw.modules.test.service;

import com.qw.modules.common.vo.Result;
import com.qw.modules.test.pojo.Card;

/**
 * ClassName: CardService <br/>
 * Description: <br/>
 * date: 2020/8/12 18:57<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
public interface CardService {
    Result<Card> insertCard(Card card);
}
