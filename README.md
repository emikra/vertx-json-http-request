# verx-json-http-request

Inspired by [superagent](https://github.com/visionmedia/superagent).
 
**NOTE: The API is not finalized and is still undergoing changes!**
 
This is a wrapper for the vertx `HttpClientRequest`. It simplifies making client libraries for json based REST APIs. It also allows you to template requests and also has a plugin interface to extend the request and response handling behavior of the client.


## vertx-json-http-request-client

This is the base client. Does not include any plugins.

## vertx-json-http-request-plugins

This includes some basic plugins for parameterizing and prefixing urls and logging requests and responses. 
