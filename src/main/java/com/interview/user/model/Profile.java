package com.interview.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Profile {

    private String displayName = StringUtils.EMPTY;
    private PortalId crmIdent = new PortalId();
    private PortalId salesQuoteIdent = new PortalId();
    private Long id;
    private Boolean isActive;
    private String userUuid;
}
