import java.io.*;
import java.util.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDFS;

public class Simpsons {
  private Model model;

  public void addStatement(String s, String p, String o) {
    Resource subject = model.createResource(s);
    Property predicate = model.createProperty(p);
    RDFNode object = model.createResource(o);
    Statement stmt = model.createStatement(subject, predicate, object);
    model.add(stmt);
  }

  public void createModel(String url) {
    model = modelFactory.createDefaultModel();
    model.read(url);

    String rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    String xsd = "http://www.w3.org/2001/XMLSchema#";

    String foaf = "http://xmlns.com/foaf/0.1/";
    String fam = "http://www.ifi.uio.no/IN3060/family#";
    String sim = "http://www.ifi.uio.no/IN3060/simpsons#";

    addStatement(sim+"Maggie", rdf+"type", foaf+"Person");
    addStatement(sim+"Maggie", foaf+"name", "Maggie Simpson");
    addStatement(sim+"Maggie", foaf+"age", "1"^^xsd:int);

    addStatement(sim+"Mona", rdf+"type", foaf+"Person");
    addStatement(sim+"Mona", foaf+"name", "Mona Simpson");
    addStatement(sim+"Mona", foaf+"age", "70"^^xsd:int);

    addStatement(sim+"Abraham", rdf+"type", foaf+"Person");
    addStatement(sim+"Abraham", foaf+"name", "Abraham Simpson");
    addStatement(sim+"Abraham", foaf+"age", "78"^^xsd:int);

    addStatement(sim+"Abraham", fam+"hasSpouse", sim+"Mona");
    addStatement(sim+"Mona", fam+"hasSpouse", sim+"Abraham");

    addStatement(sim+"Herb", rdf+"type", foaf+"Person");
    addStatement(sim+"Herb", fam+"hasFather", sim+"_:");


    StmtIterator it = model.listStatements();
    while (it.hasNext()) {
      Statement stmt = it.nextStatement();
      RDFNode object = stmt.getObject();
      if ((object instanceof Integer) && (object <2)) {
        addStatement(stmt.getSubject(), rdf+"type", fam+"Infant");
      } else if ((object instanceof Integer) && (object <18)) {
        addStatement(stmt.getSubject(), rdf+"type", fam+"Minor");
      } else if((object instanceof Integer) && (object <70)) {
        addStatement(stmt.getSubject(), rdf+"type", fam+"Old");
      }
    }

  }


  public void writeModel(String filename) {
    FileWriter out = new FileWriter(filename);
    try {
      model.write(out, "TTL");
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
  
  public static void main(String[] args) {
	    String urlIn = args[0];
	    String fileOut = args[1];
	    createModel(urlIn);
	    writeModel(fileOut);
	  }

}
