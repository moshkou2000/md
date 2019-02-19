package com.moshkou.md.configs;

public class StatusCodes {

    /*
    The list of HTTP status codes are divided into 5 classes:

    100’s: Informational codes indicating that the request initiated by the browser is continuing.
    200’s: Success codes returned when browser request was successfully received, understood, and processed by the server.
    300’s: Redirection codes returned when a new resource has been substituted for the requested resource.
    400’s: Client error codes indicating that there was a problem with the request.
    500’s: Server error codes indicating that the request was accepted, but that an error on the server prevented the fulfillment of the request.


    100 Continue Informational
    101 Switching Protocols Informational

    200 OK Successful
    201 Created Successful
    202 Accepted Successful
    203 Non-Authoritative Information Successful
    204 No Content Successful
    205 Reset Content Successful
    206 Partial Content Successful

    300 Multiple Choices Redirection
    301 Moved Permanently Redirection
    302 Found Redirection
    303 See Other Redirection
    304 Not Modified Redirection
    305 Use Proxy Redirection
    307 Temporary Redirect Redirection

    400 Bad Request Client Error
    401 Unauthorized Client Error
    402 Payment Required Client Error
    403 Forbidden Client Error
    404 Not Found Client Error
    405 Method Not Allowed Client Error
    406 Not Acceptable Client Error
    407 Proxy Authentication Required Client Error
    408 Request Timeout Client Error
    409 Conflict Client Error
    410 Gone Client Error
    411 Length Required Client Error
    412 Precondition Failed Client Error
    413 Request Entity Too Large Client Error
    414 Request-URI Too Long Client Error
    415 Unsupported Media Type Client Error
    416 Requested Range Not Satisfiable Client Error
    417 Expectation Failed Client Error

    500 Internal Server Error Server Error
    501 Not Implemented Server Error
    502 Bad Gateway Server Error
    503 Service Unavailable Server Error
    504 Gateway Timeout Server Error
    505 HTTP Version Not Supported Server Error
     */

    public final static short _100 = 100;
    public final static short _101 = 101;

    public final static short _200 = 200;
    public final static short _201 = 201;
    public final static short _202 = 202;
    public final static short _203 = 203;
    public final static short _204 = 204;
    public final static short _205 = 205;
    public final static short _206 = 206;

    public final static short _300 = 300;
    public final static short _301 = 301;
    public final static short _302 = 302;
    public final static short _303 = 303;
    public final static short _304 = 304;
    public final static short _305 = 305;
    public final static short _307 = 307;

    public final static short _400 = 400;
    public final static short _401 = 401;
    public final static short _402 = 402;
    public final static short _403 = 403;
    public final static short _404 = 404;
    public final static short _405 = 405;
    public final static short _406 = 406;
    public final static short _407 = 407;
    public final static short _408 = 408;
    public final static short _409 = 409;
    public final static short _410 = 410;
    public final static short _411 = 411;
    public final static short _412 = 412;
    public final static short _413 = 413;
    public final static short _414 = 414;
    public final static short _415 = 415;
    public final static short _416 = 416;
    public final static short _417 = 417;

    public final static short _500 = 500;
    public final static short _501 = 501;
    public final static short _502 = 502;
    public final static short _503 = 503;
    public final static short _504 = 504;
    public final static short _505 = 505;
}
