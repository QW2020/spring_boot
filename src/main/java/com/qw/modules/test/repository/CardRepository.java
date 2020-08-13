package com.qw.modules.test.repository;

import com.qw.modules.test.pojo.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ClassName: CardRepository <br/>
 * Description: <br/>
 * date: 2020/8/12 18:54<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
@Repository
public interface CardRepository extends JpaRepository<Card,Integer> {
}
