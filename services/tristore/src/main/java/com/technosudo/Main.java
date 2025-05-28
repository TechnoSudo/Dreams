package com.technosudo;

public class Main {
    public static void main(String[] args) {
        Repository repo = TripleStoreLoader.loadRepository("ontology.ttl", "ontology.ttl"); // replace with correct paths if split
        SparqlRunner.runQueries(repo);
    }
}