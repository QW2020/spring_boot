package com.qw.modules.test.controller;

import com.qw.modules.common.vo.Result;
import com.qw.modules.test.pojo.Card;
import com.qw.modules.test.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: CardController <br/>
 * Description: <br/>
 * date: 2020/8/12 18:58<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardService cardService;

    /**
     * 127.0.0.1/api/card  ----  post
     * {"cardNo":"aaaaa"}
     * 新增
     */
    @PostMapping(value = "/card",consumes = "application/json")
    public Result<Card> insertCard(@RequestBody Card card){
        return cardService.insertCard(card);
    }
}
