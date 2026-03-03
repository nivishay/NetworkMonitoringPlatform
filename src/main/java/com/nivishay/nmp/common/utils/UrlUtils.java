package com.nivishay.nmp.common.utils;

import java.net.URI;
import java.net.URISyntaxException;

public final class UrlUtils {

    private UrlUtils() {
    }

    public static void validateHttpUrl(String url) {

        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("URL must not be null or blank");
        }

        if (url.contains(" ")) {
            throw new IllegalArgumentException("URL must not contain spaces");
        }

        URI uri;

        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URL format");
        }

        String scheme = uri.getScheme();
        if (scheme == null ||
                (!scheme.equalsIgnoreCase("http") &&
                        !scheme.equalsIgnoreCase("https"))) {
            throw new IllegalArgumentException("Only HTTP/HTTPS URLs are allowed");
        }

        if (uri.getHost() == null || uri.getHost().isBlank()) {
            throw new IllegalArgumentException("URL must contain a valid host");
        }
    }
}