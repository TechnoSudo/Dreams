package com.technosudo;

import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.sail.memory.MemoryStore;

import java.io.File;
import java.io.FileInputStream;

public class TripleStoreLoader {
    public static Repository loadRepository(String ontologyPath, String instancePath) throws Exception {
        Repository repo = new SailRepository(new MemoryStore());
        repo.init();

        try (RepositoryConnection conn = repo.getConnection()) {
            Rio.parse(new FileInputStream(new File(ontologyPath)), "", RDFFormat.TURTLE)
                    .forEach(conn::add);
            Rio.parse(new FileInputStream(new File(instancePath)), "", RDFFormat.TURTLE)
                    .forEach(conn::add);
        }
        return repo;
    }
}
