# OpenNLP

## Introduction

The Apache OpenNLP library is a machine learning based toolkit for the processing of natural language text.

https://opennlp.apache.org/

## Method
    person :en-ner-person.bin
    location : en-ner-location.bin
    date : en-ner-date.bin
    time : en-ner-time.bin
    money : en-ner-money.bin
    organization :en-ner-organization.bin
    percentage : en-ner-percentage.bin

## Usage

<pre><code>java -jar NLP-0.0.1-SNAPSHOT.jar 
--method en-ner-organization.bin 
--text "Today is Friday. John was born on November 2019.John has planned a trip costs 500 hundred dollars to Colorado."  </code></pre>

