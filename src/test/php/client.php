<?php

$addr = '127.0.0.1';
$port = 8888;

function doRequest($client) {
    fwrite($client, "/path/to/some/document.pdf\x00");
    echo stream_get_contents($client) . "\n";
}

for ($i = 0; $i < 100; $i++) {
    $client1 = stream_socket_client("tcp://$addr:$port", $errno, $errorMessage);
    $client2 = stream_socket_client("tcp://$addr:$port", $errno, $errorMessage);
    $client3 = stream_socket_client("tcp://$addr:$port", $errno, $errorMessage);
    $client4 = stream_socket_client("tcp://$addr:$port", $errno, $errorMessage);

    doRequest($client1);
    doRequest($client2);
    doRequest($client3);
    doRequest($client4);

    fclose($client1);
    fclose($client2);
    fclose($client3);
    fclose($client4);
}