package com.jackc.fun.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author chenjie
 * @DATE 2019/8/2 15:23
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ResponseReceivedEvent {

    /**
     * frameId : 3DA59573A2F08856EDFC000C0820CD0A
     * loaderId : 6DA55962C008E4B0C9DB07669C86A5AC
     * requestId : 1000016836.380
     * response : {"connectionId":144,"connectionReused":true,"encodedDataLength":4244,"fromDiskCache":false,"fromServiceWorker":false,"headers":{"accept-ranges":"bytes","access-control-allow-origin":"*","cache-control":"max-age=172800","content-length":"4024","content-type":"image/jpeg","date":"Fri, 02 Aug 2019 07:05:49 GMT","etag":"\"2354025868\"","last-modified":"Thu, 14 Feb 2019 02:44:47 GMT","server":"nginx","status":"200","x-cache":"HIT","x-cache-hits":"172, 2","x-served-by":"edge4tyo"},"mimeType":"image/jpeg","protocol":"h2","remoteIPAddress":"127.0.0.1","remotePort":1080,"securityDetails":{"certificateId":0,"certificateTransparencyCompliance":"unknown","cipher":"AES_128_GCM","issuer":"DigiCert SHA2 Secure Server CA","keyExchange":"ECDHE_RSA","keyExchangeGroup":"P-256","protocol":"TLS 1.2","sanList":["*.mfcimg.com","mfcimg.com"],"signedCertificateTimestampList":[],"subjectName":"*.mfcimg.com","validFrom":1501545600,"validTo":1602072000},"securityState":"secure","status":200,"statusText":"","timing":{"connectEnd":-1,"connectStart":-1,"dnsEnd":-1,"dnsStart":-1,"proxyEnd":-1,"proxyStart":-1,"pushEnd":0,"pushStart":0,"receiveHeadersEnd":73.301,"requestTime":107795.416283,"sendEnd":18.042,"sendStart":7.137,"sslEnd":-1,"sslStart":-1,"workerReady":-1,"workerStart":-1},"url":"https://img.mfcimg.com/photos2/313/31321885/avatar.90x90.jpg"}
     * timestamp : 107795.490365
     * type : Image,XHR .....
     */

    private String frameId;
    private String loaderId;
    private String requestId;
    private ResponseBean response;
    private double timestamp;
    private String type;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class ResponseBean {
        /**
         * connectionId : 144
         * connectionReused : true
         * encodedDataLength : 4244
         * fromDiskCache : false
         * fromServiceWorker : false
         * headers : {"accept-ranges":"bytes","access-control-allow-origin":"*","cache-control":"max-age=172800","content-length":"4024","content-type":"image/jpeg","date":"Fri, 02 Aug 2019 07:05:49 GMT","etag":"\"2354025868\"","last-modified":"Thu, 14 Feb 2019 02:44:47 GMT","server":"nginx","status":"200","x-cache":"HIT","x-cache-hits":"172, 2","x-served-by":"edge4tyo"}
         * mimeType : image/jpeg
         * protocol : h2
         * remoteIPAddress : 127.0.0.1
         * remotePort : 1080
         * securityDetails : {"certificateId":0,"certificateTransparencyCompliance":"unknown","cipher":"AES_128_GCM","issuer":"DigiCert SHA2 Secure Server CA","keyExchange":"ECDHE_RSA","keyExchangeGroup":"P-256","protocol":"TLS 1.2","sanList":["*.mfcimg.com","mfcimg.com"],"signedCertificateTimestampList":[],"subjectName":"*.mfcimg.com","validFrom":1501545600,"validTo":1602072000}
         * securityState : secure
         * status : 200
         * statusText :
         * timing : {"connectEnd":-1,"connectStart":-1,"dnsEnd":-1,"dnsStart":-1,"proxyEnd":-1,"proxyStart":-1,"pushEnd":0,"pushStart":0,"receiveHeadersEnd":73.301,"requestTime":107795.416283,"sendEnd":18.042,"sendStart":7.137,"sslEnd":-1,"sslStart":-1,"workerReady":-1,"workerStart":-1}
         * url : https://img.mfcimg.com/photos2/313/31321885/avatar.90x90.jpg
         */

        private int connectionId;
        private boolean connectionReused;
        private int encodedDataLength;
        private boolean fromDiskCache;
        private boolean fromServiceWorker;
        private HeadersBean headers;
        private String mimeType;
        private String protocol;
        private String remoteIPAddress;
        private int remotePort;
        private SecurityDetailsBean securityDetails;
        private String securityState;
        private int status;
        private String statusText;
        private TimingBean timing;
        private String url;

        @JsonIgnoreProperties(ignoreUnknown = true)
        @Data
        public static class HeadersBean {
            /**
             * accept-ranges : bytes
             * access-control-allow-origin : *
             * cache-control : max-age=172800
             * content-length : 4024
             * content-type : image/jpeg
             * date : Fri, 02 Aug 2019 07:05:49 GMT
             * etag : "2354025868"
             * last-modified : Thu, 14 Feb 2019 02:44:47 GMT
             * server : nginx
             * status : 200
             * x-cache : HIT
             * x-cache-hits : 172, 2
             * x-served-by : edge4tyo
             */

            @JsonProperty("accept-ranges")
            private String acceptranges;
            @JsonProperty("access-control-allow-origin")
            private String accesscontrolalloworigin;
            @JsonProperty("cache-control")
            private String cachecontrol;
            @JsonProperty("content-length")
            private String contentlength;
            @JsonProperty("content-type")
            private String contenttype;
            private String date;
            private String etag;
            @JsonProperty("last-modified")
            private String lastmodified;
            private String server;
            private String status;
            @JsonProperty("x-cache")
            private String xcache;
            @JsonProperty("x-cache-hits")
            private String xcachehits;
            @JsonProperty("x-served-by")
            private String xservedby;

        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        @Data
        public static class SecurityDetailsBean {
            /**
             * certificateId : 0
             * certificateTransparencyCompliance : unknown
             * cipher : AES_128_GCM
             * issuer : DigiCert SHA2 Secure Server CA
             * keyExchange : ECDHE_RSA
             * keyExchangeGroup : P-256
             * protocol : TLS 1.2
             * sanList : ["*.mfcimg.com","mfcimg.com"]
             * signedCertificateTimestampList : []
             * subjectName : *.mfcimg.com
             * validFrom : 1501545600
             * validTo : 1602072000
             */

            private int certificateId;
            private String certificateTransparencyCompliance;
            private String cipher;
            private String issuer;
            private String keyExchange;
            private String keyExchangeGroup;
            private String protocol;
            private String subjectName;
            private int validFrom;
            private int validTo;
            private List<String> sanList;
            private List<?> signedCertificateTimestampList;


        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        @Data
        public static class TimingBean {
            /**
             * connectEnd : -1
             * connectStart : -1
             * dnsEnd : -1
             * dnsStart : -1
             * proxyEnd : -1
             * proxyStart : -1
             * pushEnd : 0
             * pushStart : 0
             * receiveHeadersEnd : 73.301
             * requestTime : 107795.416283
             * sendEnd : 18.042
             * sendStart : 7.137
             * sslEnd : -1
             * sslStart : -1
             * workerReady : -1
             * workerStart : -1
             */

            private int connectEnd;
            private int connectStart;
            private int dnsEnd;
            private int dnsStart;
            private int proxyEnd;
            private int proxyStart;
            private int pushEnd;
            private int pushStart;
            private double receiveHeadersEnd;
            private double requestTime;
            private double sendEnd;
            private double sendStart;
            private int sslEnd;
            private int sslStart;
            private int workerReady;
            private int workerStart;

        }
    }
}
