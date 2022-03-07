package com.example.gongdon.appconfig;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class QuerydslConfig {
    // 엔티티 매니저를 주입한 JPAQueryFactory를 Bean으로 등록하여 프로젝트 전역에서 QueryDSL을 작성할 수 있게 한다.

    @PersistenceContext
    private EntityManager em;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
    }
}
