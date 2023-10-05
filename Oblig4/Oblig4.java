import java.io.*;
import java.util.*;
import com.hp.hpl.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDFS;

class Oblig4 {
  private Model model;

  public void createModel(String url) {
    model = modelFactory.createDefaultModel();
    model.read(url);
  }

  public static main void(String[] args) {
    String urlIn = args[0];
    String sparql = args[1];
    String outFile = args[2];
    String url2 = "https://www.uio.no/studier/emner/matnat/ifi/IN3060/v21/obliger/simpsons.ttl";

    Oblig4 o = new Oblig4();
    Model schema = o.createModel(urlIn);
    Model model = o.createModel(url2);
    Model combinedModel = modelFactory.createRDFSModel(Model schema, Model model);

    Query query = QueryFactory.create(sparql);
    QueryExecution ex = QueryExecutionFactory.create(query, combinedModelmodel);
    Model res = ex.execConstruct();

    FileWriter out = new FileWriter(outFile);
    try {
      res.write(out, "TURTLE");
    }
    finally {
      try {
        out.close();
      }
      catch (Exception e) {
        //
      }
    }
  }
}
