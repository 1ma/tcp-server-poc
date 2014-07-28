<?php

$addr = '127.0.0.1';
$port = 8888;

for ($i = 0; $i < 10000; $i++) {
    $client = stream_socket_client("tcp://$addr:$port", $errno, $errorMessage);

    if (false === $client) {
        throw new RuntimeException("Failed to connect: $errno -> $errorMessage");
    }

    fwrite($client, "/path/to/some/document.pdf\x00");
    echo "$i: " . stream_get_contents($client) . "\n";
    fclose($client);
}