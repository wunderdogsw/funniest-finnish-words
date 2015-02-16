#!/bin/bash

DOWNLOAD_URL="http://www.lonnrot.net/kirjat/0460.zip"

wget $DOWNLOAD_URL

unzip 0460.zip

iconv -f ISO-8859-1 -t UTF-8 0460.txt > alastalon_salissa.txt

lein run "alastalon_salissa.txt"
