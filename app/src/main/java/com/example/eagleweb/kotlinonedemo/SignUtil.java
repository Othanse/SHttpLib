package com.example.eagleweb.kotlinonedemo;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SignUtil {
    private SignUtil() {
    }

    public static final String AWS_HASH              = "AWS4-HMAC-SHA256";
    public static final String AWS_REQUEST           = "aws4_request";
    public static final String X_AMZ_DATE_STRING     = "x-amz-date";
    public static final String HOST_STRING           = "host";
    public static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    protected static final String           EMPTY_HASH   = getSHA256Hash("");
    private static final   SimpleDateFormat DATE_NO_TIME = new SimpleDateFormat("yyyyMMdd");
    private static final   SimpleDateFormat AMZ_DATE     = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");

    static {
        DATE_NO_TIME.setTimeZone(TimeZone.getTimeZone("UTC"));
        AMZ_DATE.setTimeZone(TimeZone.getTimeZone("UTC"));
    }


    public static String byteArrayToHex(final byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a)
            sb.append(String.format("%02x", b & 0xff));
        return sb.toString();
    }

    public static String getAMZDate() {
        return AMZ_DATE.format(new Date(System.currentTimeMillis()));
    }

    public static String getNoTimeDate() {
        return DATE_NO_TIME.format(new Date(System.currentTimeMillis()));
    }

    public static byte[] getSignatureKey(final String key, final String regionName, final String serviceName) {
        byte[] ba = sign("AWS4" + key, getNoTimeDate());
        ba = sign(ba, regionName);
        ba = sign(ba, serviceName);
        ba = sign(ba, "aws4_request");
        return ba;
    }

    public static byte[] sign(final String key, final String msg) {
        try {
            return sign(key.getBytes("UTF-8"), msg);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    public static byte[] sign(final byte[] key, final String msg) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA256_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            mac.init(signingKey);
            mac.update(msg.getBytes("UTF-8"));
            return mac.doFinal();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static String getSHA256Hash(final String str) {
        try {
            return getSHA256Hash(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String getSHA256Hash(final byte[] ba) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(ba);
            return byteArrayToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String makeSignature(byte[] signingKey, final AWSParams awsParams) {

        StringBuilder sb = new StringBuilder();
        sb.append(awsParams.method).append("\n")
                .append(awsParams.path).append("\n")
                .append(awsParams.query).append("\n")
                .append(HOST_STRING).append(":").append(awsParams.host.toLowerCase()).append("\n")
                .append(X_AMZ_DATE_STRING).append(":").append(awsParams.AMZDate).append("\n");
        for (Map.Entry<String, String> me : awsParams.headers.entrySet()) {
            sb.append(me.getKey().toLowerCase()).append(":").append(me.getValue()).append("\n");
        }
        sb.append("\n")
                .append(HOST_STRING).append(";").append(X_AMZ_DATE_STRING).append("\n")
                .append(awsParams.dataHash);

        String s2s = getSHA256Hash(sb.toString());

        sb = new StringBuilder();
        sb.append(AWS_HASH).append("\n")
                .append(awsParams.AMZDate).append("\n")
                .append(awsParams.ntd).append("/")
                .append(awsParams.region).append("/")
                .append(awsParams.service).append("/")
                .append(AWS_REQUEST).append("\n");
        sb.append(s2s);
        return byteArrayToHex(sign(signingKey, sb.toString()));
    }

    public static String makeAuthHeader(final AWSParams awsParams, final String publicKey, final String signature) {
        StringBuilder sb = new StringBuilder();
        sb.append(AWS_HASH).append(" Credential=").append(publicKey).append("/");
        sb.append(getNoTimeDate()).append("/");
        sb.append(awsParams.region).append("/");
        sb.append(awsParams.service).append("/");
        sb.append(AWS_REQUEST).append(", SignedHeaders=");
        sb.append(HOST_STRING).append(";").append(X_AMZ_DATE_STRING).append(", Signature=").append(signature);
        return sb.toString();
    }

    public static Map<String, String> signRequest(final AWSParams awsParams, final String secretKey, final String publicKey) {
        byte[] sigKey = getSignatureKey(secretKey, awsParams.region, awsParams.service);

        String sign = makeSignature(sigKey, awsParams);

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("host", awsParams.host.toLowerCase());
        map.put(X_AMZ_DATE_STRING, awsParams.AMZDate);
        map.put("Authorization", makeAuthHeader(awsParams, publicKey, sign));

        return map;
    }

    public static class AWSParams {
        public final String              service;
        public final String              region;
        public final String              method;
        public final String              host;
        public final String              path;
        public final String              query;
        public final String              dataHash;
        public final String              AMZDate;
        public final String              ntd;
        public final Map<String, String> headers;

        public AWSParams(String service, String region, String method, String host, String path, String query, String dataHash, String AMZDate, String ntd, Map<String, String> map) {
            this.service = service;
            this.region = region;
            this.host = host;
            this.method = method;
            this.path = path;
            this.query = query;
            this.dataHash = dataHash;
            this.AMZDate = AMZDate;
            this.ntd = ntd;
            this.headers = Collections.unmodifiableMap(new HashMap<String, String>(map));
        }
    }


    public static class AWSParamBuilder {
        private String service;
        private String region;
        private String host;
        private String method = "GET";
        private String path   = "/";
        private String query;
        private String dataHash = EMPTY_HASH;
        private String AMZDate;
        private String ntd;
        private Map<String, String> headers = new HashMap<String, String>();

        public AWSParamBuilder() {
        }

        public AWSParams build() {
            if (this.service == null) {
                throw new IllegalStateException("Service cant be null!");
            }
            if (this.region == null) {
                throw new IllegalStateException("Region cant be null!");
            }
            if (this.host == null) {
                throw new IllegalStateException("Host cant be null!");
            }
            if (this.query == null) {
                throw new IllegalStateException("Query cant be null!");
            }
            String amzDate = this.AMZDate;
            if (amzDate == null) {
                amzDate = getAMZDate();
            }
            String lntd = this.ntd;
            if (lntd == null) {
                lntd = getNoTimeDate();
            }
            AWSParams ap = new AWSParams(service, region, method, host, path, query, dataHash, amzDate, lntd, headers);
            this.AMZDate = null;
            this.ntd = null;
            return ap;
        }

        public AWSParamBuilder setService(final String service) {
            this.service = service;
            return this;
        }

        public AWSParamBuilder setRegion(final String region) {
            this.region = region;
            return this;
        }

        public AWSParamBuilder setHost(final String host) {
            this.host = host;
            return this;
        }

        public AWSParamBuilder setMethod(final String method) {
            this.method = method;
            return this;
        }

        public AWSParamBuilder setPath(final String path) {
            this.path = path;
            return this;
        }

        public AWSParamBuilder setQuery(final String query) {
            this.query = query;
            return this;
        }

        public AWSParamBuilder setData(final byte[] ba) {
            this.dataHash = getSHA256Hash(ba);
            return this;
        }

        public AWSParamBuilder setDataHash(final String hash) {
            this.dataHash = hash;
            return this;
        }

        public AWSParamBuilder setAMZDate(final String date) {
            this.AMZDate = date;
            return this;
        }

        public AWSParamBuilder setNTD(final String date) {
            this.ntd = date;
            return this;
        }

        public AWSParamBuilder setHeaders(Map<String, String> headers) {
            this.headers.clear();
            this.headers.putAll(headers);
            return this;
        }
    }
}