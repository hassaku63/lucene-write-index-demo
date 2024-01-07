package net.hassaku63.hellolucene;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.Files;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

@Command(
    name = "write",
    description = "Lucene Sample write application"
)
public class TextIndexWriter implements Runnable {
    @Option(
        names = {"-i", "--input"},
        description = "Input file path"
    )
    String inputFilePath;

    @Option(
        names = {"-t", "--title"},
        description = "Title of input file. Default is input file name"
    )
    String title;

    @Option(
        names = {"-o", "--output"},
        description = "Output file path"
    )
    String outputFilePath;

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

        BasicFileAttributes attr = null;
        try {
            attr = Files.readAttributes(inputPath, BasicFileAttributes.class);
        } catch (Exception e) {
            System.out.println("Failed to read input file.");
            System.exit(1);
        }

        String fileName = inputPath.getFileName().toString();
        if (title == null) {
            title = fileName;
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
        TextField titleField = new TextField("title", title, Field.Store.YES);
        TextField hideenTitleField = new TextField("title_no_store", title, Field.Store.NO);
        TextField fileNameField = new TextField("file_name", fileName, Field.Store.YES);
        TextField contentField = new TextField("content", inputText, Field.Store.YES);
        TextField lastModifiedField = new TextField("last_modified_time", attr.lastModifiedTime().toString(), Field.Store.YES);
        LongField lastModifiedMillisField = new LongField("last_modified_time_millis", attr.lastModifiedTime().toMillis(), Field.Store.YES);
        LongField sizeField = new LongField("size", attr.size(), Field.Store.YES);
        Document doc = new Document();
        doc.add(titleField);
        doc.add(hideenTitleField);
        doc.add(fileNameField);
        doc.add(contentField);
        doc.add(lastModifiedField);
        doc.add(lastModifiedMillisField);
        doc.add(sizeField);

        // write to lucene index
        IndexWriter writer = null;
        try {
            writer = new IndexWriter(dir, iwc);
            writer.addDocument(doc);
            writer.commit();
            writer.close();
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