package com.technosudo;

import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;

public class SparqlRunner {
    public static void runQueries(Repository repo) {
        try (RepositoryConnection conn = repo.getConnection()) {
            runQuery(conn, "SELECT ?name ?rating WHERE { ?item a <http://example.org/dreams#DreamOffer> ; <http://example.org/dreams#name> ?name ; <http://example.org/dreams#rating> ?rating . FILTER(?rating >= 0.0) }");
            runQuery(conn, "SELECT ?name WHERE { ?item a <http://example.org/dreams#DreamOffer> ; <http://example.org/dreams#name> ?name ; <http://example.org/dreams#price> ?price . FILTER(?price < 1000) }");
            runQuery(conn, "SELECT ?name ?qty WHERE { ?item a <http://example.org/dreams#DreamOffer> ; <http://example.org/dreams#name> ?name ; <http://example.org/dreams#quantity> ?qty . FILTER(?qty > 1000) }");
            runQuery(conn, "SELECT ?name WHERE { ?item a <http://example.org/dreams#DreamOffer> ; <http://example.org/dreams#name> ?name ; <http://example.org/dreams#currency> 'USD' }");
            runQuery(conn, "SELECT ?name WHERE { { ?item <http://example.org/dreams#quantity> 0 ; <http://example.org/dreams#name> ?name } UNION { ?item <http://example.org/dreams#rating> 0.0 ; <http://example.org/dreams#name> ?name } }");
        }
    }

    private static void runQuery(RepositoryConnection conn, String queryString) {
        TupleQuery query = conn.prepareTupleQuery(queryString);
        try (TupleQueryResult result = query.evaluate()) {
            result.getBindingNames().forEach(name -> System.out.print(name + "\t"));
            System.out.println();
            while (result.hasNext()) {
                BindingSet bs = result.next();
                bs.getBindingNames().forEach(name -> System.out.print(bs.getValue(name) + "\t"));
                System.out.println();
            }
        }
    }
}
