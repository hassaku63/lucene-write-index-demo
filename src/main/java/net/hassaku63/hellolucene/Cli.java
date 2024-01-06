package net.hassaku63.hellolucene;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.PrintStream;
import java.nio.file.Files;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

@Command(
    name = "hellolucene",
    description = "Hello Lucene Sample Application",
    mixinStandardHelpOptions = true,
    version = "0.1.0"
)
public class Cli implements Runnable {
    @Option(
        names = {"-i", "--input"},
        description = "Input file path"
    )
    private String inputFilePath;

    @Option(
        names = {"-t", "--title"},
        description = "Title of input file. Default is input file name"
    )
    private String title;

    @Option(
        names = {"-o", "--output"},
        description = "Output file path"
    )
    private String outputFilePath;

    @Override
    public void run() {
        // check file path exists
        Path inputPath = Paths.get(inputFilePath);
        if (!Files.isReadable(inputPath)) {
            System.out.println("Input file path not readable.");
            System.exit(1);
        }

        // read string from input file
        String inputText = null;
        try {
            inputText = Files.readString(inputPath);
        } catch (Exception e) {
            System.out.println("Failed to read input file.");
            System.exit(1);
        }

        // set title
        if (title == null) {
            title = inputPath.getFileName().toString();
        }

        // create lucene index
        FSDirectory dir = null;
        try {
            dir = FSDirectory.open(Paths.get(outputFilePath));
        } catch (Exception e) {
            System.out.println("Failed to create lucene index.");
            System.exit(1);
        }

        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);

        // create document
        TextField titleField = new TextField("title", title, Field.Store.NO);
        TextField contentField = new TextField("content", inputText, Field.Store.YES);
        Document doc = new Document();
        doc.add(titleField);
        doc.add(contentField);

        // write to lucene index
        IndexWriter writer = null;
        try {
            writer = new IndexWriter(dir, iwc);
            writer.addDocument(doc);
            writer.commit();
        } catch (Exception e) {
            System.out.println("Failed to write lucene index.");
            System.exit(1);
        }

        try {
            dir.close();
        } catch (Exception e) {
            System.out.println("Failed to close lucene index.");
            System.exit(1);
        }
    }
}