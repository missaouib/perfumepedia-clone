package org.perfumepedia.DataBase.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;

@Component
public class BaseUrlGenerator {

    @Autowired
    private HttpServletRequest request;

    public String getUrl() {
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
        return baseUrl;
    }
}
