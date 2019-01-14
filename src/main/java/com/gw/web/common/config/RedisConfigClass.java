package com.gw.web.common.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
// import org.springframework.data.redis.connection.RedisConnectionFactory;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
// import org.springframework.data.redis.serializer.RedisSerializer;
// import org.springframework.data.redis.serializer.StringRedisSerializer;
// import org.springframework.session.data.redis.config.ConfigureRedisAction;
// import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
// import org.springframework.session.web.http.CookieSerializer;
// import org.springframework.session.web.http.DefaultCookieSerializer;
// import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
// import org.springframework.data.redis.serializer.RedisSerializer;
// import org.springframework.session.data.redis.config.ConfigureRedisAction;
// import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
// import org.springframework.session.web.http.CookieSerializer;
// import org.springframework.session.web.http.DefaultCookieSerializer;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
// import org.springframework.data.redis.serializer.RedisSerializer;
// import org.springframework.session.data.redis.config.ConfigureRedisAction;
// import org.springframework.session.web.http.CookieSerializer;
// import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * @program: template
 * @description:
 * @author: MengGuanghui
 * @create: 2019-01-02 12:30
 **/

@Configuration
public class RedisConfigClass {

    Logger logger = LoggerFactory.getLogger(RedisConfigClass.class);

    @Autowired
    ServerProperties serverProperties;

    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }
    //
    // /**
    //  * 序列化，解决springsession 存储redis数据无法直观查看问题。如：\xac\xed\x00\x05t\x00\x04AAAA 以及存对象
    //  *
    //  * @returns
    //  */
    // @Bean
    // public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
    //     return new Jackson2JsonRedisSerializer<Object>(Object.class);
    // }
    //
    //
    // @Bean
    // public CookieSerializer cookieSerializer(){
    //
    //     Session session = serverProperties.getServlet().getSession();
    //     Session.Cookie cookie = session.getCookie();
    //
    //     DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();
    //     //cookie名字
    //     if (cookie.getName() != null)
    //         defaultCookieSerializer.setCookieName(cookie.getName());
    //     //域
    //     if (cookie.getDomain() != null)
    //         defaultCookieSerializer.setDomainName(cookie.getDomain());
    //     //存储路径
    //     if (cookie.getPath() != null)
    //         defaultCookieSerializer.setCookiePath(cookie.getPath());
    //     if (cookie.getHttpOnly() != null)
    //         defaultCookieSerializer.setUseHttpOnlyCookie(cookie.getHttpOnly());
    //     // long milliseconds = session.getTimeout().toMillis() ;
    //     // int expiresTime = (int) (milliseconds/1000);
    //     int expiresTime = (int) session.getTimeout().getSeconds();       // 这段时间的秒数
    //     defaultCookieSerializer.setCookieMaxAge(expiresTime * 3);
    //
    //     logger.info("Cookie getName: "+ cookie.getName()+" getDomain: "+ cookie.getDomain()+
    //             " getPath: "+cookie.getPath()+" getHttpOnly: "+ cookie.getHttpOnly());
    //     return defaultCookieSerializer;
    // }
}
