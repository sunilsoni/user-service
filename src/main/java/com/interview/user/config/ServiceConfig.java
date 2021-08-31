package com.interview.user.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@RefreshScope
public class ServiceConfig {

    @Value("${adminUsername}")
    private String adminUsername;

    @Value("${adminPassword}")
    private String adminPassword;

    @Value("${http.client.connection.max.total}")
    private int httpClientConnectionManagerMaxTotal;

    @Value("${http.client.connection.default.max.per.route}")
    private int httpClientConnectionDefaultMaxPerRoute;

    @Value("${http.client.socket.timeout}")
    private Integer httpClientSocketTimeout;

    @Value("${http.client.connect.timeout}")
    private Integer httpClientConnectTimeout;

    @Value("${http.password.reset.url}")
    private String passwordResetUrl;

    @Value("${spring.redisHost}")
    private String redisHost;

    @Value("${passwordReset.cache.expire.duration.minutes}")
    private int passwordResetCacheDuration;

    @Value("${activeProfile.cache.expire.duration.minutes}")
    private int activeProfileCacheDuration;

    @Value("${profile.cache.expire.duration.minutes}")
    private int profileCacheDuration;

    @Value("${token.cache.expire.duration.minutes}")
    private int tokenCacheDuration;

    @Value(("${default.user.domain}"))
    private String defaultUserDomain;

    @Value("${support.sender.email}")
    private String supportSenderEmail;

    @Value("${support.sender.reply.email}")
    private String supportSenderReplyEmail;

    @Value("${notification.accountid}")
    private Long notificationAccountId;

    @Value("${forgot.username.subject}")
    private String forgotUsernameSubject;

    @Value("${http.login.default.url}")
    private String defaultLoginUrl;

    @Value("${forgotUsername.email.templateId}")
    private Long forgotUsernameEmailTemplateId;

    @Value("${password.reset.subject}")
    private String subject;

    @Value("${passwordReset.email.templateId}")
    private Long passwordResetTemplateId;
}
