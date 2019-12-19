var http = require('http');
var handleRequest = function (request, response) {
    console.log('Received request for URL: ' + request.url);
    var ip = require('ip');
    var myip = ip.address();
    response.writeHead(200);
    response.end('Hello World!' + myip);
};
var www = http.createServer(handleRequest);
www.listen(7000);
